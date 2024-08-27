package org.example.repository.hibernate;

import org.example.model.Post;
import org.example.model.Writer;
import org.example.utils.hiber.HibernateWorker;
import org.example.repository.PostRepository;
import org.hibernate.Session;

import java.util.Collections;
import java.util.List;

public class PostRepositoryHiber implements PostRepository {
    @Override
    public List<Post> getByWriterId(Long writerId) {
        try (Session session = HibernateWorker.getSession()) {
            Writer dummyWriter = session.load(Writer.class, writerId);
            List<Post> posts = dummyWriter.getPosts();
            posts = (posts == null || posts.isEmpty())
                    ? Collections.emptyList()
                    : posts;
            return posts;
        }
    }

    @Override
    public Long save(Post post, Long writerId) {
        try (Session session = HibernateWorker.getSession()) {
            session.beginTransaction();
            Writer dummyWriter = session.load(Writer.class, writerId);
            Long id = null;
            if (post.getId() != null) {
                Post current = session.get(Post.class, post.getId());
                current.setStatus(post.getStatus());
                current.setContent(post.getContent());
                current.setLabels(post.getLabels());
                id = post.getId();
            } else {
                dummyWriter.addPost(post);
                id = (Long) session.save(post);
            }
            session.getTransaction().commit();
            return id;
        }
    }

    @Override
    public void delete(long id) {
        try (Session session = HibernateWorker.getSession()) {
            session.beginTransaction();
            Post currentPost = session.get(Post.class, id);
            session.delete(currentPost);
            session.getTransaction().commit();
        }
    }

    @Override
    public Post get(Long postId) {
        try (Session session = HibernateWorker.getSession()) {
            return session.get(Post.class, postId);
        }
    }
}
