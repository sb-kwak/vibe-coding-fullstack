package com.example.vibeapp;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class PostRepository {
    private final List<Post> posts = new CopyOnWriteArrayList<>();

    public List<Post> findAll() {
        return new ArrayList<>(posts);
    }

    public void save(Post post) {
        posts.add(post);
    }

    public java.util.Optional<Post> findByNo(Long no) {
        return posts.stream()
                .filter(post -> post.getNo().equals(no))
                .findFirst();
    }
}
