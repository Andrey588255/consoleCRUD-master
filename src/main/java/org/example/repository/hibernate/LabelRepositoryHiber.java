package org.example.repository.hibernate;

import org.example.model.Label;
import org.example.model.Post;
import org.example.utils.hiber.HibernateWorker;
import org.example.repository.LabelRepository;
import org.hibernate.Session;

import java.util.Collections;
import java.util.List;

public class LabelRepositoryHiber implements LabelRepository {
    @Override
    public List<Label> getByPostId(Long postId) {
        try (Session session = HibernateWorker.getSession()) {
            Post dummyPost = session.load(Post.class, postId);
            List<Label> labels = dummyPost.getLabels();
            labels = (labels == null || labels.isEmpty())
                    ? Collections.emptyList()
                    : labels;
            session.close();
            return labels;
        }
    }

    @Override
    public Long save(Label label, Long ownerId) {
        Long id = null;
        try (Session session = HibernateWorker.getSession()) {
            session.beginTransaction();
            Post dummyPost = session.load(Post.class, ownerId);
            dummyPost.addLabel(label);
            if (label.getId() != null) {
                session.saveOrUpdate(label);
            } else {
                id = (Long) session.save(label);
            }
            session.getTransaction().commit();
        }
        return id;
    }

    @Override
    public void delete(long id) {
        try (Session session = HibernateWorker.getSession()) {
            session.beginTransaction();
            Label forDelete = (Label) session.get(Label.class, id);
            session.delete(forDelete);
            session.getTransaction().commit();
        }

    }


    @Override
    public Label get(Long labelId) {
        return HibernateWorker.getSession().get(Label.class, labelId);
    }
}
