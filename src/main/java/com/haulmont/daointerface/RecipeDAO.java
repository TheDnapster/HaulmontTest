package com.haulmont.daointerface;

import com.haulmont.entity.Recipe;

import java.util.List;

public interface RecipeDAO {
    void create(Recipe entity);
    List<Recipe> readAll();
    List<Recipe> readByDesc(String desc);
    List<Recipe> readByPatient(Recipe patient);
    void update(Recipe entity);
    void delete(Recipe recipe);
}
