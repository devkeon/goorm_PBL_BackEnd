package pbl.goorm.board.model.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pbl.goorm.board.model.DeleteStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardCreateDto {

    private String writerName;
    private String title;
    private String body;

    private DeleteStatus deleteStatus;
}
