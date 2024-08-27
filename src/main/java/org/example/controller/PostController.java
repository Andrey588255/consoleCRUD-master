package org.example.controller;

import org.example.model.Post;
import org.example.model.PostStatus;
import org.example.model.Writer;
import org.example.service.PostService;
import org.example.service.PostServiceImpl;
import org.example.view.PostView;

import java.util.Scanner;

public class PostController {
    private PostService postService;
    private Scanner scanner;
    private LabelController labelController;
    private PostView postView;

    public PostController(Scanner scanner) {
        this.scanner = scanner;
        postService = new PostServiceImpl();
        postView = new PostView();
        labelController = new LabelController(scanner);
    }

    public void refresh(Writer writer) {
        String action;
        boolean working = true;
        while (working) {
            postView.show(postService.getByWriterId(writer.getId()));
            postView.show("Виберіть дію із списку");
            action = scanner.nextLine();
            switch (action) {
                case "q": { // вихід
                    working = false;
                    break;
                }
                case "1": { // додати пост
                    onAddAction(writer.getId());
                    break;
                }
                case "2": { // редагувати пост
                    onEditAction(writer.getId());
                    break;
                }
                case "3": { // видалии пост
                    onDeleteAction();
                    break;
                }
                case "4": { // редагувати коментарі
                    onLabelAction();
                    break;
                }
            }
        }
    }

    private void onLabelAction() {
        postView.show("Введіть номер посту з таблиці для додавання коментарів");
        try {
            String id = scanner.nextLine();
            Long postId = Long.parseLong(id);
            labelController.refresh(postService.get(postId));
        } catch (Exception e) {
            e.printStackTrace();
            postView.err("Введіть коректний id");
        }
    }

    private void onDeleteAction() {
        try {
            postView.show("Введіть id посту з таблиці для видалення");
            String id = scanner.nextLine();
            Long postId = Long.parseLong(id);
            postService.remove(postId);
        } catch (Exception e) {
            e.printStackTrace();
            postView.err("Введіть коректний id з таблиці");
        }
    }

    private void onEditAction(Long writerId) {
        postView.show("Введіть вміст поста");
        String content = scanner.nextLine();
        postView.show("Виберіть статус");
        PostStatus status = switchStatus();
        postView.show("Введіть номер посту з таблиці, до якого буде закріплений коментар");
        try {
            String id = scanner.nextLine();
            Long postId = Long.parseLong(id);
            Post currentPost = postService.get(postId);
            currentPost.setContent(content);
            currentPost.setStatus(status);
            postService.update(currentPost, writerId);
        } catch (Exception e) {
            e.printStackTrace();
            postView.err("Введіть id поста із таблиці");
        }
    }

    private void onAddAction(Long id) {
        postView.show("Введіть вміст поста");
        String content = scanner.nextLine();
        postView.show("Виберіть статус");
        PostStatus status = switchStatus();
        postService.add(new Post(content, status), id);
    }

    private PostStatus switchStatus() {
        while (true) {
            postView.show("Опублікований");
            postView.show("Перевіряється");
            postView.show("Видалений");
            String action = scanner.nextLine();
            switch (action) {
                case "0":
                    return PostStatus.ACTIVE;
                case "1":
                    return PostStatus.UNDER_REVIEW;
                case "2":
                    return PostStatus.DELETED;
            }
        }
    }
}
