package com.haulmont.vaadinui.tables;

import com.haulmont.entity.Recipe;
import com.haulmont.service.RecipeService;
import com.haulmont.vaadinui.forms.RecipeForm;
import com.vaadin.ui.*;

import java.util.List;

public class RecipeTable {
    private RecipeService rSer = new RecipeService();
    private RecipeForm rform = new RecipeForm();
    private Grid<Recipe> recipeGrid = new Grid<>(Recipe.class);
    private List<Recipe> recipeList;

    public VerticalLayout recTable(){
        Label label = new Label("Рецепты");

        recipeGrid.removeAllColumns();
        recipeGrid.addColumn(Recipe::getDescription).setCaption("Описание");
        recipeGrid.addColumn(Recipe::getPatient).setCaption("Пациент");
        recipeGrid.addColumn(Recipe::getDoctor).setCaption("Доктор");
        recipeGrid.addColumn(Recipe::getCreateDate).setCaption("Дата создания");
        recipeGrid.addColumn(Recipe::getDuration).setCaption("Дата окончания");
        recipeGrid.addColumn(Recipe::getPriority).setCaption("Приоритет");
        recipeList = rSer.readAll();
        recipeGrid.setItems(recipeList);
        recipeGrid.setSizeFull();

        TextField filter1 = new TextField("Фильтр по описанию");
        filter1.addValueChangeListener(e -> {
            recipeList = rSer.readByDesc(filter1.getValue());
        });

//        Не успел додумать вовремя
//        ComboBox<Recipe> patient = new ComboBox<>();
//        patient.setItems(rSer.readAll());
//
//        patient.addValueChangeListener(e -> {
//           recipeList = rSer.readByPatient(patient.getValue());
//        });

        Button apply = new Button("Принять");
        apply.addClickListener(e -> recipeGrid.setItems(recipeList));

        Button add = new Button("Добавить");
        Button update = new Button("Редактировать");
        Button delete = new Button("Удалить");

        FormLayout filters = new FormLayout(filter1, apply);
        HorizontalLayout tools = new HorizontalLayout(add, update, delete);
        VerticalLayout recipes = new VerticalLayout(label, recipeGrid, tools, filters);
        recipes.setComponentAlignment(tools, Alignment.BOTTOM_CENTER);

        add.addClickListener(e -> tools.getUI().addWindow(rform.recipeAdd(recipeGrid)));
        update.addClickListener(e -> {
            if(!recipeGrid.getSelectedItems().isEmpty()) {
                tools.getUI().addWindow(rform.recipeUpdate(recipeGrid));
            }
            if(recipeGrid.getSelectedItems().isEmpty()){
                Notification.show("Сначала выберите рецепт");
            }
        });
        delete.addClickListener(e -> {
            if(!recipeGrid.getSelectedItems().isEmpty()) {
                rform.deleteRecipe(recipeGrid);
            }
            if(recipeGrid.getSelectedItems().isEmpty()){
                Notification.show("Выберите рецепт для удаления");
            }
        });

        return recipes;
    }
}
