package com.example.vibeapp.post;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

    /**
     * Query Method 기능을 사용하여 특정 게시글의 모든 태그를 삭제합니다.
     */
    void deleteByPostNo(Long postNo);

    /**
     * Query Method 기능을 사용하여 특정 게시글의 태그 목록을 조회합니다.
     */
    List<PostTag> findByPostNo(Long postNo);
}
