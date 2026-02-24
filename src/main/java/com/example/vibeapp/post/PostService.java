package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // Spring Data JPA의 findAll(Sort)을 사용하여 최신순으로 조회합니다.
        return postRepository.findAll(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "createdAt"));
    }

    public List<PostListDto> getPaginatedPosts(int page, int size) {
        // Spring Data JPA의 Pageable을 사용하면 더 간결해지지만, 기존 로직의 흐름을 유지하며 DTO 변환만 수행합니다.
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
        // count() 메서드로 전체 개수를 가져옵니다.
        long totalPosts = postRepository.count();
        return (int) Math.ceil((double) totalPosts / size);
    }

    protected Post getPostEntity(Long no) {
        return postRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + no));
    }

    public PostResponseDTO findById(Long no) {
        // 조회수 증가 (Custom @Query 사용)
        postRepository.increaseViews(no);
        
        Post post = getPostEntity(no);
        String tags = postTagRepository.findByPostNo(no).stream()
                .map(PostTag::getTagName)
                .collect(Collectors.joining(", "));
        return PostResponseDTO.from(post, tags);
    }

    @Transactional
    public void createPost(PostCreateDto createDto) {
        Post post = createDto.toEntity();
        // save() 메서드로 저장합니다.
        postRepository.save(post);
        saveTags(post.getNo(), createDto.tags());
    }

    @Transactional
    public void updatePost(Long no, PostUpdateDto updateDto) {
        Post post = getPostEntity(no);
        updateDto.updateEntity(post);
        // JPA의 변경 감지(Dirty Checking) 덕분에 별도의 save/update 호출 없이도 트랜잭션 종료 시 반영됩니다.
        // 명시적으로 호출하고 싶다면 postRepository.save(post)를 사용할 수 있습니다.
        
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
