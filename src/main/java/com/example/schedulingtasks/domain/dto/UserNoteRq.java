package com.example.schedulingtasks.domain.dto;

import com.example.schedulingtasks.enums.AccessLevelEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserNoteRq {

    private Long user;
    private Long note;
    private AccessLevelEnum accessLevel;

}
