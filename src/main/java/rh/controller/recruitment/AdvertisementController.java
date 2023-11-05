package rh.controller.recruitment;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rh.config.CustomUserDetails;
import rh.model.recruitment.Advertisement;
import rh.model.recruitment.Candidature;
import rh.model.recruitment.JobCategory;
import rh.repository.recruitment.AdvertisementRepository;
import rh.repository.recruitment.CandidatureRepository;
import rh.repository.recruitment.JobCategoryRepository;
import rh.repository.recruitment.RequestRepository;
import rh.service.recruitment.CandidatureService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class AdvertisementController {

    private final AdvertisementRepository advertisementRepository;
    private final RequestRepository requestRepository;
    private final JobCategoryRepository jobCategoryRepository;
    private final CandidatureService candidatureService;
    private final CandidatureRepository candidatureRepository;


    public AdvertisementController(AdvertisementRepository advertisementRepository,
                                   RequestRepository requestRepository,
                                   JobCategoryRepository jobCategoryRepository, CandidatureService candidatureService,
                                   CandidatureRepository candidatureRepository) {
        this.advertisementRepository = advertisementRepository;
        this.requestRepository = requestRepository;
        this.jobCategoryRepository = jobCategoryRepository;
        this.candidatureService = candidatureService;
        this.candidatureRepository = candidatureRepository;
    }

    // List
    @GetMapping("/advertisement/list")
    public String list(Model model) {
        List<Advertisement> advertisements = advertisementRepository.findAll(Sort.by("endDate"));
        model.addAttribute("advertisements", advertisements);
        return "recruitment/advertisement/list";
    }

    @GetMapping("/advertisement")
    public String listActive(Model model, @RequestParam(value = "jobFilter[]", required = false) List<Long> jobCategoryFilter) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails cUser = (CustomUserDetails) authentication.getPrincipal();
        List<Candidature> candidatures = candidatureRepository.findAllByUserAndStateNot(cUser.getUser(), 0);
        List<Long> candidaturesId = new ArrayList<>(candidatures.stream().map(Candidature::getAdvertisement).map(Advertisement::getId).toList());
        candidaturesId.add(-1L);

        System.out.println(candidatures.size());
        System.out.println(candidaturesId);

        List<Advertisement> advertisements;
        List<JobCategory> jobCategories = jobCategoryRepository.findAll();
        List<Long> jobFilter;

        if(jobCategoryFilter == null || jobCategoryFilter.isEmpty()) {
            advertisements = advertisementRepository.findAllByIdNotInAndEndDateAfter(candidaturesId, new Date());
            jobFilter = jobCategories.stream().map(JobCategory::getId).toList();
        } else {
            advertisements = advertisementRepository.findAllByIdNotInAndEndDateAfterAndRequestJobJobCategoryIdIn(candidaturesId, new Date(), jobCategoryFilter);
            jobFilter = jobCategoryFilter;
        }

        model.addAttribute("advertisements", advertisements);
        model.addAttribute("jobCategories" , jobCategories);
        model.addAttribute("jobFilter" , jobFilter);
        return "recruitment/advertisement/public_list";
    }

    // Detail
    @GetMapping("/advertisement/detail/{advId}")
    public String detail(Model model, @PathVariable Long advId) {
        Optional<Advertisement> advertisement = advertisementRepository.findById(advId);
        advertisement.ifPresent(value -> model.addAttribute("advertisement", value));
        return advertisement.map(value -> "recruitment/advertisement/detail").orElse("redirect:/advertisement");
    }

    // Action
    @GetMapping("/advertisement/terminate/{advId}")
    public String terminate(@PathVariable Long advId) {
        advertisementRepository.findById(advId).ifPresent(advertisement -> {
            advertisement.setEndDate(new Date());
            advertisement.getRequest().setState(15);
            requestRepository.save(advertisement.getRequest());
            advertisementRepository.save(advertisement);
        });
        return "redirect:/advertisement/list";
    }

    @GetMapping("/advertisement/change/{advId}")
    public String change(@PathVariable Long advId) {
        Optional<Advertisement> request = advertisementRepository.findById(advId);
        return request.map(advertisement -> "redirect:/request/accept/" + advertisement.getId()).orElse("redirect:/advertisement/list");
    }

    // Candidate
    @GetMapping("/advertisement/candidate/{advId}/requirements")
    public String candidateRequirements(Model model, @PathVariable Long advId) {
        Optional<Advertisement> advertisement = advertisementRepository.findById(advId);
        advertisement.ifPresent(value -> model.addAttribute("advertisement", value));
        return advertisement.map(value -> "recruitment/advertisement/requirements").orElse("redirect:/advertisement");
    }

    @PostMapping("/advertisement/candidate/{advId}/requirements")
    public String candidateRequirements(@PathVariable Long advId,
                                        @RequestParam(value = "requirements[]", required = false) List<Long> requirementsAnswersId) {
        Optional<Advertisement> advertisement = advertisementRepository.findById(advId);
        if (advertisement.isEmpty())
            return "redirect:/advertisement";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails cUser = (CustomUserDetails) authentication.getPrincipal();

        Candidature candidature = candidatureService.candidate(cUser.getUser(), advertisement.get());
        candidatureService.answerRequirements(candidature, requirementsAnswersId);

        return "redirect:/advertisement/candidate/" + advId + "/tests";
    }

    @GetMapping("/advertisement/candidate/{advId}/tests")
    public String candidateTests(Model model, @PathVariable Long advId) {
        Optional<Advertisement> advertisement = advertisementRepository.findById(advId);
        advertisement.ifPresent(value -> model.addAttribute("advertisement", value));
        return advertisement.map(value -> "recruitment/advertisement/tests").orElse("redirect:/advertisement");
    }

    @PostMapping("/advertisement/candidate/{advId}/tests")
    public String candidateTests(@PathVariable Long advId,
                                 @RequestParam(value = "tests[]", required = false) List<Long> testsAnswersId) {
        Optional<Advertisement> advertisement = advertisementRepository.findById(advId);
        if (advertisement.isEmpty())
            return "redirect:/advertisement";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails cUser = (CustomUserDetails) authentication.getPrincipal();

        Candidature candidature = candidatureService.candidate(cUser.getUser(), advertisement.get());
        candidatureService.answerTest(candidature, testsAnswersId);

        return "redirect:/advertisement/candidate/" + advId + "/interview";
    }
}
