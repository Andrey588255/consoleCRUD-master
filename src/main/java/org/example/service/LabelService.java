package org.example.service;

import org.example.model.Label;

import java.util.List;

public interface LabelService extends Service<Label, Long>{
    List<Label> getByPostId(Long postId);
}