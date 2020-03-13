package com.example.schedulingtasks.service;

import com.example.schedulingtasks.domain.dto.PageRq;
import com.example.schedulingtasks.domain.entity.AccessLevel;
import com.example.schedulingtasks.domain.entity.Note;
import com.example.schedulingtasks.domain.entity.User;
import com.example.schedulingtasks.domain.entity.UserNote;
import com.example.schedulingtasks.domain.repository.NoteRepository;
import com.example.schedulingtasks.domain.repository.UserNoteRepository;
import com.example.schedulingtasks.domain.repository.UserRepository;
import com.example.schedulingtasks.enums.AccessLevelEnum;
import com.example.schedulingtasks.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final EntityManagerFactory emf;
    private final UserRepository userRepository;
    private final UserNoteRepository userNoteRepository;
    private final NoteRepository noteRepository;

    /**
     * @param filter
     * @param page
     */
    public List<User> getAllByFilter(final User filter, final PageRq page) {
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

    public User save(final User user) {
        final EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return user;
    }

    public void delete(final Long id) {
        final EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager
                .createQuery("delete from User u where u.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public User getById(final Long id) {
        final EntityManager entityManager = emf.createEntityManager();
        User user = entityManager.find(User.class, id);
        entityManager.close();
        return user;
    }


    public List<Note> getNotesByUser(final PageRequest page) {
        final String principal = SecurityUtil.getPrincipal();
        final List<UserNote> userNotes = userNoteRepository.retrieveAllByUser(Long.valueOf(principal), page);

        return userNotes.stream().map(UserNote::getNote).collect(Collectors.toList());
    }

    @Transactional
    public List<Note> createNotes(final List<Note> notes) {
        final String principal = SecurityUtil.getPrincipal();
        final List<Note> saved = noteRepository.saveAll(notes);
        final List<UserNote> rawUserNotes = saved.stream()
                .map(savedNote -> {
                    return new UserNote()
                            .setAccessLevel(
                                    new AccessLevel().setValue(AccessLevelEnum.OWNER)
                            )
                            .setNote(savedNote)
                            .setUser(
                                    new User().setId(Long.valueOf(principal))
                            );

                })
                .collect(Collectors.toList());
        final List<UserNote> userNotes = userNoteRepository.saveAll(rawUserNotes);
        return saved;
    }

}
