package pbl.goorm.board.service;

import pbl.goorm.board.model.dto.CommentCreateDto;
import pbl.goorm.board.model.dto.CommentDeleteDto;
import pbl.goorm.board.model.dto.CommentUpdateDto;
import pbl.goorm.board.model.entity.Board;
import pbl.goorm.board.model.response.ApiResponse;

public interface CommentService {

    public Board save(CommentCreateDto createDto);

    public ApiResponse delete(CommentDeleteDto deleteDto);

    public ApiResponse update(Long commentId, CommentUpdateDto updateDto);


}
