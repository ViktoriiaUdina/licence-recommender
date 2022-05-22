package com.vika.recommender.view.forms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.Route;
import com.vika.recommender.model.Category;
import com.vika.recommender.model.Document;
import com.vika.recommender.model.Parameter;
import com.vika.recommender.repository.CategoryRepository;
import com.vika.recommender.repository.DocumentRepository;
import com.vika.recommender.repository.ParameterRepository;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Route("recommender")
public class RecommenderForm extends VerticalLayout {

    private ComboBox<Category> category = new ComboBox<>();

    private ParameterRepository parameterRepository;
    private TreeGrid<Parameter> parameterGrid;
    private Component parameterList;

    public RecommenderForm(CategoryRepository categoryRepository, ParameterRepository parameterRepository, DocumentRepository documentRepository) {
        this.parameterRepository = parameterRepository;
        parameterGrid = new TreeGrid<>(Parameter.class);
        parameterList = parameters();

        category.setPlaceholder("Выберите категорию");
        category.addValueChangeListener(el -> {
            updateParameters(parameterRepository.findByCategoryName(el.getValue().getName()));
        });
        category.setItems(categoryRepository.findAll());
        category.setWidth("50em");
        HorizontalLayout hl = new HorizontalLayout();
        Button find = new Button("Найти");
        find.addClickListener(event -> {
            List<Document> documents =
                    documentRepository.findByParameterIds(parameterGrid.asMultiSelect().getValue().stream().map(Parameter::getId).collect(Collectors.toList()));
            Dialog dialog = new Dialog();
            dialog.setSizeFull();
            dialog.setModal(true);
            dialog.add(documents(documents));
            dialog.open();
        });
        hl.add(category, find);
        hl.setFlexGrow(2, category);
        hl.setFlexGrow(1, find);

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        add(hl, parameterList);
    }

    private Component parameters() {
        HorizontalLayout hl = new HorizontalLayout();
        hl.setWidth("56em");
        hl.setHeightFull();
        hl.setAlignItems(Alignment.CENTER);
        parameterGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        parameterGrid.asMultiSelect().addSelectionListener(
                TreeUtils.enableChildAutoSelection(parameterGrid, Parameter::getChildParams)
        );
        parameterGrid.setSizeFull();
        parameterGrid.removeAllColumns();
        parameterGrid.addHierarchyColumn(Parameter::getName).setHeader("Наименование");
        hl.add(parameterGrid);
        hl.setVisible(true);
        return hl;
    }

    private void updateParameters(List<Parameter> parameters) {
        parameterGrid.setItems(parameters.stream().filter(p -> p.getParentParameter() == null).collect(Collectors.toList()), Parameter::getChildParams);
        parameterList.setVisible(true);
    }

    private Grid<Document> documents(List<Document> documents) {
        Grid<Document> documentGrid = new Grid<>(Document.class);
        documentGrid.setSizeFull();
        documentGrid.removeAllColumns();
        documentGrid.addColumn(Document::getName).setHeader("Наименование");
        documentGrid.addColumn(Document::getRef).setHeader("Ссылка");
        documentGrid.addColumn(Document::getExample).setHeader("Отрывок");
        documentGrid.setItems(documents);
        documentGrid.setVisible(true);
        documentGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        documentGrid.asSingleSelect().addValueChangeListener(el -> {
            showDocumentDialog(el.getValue());
        });
        return documentGrid;
    }

    private void showDocumentDialog(Document document) {
        Dialog dialog = new Dialog();
        dialog.setWidth("56em");
        dialog.setHeight("56em");
        dialog.setModal(true);
        TextArea textArea = new TextArea();
        textArea.setSizeFull();
        textArea.setValue(document.getExample());
        dialog.add(textArea);
        dialog.open();
    }

}
