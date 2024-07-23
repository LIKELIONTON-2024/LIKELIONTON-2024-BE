package likelion.oauth.google;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import likelion.oauth.google.dto.GoogleRequest;
import likelion.oauth.google.dto.GoogleResponse;
import likelion.oauth.google.dto.GoogleUserInfoResponse;

@RestController
@CrossOrigin("*")
public class GoogleOauthController {

	@Value("${google.client.id}")
	private String googleClientId;

	@Value("${google.client.pw}")
	private String googleClientPw;

	@Value("${google.redirect.uri}")
	private String googleRedirectUri;

	@GetMapping("/oauth2/google/login")
	public String redirectGoogleLogin() {
		StringBuilder reqUrl = new StringBuilder("https://accounts.google.com/o/oauth2/v2/auth");
		reqUrl.append("?client_id=").append(googleClientId)
			.append("&redirect_uri=").append(googleRedirectUri)
			.append("&response_type=code")
			.append("&scope=email%20profile%20openid")
			.append("&access_type=offline");
		return reqUrl.toString();
	}

	@GetMapping("/oauth2/google/user")
	public ResponseEntity<GoogleUserInfoResponse> getGoogleUserInfo(@RequestParam(value = "code") String authCode) {
		RestTemplate restTemplate = new RestTemplate();
		GoogleRequest googleOAuthRequestParam = new GoogleRequest(
			googleClientId,
			googleClientPw,
			authCode,
			googleRedirectUri,
			"authorization_code"
		);
		ResponseEntity<GoogleResponse> resultEntity = restTemplate.postForEntity(
			"https://oauth2.googleapis.com/token",
			googleOAuthRequestParam,
			GoogleResponse.class
		);

		String jwtToken = resultEntity.getBody().id_token();
		Map<String, String> map = new HashMap<>();
		map.put("id_token", jwtToken);

		ResponseEntity<GoogleUserInfoResponse> googleUserInfo = restTemplate.postForEntity(
			"https://oauth2.googleapis.com/tokeninfo",
			map,
			GoogleUserInfoResponse.class
		);
		return googleUserInfo;
	}
}
