package com.hms.dao;


import model.Doctor;
import com.hms.util.HibernateUtil;
import model.Doctor;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DoctorDAO {
    public static void saveDoctor(Doctor doctor) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(doctor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
