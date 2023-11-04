package rh.service.global;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import rh.model.global.User;
import rh.repository.global.UserRepository;

import java.util.Optional;

@Getter
@Setter
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(User user) {
        Optional<User> verifiedUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        return verifiedUser.isPresent();
    }
}
