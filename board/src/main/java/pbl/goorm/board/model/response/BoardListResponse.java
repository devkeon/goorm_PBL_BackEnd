package pbl.goorm.board.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pbl.goorm.board.model.entity.Board;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardListResponse {

    private Long boardNo;
    private String title;

    public static BoardListResponse of(Board board) {
        return new BoardListResponse(board.getId(), board.getTitle());
    }
}
