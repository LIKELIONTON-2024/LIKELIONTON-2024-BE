package likelion.chur;

import likelion.user.User;
import likelion.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ChurService {

    private final ChurRepository churRepository;
    private final UserRepository userRepository;

    public ChurService(ChurRepository churRepository,UserRepository userRepository){
        this.churRepository=churRepository;
        this.userRepository=userRepository;
    }

    public boolean sendChur(Long senderId, Long receiverId) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new IllegalArgumentException("Invalid senderId"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new IllegalArgumentException("Invalid receiverId"));

        LocalDate today = LocalDate.now();
        boolean hasSentToday = churRepository.existsBySenderAndReceiverAndSendDateBetween(
                sender, receiver, today.atStartOfDay(), today.plusDays(1).atStartOfDay());

        if (hasSentToday) {
            return false;
        }

        Chur chur = new Chur();
        chur.setSender(sender);
        chur.setReceiver(receiver);
        churRepository.save(chur);

        receiver.setTotalChuru(receiver.getTotalChuru() + 1);
        userRepository.save(receiver);

        return true;
    }

}
