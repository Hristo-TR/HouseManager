package org.example.dao;

import org.example.entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.List;

public class PaymentDAO extends GenericDAO<Payment> {
    public PaymentDAO() {
        super(Payment.class);
    }

    public List<Payment> findByApartment(Long apartmentId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Payment> query = session.createQuery(
                    "FROM Payment p WHERE p.apartment.id = :apartmentId ORDER BY p.year DESC, p.month DESC",
                    Payment.class
            );
            query.setParameter("apartmentId", apartmentId);
            List<Payment> payments = query.getResultList();
            transaction.commit();
            return payments;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding payments by apartment", e);
        }
    }

    public List<Payment> findByBuilding(Long buildingId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Payment> query = session.createQuery(
                    "FROM Payment p JOIN p.apartment a WHERE a.building.id = :buildingId",
                    Payment.class
            );
            query.setParameter("buildingId", buildingId);
            List<Payment> payments = query.getResultList();
            transaction.commit();
            return payments;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding payments by building", e);
        }
    }

    public List<Payment> findByCompany(Long companyId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Payment> query = session.createQuery(
                    "FROM Payment p JOIN p.apartment a JOIN a.building b WHERE b.company.id = :companyId",
                    Payment.class
            );
            query.setParameter("companyId", companyId);
            List<Payment> payments = query.getResultList();
            transaction.commit();
            return payments;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding payments by company", e);
        }
    }

    public List<Payment> findByEmployee(Long employeeId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Payment> query = session.createQuery(
                    "FROM Payment p JOIN p.apartment a JOIN a.building b WHERE b.employee.id = :employeeId",
                    Payment.class
            );
            query.setParameter("employeeId", employeeId);
            List<Payment> payments = query.getResultList();
            transaction.commit();
            return payments;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding payments by employee", e);
        }
    }

    public BigDecimal getTotalAmountByApartment(Long apartmentId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<BigDecimal> query = session.createQuery(
                    "SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.apartment.id = :apartmentId",
                    BigDecimal.class
            );
            query.setParameter("apartmentId", apartmentId);
            BigDecimal result = query.uniqueResult();
            transaction.commit();
            return result != null ? result : BigDecimal.ZERO;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error calculating total amount by apartment", e);
        }
    }

    public BigDecimal getTotalAmountByBuilding(Long buildingId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<BigDecimal> query = session.createQuery(
                    "SELECT COALESCE(SUM(p.amount), 0) FROM Payment p JOIN p.apartment a WHERE a.building.id = :buildingId",
                    BigDecimal.class
            );
            query.setParameter("buildingId", buildingId);
            BigDecimal result = query.uniqueResult();
            transaction.commit();
            return result != null ? result : BigDecimal.ZERO;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error calculating total amount by building", e);
        }
    }

    public BigDecimal getTotalAmountByCompany(Long companyId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<BigDecimal> query = session.createQuery(
                    "SELECT COALESCE(SUM(p.amount), 0) FROM Payment p JOIN p.apartment a JOIN a.building b WHERE b.company.id = :companyId",
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
            throw new RuntimeException("Error calculating total amount by company", e);
        }
    }

    public BigDecimal getTotalAmountByEmployee(Long employeeId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<BigDecimal> query = session.createQuery(
                    "SELECT COALESCE(SUM(p.amount), 0) FROM Payment p JOIN p.apartment a JOIN a.building b WHERE b.employee.id = :employeeId",
                    BigDecimal.class
            );
            query.setParameter("employeeId", employeeId);
            BigDecimal result = query.uniqueResult();
            transaction.commit();
            return result != null ? result : BigDecimal.ZERO;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error calculating total amount by employee", e);
        }
    }
}
