package com.haulmont.daointerface;

import com.haulmont.entity.Patient;

import java.util.List;

public interface PatientDAO {
    void create(Patient entity);
    List<Patient> readAll();
    void update(Patient entity);
    void delete(Patient patient);
}
