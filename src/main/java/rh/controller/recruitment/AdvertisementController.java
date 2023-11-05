package rh.controller.recruitment;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rh.model.recruitment.Advertisement;
import rh.repository.recruitment.AdvertisementRepository;
import rh.repository.recruitment.RequestRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class AdvertisementController {

    private final AdvertisementRepository advertisementRepository;
    private final RequestRepository requestRepository;

    public AdvertisementController(AdvertisementRepository advertisementRepository,
                                   RequestRepository requestRepository) {
        this.advertisementRepository = advertisementRepository;
        this.requestRepository = requestRepository;
    }

    @GetMapping("/advertisement/list")
    public String list(Model model) {
        List<Advertisement> advertisements = advertisementRepository.findAll(Sort.by("endDate"));
        model.addAttribute("advertisements", advertisements);
        return "recruitment/advertisement/list";
    }

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
}
