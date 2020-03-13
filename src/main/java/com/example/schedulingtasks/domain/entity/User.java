package com.example.schedulingtasks.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "user", schema = "test")
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String state;

    private Integer number;
    private Double money;

    private Boolean isApplied;

    private Date date;

//    @ManyToMany
//    @JoinTable(
//            name = "user_note", schema = "test",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "note_id")
//    )
//    private List<Note> notes;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<UserNote> userNotes;
}
