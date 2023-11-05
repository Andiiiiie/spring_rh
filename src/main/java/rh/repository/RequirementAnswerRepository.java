package rh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rh.model.recruitment.RequirementAnswer;

public interface RequirementAnswerRepository extends JpaRepository<RequirementAnswer, Long> {
}