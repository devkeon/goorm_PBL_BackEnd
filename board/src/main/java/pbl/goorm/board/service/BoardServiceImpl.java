package pbl.goorm.board.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pbl.goorm.board.model.ApiResponseStatus;
import pbl.goorm.board.model.DeleteStatus;
import pbl.goorm.board.model.dto.BoardCreateDto;
import pbl.goorm.board.model.dto.BoardDeleteDto;
import pbl.goorm.board.model.dto.BoardUpdateDto;
import pbl.goorm.board.model.entity.Board;
import pbl.goorm.board.model.response.ApiResponse;
import pbl.goorm.board.model.response.BoardListResponse;
import pbl.goorm.board.model.response.BoardResponse;
import pbl.goorm.board.repository.BoardRepository;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository repository;
    @Override
    public Board save(BoardCreateDto createDto) {
        Board board = new Board();
        board.setTitle(createDto.getTitle());
        board.setBody(createDto.getTitle());
        board.setDeleteStatus(DeleteStatus.ACTIVE);
        board.setWriterName(createDto.getWriterName());
        Board save = repository.save(board);
        return save;
    }

    @Override
    public ApiResponse delete(BoardDeleteDto deleteDto) {
        try{
            repository.delete(deleteDto.getId());
            return new ApiResponse(200, ApiResponseStatus.OK);
        } catch (EntityNotFoundException e) {
            log.info("error message={}", e.getMessage());
            return new ApiResponse(404, ApiResponseStatus.FAIL, e.getMessage());
        }
    }

    @Override
    public ApiResponse updateBoard(BoardUpdateDto updateDto) {
        Board board = new Board();
        board.setId(updateDto.getId());
        board.setTitle(updateDto.getTitle());
        board.setBody(updateDto.getBody());
        try {
            repository.update(board);
        } catch (RuntimeException e) {
            return new ApiResponse(404, ApiResponseStatus.FAIL, e.getMessage());
        }
        return new ApiResponse(200, ApiResponseStatus.OK);
    }

    @Override
    public BoardResponse findBoardAndComments(Long boardId) {
        return repository.findById(boardId)
                .map(BoardResponse::of)
                .orElseThrow(() -> new RuntimeException("cannot found such Entity board"));
    }

    @Override
    public List<BoardListResponse> findAllBoards(int start, int pageSize) {
        return repository.findAll(start, pageSize)
                .stream()
                .map(BoardListResponse::of)
                .toList();
    }
}
