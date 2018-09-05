package com.haulmont.daointerface;

import com.haulmont.entity.Doctor;

import java.util.List;

public interface DoctorDAO {
    void create(Doctor doctor);
    List<Doctor> readAll();
    void update(Doctor doctor);
    void delete(Doctor doctor);
}
