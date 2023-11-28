package com.goormthon.tricount.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    Long id;

    @NotNull
    String loginId;
    @NotEmpty
    String password;
    @NotNull
    String name;

}
