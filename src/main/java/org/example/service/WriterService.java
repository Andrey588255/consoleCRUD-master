package org.example.service;

import org.example.model.Writer;

import java.util.List;

public interface WriterService extends Service<Writer, Long>{
    Long add (Writer writer);

    Long update(Writer writer);

    List<Writer> gatAll();

    List<Writer> getAll();
}