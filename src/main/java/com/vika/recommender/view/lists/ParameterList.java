package com.vika.recommender.view.lists;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vika.recommender.model.Parameter;
import com.vika.recommender.repository.CategoryRepository;
import com.vika.recommender.repository.DocumentRepository;
import com.vika.recommender.repository.ParameterRepository;
import com.vika.recommender.view.forms.ParameterForm;

import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Parameters")
@Route("parameters")
public class ParameterList extends AbstractList<Parameter, ParameterForm> {

    public ParameterList(ParameterRepository repository, CategoryRepository categoryRepository, DocumentRepository documentRepository) {
        super(repository, true);
        ParameterForm parameterForm = new ParameterForm(repository, categoryRepository, documentRepository);
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
        TreeGrid<Parameter> treeGrid = (TreeGrid<Parameter>)grid;
        treeGrid.setClassName("parameter-grid");
        treeGrid.setSizeFull();
        treeGrid.removeAllColumns();
        treeGrid.addHierarchyColumn(Parameter::getName).setHeader("Наименование");
        treeGrid.addColumn(parameter -> parameter.getCategory().getName()).setHeader("Категория");
    }

    @Override
    protected void updateList(Grid<Parameter> grid) {
        TreeGrid<Parameter> treeGrid = (TreeGrid<Parameter>)grid;
        List<Parameter> parameters = fetchData();
        treeGrid.setItems(parameters.stream().filter(p -> p.getParentParameter() == null).collect(Collectors.toList()), Parameter::getChildParams);
    }
}
