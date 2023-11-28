package com.goormthon.tricount.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupDto {

    @NotNull
    private String name;

    @NotNull
    private String loginId;
    @NotNull
    private String password;

}
