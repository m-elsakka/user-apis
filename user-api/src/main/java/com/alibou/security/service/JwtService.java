package com.alibou.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private static final String SALTED_HASH = "450d0b0db2bcf4adde5032eca1a7c416e560cf44";

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  public String generateToken(
      Map<String, Object> extraClaims,
      UserDetails userDetails
  ) {
    return Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SALTED_HASH);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  /**
   * Generates a SHA-1 hash of email and salt and returns it as hex-encoded string.
   * @param email the email to encrypt
   * @return hex-encoded string of hash
   */
  @SneakyThrows
  public String encryptEmail(String email) {
    final String algorithm = "SHA-1";
    try {
      MessageDigest digester = MessageDigest.getInstance(algorithm);
      byte[] saltedEmail = (email + SALTED_HASH).getBytes();
      byte[] hash = digester.digest(saltedEmail);

      return String.format("%040x", new BigInteger(1, hash));
    } catch (NoSuchAlgorithmException e) {
      return null;
    }
  }
}
