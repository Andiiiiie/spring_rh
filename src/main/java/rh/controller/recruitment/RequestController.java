package rh.controller.recruitment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import rh.model.recruitment.Request;
import rh.service.recruitment.RequestService;

@Controller
public class RequestController {
    RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/request/list")
    public String list() {
        return "recruitment/request/list";
    }

    @GetMapping("/request/create")
    public String create() {
        Long id = requestService.createEmptyService().getId();
        return "redirect:/request/create/" + id;
    }

    @GetMapping("/request/create/{id}")
    public String create(@PathVariable Long id, @ModelAttribute("request") Request request, Model model) {
        model.addAttribute("id", id);
        return "recruitment/request/form";
    }
}
