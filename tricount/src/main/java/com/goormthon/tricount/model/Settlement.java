package com.goormthon.tricount.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Settlement {

    private Long id;

    @NotNull
    private String name;

}
