package org.example.view;

import org.example.model.Post;

import java.util.List;

public class PostView implements View<Post> {
    @Override
    public void show(Post post) {
        System.out.println(post);
    }

    @Override
    public void show(List<Post> posts) {
        System.out.println("\n========================================");
        System.out.println("Вікно редагування постів автора:");
        System.out.println("q - назад");
        System.out.println("1 - додати пост");
        System.out.println("2 - редагувати пост");
        System.out.println("3 - видалити пост");
        System.out.println("4 - редагувати коментарі");
        for (Post post : posts) {
            System.out.println(post);
        }
    }
}