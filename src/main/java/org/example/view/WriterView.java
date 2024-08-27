package org.example.view;

import java.io.Writer;
import java.util.List;

public class WriterView implements View<Writer>{
    @Override
    public void show(Writer writer) {
        System.out.println(writer);

    }

    @Override
    public void show(List<Writer> writers) {
          System.out.println("\n==================================");
        System.out.println("Вікно редагування автора");
        System.out.println("q - вихід");
        System.out.println("1 - додати автора");
        System.out.println("2 - редагувати автора");
        System.out.println("3 - видалити автора");
        for (Writer writer : writers) {
            System.out.println(writer);
        }
    }
}