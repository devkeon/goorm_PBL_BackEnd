package pbl.goorm.board.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pbl.goorm.board.model.DeleteStatus;
import pbl.goorm.board.model.entity.Board;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponse {

    private Long boardNo;
    private String writerName;
    private String title;
    private String body;
    private List<CommentResponse> comments;


    public static BoardResponse of(Board board) {
        BoardResponse boardResponse = new BoardResponse();
        boardResponse.setBoardNo(board.getId());
        boardResponse.setWriterName(board.getWriterName());
        boardResponse.setTitle(board.getTitle());
        boardResponse.setBody(board.getBody());
        boardResponse.setComments(board.getComments().stream()
                .filter(c -> c.getDeleteStatus().equals(DeleteStatus.ACTIVE))
                .map(CommentResponse::of)
                .toList());
        return boardResponse;
    }
}
