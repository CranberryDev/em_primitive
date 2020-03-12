package com.example.schedulingtasks.service;

import com.example.schedulingtasks.domain.dto.PageRq;
import com.example.schedulingtasks.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final EntityManagerFactory emf;

    /**
     * @param filter
     */
    public List<User> getAllByFilter(User filter, PageRq page) {
        final EntityManager em = emf.createEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<User> cq = cb.createQuery(User.class);
        final Root<User> root = cq.from(User.class);

        if (Objects.nonNull(filter.getId())) {
            cq.where(
                    cb.equal(root.get("id"), filter.getId())
            );
        }
        if (Objects.nonNull(filter.getIsApplied())) {
            cq.where(
                    cb.equal(root.get("isApplied"), filter.getIsApplied())
            );
        }
        if (Objects.nonNull(filter.getName())) {
            cq.where(
                    cb.equal(root.get("name"), filter.getName())
            );
        }
        if (Objects.nonNull(filter.getState())) {
            cq.where(
                    cb.equal(root.get("state"), filter.getState())
            );
        }
        if (Objects.nonNull(filter.getNumber())) {
            cq.where(
                    cb.equal(root.get("number"), filter.getNumber())
            );
        }
        if (Objects.nonNull(filter.getMoney())) {
            cq.where(
                    cb.equal(root.get("money"), filter.getMoney())
            );
        }
        if (Objects.nonNull(filter.getDate())) {
            cq.where(
                    cb.equal(root.get("date"), filter.getDate())
            );
        }

        final List<User> result = em.createQuery(cq)
                .setFirstResult(page.getOffset())
                .setMaxResults(page.getLimit())
                .getResultList();
        em.close();
        return result;
    }

    public User save(User user) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return user;
    }

    public void delete(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager
                .createQuery("delete from User u where u.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public User getById(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        User user = entityManager.find(User.class, id);
        entityManager.close();
        return user;
    }

}
