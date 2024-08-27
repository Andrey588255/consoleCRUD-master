package org.example.repository.jdbc;

import org.example.model.Post;
import org.example.model.Writer;
import org.example.repository.WriterRepository;
import org.example.utils.jdbc.JdbcWorker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WriterRepositoryJdbc implements WriterRepository {

    @Override
    public void delete(long id) {
        try {
            JdbcWorker.executeVoid("DELETE FROM writers WHERE id=?", id);
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

      @Override
      @Deprecated
    public Long save(Writer writer, Long ownerId) {
        throw new IllegalArgumentException("Writer can not have the owner, " +
                "operation with ownerId parameter is forbidden");
    }

     @Override
    public Writer get(Long writerId) {
        return JdbcWorker.executeGet("SELECT * FROM writers WHERE id=?", writerId, this::parse) ;
    }

    @Override
    public void delete(Writer writer) {
        delete(writer.getId());

    }

     @Override
    public Long save(Writer writer) {
        Long writerId = writer.getId();
        try {
            if (writerId == null) {
               writerId = JdbcWorker.executeSave("INSERT INTO writers (first_name, last_name) VALUES (?, ?);",
                       prepSt -> {
                   prepSt.setString(1, writer.getFirstName());
                   prepSt.setString(2, writer.getLastName());
                       });
            } else {
                Long finalWriterId = writerId;
                writerId = JdbcWorker.executeSave("UPDATE writers SET first_name=?, last_name=? WHERE id=?",
                        prepSt -> {
                    prepSt.setString(1, writer.getFirstName());
                    prepSt.setString(2, writer.getLastName());
                    prepSt.setLong(3, finalWriterId);

                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PostRepositoryJdbc postRepo = new PostRepositoryJdbc();
        for (Post post : writer.getPosts()) {
            postRepo.save(post, writerId);
        }
        return writerId;
    }

    @Override
    public List<Writer> getAll() {
        return JdbcWorker.executeGet("SELECT * FROM writers", null, this::parseList);
    }
    private Writer parse(ResultSet resultSet) throws SQLException {
        Writer currentWriter = null;
        if (resultSet.next()) {
            currentWriter = new Writer(
            resultSet.getLong("id"),
            resultSet.getString("first_name"),
            resultSet.getString("last_name"));
        currentWriter.setPosts(new PostRepositoryJdbc().getByWriterId(currentWriter.getId()));
        }
        return currentWriter;
    }

    private List<Writer> parseList(ResultSet resultSet) throws SQLException {
        List<Writer> writers = new ArrayList<>();
        PostRepositoryJdbc postRepo = new PostRepositoryJdbc();
        while (resultSet.next()) {
            Writer currentWriter = new Writer(
                    resultSet.getLong("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name;"));
            currentWriter.setPosts(postRepo.getByWriterId(currentWriter.getId()));
            writers.add(currentWriter);
        }
        return  writers;
    }
}