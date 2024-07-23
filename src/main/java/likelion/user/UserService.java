package likelion.user;

import likelion.user.dto.UserResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public UserResponseDTO getUserProfileByUserId(Long friendId){
        User user=userRepository.findById(friendId).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + friendId));

        return new UserResponseDTO(user.getUserId(), user.getNickname(), user.getUserImage(), user.getLastVerifiedDate());
    }
}
