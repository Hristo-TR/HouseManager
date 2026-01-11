package org.example.dao;

import org.example.entity.Company;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.List;

public class CompanyDAO extends GenericDAO<Company> {
    public CompanyDAO() {
        super(Company.class);
    }

    public List<Company> findAllSortedByRevenue() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Company> query = session.createQuery(
                    "SELECT DISTINCT c FROM Company c " +
                            "LEFT JOIN c.buildings b " +
                            "LEFT JOIN b.apartments a " +
                            "LEFT JOIN a.payments p " +
                            "GROUP BY c.id " +
                            "ORDER BY COALESCE(SUM(p.amount), 0) DESC",
                    Company.class
            );
            List<Company> companies = query.getResultList();
            transaction.commit();
            return companies;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding companies sorted by revenue", e);
        }
    }

    public BigDecimal getTotalRevenue(Long companyId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<BigDecimal> query = session.createQuery(
                    "SELECT COALESCE(SUM(p.amount), 0) FROM Payment p " +
                            "JOIN p.apartment a " +
                            "JOIN a.building b " +
                            "WHERE b.company.id = :companyId",
                    BigDecimal.class
            );
            query.setParameter("companyId", companyId);
            BigDecimal result = query.uniqueResult();
            transaction.commit();
            return result != null ? result : BigDecimal.ZERO;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error calculating total revenue", e);
        }
    }
}
