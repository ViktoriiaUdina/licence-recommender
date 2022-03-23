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
import org.vaadin.gatanaso.MultiselectComboBox;

public class ParameterForm extends AbstractForm<Parameter> {

    private final CategoryRepository categoryRepository;
    private final DocumentRepository documentRepository;

    private TextField name = new TextField("Наименование");
    private ComboBox<Category> category = new ComboBox<>("Категория");
    private MultiselectComboBox<Document> documents = new MultiselectComboBox("Документы");

    public ParameterForm(CategoryRepository categoryRepository, DocumentRepository documentRepository) {
        this.categoryRepository = categoryRepository;
        this.documentRepository = documentRepository;
        Binder<Parameter> binder = new BeanValidationBinder<>(Parameter.class);
        binder.bindInstanceFields(this);
        setBinder(binder);
        documents.setItems(documentRepository.findAll());
        category.setItems(categoryRepository.findAll());
        add(name, category);
        add(getDefaultButtons());
        addClassName("parameter-form");
    }


}
