package com.haulmont.vaadinui;

import javax.servlet.annotation.WebServlet;

import com.haulmont.vaadinui.tables.DoctorTable;
import com.haulmont.vaadinui.tables.PatientTable;
import com.haulmont.vaadinui.tables.RecipeTable;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Label mainLabel = new Label("Больница");

        TabSheet tabSheet = new TabSheet();
        tabSheet.addTab(new PatientTable().patTable()).setCaption("Пациенты");
        tabSheet.addTab(new DoctorTable().docTable()).setCaption("Доктора");
        tabSheet.addTab(new RecipeTable().recTable()).setCaption("Рецепты");

        HorizontalLayout horizontalLayout = new HorizontalLayout(mainLabel);

        VerticalLayout mainLayout = new VerticalLayout(horizontalLayout, tabSheet);
        mainLayout.setComponentAlignment(horizontalLayout, Alignment.TOP_CENTER);

        setContent(mainLayout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
