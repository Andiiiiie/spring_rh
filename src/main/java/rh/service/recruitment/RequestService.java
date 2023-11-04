package rh.service.recruitment;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import rh.model.global.User;
import rh.model.recruitment.*;
import rh.repository.recruitment.*;

import java.util.List;

@Getter
@Setter
@Service
public class RequestService {
    private final RequestRepository requestRepository;
    private final RequirementRepository requirementRepository;
    private final RequirementsAnswerRepository requirementsAnswerRepository;
    private final TestRepository testRepository;
    private final TestAnswerRepository testAnswerRepository;

    public RequestService(RequestRepository requestRepository,
                          RequirementRepository requirementRepository,
                          RequirementsAnswerRepository requirementsAnswerRepository,
                          TestRepository testRepository,
                          TestAnswerRepository testAnswerRepository) {
        this.requestRepository = requestRepository;
        this.requirementRepository = requirementRepository;
        this.requirementsAnswerRepository = requirementsAnswerRepository;
        this.testRepository = testRepository;
        this.testAnswerRepository = testAnswerRepository;
    }

    public Request createEmptyService() {
        return requestRepository.save(new Request());
    }

    public void create(Long id, Request request, User user) {
        request.setId(id);
        request.setState(-5);
        request.setUser(user);
        requestRepository.save(request);
    }

    public List<Request> getListForRh() {
        return requestRepository.findAllByStateNotAndStateNotOrderByRequestDateDesc(0, -5);
    }

    public List<Request> getListCreatedByService(rh.model.global.Service service) {
        return requestRepository.findAllByUser_ServiceOrderByRequestDateDesc(service);
    }

    public void addRequirement(Request request, Requirement requirement, List<String> answersValue, List<Double> answersMark) {
        requirementRepository.save(requirement);
        RequirementAnswer requirementAnswer;
        for (int i = 0; i < answersValue.size(); i++) {
            requirementAnswer = new RequirementAnswer(answersValue.get(i), answersMark.get(i), requirement);
            requirementsAnswerRepository.save(requirementAnswer);
            requirement.getRequirementAnswers().add(requirementAnswer);
        }
        requirementRepository.save(requirement);

        request.getRequirements().add(requirement);
        requestRepository.save(request);
    }

    public void deleteRequirement(Request request, Requirement requirement) {
        request.getRequirements().remove(requirement);
        requestRepository.save(request);

        requirementRepository.delete(requirement);
    }

    public void addTest(Request request, Test test, List<String> answersValue, List<Double> answersMark) {
        testRepository.save(test);

        TestAnswer testAnswer;
        for (int i = 0; i < answersValue.size(); i++) {
            testAnswer = new TestAnswer(answersValue.get(i), answersMark.get(i), test);
            testAnswerRepository.save(testAnswer);
            test.getTestAnswers().add(testAnswer);
        }
        testRepository.save(test);

        request.getTests().add(test);
        requestRepository.save(request);
    }

    public void deleteTest(Request request, Test test) {
        request.getTests().remove(test);
        requestRepository.save(request);

        testRepository.delete(test);
    }
}
