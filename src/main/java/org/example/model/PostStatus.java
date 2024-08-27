package org.example.model;

public enum PostStatus {
    ACTIVE("Опублікований"),
    UNDER_REVIEW("Перевіряється"),
    DELETED("Видалений");

    private String status;

    PostStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

}