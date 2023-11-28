package pbl.goorm.board.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pbl.goorm.board.model.entity.Comment;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private Long commentNo;
    private String body;

    public static CommentResponse of(Comment comment){
        return new CommentResponse(comment.getId(), comment.getBody());
    }
}
