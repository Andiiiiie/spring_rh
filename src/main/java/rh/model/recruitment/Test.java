package rh.model.recruitment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "test")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String question;

    @OneToMany(mappedBy = "test", orphanRemoval = true, cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<TestAnswer> testAnswers = new ArrayList<>();

    @Column(name = "coef", nullable = false)
    private double coef = 1;
}