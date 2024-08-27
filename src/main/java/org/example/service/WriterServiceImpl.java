package org.example.service;

import org.example.model.Writer;
import org.example.repository.RepositoryFactory;
import org.example.repository.WriterRepository;

import java.util.List;

public class WriterServiceImpl implements WriterService {
    private WriterRepository wRepo;

    public WriterServiceImpl() {
        this.wRepo = RepositoryFactory.getWriterRepository();
    }

    @Override
    @Deprecated
    public Long add(Writer addEntity, Long ownerId) {
        throw new IllegalArgumentException("Writer can not have the owner," +
                " operation with ownerId parameter is forbidden!");
    }

    @Override
    public Long update(Writer entity, Long ownerId) {
        throw new IllegalArgumentException("Writer can not have the owner," +
                " operation with ownerId parameter is forbidden!");
    }

    @Override
    public List<Writer> gatAll() {
        return List.of();
    }

    @Override
    public Long add(Writer writer) {
        return wRepo.save(writer);
    }

    @Override
    public void remove(Long writerId) {
        wRepo.delete(writerId);
    }

    @Override
    public Long update(Writer writer) {
        return wRepo.save(writer);
    }

    @Override
    public Writer get(Long writerId) {
        return wRepo.get(writerId);
    }

      @Override
    public List<Writer> getAll() {
        return wRepo.getAll();
    }
}