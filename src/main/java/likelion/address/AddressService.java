package likelion.address;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import likelion.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AddressService {
    @Value("${kakao.rest.key}")
    private String kakaoRestKey;

    public Address getLatLonFromAddress(String address, User user) {
        String url = "https://dapi.kakao.com/v2/local/search/address.json?query=" + address;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoRestKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return parseAddress(response.getBody(), user);
    }

    private Address parseAddress(String responseBody, User user) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode documentsNode = rootNode.path("documents").get(0);

            String addressName = documentsNode.path("address_name").asText();
            String latitude = documentsNode.path("y").asText();
            String longitude = documentsNode.path("x").asText();

            return new Address(user, addressName, latitude, longitude);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse address from response", e);
        }
    }
}
