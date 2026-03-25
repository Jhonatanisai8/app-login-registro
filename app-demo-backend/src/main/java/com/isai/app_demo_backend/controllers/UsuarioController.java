package com.isai.app_demo_backend.controllers;

import com.isai.app_demo_backend.dtos.ReqRes;
import com.isai.app_demo_backend.services.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class UsuarioController {
  private final UserManagementService userManagementService;

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<ReqRes> registrar(@RequestBody ReqRes registrarRequest) {
    return ResponseEntity.ok(userManagementService.registrar(registrarRequest));
  }

  @RequestMapping(path = "/admin/usuarios", method = RequestMethod.GET)
  public ResponseEntity<ReqRes> obtenerUsuarios() {
    return ResponseEntity.ok(userManagementService.obtenerUsuarios());
  }

  @RequestMapping(path = "/admin/usuarios{idUsuario}", method = RequestMethod.GET)
  public ResponseEntity<ReqRes> obtenerUsuario(@PathVariable Long idUsuario) {
    return ResponseEntity.ok(userManagementService.obtenerUsuarioID(idUsuario));
  }

  @RequestMapping(path = "/admin/usuarios{idUsuario}", method = RequestMethod.PUT)
  public ResponseEntity<ReqRes> actualizarUsuario(@PathVariable Long idUsuario, @RequestBody ReqRes actualizarUsuarioRequest) {
    return ResponseEntity.ok(userManagementService.actualizarUsuarioID(idUsuario, actualizarUsuarioRequest));
  }
}
