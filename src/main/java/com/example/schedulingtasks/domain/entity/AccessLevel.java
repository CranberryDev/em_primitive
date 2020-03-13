package com.example.schedulingtasks.domain.entity;

import com.example.schedulingtasks.domain.attributeconverter.AccessLevelAttributeConverter;
import com.example.schedulingtasks.enums.AccessLevelEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Entity
@Table(name = "access_level", schema = "test")
@Getter
@Setter
@Immutable
public class AccessLevel {

    @Id
    @Convert(converter = AccessLevelAttributeConverter.class)
    @Enumerated(EnumType.STRING)
    private AccessLevelEnum value;

}
