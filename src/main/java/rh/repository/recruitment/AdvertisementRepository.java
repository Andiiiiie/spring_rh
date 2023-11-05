package rh.repository.recruitment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import rh.model.recruitment.Advertisement;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface AdvertisementRepository extends
        JpaRepository<Advertisement, Long>,
        CrudRepository<Advertisement, Long> {
    Advertisement findByRequestId(Long id);

    List<Advertisement> findAllByIdNotInAndEndDateAfter(List<Long> candidaturesId, Date date);


    List<Advertisement> findAllByIdNotInAndEndDateAfterAndRequestJobJobCategoryIdIn(List<Long> candidaturesId, Date date, List<Long> jobCategoryFilter);
}