package com.vika.recommender.view.forms;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vika.recommender.model.Category;
import com.vika.recommender.model.Document;
import com.vika.recommender.model.Parameter;
import com.vika.recommender.repository.CategoryRepository;
import com.vika.recommender.repository.DocumentRepository;
import com.vika.recommender.repository.ParameterRepository;
import org.vaadin.gatanaso.MultiselectComboBox;

public class ParameterForm extends AbstractForm<Parameter> {

    private TextField name = new TextField("Наименование");
    private ComboBox<Category> category = new ComboBox<>("Категория");
    private ComboBox<Parameter> parentParameter = new ComboBox<>("Родительский параметр");
    private MultiselectComboBox<Document> documents = new MultiselectComboBox("Документы");

    public ParameterForm(ParameterRepository parameterRepository, CategoryRepository categoryRepository, DocumentRepository documentRepository) {
        Binder<Parameter> binder = new BeanValidationBinder<>(Parameter.class);
        binder.bindInstanceFields(this);
        setBinder(binder);
        documents.setItems(documentRepository.findAll());
        parentParameter.setItems(parameterRepository.findAll());
        parentParameter.addValueChangeListener(el -> {
            if (el.getValue() == null) {
                category.setReadOnly(false);
            } else {
                category.setValue(el.getValue().getCategory());
                category.setReadOnly(true);
            }
        });
        category.setItems(categoryRepository.findAll());
        add(name, parentParameter, category, documents);
        add(getDefaultButtons());
        addClassName("parameter-form");
    }
}
