package com.example.vibeapp.post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.util.List;

// @Repository - Bean 충돌 방지를 위해 주석 처리
public class PostTagRepositoryOld {

    @PersistenceContext
    private EntityManager em;

    /**
     * em.persist()를 사용하여 태그 엔티티를 저장합니다.
     */
    public void save(PostTag postTag) {
        em.persist(postTag);
    }

    /**
     * 식별자를 이용해 특정 태그를 삭제합니다.
     */
    public void deleteById(Long id) {
        PostTag tag = em.find(PostTag.class, id);
        if (tag != null) {
            em.remove(tag);
        }
    }

    /**
     * JPQL DELETE 문을 사용하여 특정 게시글의 모든 태그를 삭제합니다.
     */
    public void deleteByPostNo(Long postNo) {
        em.createQuery("DELETE FROM PostTag t WHERE t.postNo = :postNo")
          .setParameter("postNo", postNo)
          .executeUpdate();
    }

    /**
     * JPQL을 사용하여 게시글 번호에 해당하는 태그 목록을 조회합니다.
     */
    public List<PostTag> findByPostNo(Long postNo) {
        return em.createQuery("SELECT t FROM PostTag t WHERE t.postNo = :postNo", PostTag.class)
                 .setParameter("postNo", postNo)
                 .getResultList();
    }
}
