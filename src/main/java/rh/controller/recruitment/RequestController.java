package rh.controller.recruitment;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rh.config.CustomUserDetails;
import rh.model.global.Service;
import rh.model.recruitment.*;
import rh.repository.recruitment.*;
import rh.service.recruitment.RequestService;

import java.util.List;
import java.util.Optional;

@Controller
public class RequestController {
    RequestService requestService;
    private final JobRepository jobRepository;
    private final RequestRepository requestRepository;
    private final RequirementTypeRepository requirementTypeRepository;
    private final RequirementRepository requirementRepository;

    public RequestController(RequestService requestService,
                             JobRepository jobRepository,
                             RequestRepository requestRepository,
                             RequirementTypeRepository requirementTypeRepository,
                             RequirementRepository requirementRepository) {
        this.requestService = requestService;
        this.jobRepository = jobRepository;
        this.requestRepository = requestRepository;
        this.requirementTypeRepository = requirementTypeRepository;
        this.requirementRepository = requirementRepository;
    }

    // List
    @GetMapping("/request/list")
    public String list(Model model) {
        List<Request> requests = requestService.getListForRh();

        model.addAttribute("requests", requests);
        return "recruitment/request/list";
    }

    @GetMapping("/request/list/service")
    public String listService(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Service service = userDetails.getService();

        List<Request> requests = requestService.getListCreatedByService(service);

        model.addAttribute("requests", requests);
        model.addAttribute("service", service);
        return "recruitment/request/list_service";
    }

    // Detail
    @GetMapping("/request/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Optional<Request> requestC = requestRepository.findById(id);
        if (requestC.isEmpty()) return "redirect:/request/list";

        Request request = requestC.get();
        model.addAttribute("request", request);
        return "recruitment/request/detail";
    }


    // Create general info
    @GetMapping("/request/create")
    public String create() {
        Long id = requestService.createEmptyService().getId();
        return "redirect:/request/create/" + id;
    }

    @GetMapping("/request/create/{id}")
    public String create(@PathVariable Long id, @ModelAttribute("request") Request request, Model model) {
        List<Job> jobs = jobRepository.findAll(Sort.by("jobCategory.name", "title"));

        Optional<Request> requestC = requestRepository.findById(id);
        if (requestC.isEmpty()) return "redirect:/request/list";
        request = requestC.get();

        model.addAttribute("id", id);
        model.addAttribute("jobs", jobs);
        model.addAttribute("request", request);
        return "recruitment/request/form";
    }

    @PostMapping("/request/create/{id}")
    public String create(@PathVariable Long id, @ModelAttribute("request") Request request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        requestService.create(id, request, userDetails.getUser());
        return "redirect:/request/create/" + id;
    }


    // Requirements
    @GetMapping("/request/create/{requestId}/requirements")
    public String createRequirements(@PathVariable Long requestId, Model model, @ModelAttribute("requirement") Requirement requirement) {
        Optional<Request> requestC = requestRepository.findById(requestId);
        if (requestC.isEmpty()) return "redirect:/request/list";

        List<RequirementType> requirementTypes = requirementTypeRepository.findAll(Sort.by("name"));
        List<Requirement> requirements = requirementRepository.findAll(Sort.by("requirementType.name", "name"));

        Request request = requestC.get();
        model.addAttribute("request", request);
        model.addAttribute("requirementTypes", requirementTypes);
        model.addAttribute("requirements", requirements);

        return "recruitment/request/requirements";
    }

    @PostMapping("/request/create/{requestId}/requirements")
    public String createRequirements(@PathVariable Long requestId,
                                     @ModelAttribute("requirement") Requirement requirement,
                                     @RequestParam("answer_value[]") List<String> answersValue,
                                     @RequestParam("answer_mark[]") List<Double> answersMark) {
        Optional<Request> requestC = requestRepository.findById(requestId);
        if (requestC.isEmpty()) return "redirect:/request/list";

        requestService.addRequirement(requestC.get(), requirement, answersValue, answersMark);

        return "redirect:/request/create/" + requestId + "/requirements";
    }

