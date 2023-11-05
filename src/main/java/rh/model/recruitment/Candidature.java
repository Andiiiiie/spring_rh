package rh.model.recruitment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import rh.model.global.User;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "candidature")
public class Candidature {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "candidature_date")
    private Date candidatureDate = new Date();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "state", nullable = false)
    private int state = 0; // 0: creation, 5: finished requirements, 10; finished test, 15: pending, 10: accepted, -10: rejected

    @Column(name = "requirements_mark")
    private double requirementsMark;

    @Column(name = "tests_mark")
    private double testsMark;

    @Column(name = "interview_mark")
    private double interviewMark;
}