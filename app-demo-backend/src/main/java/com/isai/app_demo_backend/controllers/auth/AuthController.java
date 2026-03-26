package com.isai.app_demo_backend.controllers.auth;

import com.isai.app_demo_backend.dtos.ReqRes;
import com.isai.app_demo_backend.services.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final UserManagementService userManagementService;

  @RequestMapping(path = "/login", method = RequestMethod.POST)
  public ResponseEntity<ReqRes> login(@RequestBody ReqRes loginRequest) {
    return ResponseEntity.ok(userManagementService.login(loginRequest));
  }

  @RequestMapping(path = "/registrar", method = RequestMethod.POST)
  public ResponseEntity<ReqRes> registrar(@RequestBody ReqRes registrarRequest) {
    return ResponseEntity.ok(userManagementService.registrar(registrarRequest));
  }

  @RequestMapping(path = "/refresh-token", method = RequestMethod.POST)
  public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes refreshTokenRequest) {
    return ResponseEntity.ok(userManagementService.refreshToken(refreshTokenRequest));
  }
}
