package likelion.auth;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtTokenUtil {

	@Value("${jwt.secret}")
	private String secret;

	private SecretKey key;

	@PostConstruct
	public void init() {
		this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	}

	public String getUserIdFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Map<String, String> generateTokens(Long userId, String nickname, String email) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("nickname", nickname);
		claims.put("email", email);
		String accessToken = generateAccessToken(claims, userId.toString());
		String refreshToken = generateRefreshToken(userId);
		Map<String, String> tokens = new HashMap<>();
		tokens.put("accessToken", accessToken);
		tokens.put("refreshToken", refreshToken);
		return tokens;
	}

	public String generateAccessToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
			.setClaims(claims)
			.setSubject(subject)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 20))
			.signWith(key)
			.compact();
	}

	public String generateAccessToken(Long userId, String nickname, String email) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("nickname", nickname);
		claims.put("email", email);
		return generateAccessToken(claims, userId.toString());
	}

	public String generateRefreshToken(Long userId) {
		return Jwts.builder()
			.setSubject(userId.toString())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
			.signWith(key)
			.compact();
	}

	public Boolean validateToken(String token, Long userId) {
		final String userIdFromToken = getUserIdFromToken(token);
		return (userIdFromToken.equals(userId.toString()) && !isTokenExpired(token));
	}

	private Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
}
