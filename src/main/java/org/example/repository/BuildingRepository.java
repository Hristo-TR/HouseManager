package org.example.repository;

import org.example.entity.Building;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class BuildingRepository extends BaseRepository<Building> {
    public BuildingRepository() {
        super(Building.class);
    }

    public List<Building> findByCompany(Long companyId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Building> query = session.createQuery(
                    "FROM Building b WHERE b.company.id = :companyId",
                    Building.class
            );
            query.setParameter("companyId", companyId);
            List<Building> buildings = query.getResultList();
            transaction.commit();
            return buildings;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding buildings by company", e);
        }
    }

    public List<Building> findByEmployee(Long employeeId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Building> query = session.createQuery(
                    "FROM Building b WHERE b.employee.id = :employeeId",
                    Building.class
            );
            query.setParameter("employeeId", employeeId);
            List<Building> buildings = query.getResultList();
            transaction.commit();
            return buildings;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding buildings by employee", e);
        }
    }
}
