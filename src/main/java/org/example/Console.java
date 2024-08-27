package org.example;

import org.example.controller.WriterController;

public class Console {
    public static void main(String[] args){
        WriterController controller = new WriterController();
        controller.refresh();

        System.out.println("Програма завершила роботу ...");
    }
}
