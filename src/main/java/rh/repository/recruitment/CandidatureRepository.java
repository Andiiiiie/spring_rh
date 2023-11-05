package rh.repository.recruitment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import rh.model.global.User;
import rh.model.recruitment.Advertisement;
import rh.model.recruitment.Candidature;

import java.util.List;
import java.util.Optional;

public interface CandidatureRepository
        extends JpaRepository<Candidature, Long>,
        CrudRepository<Candidature, Long> {
    Optional<Candidature> findFirstByUserAndAdvertisement(User user, Advertisement advertisement);

    List<Candidature> findAllByUserAndStateNot(User user, int i);
}