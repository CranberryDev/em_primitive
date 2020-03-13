package com.example.schedulingtasks.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "note", schema = "test")
@Getter
@Setter
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinTable
//    private AccessLevel accessLevel;

    public void copyFrom(final Note from) {
        this
                .setText(from.getText())
                .setId(from.getId());
    }

}
