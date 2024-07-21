package likelion.chur;

import org.springframework.stereotype.Service;

@Service
public class ChurService {

    private final ChurRepository churRepository;

    public ChurService(ChurRepository churRepository){
        this.churRepository=churRepository;
    }

}
