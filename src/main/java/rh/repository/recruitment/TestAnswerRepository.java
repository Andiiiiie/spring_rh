package rh.repository.recruitment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rh.model.recruitment.TestAnswer;

@Repository
public interface TestAnswerRepository extends JpaRepository<TestAnswer, Long> {
}