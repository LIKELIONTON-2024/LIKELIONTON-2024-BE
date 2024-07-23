package likelion.friend;

import jakarta.persistence.EntityNotFoundException;
import likelion.user.User;
import likelion.user.UserRepository;
import org.springframework.stereotype.Service;
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

    public void deleteFriendFromList(Long friendId,Long userId){
        User user=userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        User friend=userRepository.findById(friendId).orElseThrow(()->new EntityNotFoundException("Friend not found"));

        Friend friendRelation1 = friendRepository.findByUserAndFriend(user, friend).orElseThrow(() -> new EntityNotFoundException("Friend relationship not found"));
        Friend friendRelation2 = friendRepository.findByUserAndFriend(friend, user).orElseThrow(() -> new EntityNotFoundException("Friend relationship not found"));

        friendRepository.delete(friendRelation1);
        friendRepository.delete(friendRelation2);
    }

}
