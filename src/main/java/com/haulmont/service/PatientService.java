package com.haulmont.service;

import com.haulmont.daointerface.PatientDAO;
import com.haulmont.entity.Patient;
import com.haulmont.hibernateutils.SessionUtil;
import org.hibernate.Session;
import java.util.List;

public class PatientService extends SessionUtil implements PatientDAO {

    @Override
    public void create(Patient entity) {
        openTransactionSession();

        Session session = getSession();
        session.save(entity);

        closeTransactionSesstion();
    }

    @Override
    public List<Patient> readAll() {
        openTransactionSession();

        Session session = getSession();
        List<Patient> patientList = session.createQuery("SELECT up FROM " + Patient.class.getSimpleName() + " up").list();

        closeTransactionSesstion();
        return patientList;
    }

    @Override
    public void update(Patient entity) {
        openTransactionSession();

        Session session = getSession();
        session.update(entity);

        closeTransactionSesstion();
    }

    @Override
    public void delete(Patient patient) {
        openTransactionSession();

        Session session = getSession();
        session.remove(patient);

        closeTransactionSesstion();
    }

}
