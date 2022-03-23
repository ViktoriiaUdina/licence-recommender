package com.vika.recommender.view.forms;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vika.recommender.model.Category;

public class CategoryForm extends AbstractForm<Category> {

    private TextField name = new TextField("Наименование");

    public CategoryForm() {
        Binder<Category> binder = new BeanValidationBinder<>(Category.class);
        binder.bindInstanceFields(this);
        setBinder(binder);
        add(name);
        add(getDefaultButtons());
        addClassName("category-form");
    }

}
