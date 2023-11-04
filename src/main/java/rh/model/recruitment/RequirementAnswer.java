package rh.model.recruitment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "requirement_answer")
public class RequirementAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "mark", nullable = false)
    private double mark;


    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "requirement_id")
    private Requirement requirement;

    public RequirementAnswer() {
    }

    public RequirementAnswer(String value, double mark, Requirement requirement) {
        this.value = value;
        this.mark = mark;
        this.requirement = requirement;
    }
}