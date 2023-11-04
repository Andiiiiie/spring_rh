package rh.repository.recruitment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rh.model.recruitment.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
}