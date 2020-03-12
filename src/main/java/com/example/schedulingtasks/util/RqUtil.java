package com.example.schedulingtasks.util;

import com.example.schedulingtasks.domain.dto.PageRq;
import lombok.NoArgsConstructor;

import java.util.Objects;


@NoArgsConstructor
public class RqUtil {

    public static void checkPage(final PageRq page) {
        if(Objects.isNull(page) || Objects.isNull(page.getLimit()) || Objects.isNull(page.getOffset())) {
            throw new RuntimeException();
        }
    }

}
