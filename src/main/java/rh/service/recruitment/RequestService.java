package rh.service.recruitment;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import rh.model.recruitment.Request;
import rh.repository.recruitment.RequestRepository;

@Getter
@Setter
@Service
public class RequestService {
    private final RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public Request createEmptyService() {
        return requestRepository.save(new Request());
    }
}
