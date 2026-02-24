package com.example.vibeapp.post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

// @Repository - Bean 충돌 방지를 위해 주석 처리
public class PostRepositoryOld {

    @PersistenceContext
    private EntityManager em;

    /**
     * JPQL을 사용하여 모든 게시글을 조회합니다.
     */
    public List<Post> findAll() {
        return em.createQuery("SELECT p FROM Post p ORDER BY p.createdAt DESC", Post.class)
                 .getResultList();
    }

    /**
     * em.find()를 사용하여 식별자로 게시글을 단건 조회합니다.
     */
    public Optional<Post> findById(Long no) {
        Post post = em.find(Post.class, no);
        return Optional.ofNullable(post);
    }

    /**
     * em.persist()를 사용하여 새 게시글 엔티티를 영속성 컨텍스트에 저장합니다.
     */
    public void save(Post post) {
        em.persist(post);
    }

    /**
     * 변경 감지(Dirty Checking) 기능을 활용하여 엔티티를 수정합니다.
     * 서비스 계층의 트랜잭션이 종료될 때 변경된 필드가 DB에 반영됩니다.
     */
    public void update(Post post) {
        // JPA의 영속성 컨텍스트는 관리 중인 엔티티의 변경을 자동으로 감지합니다.
        // 식별자가 있는 경우 merge()를 사용해 준영속 상태의 엔티티를 다시 영속 상태로 만들 수 있습니다.
        em.merge(post);
    }

    /**
     * em.remove()를 사용하여 영속 상태의 엔티티를 삭제합니다.
     */
    public void deleteById(Long no) {
        Post post = em.find(Post.class, no);
        if (post != null) {
            em.remove(post);
        }
    }

    /**
     * JPQL UPDATE 문을 사용하여 조회수를 직접 증가시킵니다.
     */
    public void increaseViews(Long no) {
        em.createQuery("UPDATE Post p SET p.views = p.views + 1 WHERE p.no = :no")
          .setParameter("no", no)
          .executeUpdate();
    }
}
