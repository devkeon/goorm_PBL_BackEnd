package pbl.goorm.board.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pbl.goorm.board.model.DeleteStatus;
import pbl.goorm.board.model.dto.CommentCreateDto;
import pbl.goorm.board.model.dto.CommentDeleteDto;
import pbl.goorm.board.model.dto.CommentUpdateDto;
import pbl.goorm.board.model.entity.Board;
import pbl.goorm.board.model.entity.Comment;
import pbl.goorm.board.repository.BoardRepository;
import pbl.goorm.board.repository.CommentRepository;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
public class CommentServiceTest {

    @Autowired
    CommentService service;

    @Autowired
    CommentRepository repository;

    @Autowired
    BoardRepository boardRepository;


    @AfterEach
    void afterEach() {
        repository.clear();
    }

    @Test
    void save(){
        Board board = new Board(1L,
                "test",
                "testBoard",
                "testBody",
                DeleteStatus.ACTIVE,
                new ArrayList<>());
        boardRepository.save(board);
        CommentCreateDto createDto = new CommentCreateDto("test");
        Board save = service.save(board, createDto);
        Comment comment = save.getComments().stream()
                .findAny()
                .orElseThrow();
        assertThat(comment.getBody()).isEqualTo(createDto.getBody());
    }

    @Test
    void delete() {
        Board board = new Board(1L,
                "test",
                "testBoard",
                "testBody",
                DeleteStatus.ACTIVE,
                new ArrayList<>());
        boardRepository.save(board);
        CommentCreateDto createDto = new CommentCreateDto("test");
        Board save = service.save(board, createDto);
        Comment findComment = save.getComments().stream().findAny().orElseThrow();

        CommentDeleteDto commentDeleteDto = new CommentDeleteDto(findComment.getId());
        service.delete(commentDeleteDto);

        assertThat(repository.findById(findComment.getId()).isEmpty()).isTrue();
    }

    @Test
    void update() {
        Board board = new Board(1L,
                "test",
                "testBoard",
                "testBody",
                DeleteStatus.ACTIVE,
                new ArrayList<>());
        boardRepository.save(board);
        CommentCreateDto createDto = new CommentCreateDto("test");
        Board save = service.save(board, createDto);
        Comment comment = save.getComments().stream()
                .findAny()
                .orElseThrow();

        CommentUpdateDto updateDto = new CommentUpdateDto("modifed");
        service.update(comment.getId(), updateDto);
        Comment findComment = repository.findById(comment.getId()).orElseThrow();
        assertThat(findComment.getBody()).isEqualTo(updateDto.getBody());
    }
}
