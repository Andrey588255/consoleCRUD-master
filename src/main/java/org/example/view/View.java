package org.example.view;

import java.util.List;

public interface View <T>{
   void show (T t);
   void show (List <T> ts);

   default void show(String massage) {
        System.out.println(massage);
   }

   default void err (String err){
         System.err.println(err);
   }
}