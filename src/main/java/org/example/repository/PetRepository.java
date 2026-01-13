package org.example.repository;

import org.example.entity.Pet;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PetRepository extends BaseRepository<Pet> {
    public PetRepository() {
        super(Pet.class);
    }

    public List<Pet> findByApartment(Long apartmentId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Pet> query = session.createQuery(
                    "FROM Pet p WHERE p.apartment.id = :apartmentId",
                    Pet.class
            );
            query.setParameter("apartmentId", apartmentId);
            List<Pet> pets = query.getResultList();
            transaction.commit();
            return pets;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding pets by apartment", e);
        }
    }
}
