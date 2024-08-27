package org.example.service;

import org.example.model.Label;
import org.example.repository.LabelRepository;
import org.example.repository.RepositoryFactory;

import java.util.List;

public class LabelServiceImpl implements LabelService{
    private LabelRepository lRepo;

        public LabelServiceImpl() {
       this.lRepo = RepositoryFactory.getLabelRepository();
    }

    @Override
    public List<Label> getByPostId(Long postId) {
        return lRepo.getByPostId(postId);
    }

 @Override
    public Long add(Label label, Long postId) {
       return lRepo.save(label, postId);
    }

    @Override
    public void remove(Long id) {
       lRepo.delete(id);
    }

   @Override
    public Long update(Label label, Long ownerId) {
        return lRepo.save(label, ownerId);
    }

    @Override
    public Label get(Long labelId) {
        return lRepo.get(labelId);
    }
}