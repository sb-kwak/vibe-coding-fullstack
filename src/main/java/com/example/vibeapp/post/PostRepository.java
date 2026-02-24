package com.example.vibeapp.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * @Modifying과 @Query를 사용하여 특정 게시글의 조회수를 증가시킵니다.
     * 벌크성 수정 쿼리는 영속성 컨텍스트를 거치지 않으므로 주의가 필요합니다.
     */
    @Modifying
    @Query("UPDATE Post p SET p.views = p.views + 1 WHERE p.no = :no")
    void increaseViews(@Param("no") Long no);
}
