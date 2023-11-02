package rh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rh.model.User;

import java.util.Optional;

@Repository
public interface UserRepository
        extends JpaRepository<User, Long>,
        CrudRepository<User, Long> {
    Optional<User> findByEmailAndPassword(String email, String password);
}