package com.example.schedulingtasks.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "access_level", schema = "test")
@Getter
@Setter
@Immutable
public class AccessLevel {

    @Id
    private String value;

}
