package likelion.friendrequest;

import likelion.friend.Friend;
import likelion.friend.FriendRepository;
import likelion.user.User;
import likelion.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Autowired
    public FriendRequestService(FriendRequestRepository friendRequestRepository,FriendRepository friendRepository,UserRepository userRepository){
        this.friendRequestRepository=friendRequestRepository;
        this.friendRepository = friendRepository;
        this.userRepository=userRepository;
    }

    public String getFriendRequestStatus(Long userId, Long friendId){
        if(friendRepository.existsByUserUserIdAndFriendUserId(userId,friendId) ||
                friendRepository.existsByUserUserIdAndFriendUserId(friendId,userId))
            return "FRIENDS";

        if(friendRequestRepository.existsBySenderUserIdAndReceiverUserId(userId,friendId)||
        friendRequestRepository.existsBySenderUserIdAndReceiverUserId(friendId,userId))
            return "PENDING";

        return "NONE";
    }

    public Boolean sendFriendRequest(Long senderId, Long receiverId){
        if(friendRepository.existsByUserUserIdAndFriendUserId(senderId, receiverId) ||
        friendRepository.existsByUserUserIdAndFriendUserId(receiverId,senderId) ||
        friendRequestRepository.existsBySenderUserIdAndReceiverUserId(senderId,receiverId) ||
        friendRequestRepository.existsBySenderUserIdAndReceiverUserId(receiverId,senderId))
            return false;

        User sender = userRepository.findById(senderId).orElseThrow(() -> new IllegalArgumentException("Invalid sender ID"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new IllegalArgumentException("Invalid receiver ID"));

        FriendRequest friendRequest=new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);
        friendRequest.setCreatedDate(LocalDateTime.now());
        friendRequest.setStatus("PENDING");
        friendRequestRepository.save(friendRequest);

        return true;
    }

    public Boolean acceptFriendRequest(Long senderId, Long receiverId){
        FriendRequest friendRequest = friendRequestRepository.findBySenderUserIdAndReceiverUserIdAndStatus(senderId, receiverId, "PENDING")
                .orElseThrow(() -> new IllegalArgumentException("Invalid request or request already accepted/rejected"));

        Friend friend1=new Friend();
        friend1.setUser(friendRequest.getReceiver());
        friend1.setFriend(friendRequest.getSender());
        friendRepository.save(friend1);

        Friend friend2=new Friend();
        friend2.setUser(friendRequest.getSender());
        friend2.setFriend(friendRequest.getReceiver());
        friendRepository.save(friend2);

        friendRequestRepository.delete(friendRequest);
        return true;
    }

    public Boolean deleteFriendRequest(Long senderId, Long receiverId){
        FriendRequest friendRequest = friendRequestRepository.findBySenderUserIdAndReceiverUserIdAndStatus(senderId, receiverId, "PENDING")
                .orElseThrow(() -> new IllegalArgumentException("Invalid request or request already accepted/rejected"));

        friendRequestRepository.delete(friendRequest);
        return true;
    }
}
