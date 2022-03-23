package com.vika.recommender.view.forms;

import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vika.recommender.model.Document;
import com.vika.recommender.model.Parameter;
import com.vika.recommender.repository.ParameterRepository;
import org.vaadin.gatanaso.MultiselectComboBox;


public class DocumentForm extends AbstractForm<Document> {

    private TextField name = new TextField("Наименование");
    private TextField ref = new TextField("Ссылка на полную версию");
    private TextArea example = new TextArea("Отрывок");
    private MultiselectComboBox<Parameter> parameters = new MultiselectComboBox("Параметры");

    public DocumentForm(ParameterRepository parameterRepository) {
        Binder<Document> binder = new BeanValidationBinder<>(Document.class);
        binder.bindInstanceFields(this);
        setBinder(binder);
        parameters.setItems(parameterRepository.findAll());
        add(name, ref, example, parameters);
        add(getDefaultButtons());
        addClassName("document-form");
    }
}
