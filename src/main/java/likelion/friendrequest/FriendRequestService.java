package likelion.friendrequest;

import org.springframework.stereotype.Service;

@Service
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;

    public FriendRequestService(FriendRequestRepository friendRequestRepository){
        this.friendRequestRepository=friendRequestRepository;
    }

}
