package org.example.dao;

import org.example.entity.Resident;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ResidentDAO extends GenericDAO<Resident> {
    public ResidentDAO() {
        super(Resident.class);
    }

    public List<Resident> findByApartment(Long apartmentId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Resident> query = session.createQuery(
                    "FROM Resident r WHERE r.apartment.id = :apartmentId",
                    Resident.class
            );
            query.setParameter("apartmentId", apartmentId);
            List<Resident> residents = query.getResultList();
            transaction.commit();
            return residents;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding residents by apartment", e);
        }
    }

    public List<Resident> findByBuilding(Long buildingId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Resident> query = session.createQuery(
                    "FROM Resident r JOIN r.apartment a WHERE a.building.id = :buildingId",
                    Resident.class
            );
            query.setParameter("buildingId", buildingId);
            List<Resident> residents = query.getResultList();
            transaction.commit();
            return residents;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding residents by building", e);
        }
    }

    public List<Resident> findAllSortedByName() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Resident> query = session.createQuery(
                    "FROM Resident r ORDER BY r.firstName, r.lastName",
                    Resident.class
            );
            List<Resident> residents = query.getResultList();
            transaction.commit();
            return residents;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding residents sorted by name", e);
        }
    }

    public List<Resident> findAllSortedByAge() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Resident> query = session.createQuery(
                    "FROM Resident r ORDER BY r.birthDate DESC",
                    Resident.class
            );
            List<Resident> residents = query.getResultList();
            transaction.commit();
            return residents;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding residents sorted by age", e);
        }
    }
}
