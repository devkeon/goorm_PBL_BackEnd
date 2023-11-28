package pbl.goorm.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pbl.goorm.board.model.ApiResponseStatus;
import pbl.goorm.board.model.DeleteStatus;
import pbl.goorm.board.model.dto.CommentCreateDto;
import pbl.goorm.board.model.dto.CommentDeleteDto;
import pbl.goorm.board.model.dto.CommentUpdateDto;
import pbl.goorm.board.model.entity.Board;
import pbl.goorm.board.model.entity.Comment;
import pbl.goorm.board.model.response.ApiResponse;
import pbl.goorm.board.repository.BoardRepository;
import pbl.goorm.board.repository.CommentRepository;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Override
    public Board save(CommentCreateDto createDto) {
        Board findBoard = boardRepository.findById(createDto.getBoardId())
                .stream().findAny()
                .orElseThrow(() -> new RuntimeException("no such board"));
        Comment comment = new Comment();
        comment.setBody(createDto.getBody());
        comment.setDeleteStatus(DeleteStatus.ACTIVE);
        comment.setBoard(findBoard);
        findBoard.addComment(comment);
        findBoard.setComments(findBoard.getComments().stream()
                .filter(c -> c.getDeleteStatus().equals(DeleteStatus.ACTIVE)).toList());
        return findBoard;
    }

    @Override
    public ApiResponse delete(CommentDeleteDto deleteDto) {
        try {
            commentRepository.delete(deleteDto.getId());
        } catch (RuntimeException e) {
            return new ApiResponse(404, ApiResponseStatus.FAIL, e.getMessage());
        }
        return new ApiResponse(200, ApiResponseStatus.OK);
    }

    @Override
    public ApiResponse update(Long commentId, CommentUpdateDto updateDto) {
        try {
            commentRepository.update(commentId, updateDto);
        } catch (RuntimeException e) {
            return new ApiResponse(404, ApiResponseStatus.FAIL, e.getMessage());
        }
        return new ApiResponse(200, ApiResponseStatus.OK);
    }
}
