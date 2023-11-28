package pbl.goorm.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pbl.goorm.board.model.dto.BoardCreateDto;
import pbl.goorm.board.model.dto.BoardDeleteDto;
import pbl.goorm.board.model.dto.BoardListRequest;
import pbl.goorm.board.model.dto.BoardUpdateDto;
import pbl.goorm.board.model.entity.Board;
import pbl.goorm.board.model.response.ApiResponse;
import pbl.goorm.board.model.response.BoardListResponse;
import pbl.goorm.board.model.response.BoardResponse;
import pbl.goorm.board.service.BoardService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService service;

    //등록
    @PostMapping("/create")
    public Board create(@RequestBody BoardCreateDto boardCreateDto) {
        return service.save(boardCreateDto);
    }

    //전체 조회
    @GetMapping("/all")
    public List<BoardListResponse> findAll(@RequestBody BoardListRequest listRequest) {
        return service.findAllBoards(listRequest.getStart(), listRequest.getPageSize());
    }

    //단건 조회
    @GetMapping("/{id}")
    public BoardResponse findById(@PathVariable Long id) {
        return service.findBoardAndComments(id);
    }

    //수정
    @PostMapping("/update")
    public ApiResponse update(@RequestBody BoardUpdateDto updateDto) {
        return service.updateBoard(updateDto);
    }

    //삭제
    @DeleteMapping("/delete")
    public ApiResponse delete(@RequestBody BoardDeleteDto deleteDto) {
        return service.delete(deleteDto);
    }
}
