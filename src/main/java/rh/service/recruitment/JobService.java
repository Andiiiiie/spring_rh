package rh.service.recruitment;

import org.springframework.stereotype.Service;
import rh.repository.recruitment.JobRepository;

@Service
public class JobService {
    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }
}
