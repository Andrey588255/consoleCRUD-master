package org.example.service;

import org.example.model.Post;
import org.example.repository.PostRepository;
import org.example.repository.RepositoryFactory;


import java.util.List;

public class PostServiceImpl implements PostService {
    private final PostRepository pRepo;

    public PostServiceImpl() {
        this.pRepo = RepositoryFactory.getPostRepository();
    }

    public PostServiceImpl(PostRepository pRepo) {
        this.pRepo = pRepo;
    }

    @Override
    public Long add(Post post, Long ownerId) {
        return pRepo.save(post, ownerId);
    }

    @Override
    public void remove(Long postId) {
        pRepo.delete(postId);
    }

    @Override
    public Long update(Post post, Long writerId) {
        return pRepo.save(post, writerId);
    }

    @Override
    public Post get(Long postId) {
        return pRepo.get(postId);
    }

    public List<Post> getByWriterId(Long id) {
        return pRepo.getByWriterId(id);
    }
}