package pbl.goorm.board.model.dto;

import lombok.Data;

@Data
public class BoardUpdateDto {

    private Long id;
    private String title;
    private String body;

    public BoardUpdateDto(Long id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public BoardUpdateDto() {
    }
}
