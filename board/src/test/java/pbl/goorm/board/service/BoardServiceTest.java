package pbl.goorm.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pbl.goorm.board.model.DeleteStatus;
import pbl.goorm.board.model.dto.BoardCreateDto;
import pbl.goorm.board.model.dto.BoardDeleteDto;
import pbl.goorm.board.model.dto.BoardUpdateDto;
import pbl.goorm.board.model.entity.Board;
import pbl.goorm.board.model.entity.Comment;
import pbl.goorm.board.model.response.BoardListResponse;
import pbl.goorm.board.model.response.BoardResponse;
import pbl.goorm.board.repository.BoardRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
public class BoardServiceTest {

    @Autowired
    BoardService service;

    @Autowired
    BoardRepository repository;

    @BeforeEach
    void beforeEach() {
        Board board1 = new Board(1L,
                "test",
                "testBoard",
                "testBody",
                DeleteStatus.ACTIVE,
                new ArrayList<>());
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setBody("testComment1");
        Board board = board1.addComment(comment);
        repository.save(board);

        Board board2 = new Board(2L,
                "test",
                "testBoard",
                "testBody",
                DeleteStatus.ACTIVE,
                new ArrayList<>());
        Comment comment3 = new Comment();
        comment3.setId(3L);
        comment3.setBody("testComment3");
        Board board3 = board2.addComment(comment3);
        repository.save(board3);
    }

    @Test
    void save(){
        BoardCreateDto boardCreateDto = new BoardCreateDto("testWriter", "testTitle",
                "testBody", DeleteStatus.ACTIVE);
        Board saveBoard = service.save(boardCreateDto);
        assertThat(saveBoard.getTitle()).isEqualTo(boardCreateDto.getTitle());
    }

    @Test
    void delete() {
        BoardDeleteDto deleteDto = new BoardDeleteDto(2L);
        service.delete(deleteDto);
        RuntimeException e = Assertions.assertThrows(RuntimeException.class, () -> service.findBoardAndComments(deleteDto.getId()));
        log.info("error message={}", e.getMessage());
    }

    @Test
    void updateBoard(){
        BoardUpdateDto updateDto = new BoardUpdateDto(1L, "modified", "modifiedBody");
        service.updateBoard(updateDto);
        BoardResponse findBoard = service.findBoardAndComments(updateDto.getId());
        assertThat(findBoard.getBody()).isEqualTo(updateDto.getBody());
    }

    @Test
    void findAllBoards(){
        List<BoardListResponse> allBoards = service.findAllBoards(0, 10);
        assertThat(allBoards.size()).isEqualTo(2);
    }

}
