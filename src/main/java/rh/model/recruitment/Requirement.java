package rh.model.recruitment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "requirement")
public class Requirement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "requirement_type_id")
    private RequirementType requirementType;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "requirement_id")
    private List<Answer> answers = new ArrayList<>();

    @Column(name = "coef", nullable = false)
    private double coef = 1;
}