package com.example.demo.util;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultJwtParserBuilder;

@Service
public class JwtToken {

	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

	public String generateToken(String userName, String Role) {
		Map<String, Object> claims = new HashMap<>();

		claims.put("Role", Role);
		return createToken(claims, userName);
	}

	// Create a JWT token with specified claims and subject (user name)
	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // Token valid for 30 minutes
				.signWith(generateKey(SECRET)).compact();
	}

	public SecretKey generateKey(String secret) {
		byte[] decodedKey = Base64.getDecoder().decode(secret);
		SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length,
				SignatureAlgorithm.HS256.getJcaName());
		return secretKey;
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return new DefaultJwtParserBuilder().setSigningKey(generateKey(SECRET)) // Use decoded secret key
				.build().parseClaimsJws(token).getBody();
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * this method is used to check whether the token is expired or not
	 */
	public Boolean isTokenExpired(String token) {
		Boolean isExpired = extractExpiration(token).before(new Date());
		return isExpired;
	}

	public Date extractExpiration(String token) {

		return extractClaim(token, Claims::getExpiration);
	}

	public Boolean validateToken(String token) {
		final String username = extractUsername(token);
		return ( !isTokenExpired(token));
	}

}