    @GetMapping("/request/create/{requestId}/requirements/{requirementId}/delete")
    public String deleteRequirement(@PathVariable Long requestId, @PathVariable Long requirementId) {
        Optional<Request> requestC = requestRepository.findById(requestId);
        if (requestC.isEmpty()) return "redirect:/request/list";

        Optional<Requirement> requirementC = requirementRepository.findById(requirementId);
        if (requirementC.isEmpty()) return "redirect:/request/create/" + requestId + "/requirements";

        requestService.deleteRequirement(requestC.get(), requirementC.get());

        return "redirect:/request/create/" + requestId + "/requirements";
    }


    // Tests
    @GetMapping("/request/create/{requestId}/tests")
    public String createTests(@PathVariable Long requestId, Model model, @ModelAttribute("test") Test test) {
        Optional<Request> requestC = requestRepository.findById(requestId);
        if (requestC.isEmpty()) return "redirect:/request/list";

        List<Test> tests = requestC.get().getTests();

        model.addAttribute("request", requestC.get());
        model.addAttribute("tests", tests);

        return "recruitment/request/tests";
    }

    @PostMapping("/request/create/{requestId}/tests")
    public String createTests(@PathVariable Long requestId,
                              @ModelAttribute("test") Test test,
                              @RequestParam("answer_value[]") List<String> answersValue,
                              @RequestParam("answer_mark[]") List<Double> answersMark) {
        Optional<Request> requestC = requestRepository.findById(requestId);
        if (requestC.isEmpty()) return "redirect:/request/list";;

        requestService.addTest(requestC.get(), test, answersValue, answersMark);

        return "redirect:/request/create/" + requestId + "/tests";
    }

    @GetMapping("/request/create/{requestId}/tests/{testId}/delete")
    public String deleteTest(@PathVariable Long requestId, @PathVariable Long testId) {
        Optional<Request> requestC = requestRepository.findById(requestId);
        if (requestC.isEmpty()) return "redirect:/request/list";

        Optional<Test> testC = requestC.get().getTests().stream().filter(test -> test.getId().equals(testId)).findFirst();
        if (testC.isEmpty()) return "redirect:/request/create/" + requestId + "/tests";

        requestService.deleteTest(requestC.get(), testC.get());

        return "redirect:/request/create/" + requestId + "/tests";
    }


    // Action
    @GetMapping("/request/save/{requestId}")
    public String validate(@PathVariable Long requestId) {
        Optional<Request> requestC = requestRepository.findById(requestId);
        if (requestC.isEmpty()) return "redirect:/request/list";

        Request request = requestC.get();
        request.setState(5);
        requestRepository.save(request);

        return "redirect:/request/list";
    }

    @GetMapping("/request/delete/{requestId}")
    public String delete(@PathVariable Long requestId) {
        Optional<Request> requestC = requestRepository.findById(requestId);
        if (requestC.isEmpty()) return "redirect:/request/list";

        Request request = requestC.get();
        requestRepository.delete(request);

        return "redirect:/request/list";
    }

    @GetMapping("/request/accept/{requestId}")
    public String accept(@PathVariable Long requestId) {
        Optional<Request> requestC = requestRepository.findById(requestId);
        if (requestC.isEmpty()) return "redirect:/request/list";

        Request request = requestC.get();
        request.setState(10);
        requestRepository.save(request);

        return "redirect:/request/list";
    }

    @GetMapping("/request/reject/{requestId}")
    public String reject(@PathVariable Long requestId) {
        Optional<Request> requestC = requestRepository.findById(requestId);
        if (requestC.isEmpty()) return "redirect:/request/list";

        Request request = requestC.get();
        request.setState(-10);
        requestRepository.save(request);

        return "redirect:/request/list";
    }
}
