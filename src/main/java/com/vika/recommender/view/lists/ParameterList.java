package com.vika.recommender.view.lists;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vika.recommender.model.Parameter;
import com.vika.recommender.repository.CategoryRepository;
import com.vika.recommender.repository.DocumentRepository;
import com.vika.recommender.repository.ParameterRepository;
import com.vika.recommender.view.forms.ParameterForm;

@PageTitle("Parameters")
@Route("parameters")
public class ParameterList extends AbstractList<Parameter, ParameterForm> {

    public ParameterList(ParameterRepository repository, CategoryRepository categoryRepository, DocumentRepository documentRepository) {
        super(repository);
        ParameterForm parameterForm = new ParameterForm(categoryRepository, documentRepository);
        parameterForm.setWidth("25em");
        add(addForm(parameterForm));
        addClassName("parameter-list");
    }

    @Override
    protected Component configureToolbar() {
        TextField filterText = new TextField();
        filterText.setPlaceholder("Поиск по наименованию...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button addCategoryButton = new Button("Добавить параметр");
        addCategoryButton.addClickListener(click -> {
            addEntity(new Parameter());
        });

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addCategoryButton);
        toolbar.addClassName("parameter-toolbar");
        return toolbar;
    }

    @Override
    protected void configureGrid(Grid<Parameter> grid) {
        grid.setClassName("parameter-grid");
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(Parameter::getName).setHeader("Наименование");
        grid.addColumn(parameter -> parameter.getCategory().getName()).setHeader("Категория");
    }

}
