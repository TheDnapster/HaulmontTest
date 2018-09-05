package com.haulmont.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;


@Entity
@Table
public class Patient{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String ln;
    @Column
    private String fn;
    @Column
    private String mn;
    @Column
    private long phone;

    public Patient() {
    }

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

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id) &&
                Objects.equals(ln, patient.ln) &&
                Objects.equals(fn, patient.fn) &&
                Objects.equals(mn, patient.mn) &&
                Objects.equals(phone, patient.phone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, ln, fn, mn, phone);
    }

    //Такой формат, как способ реализовать вывод "объекта" в рецепте.
    @Override
    public String toString() {
        return ln + " " + fn + " " + mn;
    }
}