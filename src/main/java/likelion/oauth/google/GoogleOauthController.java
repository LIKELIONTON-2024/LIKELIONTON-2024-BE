package likelion.oauth.google;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import likelion.oauth.google.dto.GoogleUserInfoResponse;

@RestController
@CrossOrigin("*")
public class GoogleOauthController {

	private final GoogleOauthService googleOauthService;

	@Autowired
	public GoogleOauthController(GoogleOauthService googleOauthService) {
		this.googleOauthService = googleOauthService;
	}

	@GetMapping("/oauth2/google/login")
	public String redirectGoogleLogin() {
		System.out.println("로그인 페이지 얻기 API 호출됨");
		return googleOauthService.getGoogleLoginUrl();
	}

	@GetMapping("/oauth2/google/user")
	public GoogleUserInfoResponse getGoogleUserInfo(@RequestParam(value = "code") String authCode) {
		return googleOauthService.getGoogleUserInfo(authCode);
	}
}
