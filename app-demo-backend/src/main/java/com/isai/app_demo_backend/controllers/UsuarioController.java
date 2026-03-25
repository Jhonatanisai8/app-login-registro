package com.isai.app_demo_backend.controllers;

import com.isai.app_demo_backend.dtos.ReqRes;
import com.isai.app_demo_backend.services.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
  private final UserManagementService userManagementService;

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<ReqRes> registrar(@RequestBody ReqRes registrarRequest) {
    return ResponseEntity.ok(userManagementService.registrar(registrarRequest));
  }

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<ReqRes> obtenerUsuarios() {
    return ResponseEntity.ok(userManagementService.obtenerUsuarios());
  }
}
