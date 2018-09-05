package com.haulmont.vaadinui.tables;

import com.haulmont.entity.Doctor;
import com.haulmont.service.DoctorService;
import com.haulmont.vaadinui.forms.DoctorForm;
import com.vaadin.ui.*;

import java.util.List;

public class DoctorTable {
    private DoctorForm df = new DoctorForm();
    private DoctorService ds = new DoctorService();
    private Grid<Doctor> doctorGrid = new Grid<>(Doctor.class);

    public VerticalLayout docTable(){
        Label label = new Label("Доктора");

        doctorGrid.removeAllColumns();
        doctorGrid.addColumn(Doctor::getLn).setCaption("Фамилия");
        doctorGrid.addColumn(Doctor::getFn).setCaption("Имя");
        doctorGrid.addColumn(Doctor::getMn).setCaption("Отчество");
        doctorGrid.addColumn(Doctor::getSpecialty).setCaption("Специальность");
        List<Doctor> doclist = ds.readAll();
        doctorGrid.setItems(doclist);
        doctorGrid.setSizeFull();

        Button add = new Button("Добавить");
        Button update = new Button("Редактировать");
        Button delete = new Button("Удалить");

        HorizontalLayout tools = new HorizontalLayout(add, update, delete);
        VerticalLayout doctors = new VerticalLayout(label, doctorGrid, tools);

        add.addClickListener(e -> tools.getUI().addWindow(df.addDoctor(doctorGrid)));
        update.addClickListener(e -> {
            if(!doctorGrid.getSelectedItems().isEmpty()) {
                tools.getUI().addWindow(df.updateDoctor(doctorGrid));
            }
            if(doctorGrid.getSelectedItems().isEmpty()){
                Notification.show("Сначала выберите доктора");
            }
        });
        delete.addClickListener(e -> {
            if(!doctorGrid.getSelectedItems().isEmpty()) {
                df.deleteDoctor(doctorGrid);
            }
            if(doctorGrid.getSelectedItems().isEmpty()){
                Notification.show("Выберите доктора для удаления");
            }
        });

        return doctors;
    }


}
