package com.isai.app_demo_backend.services.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtils {
  private final SecretKey secretKey;
  private final long expirationMs;

  public JWTUtils(
      @Value("${jwt.expiration-ms:3600000}") long expirationMs,
      @Value("${jwt.secret}") String cadenaSecreta) {
    this.expirationMs = expirationMs;
    byte[] keyBytes = cadenaSecreta.getBytes(java.nio.charset.StandardCharsets.UTF_8);
    this.secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
  }

  // token principal para autenticar al usuario
  public String generarToken(UserDetails userDetails) {
    return Jwts.builder()
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
        .signWith(secretKey)
        .compact();
  }

  // token para refrescar el token principal
  public String generarRefreshToken(Map<String, Object> claims, UserDetails userDetails) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
        .signWith(secretKey)
        .compact();
  }

  // extraer el nombre del usuario del token
  public String extraerNombreUsuario(String token) {
    return getClaims(token, Claims::getSubject);
  }

  // extraer todas las claims del token
  private <T> T getClaims(String token, Function<Claims, T> claimsResolver) {
    return claimsResolver.apply(Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token).getPayload());
  }

  // validar que el token sea valido
  public boolean esTokenValido(String token, UserDetails userDetails) {
    final String nombreUsuario = extraerNombreUsuario(token);
    return (nombreUsuario.equals(userDetails.getUsername()) && !esTokenExpirado(token));
  }

  // validar que el token no este expirado
  private boolean esTokenExpirado(String token) {
    return getClaims(token, Claims::getExpiration).before(new Date());
  }

}
