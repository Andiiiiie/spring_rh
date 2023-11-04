package rh.model.global;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import rh.model.util.Privilege;

@Getter
@Setter
@Entity
@Table(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role = "USER";

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service; // null if candidate
}