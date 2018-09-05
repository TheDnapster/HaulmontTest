package com.haulmont.vaadinui.forms;

import com.haulmont.entity.Doctor;
import com.haulmont.service.DoctorService;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import javax.persistence.PersistenceException;
import java.util.List;

public class DoctorForm {

    private DoctorService docSer = new DoctorService();
    private Doctor doctor = new Doctor();
    private Binder<Doctor> dBinder = new Binder<>(Doctor.class);

    public Window addDoctor(Grid grid){

        Window window = new Window("Окно добавления доктора");
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
        TextField spec = new TextField("Специальность");

        //Скромно валидируем поля.
        dBinder.forField(lName).asRequired("Поле не должно быть пустым")
                .withValidator(new RegexpValidator("Лишь буквы, без цифр и разных символов", "[' 'а-яА-Яa-zA-ZЁё]{1,100}", true))
                .withValidator(new StringLengthValidator("От двух до двадцати символов", 2, 20))
                .bind(Doctor::getLn, Doctor::setLn);
        dBinder.forField(fName).asRequired("Поле не должно быть пустым")
                .withValidator(new RegexpValidator("Лишь буквы, без цифр и разных символов", "[' 'а-яА-Яa-zA-ZЁё]{1,100}", true))
                .withValidator(new StringLengthValidator("От двух до двадцати символов", 2, 20))
                .bind(Doctor::getFn, Doctor::setFn);
        dBinder.forField(mName).asRequired("Поле не должно быть пустым")
                .withValidator(new RegexpValidator("Лишь буквы, без цифр и разных символов", "[' 'а-яА-Яa-zA-ZЁё]{1,100}", true))
                .withValidator(new StringLengthValidator("От двух до двадцати символов", 2, 20))
                .bind(Doctor::getMn, Doctor::setMn);
        dBinder.forField(spec).asRequired("Поле не должно быть пустым")
                .withValidator(new RegexpValidator("Лишь буквы, без цифр и разных символов", "[' 'а-яА-Яa-zA-ZЁё]{1,100}", true))
                .withValidator(new StringLengthValidator("Минимум 3 символа", 3, 25))
                .bind(Doctor::getSpecialty, Doctor::setSpecialty);

        Button save = new Button("ОК");
        Button cancel = new Button("Отменить");

        VerticalLayout doctorWindow = new VerticalLayout();
        doctorWindow.setMargin(true);
        doctorWindow.setSizeFull();

        HorizontalLayout names = new HorizontalLayout(lName, fName, mName);
        HorizontalLayout speci = new HorizontalLayout(spec);
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);


        save.addClickListener(e -> {

            //Пытаемся записать объект, ловим исключение, если поля не соответствуют валидатору.
            try {
                dBinder.writeBean(doctor);
                docSer.create(doctor);
                updateDoctorsList(grid);
                window.close();
            } catch (ValidationException e1) {
                Notification.show("Заполните все поля правильно");
            }

        });

        cancel.addClickListener(e -> window.close());

        add.addComponents(names, speci, buttons);

        window.setContent(add);

        return window;
    }

    //Обновляем нашу таблицу
    private void updateDoctorsList(Grid grid){
        List<Doctor> doctors = docSer.readAll();
        grid.setItems(doctors);
    }

    public Window updateDoctor(Grid grid){
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
        TextField spec = new TextField("Специальность");

        dBinder.forField(lName).asRequired("Поле не должно быть пустым")
                .withValidator(new RegexpValidator("Лишь буквы, без цифр и разных символов", "[' 'а-яА-Яa-zA-ZЁё]{1,100}", true))
                .withValidator(new StringLengthValidator("От двух до двадцати символов", 2, 20))
                .bind(Doctor::getLn, Doctor::setLn);
        dBinder.forField(fName).asRequired("Поле не должно быть пустым")
                .withValidator(new RegexpValidator("Лишь буквы, без цифр и разных символов", "[' 'а-яА-Яa-zA-ZЁё]{1,100}", true))
                .withValidator(new StringLengthValidator("От двух до двадцати символов", 2, 20))
                .bind(Doctor::getFn, Doctor::setFn);
        dBinder.forField(mName).asRequired("Поле не должно быть пустым")
                .withValidator(new RegexpValidator("Лишь буквы, без цифр и разных символов", "[' 'а-яА-Яa-zA-ZЁё]{1,100}", true))
                .withValidator(new StringLengthValidator("От двух до двадцати символов", 2, 20))
                .bind(Doctor::getMn, Doctor::setMn);
        dBinder.forField(spec).asRequired("Поле не должно быть пустым")
                .withValidator(new RegexpValidator("Лишь буквы, без цифр и разных символов", "[' 'а-яА-Яa-zA-ZЁё]{1,100}", true))
                .withValidator(new StringLengthValidator("Минимум 3 символа", 3, 25))
                .bind(Doctor::getSpecialty, Doctor::setSpecialty);

        Button save = new Button("ОК");
        Button cancel = new Button("Отменить");

        VerticalLayout doctorWindow = new VerticalLayout();
        doctorWindow.setMargin(true);
        doctorWindow.setSizeFull();

        HorizontalLayout names = new HorizontalLayout(lName, fName, mName);
        HorizontalLayout speci = new HorizontalLayout(spec);
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);

        save.addClickListener(e -> {

                try {
                    //Необходимо, чтобы кнопка реагировала на выбранный колумн, иначе объект не проинициализируется.
                    Doctor doctor = (Doctor) grid.getSelectedItems().toArray()[0];

                    dBinder.writeBean(doctor);
                    docSer.update(doctor);
                    //Потом закидывается в грид
                    updateDoctorsList(grid);
                    window.close();
                } catch (ValidationException e1) {
                    Notification.show("Заполните все поля правильно");
                }
        });

        cancel.addClickListener(e -> window.close());

        update.addComponents(names, speci, buttons);
        window.setContent(update);

        return window;
    }

    public void deleteDoctor(Grid grid){
        //Есть защита от удаления на уровне БД, тут я просто ловлю ошибку возникающую при удалении и обрабатываю.
        try{
        Doctor doctor = (Doctor)grid.getSelectedItems().toArray()[0];
        docSer.delete(doctor);

        updateDoctorsList(grid);}
        catch (PersistenceException ex){
            Notification.show("Нельзя удалить доктора, у которого есть рецепт");
        }
    }

}
