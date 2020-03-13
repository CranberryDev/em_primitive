package com.example.schedulingtasks.domain.repository;

import com.example.schedulingtasks.domain.entity.UserNote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserNoteRepository extends JpaRepository<UserNote, Long> {

    @Query("select un from UserNote un " +
            "left join fetch un.note " +
            "where un.user.id = :userId " +
            "and " +
            "(un.accessLevel.value = com.example.schedulingtasks.enums.AccessLevelEnum.OWNER " +
            "or un.accessLevel.value = com.example.schedulingtasks.enums.AccessLevelEnum.READ " +
            "or un.accessLevel.value = com.example.schedulingtasks.enums.AccessLevelEnum.WRITE)")
    @Transactional(readOnly = true)
    List<UserNote> retrieveAllByUser(final Long userId, final Pageable page);


    @Query("select un from UserNote un " +
            "left join fetch un.note " +
            "where un.user.id = :userId and un.note.id in :noteIds " +
            "and " +
            "(un.accessLevel.value = com.example.schedulingtasks.enums.AccessLevelEnum.OWNER " +
            "or un.accessLevel.value = com.example.schedulingtasks.enums.AccessLevelEnum.READ " +
            "or un.accessLevel.value = com.example.schedulingtasks.enums.AccessLevelEnum.WRITE)")
    @Transactional(readOnly = true)
    List<UserNote> retrieveAllByUserAndByNoteIds(final Long userId, final List<Long> noteIds);


}
