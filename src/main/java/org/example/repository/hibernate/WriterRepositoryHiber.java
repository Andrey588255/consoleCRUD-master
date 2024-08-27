package org.example.repository.hibernate;

import org.example.model.Writer;
import org.example.utils.hiber.HibernateWorker;
import org.example.repository.WriterRepository;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

public class WriterRepositoryHiber implements WriterRepository {
    @Override
    @Deprecated
    public Long save(Writer object, Long ownerId) {
        throw new UnsupportedOperationException("Writer can not have the owner");
    }

    @Override
    public void delete(long id) {
        try (Session session = HibernateWorker.getSession()) {
            session.beginTransaction();
            Writer currentWriter = session.get(Writer.class, id);
            if (currentWriter != null) {
                session.delete(currentWriter);
            }
            session.getTransaction().commit();
        }
    }

    @Override
    public Writer get(Long writerId) {
        try (Session session = HibernateWorker.getSession()) {
            return session.get(Writer.class, writerId);
        }
    }

    @Override
    public void delete(Writer writer) {
        try (Session session = HibernateWorker.getSession()) {
            session.beginTransaction();
            session.delete(writer);
            session.getTransaction().commit();
        }
    }

    @Override
    public Long save(Writer writer) {
        try (Session session = HibernateWorker.getSession()) {
            session.beginTransaction();
            Long id = null;
            if (writer.getId() != null) {
                session.saveOrUpdate(writer);
            } else {
                id = (long) session.save(writer);
            }
            session.getTransaction().commit();
            return id;
        }
    }

    @Override
    public List<Writer> getAll() {
        try (Session session = HibernateWorker.getSession()) {
            Query query = session.createQuery("from Writer");
            List<Writer> writers = query.getResultList();
            writers = (writers == null || writers.isEmpty())
                    ? Collections.emptyList()
                    : writers;
            return writers;
        }
    }
}
