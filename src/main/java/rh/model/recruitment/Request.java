package rh.model.recruitment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import rh.model.global.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "service_id")
    private Service service;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "job_id")
    private Job job;

    @Column(name = "state")
    private int state; // -10: rejected, -5: draft, 0: pending, 10: accepted

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address; // lat, long

    @Column(name = "request_date")
    private LocalDateTime requestDate = LocalDateTime.now();

    @Column(name = "daily_work_hour")
    private int dailyWorkHour;

    @Column(name = "daily_person_hour")
    private int dailyPersonHour;

    @Column(name = "day_number")
    private int dayNumber;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "request_req_id")
    private List<Requirement> requirements = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "request_test_id")
    private List<Test> tests = new ArrayList<>();

}