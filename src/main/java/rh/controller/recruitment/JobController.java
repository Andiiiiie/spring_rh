package rh.controller.recruitment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import rh.model.recruitment.Job;
import rh.model.recruitment.JobCategory;
import rh.repository.recruitment.JobCategoryRepository;
import rh.repository.recruitment.JobRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class JobController {

    private final JobRepository jobRepository;
    private final JobCategoryRepository jobCategoryRepository;

    public JobController(JobRepository jobRepository,
                         JobCategoryRepository jobCategoryRepository) {
        this.jobRepository = jobRepository;
        this.jobCategoryRepository = jobCategoryRepository;
    }

    @GetMapping("/job/list")
    public String getList(Model model) {
        List<Job> jobList = jobRepository.findAll();
        model.addAttribute(jobList);
        return "recruitment/job/list";
    }

    @GetMapping("/job/create")
    public String create(@ModelAttribute("job") Job job, Model model) {
        List<JobCategory> jobCategoryList = jobCategoryRepository.findAll();
        model.addAttribute(jobCategoryList);
        return "recruitment/job/create";
    }

    @PostMapping("/job/create")
    public ModelAndView create(@ModelAttribute("job") Job job) {
        jobRepository.save(job);
        return new ModelAndView("redirect:/job/list");
    }

    @GetMapping("/job/update/{id}")
    public String update(Model model, @PathVariable Long id) {
        List<JobCategory> jobCategoryList = jobCategoryRepository.findAll();
        Optional<Job> updJob = jobRepository.findById(id);
        if (updJob.isEmpty())
            return "redirect:/job/list";
        Job job = updJob.get();
        model.addAttribute(jobCategoryList);
        model.addAttribute(job);
        return "recruitment/job/update";
    }

    @PostMapping("/job/update/{id}")
    public ModelAndView update(@ModelAttribute("job") Job job, @PathVariable Long id) {
        job.setId(id);
        jobRepository.save(job);
        return new ModelAndView("redirect:/job/list");
    }

    @GetMapping("/job/delete/{id}")
    public String delete(@PathVariable Long id) {
        Optional<Job> updJob = jobRepository.findById(id);
        if (updJob.isEmpty())
            return "redirect:/job/list";
        jobRepository.delete(updJob.get());
        return "redirect:/job/list";
    }
}
