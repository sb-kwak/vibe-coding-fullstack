package com.example.vibeapp.post;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Optional;

@Mapper
public interface PostRepository {
    List<Post> findAll();
    Optional<Post> findById(Long no);
    void save(Post post);
    void update(Post post);
    void deleteById(Long no);
    void increaseViews(Long no);
}
