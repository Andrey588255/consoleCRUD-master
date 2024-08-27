package org.example.repository.jdbc;

import org.example.model.Label;
import org.example.model.Post;
import org.example.model.PostStatus;
import org.example.repository.PostRepository;
import org.example.utils.jdbc.JdbcWorker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PostRepositoryJdbc implements PostRepository {

     @Override
    public Long save(Post post, Long writerId) {
         Long post_Id = null;
         try {
             // Якщо додаємо новий пост і для нього не існує id
             if (post.getId() == null){
                 post_Id = JdbcWorker.executeSave("INSERT INTO posts (content, status, create_time, update_time, writer_id)" +
                         "VALUES (?, ?, ?, ?, ?)", prepSt -> {
                     prepSt.setString(1, post.getContent());
                     prepSt.setString(2, post.getStatus().name());
                     prepSt.setTimestamp(3, Timestamp.valueOf(post.getCreated()));
                     prepSt.setTimestamp(4, Timestamp.valueOf(post.getUpdated()));
                     prepSt.setLong(5, writerId);
                 });
             } else {
                 JdbcWorker.executeSave("UPDATE post SET content=?, status=?, update_time=? WHERE id=?", prepSt -> {
                     prepSt.setString(1, post.getContent());
                     prepSt.setString(2, post.getStatus().name());
                     prepSt.setTimestamp(3, Timestamp.valueOf(post.getUpdated()));
                      prepSt.setLong(4, post.getId());
                 });

             }
             // Записуємо в БД коментарі для посту
             LabelRepositoryJdbc labelRepo = new LabelRepositoryJdbc();
             for (Label label : post.getLabels()){
                 labelRepo.save(label, post_Id);
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }
        return post_Id;
    }

    @Override
    public void delete(long id) {
         try {
             JdbcWorker.executeVoid("DELETE from posts WHERE id=?", id);
         } catch (SQLException e) {
             e.printStackTrace();
         }
    }

      @Override
    public Post get(Long id) {
         Post currentPost = JdbcWorker.executeGet("SELECT * FROM posts WHERE writer_id=?", id, this::parse);
         if (currentPost != null) {
             currentPost.setLabels(new LabelRepositoryJdbc().getByPostId(id));
         }
        return currentPost;
    }

       @Override
    public List<Post> getByWriterId(Long id) {
        List<Post> result = JdbcWorker.executeGet("SELECT * FROM posts WHERE writer_id=?", id, this::parseList);
        try {
            fillPostsLabels(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    private List<Post> fillPostsLabels(List<Post> resultPosts) throws SQLException {
         if (resultPosts == null || resultPosts.isEmpty()) {
             return null;
         }
         LabelRepositoryJdbc labelRepository = new LabelRepositoryJdbc();
         List<Post> list = new ArrayList<>();
         for (Post post : resultPosts) {
             post.setLabels(labelRepository.getByPostId(post.getId()));
             list.add(post);
         }
         return list;
    }

    private PostStatus switchStatus(String statusFromDb){
         switch (statusFromDb) {
             case "ACTIVE":
                 return PostStatus.ACTIVE;
             case "DELETED":
                 return PostStatus.DELETED;
         }
         return PostStatus.UNDER_REVIEW;
    }

    private List<Post> parseList(ResultSet resultSet) {
         List<Post> posts = new ArrayList<>();
         try {
             while (resultSet.next()) {
                 Post currentPost = new Post(resultSet.getLong("id"), resultSet.getString("content"),
                         JdbcWorker.convertToLocalDateTime(resultSet.getTimestamp("update_time")));
                 currentPost.setStatus(switchStatus(resultSet.getString("status")));
                 posts.add(currentPost);

             }
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return  posts;
    }

    private Post parse(ResultSet resultSet) throws SQLException {
         if (resultSet.next()) {
             Post cerrentPost = new Post(resultSet.getLong("id"), resultSet.getString("content"),
                     JdbcWorker.convertToLocalDateTime(resultSet.getTimestamp("update_time")));
             cerrentPost.setStatus(switchStatus(resultSet.getString("status")));
             return cerrentPost;
         }
         return null;
     }
}