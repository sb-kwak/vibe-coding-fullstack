package com.example.vibeapp.post;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class PostRepository {
    private final List<Post> posts = new CopyOnWriteArrayList<>();
    private final java.util.concurrent.atomic.AtomicLong sequence = new java.util.concurrent.atomic.AtomicLong(0);

    public List<Post> findAll() {
        return new ArrayList<>(posts);
    }

    public void save(Post post) {
        if (post.getNo() == null) {
            post.setNo(sequence.incrementAndGet());
        } else {
            // Update: Remove existing post with the same NO if it exists
            posts.removeIf(p -> p.getNo().equals(post.getNo()));
            sequence.set(Math.max(sequence.get(), post.getNo()));
        }
        posts.add(post);
    }

    public java.util.Optional<Post> findById(Long no) {
        return posts.stream()
                .filter(post -> post.getNo().equals(no))
                .findFirst();
    }

    public void deleteById(Long no) {
        posts.removeIf(post -> post.getNo().equals(no));
    }
}
