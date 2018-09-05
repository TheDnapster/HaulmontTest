package com.haulmont.service;

import com.haulmont.daointerface.DoctorDAO;
import com.haulmont.entity.Doctor;
import com.haulmont.hibernateutils.SessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.List;

public class DoctorService extends SessionUtil implements DoctorDAO {

    @Override
    public void create(Doctor doctor) {
        openTransactionSession();

        Session session = getSession();
        session.save(doctor);

        closeTransactionSesstion();
    }

    @Override
    public List<Doctor> readAll() {
        //Открываем и сессию, и транзакцию через утилиту
        openTransactionSession();

        //sql запрос
        Session session = getSession();
        List<Doctor> doctorsList = session.createQuery("SELECT up FROM " + Doctor.class.getSimpleName() + " up").list();

        //Закрываем сессию и транзакцию, тоже удобно, это используется во всех методах CRUD операция и не только.
        closeTransactionSesstion();
        return doctorsList;
    }


    @Override
    public void update(Doctor doctor) {
        try{
            openTransactionSession();

            Session session = getSession();
            session.update(doctor);

            closeTransactionSesstion();
        }
        catch (HibernateException | NoResultException e){
            getTransaction().rollback();
        }
    }

    @Override
    public void delete(Doctor doctor) {
        openTransactionSession();

        Session session = getSession();
        session.remove(doctor);

        closeTransactionSesstion();
    }
}
