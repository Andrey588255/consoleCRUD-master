package org.example.service;

import org.example.model.Post;
import java.util.List;

public interface PostService extends Service<Post, Long> {
    List<Post> getByWriterId(Long id);
}