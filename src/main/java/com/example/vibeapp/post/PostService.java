package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // Removed in-memory init

    private List<Post> getAllPosts() {
        return postRepository.findAll().stream()
                .sorted((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()))
                .toList();
    }

    public List<PostListDto> getPaginatedPosts(int page, int size) {
        List<Post> allPosts = getAllPosts();
        int fromIndex = (page - 1) * size;
        if (fromIndex >= allPosts.size()) {
            return List.of();
        }
        int toIndex = Math.min(fromIndex + size, allPosts.size());
        return allPosts.subList(fromIndex, toIndex).stream()
                .map(PostListDto::from)
                .collect(Collectors.toList());
    }

    public int getTotalPages(int size) {
        int totalPosts = postRepository.findAll().size();
        return (int) Math.ceil((double) totalPosts / size);
    }

    protected Post getPostEntity(Long no) {
        return postRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + no));
    }

    public PostResponseDTO findById(Long no) {
        postRepository.increaseViews(no);
        return PostResponseDTO.from(getPostEntity(no));
    }

    public void createPost(PostCreateDto createDto) {
        Post post = createDto.toEntity();
        postRepository.save(post);
    }

    public void updatePost(Long no, PostUpdateDto updateDto) {
        Post post = getPostEntity(no);
        updateDto.updateEntity(post);
        postRepository.update(post);
    }

    public void deleteById(Long no) {
        postRepository.deleteById(no);
    }
}
