package likelion.friend;

import likelion.user.User;
import likelion.user.UserRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public FriendService(FriendRepository friendRepository,UserRepository userRepository){
        this.friendRepository=friendRepository;
        this.userRepository=userRepository;
    }

    public List<User> getFriendsByUserId(Long userId) {
        List<Friend> myFriends=friendRepository.findByUserUserId(userId);
        return myFriends.stream()
                .map(Friend::getFriend)
                .collect(Collectors.toList());
    }

    public List<User> getUsersBySearchKeyword(String searchKeyword){
        return userRepository.findByNicknameIsContaining(searchKeyword);
    }

}
