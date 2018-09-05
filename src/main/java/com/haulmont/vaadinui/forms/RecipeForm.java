package com.haulmont.vaadinui.forms;

import com.haulmont.entity.Doctor;
import com.haulmont.entity.Patient;
import com.haulmont.entity.Recipe;
import com.haulmont.service.DoctorService;
import com.haulmont.service.PatientService;
import com.haulmont.service.RecipeService;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.LocalDateTimeToDateConverter;
import com.vaadin.ui.*;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class RecipeForm {

    private RecipeService rSer = new RecipeService();
    private Recipe recipe = new Recipe();
    private PatientService pS = new PatientService();
    private DoctorService dS = new DoctorService();
    private Binder<Recipe> rBinder = new Binder<>(Recipe.class);

    public Window recipeAdd(Grid grid){
        Window window = new Window("Редактировать рецепт");
        window.center();
        window.setModal(true);
        window.setWidth("500");
        window.setHeight("500");

        VerticalLayout addRecipe = new VerticalLayout();
        addRecipe.setSizeFull();
        addRecipe.setMargin(true);

        TextField descr = new TextField("Описание");
        ComboBox<Patient> patientB = new ComboBox<>("Пациенты");
        ComboBox<Doctor> doctorB = new ComboBox<>("Доктора");
        DateTimeField dateCreate = new DateTimeField("Дата создания");
        DateTimeField duration = new DateTimeField("Дата окончания");
        NativeSelect<Recipe.Priority> prior = new NativeSelect<>();

        rBinder.forField(descr).asRequired("Поле не может быть пустым")
                .bind(Recipe::getDescription, Recipe::setDescription);
        rBinder.forField(patientB).asRequired("Поле не может быть пустым")
                .bind(Recipe::getPatient, Recipe::setPatient);
        rBinder.forField(doctorB).asRequired("Поле не может быть пустым")
                .bind(Recipe::getDoctor, Recipe::setDoctor);
        rBinder.forField(dateCreate).asRequired("Поле не может быть пустым")
                .withConverter(new LocalDateTimeToDateConverter(ZoneId.systemDefault()))
                .bind(Recipe::getCreateDate, Recipe::setCreateDate);
        rBinder.forField(duration).asRequired("Поле не может быть пустым")
                .withConverter(new LocalDateTimeToDateConverter(ZoneId.systemDefault()))
                .bind(Recipe::getDuration, Recipe::setDuration);
        rBinder.forField(prior).asRequired("Поле не может быть пустым")
                .bind(Recipe::getPriority, Recipe::setPriority);



        patientB.setItems(pS.readAll());
        doctorB.setItems(dS.readAll());
        prior.setItems(Recipe.Priority.values());

        HorizontalLayout descript = new HorizontalLayout(descr);
        HorizontalLayout patdoc = new HorizontalLayout(patientB, doctorB);
        HorizontalLayout dates = new HorizontalLayout(dateCreate, duration);
        HorizontalLayout prio = new HorizontalLayout(prior);

        Button add = new Button("ОК");
        Button close = new Button("Отменить");

        HorizontalLayout buttons = new HorizontalLayout(add, close);
        add.addClickListener(e -> {

            try {
                rBinder.writeBean(recipe);
                rSer.create(recipe);
                updateRecipeList(grid);
                window.close();
            } catch (ValidationException e1) {
                Notification.show("Заполните все поля правильно");
            }

        });
        close.addClickListener(e -> window.close());

        addRecipe.addComponents(descript, patdoc, dates, prio, buttons);

        window.setContent(addRecipe);

        return window;
    }

    private void updateRecipeList(Grid grid){
        List<Recipe> recipes = rSer.readAll();
        grid.setItems(recipes);
    }


    public Window recipeUpdate(Grid grid){
        Window window = new Window("Редактировать рецепт");
        window.center();
        window.setModal(true);
        window.setWidth("500");
        window.setHeight("500");

        VerticalLayout updateRec = new VerticalLayout();
        updateRec.setSizeFull();
        updateRec.setMargin(true);

        TextField descr = new TextField("Описание");
        ComboBox<Patient> patientB = new ComboBox<>("Пациенты");
        ComboBox<Doctor> doctorB = new ComboBox<>("Доктора");
        DateTimeField dateCreate = new DateTimeField("Дата создания");
        DateTimeField duration = new DateTimeField("Дата окончания");
        NativeSelect<Recipe.Priority> prior = new NativeSelect<>();

        rBinder.forField(descr).asRequired("Поле не может быть пустым")
                .bind(Recipe::getDescription, Recipe::setDescription);
        rBinder.forField(patientB).asRequired("Поле не может быть пустым")
                .bind(Recipe::getPatient, Recipe::setPatient);
        rBinder.forField(doctorB).asRequired("Поле не может быть пустым")
                .bind(Recipe::getDoctor, Recipe::setDoctor);
        rBinder.forField(dateCreate).asRequired("Поле не может быть пустым")
                .withConverter(new LocalDateTimeToDateConverter(ZoneId.systemDefault()))
                .bind(Recipe::getCreateDate, Recipe::setCreateDate);
        rBinder.forField(duration).asRequired("Поле не может быть пустым")
                .withConverter(new LocalDateTimeToDateConverter(ZoneId.systemDefault()))
                .bind(Recipe::getDuration, Recipe::setDuration);
        rBinder.forField(prior).asRequired("Поле не может быть пустым")
                .bind(Recipe::getPriority, Recipe::setPriority);

        patientB.setItems(pS.readAll());
        doctorB.setItems(dS.readAll());
        prior.setItems(Recipe.Priority.values());

        HorizontalLayout descript = new HorizontalLayout(descr);
        HorizontalLayout patdoc = new HorizontalLayout(patientB, doctorB);
        HorizontalLayout dates = new HorizontalLayout(dateCreate, duration);
        HorizontalLayout prio = new HorizontalLayout(prior);

        Button add = new Button("ОК");
        Button close = new Button("Отменить");

        HorizontalLayout buttons = new HorizontalLayout(add, close);
        add.addClickListener(e -> {

            try {
                Recipe recipe = (Recipe)grid.getSelectedItems().toArray()[0];
                rBinder.writeBean(recipe);
                rSer.update(recipe);
                updateRecipeList(grid);
                window.close();

            } catch (ValidationException e1) {
                Notification.show("Заполните все поля правильно");
            }

        });
        close.addClickListener(e -> window.close());

        updateRec.addComponents(descript, patdoc, dates, prio, buttons);

        window.setContent(updateRec);

        return window;
    }


    public void deleteRecipe(Grid grid){
        Recipe recipe = (Recipe)grid.getSelectedItems().toArray()[0];
        rSer.delete(recipe);
        updateRecipeList(grid);
    }

}
