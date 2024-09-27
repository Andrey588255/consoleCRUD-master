package org.example.controller;

import org.example.model.Writer;
import org.example.service.WriterService;
import org.example.service.WriterServiceImpl;
import org.example.view.View;
import org.example.view.WriterView;

import java.util.Scanner;

public class WriterController {
    private WriterService writerService;
    private View writerViev;
    boolean working;
    private final Scanner scanner;
    private PostController postController;

    public WriterController() {
        scanner = new Scanner(System.in);
        writerViev = new WriterView();
        writerService = new WriterServiceImpl();
        working = true;
        postController = new PostController(scanner);
    }

    public void refresh() {
        String action;
        while (working) {
            writerViev.show(writerService.gatAll());
            writerViev.show("виберіть дію із списку");
            action = scanner.nextLine();
            switch (action) {
                case "q": { //вихід
                    working = false;
                    break;
                }
                case "1": { //додати автора
                    onAddAction();
                    break;
                }
                case "2": { //редагувати автора
                    onEditAction();
                    break;
                }
                case "3" : { //видалити автора
                    onDeleteAction();
                    break;
                }
            }
        }
    }

    private void onDeleteAction() {
        writerViev.show("Введіть id автора з таблиці для видалення");
        String action = scanner.nextLine();
        try {
            Long id = Long.parseLong(action);
            writerService.remove(id);
        } catch (Exception e) {
            e.printStackTrace();
            writerViev.err("Введіть коректний id");
            return;
        }
    }

    private void onEditAction() {
        writerViev.show("Введіть id автора для редагування");
        try {
            String id = scanner.nextLine();
            Long writerId = Long.parseLong(id);
            Writer currentWriter = writerService.get(writerId);
            writerViev.show(currentWriter);
            postController.refresh(currentWriter);
        } catch (Exception e) {
            e.printStackTrace();
            writerViev.show("Введіть коректний id");
            return;
        }
    }

    private void onAddAction() {
        System.out.println();
        String name = scanner.nextLine();
        System.out.println();
        String lastName = scanner.nextLine();
        Writer newWriter = new Writer(name, lastName);
        writerService.add(newWriter);
        }
    }