package rh.repository.recruitment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rh.model.recruitment.Request;

@Repository
public interface RequestRepository
        extends JpaRepository<Request, Long>,
        CrudRepository<Request, Long> {
}
