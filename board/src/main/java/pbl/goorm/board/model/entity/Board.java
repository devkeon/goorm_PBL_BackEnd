package pbl.goorm.board.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import pbl.goorm.board.model.DeleteStatus;

import java.util.List;

@Slf4j
@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_id")
    private Long id;

    private String writerName;
    private String title;
    private String body;

    @Enumerated(EnumType.STRING)
    private DeleteStatus deleteStatus;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    private List<Comment> comments;

    public Board addComment(Comment comment){
        comment.setBoard(this);
        comment.setDeleteStatus(DeleteStatus.ACTIVE);
        this.getComments().add(comment);
        return this;
    }
}
