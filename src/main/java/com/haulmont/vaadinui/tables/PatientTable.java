package com.haulmont.vaadinui.tables;

import com.haulmont.entity.Patient;
import com.haulmont.service.PatientService;
import com.haulmont.vaadinui.forms.PatientForm;
import com.vaadin.ui.*;

import java.util.List;

public class PatientTable {
    private PatientForm pf = new PatientForm();
    private PatientService ps = new PatientService();
    private Grid<Patient> patientGrid = new Grid<>(Patient.class);

    public VerticalLayout patTable(){
        Label label = new Label("Пациенты");

        patientGrid.removeAllColumns();
        patientGrid.addColumn(Patient::getLn).setCaption("Фамилия");
        patientGrid.addColumn(Patient::getFn).setCaption("Имя");
        patientGrid.addColumn(Patient::getMn).setCaption("Отчество");
        patientGrid.addColumn(Patient::getPhone).setCaption("Мобильный телефон");
        List<Patient> patientList = ps.readAll();
        patientGrid.setItems(patientList);
        patientGrid.setSizeFull();

        Button add = new Button("Добавить");
        Button update = new Button("Редактировать");
        Button delete = new Button("Удалить");

        HorizontalLayout tools = new HorizontalLayout(add, update, delete);
        VerticalLayout patients = new VerticalLayout(label, patientGrid, tools);

        add.addClickListener(e -> tools.getUI().addWindow(pf.addPatient(patientGrid)));
        update.addClickListener(e -> {
            if(!patientGrid.getSelectedItems().isEmpty()) {
                tools.getUI().addWindow(pf.updatePatient(patientGrid));
            }
            if(patientGrid.getSelectedItems().isEmpty()){
                Notification.show("Сначала выберите пациента");
            }
        });
        delete.addClickListener(e -> {
            if(!patientGrid.getSelectedItems().isEmpty()) {
                pf.deletePatient(patientGrid);
            }
            if(patientGrid.getSelectedItems().isEmpty()){
            Notification.show("Выберите пациента для удаления");
            }
        });

        return patients;
    }

}
