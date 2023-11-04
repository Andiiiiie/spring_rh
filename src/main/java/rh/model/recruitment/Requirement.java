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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requirement_type_id")
    private RequirementType requirementType;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "requirement", orphanRemoval = true, cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<RequirementAnswer> requirementAnswers = new ArrayList<>();

    @Column(name = "coef", nullable = false)
    private double coef = 1;
}