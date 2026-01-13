package org.example.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public abstract class BaseRepository<T> {
    protected SessionFactory sessionFactory;
    private Class<T> entityClass;

    public BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.sessionFactory = org.example.config.HibernateConfig.getSessionFactory();
    }

    public T save(T entity) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error saving entity", e);
        }
    }

    public T update(T entity) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error updating entity", e);
        }
    }

    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            T entity = session.get(entityClass, id);
            if (entity != null) {
                session.remove(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting entity", e);
        }
    }

    public T findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            T entity = session.get(entityClass, id);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding entity by id", e);
        }
    }

    public List<T> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<T> query = session.createQuery("from " + entityClass.getSimpleName(), entityClass);
            List<T> entities = query.getResultList();
            transaction.commit();
            return entities;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            org.slf4j.LoggerFactory.getLogger(BaseRepository.class).error("Error finding all entities of type " + entityClass.getSimpleName(), e);
            throw new RuntimeException("Error finding all entities", e);
        }
    }
}
