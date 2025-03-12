package com.planetsystems.api.school.staffTransfer.security;

import com.planetsystems.api.school.staffTransfer.exceptions.InvalidTokenException;
import com.planetsystems.api.school.staffTransfer.util.AppMessages;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

	private String SECRET_KEY = "9fbd8e90a5d447abf722ad4a993fadcbfe7d16f38c8b4e59abdfbaadd1b70c6d";
	@Getter
	@Setter
	private String username;
	@Getter
	@Setter
	private String userId;

	public String extractUsername(String token) throws Exception {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) throws Exception {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws Exception {
		try {
			final Claims claims = extractAllClaims(token);
			return claimsResolver.apply(claims);
		} catch (Exception e) {
			throw new InvalidTokenException(AppMessages.INVALID_TOKEN);
		}
	}

	public Claims extractAllClaims(String token) {
		try {
			return Jwts.parser()
					.setSigningKey(SECRET_KEY)
					.parseClaimsJws(token)
					.getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		} catch (Exception e) {
			throw new InvalidTokenException(AppMessages.INVALID_TOKEN);
		}
	}

	public boolean isValidJwt(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException e) {
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean isTokenExpired(String token) throws Exception {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();

		return createToken(claims, userDetails.getUsername());
	}

	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, username);
	}

	public String generateAuthToken(String username,String userId, String deviceId) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("userName", username);
		claims.put("deviceId", deviceId);
		return createToken(claims, username);
	}

	private String createToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) throws Exception {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()));
	}

}