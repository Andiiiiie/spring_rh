package rh.repository.recruitment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rh.model.global.Service;
import rh.model.recruitment.Request;

import java.util.List;

@Repository
public interface RequestRepository
        extends JpaRepository<Request, Long>,
        CrudRepository<Request, Long> {

    List<Request> findAllByUser_ServiceOrderByRequestDateDesc(Service service);

    List<Request> findAllByStateNotAndStateNotOrderByRequestDateDesc(int i, int i1);
}
