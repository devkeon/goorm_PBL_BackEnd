package pbl.goorm.board.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pbl.goorm.board.model.DeleteStatus;
import pbl.goorm.board.model.dto.BoardUpdateDto;
import pbl.goorm.board.model.entity.Board;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository{

    private final EntityManager em;

    @Override
    public Board save(Board board) {
        em.persist(board);
        return board;
    }

    @Override
    public void delete(Long boardId) {
        Board board = em.find(Board.class, boardId);
        if (board != null) {
            board.setDeleteStatus(DeleteStatus.DELETE);
        } else if (board == null) {
            throw new RuntimeException("no entity found");
        }
    }

    @Override
    public void update(Board updateBoard) {
        Board board = em.find(Board.class, updateBoard.getId());
        //TODO: 에러 throw를 repository에서도 하는지?
        if (board == null){
            throw new RuntimeException("no entity found");
        }
        board.setTitle(updateBoard.getTitle());
        board.setBody(updateBoard.getBody());
    }

    @Override
    public Optional<Board> findById(Long boardId) {

        String jpql = "select b from Board b left join fetch b.comments" +
                " where b.deleteStatus = 'ACTIVE' and b.id=:boardId";

        TypedQuery<Board> query = em.createQuery(jpql, Board.class);
        query.setParameter("boardId", boardId);

        Optional<Board> findBoard = query.getResultList()
                .stream()
                .filter(b -> b.getClass().equals(Board.class))
                .findAny();

        return findBoard;
    }

    @Override
    public List<Board> findAll(int startPage, int pageSize) {

        String jpql = "select b from Board b left join fetch b.comments" +
                " where b.deleteStatus = 'ACTIVE' order by  b.id desc";

        TypedQuery<Board> query = em.createQuery(jpql, Board.class);
        query.setFirstResult(startPage);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    @Override
    public void clean() {
        String jpql = "delete from Board";
        Query query = em.createQuery(jpql);
        query.executeUpdate();
    }
}
