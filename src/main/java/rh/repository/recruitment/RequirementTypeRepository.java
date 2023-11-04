package rh.repository.recruitment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rh.model.recruitment.RequirementType;

@Repository
public interface RequirementTypeRepository
        extends JpaRepository<RequirementType, Long>,
        CrudRepository<RequirementType, Long> {
}