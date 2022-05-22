package com.vika.recommender.view.lists;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vika.recommender.view.forms.AbstractForm;
import net.jodah.typetools.TypeResolver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractList<T, F extends AbstractForm<T>> extends VerticalLayout {

    private final Class<T> entityClass;
    private final JpaRepository<T, Long> repository;

    private F form;
    private final Grid<T> grid;

    abstract Component configureToolbar();
    abstract void configureGrid(Grid<T> grid);

    public AbstractList(JpaRepository<T, Long> repository, boolean useTree) {
        this.repository = repository;
        this.entityClass = (Class<T>) TypeResolver.resolveRawArguments(AbstractList.class, getClass())[0];
        if (useTree) {
            this.grid = new TreeGrid<>(entityClass);
        } else {
            this.grid = new Grid<>(entityClass);
        }
        setSizeFull();
        configureGrid(grid);
        updateList(grid);
        add(configureToolbar());
    }

    protected List<T> fetchData() {
        return repository.findAll();
    }

    protected void updateList(Grid<T> grid) {
        grid.setItems(fetchData());
    }

    protected Component addForm(F form) {
        this.form = form;
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.addClassName("content");
        content.setSizeFull();
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);

        form.addListener(AbstractForm.SaveEvent.class, this::saveEntity);
        form.addListener(AbstractForm.DeleteEvent.class, this::deleteEntity);
        form.addListener(AbstractForm.CloseEvent.class, e -> closeEditor());

        form.setVisible(false);

        grid.asSingleSelect().addValueChangeListener(el ->
                editEntity(el.getValue())
        );

        return content;
    }

    void addEntity(T entity) {
        grid.asSingleSelect().clear();
        editEntity(entity);
    }

    private void saveEntity(AbstractForm.SaveEvent event) {
        repository.save((T)event.getEntity());
        updateList(grid);
        closeEditor();
    }

    private void deleteEntity(AbstractForm.DeleteEvent event) {
        repository.delete((T)event.getEntity());
        updateList(grid);
        closeEditor();
    }

    private void editEntity(T entity) {
        if (entity == null) {
            closeEditor();
        } else {
            form.setEntity(entity);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setEntity(null);
        form.setVisible(false);
        removeClassName("editing");
    }
}
