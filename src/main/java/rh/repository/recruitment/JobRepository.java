package rh.repository.recruitment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rh.model.recruitment.Job;

@Repository
public interface JobRepository
        extends JpaRepository<Job, Long>,
        CrudRepository<Job, Long> {
}