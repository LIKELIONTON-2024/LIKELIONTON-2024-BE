package likelion.friend;

import jakarta.persistence.EntityNotFoundException;
import likelion.friend.dto.FriendListResponse;
import likelion.user.User;
import likelion.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Autowired
    public FriendService(FriendRepository friendRepository,UserRepository userRepository){
        this.friendRepository=friendRepository;
        this.userRepository=userRepository;
    }

    public List<FriendListResponse> getFriendsByUserId(Long userId) {
        List<Friend> myFriends=friendRepository.findByUserUserId(userId);
        return myFriends.stream()
                .map(friend -> new FriendListResponse(
                        friend.getFriend().getUserId(),
                        friend.getFriend().getNickname(),
                        friend.getFriend().getUserImage()
                ))
                .collect(Collectors.toList());
    }

    public List<FriendListResponse> getUsersBySearchKeyword(String searchKeyword) {
        List<User> users = userRepository.findByNicknameIsContaining(searchKeyword);
        return users.stream()
                .map(user -> new FriendListResponse(
                        user.getUserId(),
                        user.getNickname(),
                        user.getUserImage()
                ))
                .collect(Collectors.toList());
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
