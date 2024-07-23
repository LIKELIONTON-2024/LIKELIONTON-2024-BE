package likelion.friendrequest;

import likelion.friend.FriendRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final FriendRepository friendRepository;

    public FriendRequestService(FriendRequestRepository friendRequestRepository,FriendRepository friendRepository){
        this.friendRequestRepository=friendRequestRepository;
        this.friendRepository = friendRepository;
    }

    public String getFriendRequestStatus(Long userId, Long friendId){
        if(friendRepository.existsByUserUserIdAndFriendUserId(userId,friendId) ||
                friendRepository.existsByUserUserIdAndFriendUserId(friendId,userId))
            return "FRIENDS";

        Optional<FriendRequest> sentRequest =friendRequestRepository.findBySenderUserIdAndAndReceiverUserId(userId, friendId);
        if(sentRequest.isPresent())
            return "PENDING";

        Optional<FriendRequest> receivedRequest =friendRequestRepository.findBySenderUserIdAndAndReceiverUserId(friendId, userId);
        if(receivedRequest .isPresent())
            return "PENDING";

        return "NONE";
    }

}
