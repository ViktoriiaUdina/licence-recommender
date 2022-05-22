package com.vika.recommender.view.lists;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vika.recommender.model.Document;
import com.vika.recommender.repository.DocumentRepository;
import com.vika.recommender.repository.ParameterRepository;
import com.vika.recommender.view.forms.DocumentForm;

@PageTitle("Documents")
@Route("documents")
public class DocumentList extends AbstractList<Document, DocumentForm> {

    public DocumentList(DocumentRepository repository, ParameterRepository parameterRepository) {
        super(repository, false);
        DocumentForm documentForm = new DocumentForm(parameterRepository);
        documentForm.setWidth("25em");
        add(addForm(documentForm));
        addClassName("document-list");
    }

    @Override
    protected Component configureToolbar() {
        TextField filterText = new TextField();
        filterText.setPlaceholder("Поиск по наименованию...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button addCategoryButton = new Button("Добавить документ");
        addCategoryButton.addClickListener(click -> addEntity(new Document()));

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addCategoryButton);
        toolbar.addClassName("document-toolbar");
        return toolbar;
    }

    @Override
    protected void configureGrid(Grid<Document> grid) {
        grid.setClassName("document-grid");
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(Document::getName).setHeader("Наименование");
        grid.addColumn(Document::getRef).setHeader("Ссылка на полную версию");
    }

}
