package rh.model.recruitment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "requirement_answer")
public class TestAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "mark", nullable = false)
    private double mark;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "test_id")
    private Test test;


    public TestAnswer() {
    }

    public TestAnswer(String value, double mark, Test test) {
        this.value = value;
        this.mark = mark;
        this.test = test;
    }
}