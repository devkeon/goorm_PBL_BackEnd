package pbl.goorm.board.service;

import pbl.goorm.board.model.dto.BoardCreateDto;
import pbl.goorm.board.model.dto.BoardDeleteDto;
import pbl.goorm.board.model.dto.BoardUpdateDto;
import pbl.goorm.board.model.entity.Board;
import pbl.goorm.board.model.response.ApiResponse;
import pbl.goorm.board.model.response.BoardListResponse;
import pbl.goorm.board.model.response.BoardResponse;

import java.util.List;

public interface BoardService {

    public Board save(BoardCreateDto createDto);

    public ApiResponse delete(BoardDeleteDto deleteDto);

    public ApiResponse updateBoard(BoardUpdateDto updateDto);

    public BoardResponse findBoardAndComments(Long boardId);

    public List<BoardListResponse> findAllBoards(int start, int pageSize);

}
