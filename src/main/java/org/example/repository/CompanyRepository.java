package org.example.repository;

import org.example.entity.Company;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.List;

public class CompanyRepository extends BaseRepository<Company> {
    public CompanyRepository() {
        super(Company.class);
    }

    public List<Company> findAllSortedByRevenue() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<Company> companies = session.createQuery("FROM Company", Company.class).getResultList();
            companies.sort((c1, c2) -> {
                BigDecimal rev1 = getRevenueInSession(session, c1.getId());
                BigDecimal rev2 = getRevenueInSession(session, c2.getId());
                return rev2.compareTo(rev1);
            });
            transaction.commit();
            return companies;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding companies sorted by revenue", e);
        }
    }

    private BigDecimal getRevenueInSession(Session session, Long companyId) {
        Query<BigDecimal> query = session.createQuery(
                "SELECT COALESCE(SUM(p.amount), 0) FROM Payment p " +
                        "JOIN p.apartment a " +
                        "JOIN a.building b " +
                        "WHERE b.company.id = :companyId",
                BigDecimal.class
        );
        query.setParameter("companyId", companyId);
        BigDecimal result = query.uniqueResult();
        return result != null ? result : BigDecimal.ZERO;
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
