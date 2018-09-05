package com.haulmont.vaadinui.forms;

import com.haulmont.entity.Doctor;
import com.haulmont.entity.Patient;
import com.haulmont.service.PatientService;
import com.vaadin.data.*;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import javax.persistence.PersistenceException;
import java.util.List;

public class PatientForm {

    private PatientService pSer = new PatientService();
    private Patient patient = new Patient();
    private Binder<Patient> pBinder = new Binder<>(Patient.class);

    public Window addPatient(Grid grid){

        Window window = new Window("Окно добавления пациента");
        window.center();
        window.setModal(true);
        window.setWidth("650");
        window.setHeight("300");

        VerticalLayout add = new VerticalLayout();
        add.setSizeFull();
        add.setMargin(true);

        TextField lName = new TextField("Фамилия");
        TextField fName = new TextField("Имя");
        TextField mName = new TextField("Отчество");
        TextField pNumber = new TextField("Мобильный телефон");

        pBinder.forField(lName).asRequired("Поле не должно быть пустым")
                .withValidator(new RegexpValidator("Лишь буквы, без цифр и разных символов", "[' 'а-яА-Яa-zA-ZЁё]{1,100}", true))
                .withValidator(new StringLengthValidator("Минимум 3 символа", 3, 12))
                .bind(Patient::getLn, Patient::setLn);
        pBinder.forField(fName).asRequired("Поле не должно быть пустым")
                .withValidator(new RegexpValidator("Лишь буквы, без цифр и разных символов", "[' 'а-яА-Яa-zA-ZЁё]{1,100}", true))
                .withValidator(new StringLengthValidator("Минимум 3 символа", 3, 12))
                .bind(Patient::getFn, Patient::setFn);
        pBinder.forField(mName).asRequired("Поле не должно быть пустым")
                .withValidator(new RegexpValidator("Лишь буквы, без цифр и разных символов", "[' 'а-яА-Яa-zA-ZЁё]{1,100}", true))
                .withValidator(new StringLengthValidator("Минимум 3 символа", 3, 12))
                .bind(Patient::getMn, Patient::setMn);
        pBinder.forField(pNumber).asRequired("Поле не должно быть пустым")
                .withValidator(str -> str.length() == 11, "начиная с 8, только 11 цифр без + и прочих знаков")
                .withConverter(new StringToLongConverter("Только цифры"))
                .bind(Patient::getPhone, Patient::setPhone);

        Button save = new Button("ОК");
        Button cancel = new Button("Отменить");

        VerticalLayout doctorWindow = new VerticalLayout();
        doctorWindow.setMargin(true);
        doctorWindow.setSizeFull();

        HorizontalLayout names = new HorizontalLayout(lName, fName, mName);
        HorizontalLayout pnumber = new HorizontalLayout(pNumber);
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);

        save.addClickListener(e -> {

            try {
                pBinder.writeBean(patient);
                pBinder.writeBean(patient);
                pSer.create(patient);
                updatePatientsList(grid);
                window.close();
            } catch (ValidationException e1) {
                Notification.show("Заполните все поля правильно");
            }

        });

        cancel.addClickListener(e -> window.close());

        add.addComponents(names, pnumber, buttons);

        window.setContent(add);

        return window;

    }

    private void updatePatientsList(Grid grid){
        List<Patient> patients = pSer.readAll();
        grid.setItems(patients);
    }

    public Window updatePatient(Grid grid){
        Window window = new Window("Окно редактирования доктора");
        window.center();
        window.setModal(true);
        window.setWidth("650");
        window.setHeight("300");

        VerticalLayout update = new VerticalLayout();
        update.setSizeFull();
        update.setMargin(true);

        TextField lName = new TextField("Фамилия");
        TextField fName = new TextField("Имя");
        TextField mName = new TextField("Отчество");
        TextField pNumber = new TextField("Мобильный телефон");

        pBinder.forField(lName).asRequired("Поле не должно быть пустым")
                .withValidator(new RegexpValidator("Лишь буквы, без цифр и разных символов", "[' 'а-яА-Яa-zA-ZЁё]{1,100}", true))
                .withValidator(new StringLengthValidator("Минимум 3 символа", 3, 12))
                .bind(Patient::getLn, Patient::setLn);
        pBinder.forField(fName).asRequired("Поле не должно быть пустым")
                .withValidator(new RegexpValidator("Лишь буквы, без цифр и разных символов", "[' 'а-яА-Яa-zA-ZЁё]{1,100}", true))
                .withValidator(new StringLengthValidator("Минимум 3 символа", 3, 12))
                .bind(Patient::getFn, Patient::setFn);
        pBinder.forField(mName).asRequired("Поле не должно быть пустым")
                .withValidator(new RegexpValidator("Лишь буквы, без цифр и разных символов", "[' 'а-яА-Яa-zA-ZЁё]{1,100}", true))
                .withValidator(new StringLengthValidator("Минимум 3 символа", 3, 12))
                .bind(Patient::getMn, Patient::setMn);
        pBinder.forField(pNumber).asRequired("Поле не должно быть пустым")
                .withValidator(str -> str.length() == 11, "начиная с 8, только 11 цифр без + и прочих знаков")
                .withConverter(new StringToLongConverter("Только цифры"))
                .bind(Patient::getPhone, Patient::setPhone);

        Button save = new Button("ОК");
        Button cancel = new Button("Отменить");

        VerticalLayout doctorWindow = new VerticalLayout();
        doctorWindow.setMargin(true);
        doctorWindow.setSizeFull();

        HorizontalLayout names = new HorizontalLayout(lName, fName, mName);
        HorizontalLayout pnumber = new HorizontalLayout(pNumber);
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);

        save.addClickListener(e -> {
            try {
                Patient patient = (Patient) grid.getSelectedItems().toArray()[0];
                pBinder.writeBean(patient);
                pSer.update(patient);
                window.close();
                updatePatientsList(grid);
            } catch (ValidationException e1) {
                Notification.show("Заполните все поля правильно");
            }


        });
        cancel.addClickListener(e -> window.close());

        update.addComponents(names, pnumber, buttons);
        window.setContent(update);

        return window;
    }

    public void deletePatient(Grid grid){
        try {
            Patient patient = (Patient) grid.getSelectedItems().toArray()[0];
            pSer.delete(patient);

            updatePatientsList(grid);
        }catch (PersistenceException ex){
            Notification.show("Нельзя удалить пациента, у которого есть рецепт");
        }
    }
}
