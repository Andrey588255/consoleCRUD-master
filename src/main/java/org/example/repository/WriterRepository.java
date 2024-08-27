package org.example.repository;

import org.example.model.Writer;

import java.util.List;

public interface WriterRepository extends Repository<Writer, Long>{

    void delete(Writer writer);

    Long save(Writer writer);

    List<Writer> getAll();
}