package rh.repository.recruitment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import rh.model.recruitment.Advertisement;

public interface AdvertisementRepository extends
        JpaRepository<Advertisement, Long>,
        CrudRepository<Advertisement, Long> {
    Advertisement findByRequestId(Long id);
}