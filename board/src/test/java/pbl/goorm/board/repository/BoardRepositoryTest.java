package pbl.goorm.board.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pbl.goorm.board.model.DeleteStatus;
import pbl.goorm.board.model.entity.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @BeforeEach
    void beforeEach() {
        Board board = new Board();
        board.setBody("testBody");
        board.setTitle("test");
        board.setWriterName("testBoard");
        board.setDeleteStatus(DeleteStatus.ACTIVE);
        boardRepository.save(board);
    }

    @AfterEach
    void afterEach() {
        boardRepository.clean();
    }

    @Test
    void save(){
        Board findBoard = boardRepository.findById(1L).orElseThrow();
        assertThat(findBoard.getId()).isEqualTo(1L);
    }

    @Test
    void delete(){
        boardRepository.delete(1L);
        Optional<Board> findBoard = boardRepository.findById(1L);
        log.info("findBoard = {}", findBoard);
        assertThat(findBoard.isEmpty()).isTrue();
    }

    @Test
    void update() {
        Board updateBoard = new Board();
        updateBoard.setBody("modified");
        updateBoard.setTitle("test");
        boardRepository.update(updateBoard);
        Board findBoard = boardRepository.findById(1L).orElseThrow();
        assertThat(findBoard.getBody()).isEqualTo(updateBoard.getBody());
    }

    @Test
    void findAll() {
        Board board = new Board(2L,
                "test2",
                "testBoard2",
                "testBody2",
                DeleteStatus.ACTIVE,
                new ArrayList<>());
        boardRepository.save(board);
        List<Board> boards = boardRepository.findAll(0, 10);
        assertThat(boards.size()).isEqualTo(2);
    }

}
