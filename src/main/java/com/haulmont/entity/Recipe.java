package com.haulmont.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table
public class Recipe{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String description;
    @ManyToOne(optional = false, targetEntity = Patient.class, cascade = CascadeType.REFRESH)
    @JoinColumn
    private Patient patient;
    @ManyToOne(optional = false, targetEntity = Doctor.class, cascade = CascadeType.REFRESH)
    @JoinColumn
    private Doctor doctor;
    @Column
    private Date createDate;
    @Column
    private Date duration;
    @Column
    private Priority priority;



    public Recipe() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(id, recipe.id) &&
                Objects.equals(description, recipe.description) &&
                Objects.equals(patient, recipe.patient) &&
                Objects.equals(doctor, recipe.doctor) &&
                Objects.equals(createDate, recipe.createDate) &&
                Objects.equals(duration, recipe.duration) &&
                priority == recipe.priority;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, description, patient, doctor, createDate, duration, priority);
    }

    //Делал для фильтрации по комбобоксу, не успел закончить
    @Override
    public String toString() {
        return patient.toString();
    }

    public enum Priority{
        SLOW, NORMAL, FAST
    }
}