package com.example.schedulingtasks.domain.attributeconverter;

import com.example.schedulingtasks.enums.AccessLevelEnum;

import javax.persistence.AttributeConverter;

public class AccessLevelAttributeConverter implements AttributeConverter<AccessLevelEnum, String> {
    @Override
    public String convertToDatabaseColumn(final AccessLevelEnum accessLevelEnum) {
        return accessLevelEnum.getValue();
    }

    @Override
    public AccessLevelEnum convertToEntityAttribute(final String accessLevel) {
        return AccessLevelEnum.valueOf(accessLevel);
    }
}
