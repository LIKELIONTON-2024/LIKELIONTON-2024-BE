package likelion.visit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import likelion.spot.Spot;
import likelion.spot.SpotRepository;
import likelion.user.User;
import likelion.user.UserRepository;

@Service
public class VisitService {

	@Value("${kakao.rest.key}")
	private String kakaoRestKey;

	private final UserRepository userRepository;
	private final SpotRepository spotRepository;
	private final VisitRepository visitRepository;

	public VisitService(UserRepository userRepository, SpotRepository spotRepository, VisitRepository visitRepository) {
		this.userRepository = userRepository;
		this.spotRepository = spotRepository;
		this.visitRepository = visitRepository;
	}

	public void createVisit(Long userId, Long spotId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
		Spot spot = spotRepository.findById(spotId).orElseThrow(() -> new IllegalArgumentException("Spot not found"));

		int distance = getDistance(user, spot);

		Visit visit = new Visit(user, spot, distance);
		user.setLastVerifiedDate(visit.getVisitDate());
		user.setTotalChuru(user.getTotalChuru() + 1);
		user.setTotalDistance(user.getTotalDistance() + visit.getDistance().floatValue());
		user.setTotalVisits(user.getTotalVisits() + 1);
		if (user.getTotalVisits() == 1)
			user.setUserBadge("https://likelion-hikikomori.s3.ap-northeast-2.amazonaws.com/badge.png");
		visitRepository.save(visit);
	}

	private int getDistance(User user, Spot spot) {
		String url = "https://apis-navi.kakaomobility.com/v1/destinations/directions";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "KakaoAK " + kakaoRestKey);

		Map<String, Object> origin = new HashMap<>();
		origin.put("x", Double.parseDouble(user.getAddressEntity().getLongitude()));
		origin.put("y", Double.parseDouble(user.getAddressEntity().getLatitude()));

		Map<String, Object> destination = new HashMap<>();
		destination.put("x", Double.parseDouble(spot.getLongitude()));
		destination.put("y", Double.parseDouble(spot.getLatitude()));
		destination.put("key", "0");

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("origin", origin);
		requestBody.put("destinations", new Map[] {destination});
		requestBody.put("radius", 5000);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(response.getBody());
			JsonNode routesNode = rootNode.path("routes").get(0);
			JsonNode summaryNode = routesNode.path("summary");
			int distance = summaryNode.path("distance").asInt(); // 거리 값 (단위: 미터)

			return distance;
		} catch (Exception e) {
			throw new RuntimeException("Failed to parse distance from response", e);
		}
	}

	public List<LocalDateTime> getVisitDates(Long userId) {
		List<Visit> visits = visitRepository.findAllByUser_UserId(userId);
		return visits.stream()
			.map(Visit::getVisitDate)
			.collect(Collectors.toList());
	}

	public int getConsecutiveVisitDays(Long userId) {
		List<LocalDate> visitDates = visitRepository.findAllByUser_UserId(userId).stream()
			.map(visit -> visit.getVisitDate().toLocalDate())
			.sorted()
			.collect(Collectors.toList());

		LocalDate today = LocalDate.now();
		int consecutiveDays = 0;
		boolean visitedToday = false;

		for (int i = visitDates.size() - 1; i >= 0; i--) {
			LocalDate visitDate = visitDates.get(i);
			if (visitDate.equals(today)) {
				visitedToday = true;
				consecutiveDays++;
			} else if (visitDate.equals(today.minusDays(consecutiveDays + 1))) {
				consecutiveDays++;
			} else {
				break;
			}
		}

		return visitedToday ? consecutiveDays : Math.max(consecutiveDays, 1) - 1;
	}
}
