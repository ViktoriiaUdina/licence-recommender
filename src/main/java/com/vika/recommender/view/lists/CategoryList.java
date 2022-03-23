package com.vika.recommender.view.lists;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vika.recommender.model.Category;
import com.vika.recommender.repository.CategoryRepository;
import com.vika.recommender.view.forms.CategoryForm;

@PageTitle("Categories")
@Route("categories")
public class CategoryList extends AbstractList<Category, CategoryForm> {

    public CategoryList(CategoryRepository repository) {
        super(repository);
        CategoryForm categoryForm = new CategoryForm();
        categoryForm.setWidth("25em");
        add(addForm(categoryForm));
        addClassName("category-list");
    }

    @Override
    protected Component configureToolbar() {
        TextField filterText = new TextField();
        filterText.setPlaceholder("Поиск по наименованию...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button addCategoryButton = new Button("Добавить категорию");
        addCategoryButton.addClickListener(click -> addEntity(new Category()));

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addCategoryButton);
        toolbar.addClassName("category-toolbar");
        return toolbar;
    }

    @Override
    protected void configureGrid(Grid<Category> grid) {
        grid.setClassName("category-grid");
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(Category::getName).setHeader("Наименование");
    }
}
