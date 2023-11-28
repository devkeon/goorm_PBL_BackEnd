package pbl.goorm.board.repository;

import pbl.goorm.board.model.dto.CommentUpdateDto;
import pbl.goorm.board.model.entity.Comment;

import java.util.Optional;

public interface CommentRepository {

    public void delete(Long commentId);

    public void update(Long commentId, CommentUpdateDto updateDto);

    Optional<Comment> findById(Long commentId);

    public void clear();

}
