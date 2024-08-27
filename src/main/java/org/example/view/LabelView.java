package org.example.view;

import org.example.model.Label;
;
import java.util.List;

public class LabelView implements View<Label>{

    @Override
    public void show(Label label) {
        System.out.println(label);
    }

    @Override
    public void show(List<Label> labels) {
        System.out.println("\n==============================");
        System.out.println("Вікно редагування коментарів посту ");
        System.out.println("q - назад");
        System.out.println( "1 - додати коментар");
        System.out.println("2 - видалити коменар");
        for (Label label : labels)
            show(label);
    }
}