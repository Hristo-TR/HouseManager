package org.example.dao;

import org.example.entity.FeeConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class FeeConfigurationDAO extends GenericDAO<FeeConfiguration> {
    public FeeConfigurationDAO() {
        super(FeeConfiguration.class);
    }

    public FeeConfiguration findByBuilding(Long buildingId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<FeeConfiguration> query = session.createQuery(
                    "FROM FeeConfiguration fc WHERE fc.building.id = :buildingId",
                    FeeConfiguration.class
            );
            query.setParameter("buildingId", buildingId);
            FeeConfiguration feeConfig = query.uniqueResult();
            transaction.commit();
            return feeConfig;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding fee configuration by building", e);
        }
    }
}
