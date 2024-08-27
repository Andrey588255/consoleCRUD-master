package org.example.controller;

import org.example.model.Label;
import org.example.model.Post;
import org.example.service.LabelService;
import org.example.service.LabelServiceImpl;
import org.example.view.LabelView;
import org.example.view.View;

import java.util.Scanner;



public class LabelController {
    private View<Label> labelView;
    private LabelService labelService;
    private Scanner scanner;

    public LabelController(Scanner scanner) {
        this.scanner = scanner;
        labelView = new LabelView();
        labelService = new LabelServiceImpl();
    }

    public void refresh(Post post) {
        String action;
        boolean working = true;
        while (working) {
            labelView.show(labelService.getByPostId(post.getId()));
            labelView.show("Виберіть дію із списку");
            action = scanner.nextLine();
            switch (action) {
                case "q": { //вихід
                    working = false;
                    break;
                }
                case "1": { //додайте коментар
                    onAddAction(post.getId());
                    break;
                }
                case "2": { //видаліть коментар
                    onDeleteAction();
                    break;
                }
            }
        }
    }

    private void onDeleteAction() {
        labelView.show("Введіть id коментаря із таблиці для видалення");
        try {
            String id = scanner.nextLine();
            Long labelId = Long.parseLong(id);
            labelService.remove(labelId);
        } catch (Exception e) {
            e.printStackTrace();
            labelView.err("Введіть коректний id!");
        }
    }

    private void onAddAction(Long id) {
        labelView.show("Введіть коментар");
        String name = scanner.nextLine();
        labelService.add(new Label(name), id);
    }
}
