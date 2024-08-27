package org.example.repository;

import org.example.repository.hibernate.LabelRepositoryHiber;
import org.example.repository.hibernate.PostRepositoryHiber;
import org.example.repository.hibernate.WriterRepositoryHiber;
import org.example.repository.jdbc.LabelRepositoryJdbc;
import org.example.repository.jdbc.PostRepositoryJdbc;
import org.example.repository.jdbc.WriterRepositoryJdbc;

import java.nio.file.Files;
import java.nio.file.Paths;

public class RepositoryFactory {
    private static WriterRepository writerRepository;
    public static PostRepository postRepository;
    public static LabelRepository labelRepository;
    static {
        if (Files.exists(Paths.get("src/main/resources/hiber.properties"))) {
            writerRepository = new WriterRepositoryHiber();
            postRepository = new PostRepositoryHiber();
            labelRepository = new LabelRepositoryHiber();
        } else if (Files.exists(Paths.get("src/main/resources/jdbc.properties"))) {
            writerRepository = new WriterRepositoryJdbc();
            postRepository = new PostRepositoryJdbc();
            labelRepository = new LabelRepositoryJdbc();
        }
    }

    public static WriterRepository getWriterRepository() {
        return writerRepository;
    }

    public static PostRepository getPostRepository() {
        return postRepository;
    }

    public static LabelRepository getLabelRepository() {
        return labelRepository;
    }
}
