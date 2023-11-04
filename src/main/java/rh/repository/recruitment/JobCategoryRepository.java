package rh.repository.recruitment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import rh.model.recruitment.JobCategory;

public interface JobCategoryRepository
        extends JpaRepository<JobCategory, Long>,
        CrudRepository<JobCategory, Long> {
}