package com.vika.recommender.view.forms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public abstract class AbstractForm<T> extends FormLayout {

    private T entity;

    private Binder<T> binder;

    private final Button saveButton = new Button("Сохранить");
    private final Button deleteButton = new Button("Удалить");
    private final Button cancelButton = new Button("Отменить");

    public void setBinder(Binder<T> binder) {
        this.binder = binder;
    }

    public void setEntity(T entity) {
        this.entity = entity;
        binder.readBean(entity);
    }

    protected Component getDefaultButtons() {
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        saveButton.addClickShortcut(Key.ENTER);
        cancelButton.addClickShortcut(Key.ESCAPE);

        saveButton.addClickListener(event -> validateAndSave());
        deleteButton.addClickListener(event -> fireEvent(new DeleteEvent(this, entity)));
        cancelButton.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> saveButton.setEnabled(binder.isValid()));

        return new HorizontalLayout(saveButton, deleteButton, cancelButton);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(entity);
            fireEvent(new SaveEvent(this, entity));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class AbstractFromEvent extends ComponentEvent<AbstractForm> {
        private Object entity;

        protected AbstractFromEvent(AbstractForm<Object> source, Object entity) {
            super(source, false);
            this.entity = entity;
        }

        public Object getEntity() {
            return entity;
        }
    }

    public static class SaveEvent extends AbstractFromEvent {
        SaveEvent(AbstractForm source, Object entity) {
            super(source, entity);
        }
    }

    public static class DeleteEvent extends AbstractFromEvent {
        DeleteEvent(AbstractForm source, Object entity) {
            super(source, entity);
        }

    }

    public static class CloseEvent extends AbstractFromEvent {
        CloseEvent(AbstractForm source) {
            super(source, null);
        }
    }

    public <K extends ComponentEvent<?>> Registration addListener(Class<K> eventType,
                                                                  ComponentEventListener<K> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
