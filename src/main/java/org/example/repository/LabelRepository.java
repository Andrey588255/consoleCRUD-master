package org.example.repository;

import org.example.model.Label;

import java.util.List;

public interface LabelRepository extends Repository<Label, Long>{
    List<Label> getByPostId(Long id);}