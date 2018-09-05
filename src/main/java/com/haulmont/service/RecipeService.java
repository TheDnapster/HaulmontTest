package com.haulmont.service;

import com.haulmont.daointerface.RecipeDAO;
import com.haulmont.entity.Recipe;
import com.haulmont.hibernateutils.SessionUtil;
import org.hibernate.Session;

import java.util.List;

public class RecipeService extends SessionUtil implements RecipeDAO {

    @Override
    public void create(Recipe entity) {
        openTransactionSession();

        Session session = getSession();
        session.save(entity);

        closeTransactionSesstion();
    }

    @Override
    public List<Recipe> readAll() {
        openTransactionSession();

        Session session = getSession();
        List<Recipe> entityList = session.createQuery("SELECT up FROM " + Recipe.class.getSimpleName() + " up").list();
        closeTransactionSesstion();

        return entityList;
    }

    @Override
    public List<Recipe> readByDesc(String desc) {
        openTransactionSession();

        Session session = getSession();
        List<Recipe> recipeList = session.createQuery("select d from Recipe d where d.description like '%" + desc + "%'", Recipe.class).list();

        closeTransactionSesstion();
        return recipeList;
    }

    @Override
    public List<Recipe> readByPatient(Recipe patient) {
        openTransactionSession();

        Session session = getSession();
        List<Recipe> patientList = session.createQuery("select p from Recipe p where p.patient.class like '%" + patient + "%'", Recipe.class).list();

        closeTransactionSesstion();
        return patientList;
    }


    @Override
    public void update(Recipe entity) {
        openTransactionSession();

        Session session = getSession();
        session.update(entity);

        closeTransactionSesstion();
    }

    @Override
    public void delete(Recipe recipe) {
        openTransactionSession();

        Session session = getSession();
        session.remove(recipe);

        closeTransactionSesstion();
    }
}
