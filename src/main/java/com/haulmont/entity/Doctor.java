package com.haulmont.entity;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table
public class Doctor{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String fn;
    @Column
    private String mn;
    @Column
    private String ln;
    @Column
    private String specialty;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
    }

    public String getMn() {
        return mn;
    }

    public void setMn(String sn) {
        this.mn = sn;
    }

    public String getLn() {
        return ln;
    }

    public void setLn(String ln) {
        this.ln = ln;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(id, doctor.id) &&
                Objects.equals(fn, doctor.fn) &&
                Objects.equals(mn, doctor.mn) &&
                Objects.equals(ln, doctor.ln) &&
                Objects.equals(specialty, doctor.specialty);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, fn, mn, ln, specialty);
    }

    //Такой формат, как способ реализовать вывод "объекта" в рецепте.
    @Override
    public String toString() {
        return ln + " " + fn + " " + mn;
    }
}