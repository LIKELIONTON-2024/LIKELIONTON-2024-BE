package likelion.friend;

import likelion.user.User;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendService {

    private final FriendRepository friendRepository;

    public FriendService(FriendRepository friendRepository){
        this.friendRepository=friendRepository;
    }

    public List<User> getFriendsByUserId(Long userId) {
        List<Friend> myFriends=friendRepository.findByUserUserId(userId);
        return myFriends.stream()
                .map(Friend::getFriend)
                .collect(Collectors.toList());
    }

}
