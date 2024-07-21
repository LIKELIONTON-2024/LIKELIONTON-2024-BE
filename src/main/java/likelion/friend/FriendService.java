package likelion.friend;

import org.springframework.stereotype.Service;

@Service
public class FriendService {

    private final FriendRepository friendRepository;

    public FriendService(FriendRepository friendRepository){
        this.friendRepository=friendRepository;
    }

}
