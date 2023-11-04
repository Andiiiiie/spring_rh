package rh.repository.recruitment;

import org.springframework.data.jpa.repository.JpaRepository;
import rh.model.recruitment.RequirementAnswer;

public interface RequirementsAnswerRepository extends JpaRepository<RequirementAnswer, Long> {
}