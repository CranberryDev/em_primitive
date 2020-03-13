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
import java.util.Set;
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


    public List<UserNote> getNotesByUser(final PageRequest page) {
        final String principal = SecurityUtil.getPrincipal();

        return userNoteRepository.retrieveAllByUser(Long.valueOf(principal), page);
    }

    /**
     * @param notes - all objects in the list should not contain id value
     * @return - created entity list
     */
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
        userNoteRepository.saveAll(rawUserNotes);
        return saved;
    }

    /**
     * @param notes - all objects is the list should contain id value as not null
     * @return - updated entity list
     */
    @Transactional
    public List<Note> updateNotes(final List<Note> notes) {
        final String principal = SecurityUtil.getPrincipal();
        final List<Long> noteIds = notes.stream().map(Note::getId).collect(Collectors.toList());
        final List<UserNote> userNotes = userNoteRepository.retrieveAllByUserAndByNoteIds(
                Long.valueOf(principal),
                noteIds
        );
        final Set<Note> notesForUpdate = userNotes.stream()
                .map(userNote -> {
                    final Note from = notes.stream()
                            .filter(newNote -> {
                                final Note oldNote = userNote.getNote();
                                return Objects.equals(oldNote.getId(), newNote.getId());
                            })
                            .findFirst()
                            .orElseThrow(RuntimeException::new);
                    userNote.getNote().copyFrom(from);
                    return userNote.getNote();
                })
                .collect(Collectors.toSet());
        return noteRepository.saveAll(notesForUpdate);
    }

    @Transactional
    public void grantAuthority(final List<UserNote> userNotes) {
        checkPermission(userNotes);
        checkPublicPermissionSubmitting(userNotes);

        userNoteRepository.saveAll(userNotes);
    }

    @Transactional
    public void removeAuthority(final List<UserNote> userNotes) {
        checkPermission(userNotes);

        final List<Long> userNoteIds = userNotes.stream()
                .map(userNote -> userNote.getNote().getId())
                .collect(Collectors.toList());
        final List<UserNote> userNotesForDeletion =
                userNoteRepository.retrieveAllByUserAndByNoteIdsAndByAccessLevelIsOwner(
                        Long.valueOf(SecurityUtil.getPrincipal()),
                        userNoteIds
                );
        userNoteRepository.deleteInBatch(userNotesForDeletion);
    }

    /**
     * Check if all wanted userNotes are accessible for current user
     * Check by comparing id of wanted userNotes with accessible userNotes
     * in case of insufficient permission throw exception
     *
     * @param userNotes
     * @throws RuntimeException - if don't have permission for some entities
     */
    private void checkPermission(final List<UserNote> userNotes) {
        final String principal = SecurityUtil.getPrincipal();
        final List<Long> noteIds = userNotes.stream()
                .map(UserNote::getNote)
                .map(Note::getId)
                .collect(Collectors.toList());

        final List<UserNote> accessibleNotes =
                userNoteRepository.retrieveAllByUserAndByNoteIdsAndByAccessLevelIsOwner(Long.valueOf(principal), noteIds);
        userNotes
                .forEach(userNote -> {
                    final Long wantedNoteId = userNote.getNote().getId();
                    final boolean hasAccess = accessibleNotes.stream()
                            .map(accessibleNote -> accessibleNote.getNote().getId())
                            .anyMatch(accessibleId -> Objects.equals(wantedNoteId, accessibleId));
                    if (!hasAccess) {
                        throw new RuntimeException();
                    }
                });
    }

    /**
     * Public access can ba assigned only on owner(if multiple owners then on current user(if owner))
     *
     * @param userNotes
     */
    private void checkPublicPermissionSubmitting(final List<UserNote> userNotes) {
        final String principal = SecurityUtil.getPrincipal();
        final boolean canAssignPublicAccessOnNote = userNotes.stream()
                .filter(userNote -> AccessLevelEnum.PUBLIC_READ == userNote.getAccessLevel().getValue())
                .allMatch(userNote -> Objects.equals(Long.valueOf(principal), userNote.getUser().getId()));
        if (!canAssignPublicAccessOnNote) {
            throw new RuntimeException();
        }
    }

}
