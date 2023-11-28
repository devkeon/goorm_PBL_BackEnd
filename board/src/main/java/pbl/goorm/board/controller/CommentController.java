package pbl.goorm.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pbl.goorm.board.model.dto.CommentCreateDto;
import pbl.goorm.board.model.dto.CommentDeleteDto;
import pbl.goorm.board.model.dto.CommentUpdateDto;
import pbl.goorm.board.model.entity.Board;
import pbl.goorm.board.model.response.ApiResponse;
import pbl.goorm.board.service.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService service;

    @PostMapping("/create")
    public Board create(@RequestBody CommentCreateDto createDto) {
        return service.save(createDto);
    }

    @DeleteMapping("/delete")
    public ApiResponse delete(@RequestBody CommentDeleteDto deleteDto) {
        return service.delete(deleteDto);
    }

    @PostMapping("/update/{id}")
    public ApiResponse update(@PathVariable Long id, @RequestBody CommentUpdateDto updateDto) {
        return service.update(id, updateDto);
    }
}
