package rh.service.recruitment;

import org.springframework.stereotype.Service;
import rh.model.global.User;
import rh.model.recruitment.Advertisement;
import rh.model.recruitment.Candidature;
import rh.model.recruitment.RequirementAnswer;
import rh.model.recruitment.TestAnswer;
import rh.repository.RequirementAnswerRepository;
import rh.repository.recruitment.CandidatureRepository;
import rh.repository.recruitment.TestAnswerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CandidatureService {

    private final CandidatureRepository candidatureRepository;
    private final RequirementAnswerRepository requirementAnswerRepository;
    private final TestAnswerRepository testAnswerRepository;

    public CandidatureService(CandidatureRepository candidatureRepository,
                              RequirementAnswerRepository requirementAnswerRepository,
                              TestAnswerRepository testAnswerRepository) {
        this.candidatureRepository = candidatureRepository;
        this.requirementAnswerRepository = requirementAnswerRepository;
        this.testAnswerRepository = testAnswerRepository;
    }

    public Candidature candidate(User user, Advertisement advertisement) {
        Optional<Candidature> candidatureO = candidatureRepository.findFirstByUserAndAdvertisement(user, advertisement);
        Candidature candidature = new Candidature();
        if (candidatureO.isPresent())
            candidature = candidatureO.get();

        candidature.setUser(user);
        candidature.setAdvertisement(advertisement);
        candidatureRepository.save(candidature);
        return candidature;
    }

    public void answerRequirements(Candidature candidature, List<Long> requirementsAnswersId) {
        double total = 0;
        double coefficient = 0;
        for (Long requirementAnswerId : requirementsAnswersId) {
            Optional<RequirementAnswer> requirementAnswer = requirementAnswerRepository.findById(requirementAnswerId);
            if (requirementAnswer.isPresent()) {
                total += requirementAnswer.get().getMarkWithCoefficient();
                coefficient += requirementAnswer.get().getRequirement().getCoef();
            }
        }

        candidature.setRequirementsMark(total / coefficient);
        candidature.setState(5);
        candidatureRepository.save(candidature);
    }

    public void answerTest(Candidature candidature, List<Long> testsAnswersId) {
        double total = 0;
        double coefficient = 0;
        for (Long testAnswerId : testsAnswersId) {
            Optional<TestAnswer> testAnswer = testAnswerRepository.findById(testAnswerId);
            if (testAnswer.isPresent()) {
                total += testAnswer.get().getMarkWithCoefficient();
                coefficient += testAnswer.get().getTest().getCoef();
            }
        }

        candidature.setTestsMark(total / coefficient);
        candidature.setState(10);
        candidatureRepository.save(candidature);
    }
}
