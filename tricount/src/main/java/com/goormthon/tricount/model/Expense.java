package com.goormthon.tricount.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "name", "memberName", "expenseDateTime", "memberId"})
public class Expense {
    private Long id;

    @NotNull
    private String name;
    private String memberName;
    @NotNull
    private Integer price;
    @NotNull
    private Long memberId;
    private String expenseDateTime;

}
