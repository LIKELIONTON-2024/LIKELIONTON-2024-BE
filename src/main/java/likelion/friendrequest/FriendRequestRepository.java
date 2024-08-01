package likelion.friendrequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    Boolean existsBySenderUserIdAndReceiverUserId(Long senderId, Long receiverId);
    Optional<FriendRequest> findBySenderUserIdAndReceiverUserIdAndStatus(Long senderId, Long receiverId,String status);
    List<FriendRequest> findByReceiverUserId(Long receiverId);
}
