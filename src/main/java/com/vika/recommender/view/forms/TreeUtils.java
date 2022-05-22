package com.vika.recommender.view.forms;

import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.selection.MultiSelectionListener;
import com.vika.recommender.model.Parameter;

import java.util.HashSet;
import java.util.List;
import java.util.function.Function;

public class TreeUtils {

    public static <T> MultiSelectionListener enableChildAutoSelection(TreeGrid<T> treeGrid, Function<T, List<T>> childProvider) {
        return el -> {
            HashSet<Parameter> old = new HashSet<>(el.getOldSelection());
            HashSet<Parameter> newValues = new HashSet<>(el.getValue());
            if (newValues.size() > old.size()) {
                newValues.removeAll(old);
                T lastSelected = (T) newValues.stream().findFirst().get();
                recursiveSelectChildren(treeGrid, lastSelected, childProvider);
            } else {
                old.removeAll(newValues);
                T lastUnselected = (T) old.stream().findFirst().get();
                recursiveUnselectChildren(treeGrid, lastUnselected, childProvider);
            }
        };
    }

    public static <T> void recursiveSelectChildren(TreeGrid<T> treeGrid, T root, Function<T, List<T>> childProvider) {
        childProvider.apply(root).forEach(el -> {
            recursiveSelectChildren(treeGrid, el, childProvider);
            treeGrid.select(el);
        });
    }

    public static <T> void recursiveUnselectChildren(TreeGrid<T> treeGrid, T root, Function<T, List<T>> childProvider) {
        childProvider.apply(root).forEach(el -> {
            recursiveUnselectChildren(treeGrid, el, childProvider);
            treeGrid.deselect(el);
        });
    }
}
