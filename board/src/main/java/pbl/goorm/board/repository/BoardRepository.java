package pbl.goorm.board.repository;

import pbl.goorm.board.model.dto.BoardUpdateDto;
import pbl.goorm.board.model.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    public Board save(Board board);

    public void delete(Long boardId);

    public void update(Board board);

    public Optional<Board> findById(Long boardId);

    public List<Board> findAll(int startPage, int pageSize);

    public void clean();

}
