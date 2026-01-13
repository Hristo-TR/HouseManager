package org.example.repository;

import org.example.entity.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class EmployeeRepository extends BaseRepository<Employee> {
    public EmployeeRepository() {
        super(Employee.class);
    }

    public List<Employee> findByCompany(Long companyId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Employee> query = session.createQuery(
                    "FROM Employee e WHERE e.company.id = :companyId",
                    Employee.class
            );
            query.setParameter("companyId", companyId);
            List<Employee> employees = query.getResultList();
            transaction.commit();
            return employees;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding employees by company", e);
        }
    }

    public List<Employee> findAllSortedByName() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Employee> query = session.createQuery(
                    "FROM Employee e ORDER BY e.firstName, e.lastName",
                    Employee.class
            );
            List<Employee> employees = query.getResultList();
            transaction.commit();
            return employees;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding employees sorted by name", e);
        }
    }

    public List<Employee> findAllSortedByBuildingCount() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Employee> query = session.createQuery(
                    "SELECT e FROM Employee e " +
                            "LEFT JOIN e.buildings b " +
                            "GROUP BY e.id " +
                            "ORDER BY COUNT(b.id) ASC",
                    Employee.class
            );
            List<Employee> employees = query.getResultList();
            transaction.commit();
            return employees;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding employees sorted by building count", e);
        }
    }

    public Employee findEmployeeWithFewestBuildings(Long companyId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Employee> query = session.createQuery(
                    "SELECT e FROM Employee e " +
                            "LEFT JOIN e.buildings b " +
                            "WHERE e.company.id = :companyId " +
                            "GROUP BY e.id " +
                            "ORDER BY COUNT(b.id) ASC",
                    Employee.class
            );
            query.setParameter("companyId", companyId);
            query.setMaxResults(1);
            Employee employee = query.uniqueResult();
            transaction.commit();
            return employee;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding employee with fewest buildings", e);
        }
    }

    public List<Employee> findOtherEmployees(Long companyId, Long excludeEmployeeId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Employee> query = session.createQuery(
                    "FROM Employee e WHERE e.company.id = :companyId AND e.id != :excludeId",
                    Employee.class
            );
            query.setParameter("companyId", companyId);
            query.setParameter("excludeId", excludeEmployeeId);
            List<Employee> employees = query.getResultList();
            transaction.commit();
            return employees;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding other employees", e);
        }
    }
}
