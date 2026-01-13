package org.example.repository;

import org.example.entity.Apartment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ApartmentRepository extends BaseRepository<Apartment> {
    public ApartmentRepository() {
        super(Apartment.class);
    }

    public List<Apartment> findByBuilding(Long buildingId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Apartment> query = session.createQuery(
                    "FROM Apartment a WHERE a.building.id = :buildingId",
                    Apartment.class
            );
            query.setParameter("buildingId", buildingId);
            List<Apartment> apartments = query.getResultList();
            transaction.commit();
            return apartments;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding apartments by building", e);
        }
    }

    public List<Apartment> findByOwner(Long ownerId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Apartment> query = session.createQuery(
                    "FROM Apartment a WHERE a.owner.id = :ownerId",
                    Apartment.class
            );
            query.setParameter("ownerId", ownerId);
            List<Apartment> apartments = query.getResultList();
            transaction.commit();
            return apartments;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding apartments by owner", e);
        }
    }
}
