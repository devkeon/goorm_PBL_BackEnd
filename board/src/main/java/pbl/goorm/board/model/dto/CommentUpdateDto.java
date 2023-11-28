package pbl.goorm.board.model.dto;

import lombok.Data;

@Data
public class CommentUpdateDto {

    private String body;

    public CommentUpdateDto() {
    }

    public CommentUpdateDto(String body) {
        this.body = body;
    }
}
