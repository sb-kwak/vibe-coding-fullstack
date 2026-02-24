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
    private final PostTagRepository postTagRepository;

    public PostService(PostRepository postRepository, PostTagRepository postTagRepository) {
        this.postRepository = postRepository;
        this.postTagRepository = postTagRepository;
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
        Post post = getPostEntity(no);
        String tags = postTagRepository.findByPostNo(no).stream()
                .map(PostTag::getTagName)
                .collect(Collectors.joining(", "));
        return PostResponseDTO.from(post, tags);
    }

    public void createPost(PostCreateDto createDto) {
        Post post = createDto.toEntity();
        postRepository.save(post);
        saveTags(post.getNo(), createDto.tags());
    }

    public void updatePost(Long no, PostUpdateDto updateDto) {
        Post post = getPostEntity(no);
        updateDto.updateEntity(post);
        postRepository.update(post);
        
        postTagRepository.deleteByPostNo(no);
        saveTags(no, updateDto.tags());
    }

    private void saveTags(Long postNo, String tagsString) {
        if (tagsString == null || tagsString.isBlank()) {
            return;
        }

        String[] tags = tagsString.split(",");
        for (String tagName : tags) {
            String trimmedTag = tagName.trim();
            if (!trimmedTag.isEmpty()) {
                postTagRepository.save(new PostTag(null, postNo, trimmedTag));
            }
        }
    }

    public void deleteById(Long no) {
        postRepository.deleteById(no);
    }
}
