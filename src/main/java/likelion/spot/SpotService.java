package likelion.spot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import likelion.spot.dto.SpotRecommendResponse;
import likelion.user.User;
import likelion.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class SpotService {

    @Value("${kakao.rest.key}")
    private String kakaoRestKey;

    private final SpotRepository spotRepository;
    private final UserRepository userRepository;
    private static final List<String> CATEGORIES = Arrays.asList(
            "대형마트", "편의점", "어린이집, 유치원", "학교", "학원",
            "주차장", "주유소, 충전소", "지하철역", "은행", "문화시설",
            "중개업소", "공공기관", "관광명소", "숙박", "음식점",
            "카페", "병원", "약국"
    );

    public SpotService(SpotRepository spotRepository,UserRepository userRepository){
        this.spotRepository=spotRepository;
        this.userRepository=userRepository;
    }

    public List<SpotRecommendResponse> recommendSpots(Long userId, double radius) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        String latitude = user.getAddressEntity().getLatitude();
        String longitude = user.getAddressEntity().getLongitude();

        List<SpotRecommendResponse> spotRecommendResponses = new ArrayList<>();
        Set<String> usedCategories = new HashSet<>();

        while (spotRecommendResponses.size() < 3 && usedCategories.size() < CATEGORIES.size()) {
            String category = getRandomCategory(usedCategories);
            usedCategories.add(category);
            List<Spot> foundSpots = searchSpotsUsingKakaoApi(latitude, longitude, radius, category, user);

            if (!foundSpots.isEmpty()) {
                Spot spot = foundSpots.get(0);
                spotRepository.save(spot);
                spotRecommendResponses.add(new SpotRecommendResponse(user.getUserId(), spot.getSpotId(), spot.getName(), spot.getLatitude(), spot.getLongitude()));
            }
        }
        return spotRecommendResponses;
    }

    private String getRandomCategory(Set<String> usedCategories) {
        Random random = new Random();
        String category;
        do {
            category = CATEGORIES.get(random.nextInt(CATEGORIES.size()));
        } while (usedCategories.contains(category));
        return category;
    }

    private List<Spot> searchSpotsUsingKakaoApi(String latitude, String longitude, double radius, String keyword, User user) {
        String url = String.format("https://dapi.kakao.com/v2/local/search/keyword.json?y=%s&x=%s&radius=%f&query=%s", latitude, longitude, radius * 1000, keyword);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoRestKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return parseSpots(response.getBody(), user);
    }

    private List<Spot> parseSpots(String responseBody, User user) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode documentsNode = rootNode.path("documents");

            List<Spot> spots = new ArrayList<>();
            for (JsonNode node : documentsNode) {
                String name = node.path("place_name").asText();
                String address = node.path("address_name").asText();
                String latitude = node.path("y").asText();
                String longitude = node.path("x").asText();
                spots.add(new Spot(user, name, address, latitude, longitude));
            }

            return spots;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse spots from response", e);
        }
    }
}
