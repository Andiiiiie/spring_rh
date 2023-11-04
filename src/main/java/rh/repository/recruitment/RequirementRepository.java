package rh.repository.recruitment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import rh.model.recruitment.Requirement;

public interface RequirementRepository extends JpaRepository<Requirement, Long>,
        CrudRepository<Requirement, Long> {
}