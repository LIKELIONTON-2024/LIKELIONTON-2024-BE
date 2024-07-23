package likelion.oauth.google;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import likelion.auth.JwtTokenUtil;
import likelion.oauth.google.dto.GoogleRequest;
import likelion.oauth.google.dto.GoogleResponse;
import likelion.oauth.google.dto.GoogleUserInfoResponse;
import likelion.user.User;
import likelion.user.UserService;

@Service
public class GoogleOauthService {

	@Value("${google.client.id}")
	private String googleClientId;

	@Value("${google.client.pw}")
	private String googleClientPw;

	@Value("${google.redirect.uri}")
	private String googleRedirectUri;

	private final UserService userService;
	private final JwtTokenUtil jwtTokenUtil;

	@Autowired
	public GoogleOauthService(UserService userService, JwtTokenUtil jwtTokenUtil) {
		this.userService = userService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	public String getGoogleLoginUrl() {
		StringBuilder reqUrl = new StringBuilder("https://accounts.google.com/o/oauth2/v2/auth");
		reqUrl.append("?client_id=").append(googleClientId)
			.append("&redirect_uri=").append(googleRedirectUri)
			.append("&response_type=code")
			.append("&scope=email%20profile%20openid")
			.append("&access_type=offline");
		return reqUrl.toString();
	}

	public GoogleUserInfoResponse getGoogleUserInfo(String authCode) {
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

		ResponseEntity<GoogleUserInfoResponse> googleUserInfoResponse = restTemplate.postForEntity(
			"https://oauth2.googleapis.com/tokeninfo",
			map,
			GoogleUserInfoResponse.class
		);

		String email = googleUserInfoResponse.getBody().email();
		String name = googleUserInfoResponse.getBody().name();
		User user = userService.findByEmail(email);

		if (user != null) {
			String token = jwtTokenUtil.generateToken(user.getUserId(), user.getNickname(), user.getEmail());
			return GoogleUserInfoResponse.from(email, name, token, user, true);
		} else {
			return GoogleUserInfoResponse.from(email, name, null, null, false);
		}
	}
}
