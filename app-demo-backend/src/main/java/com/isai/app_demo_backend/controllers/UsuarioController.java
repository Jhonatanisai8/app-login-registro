package com.isai.app_demo_backend.controllers;

import com.isai.app_demo_backend.dtos.ReqRes;
import com.isai.app_demo_backend.services.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class UsuarioController {
  private final UserManagementService userManagementService;

  @RequestMapping(path = "/admin/usuarios", method = RequestMethod.GET)
  public ResponseEntity<ReqRes> obtenerUsuarios() {
    return ResponseEntity.ok(userManagementService.obtenerUsuarios());
  }

  @RequestMapping(path = "/admin/usuarios/{idUsuario}", method = RequestMethod.GET)
  public ResponseEntity<ReqRes> obtenerUsuario(@PathVariable Long idUsuario) {
    return ResponseEntity.ok(userManagementService.obtenerUsuarioID(idUsuario));
  }

  @RequestMapping(path = "/admin/usuarios/{idUsuario}", method = RequestMethod.PUT)
  public ResponseEntity<ReqRes> actualizarUsuario(@PathVariable Long idUsuario, @RequestBody ReqRes actualizarUsuarioRequest) {
    return ResponseEntity.ok(userManagementService.actualizarUsuarioID(idUsuario, actualizarUsuarioRequest));
  }

  @RequestMapping(path = "/adminuser/obtener-perfil", method = RequestMethod.GET)
  public ResponseEntity<ReqRes> obtenerPerfil() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    ReqRes response = userManagementService.obtenerInformacionUsuarioPorEmail(email);
    return ResponseEntity.status(response.getCodigoEstado()).body(response);
  }

  @RequestMapping(path = "/admin/usuarios{idUsuario}", method = RequestMethod.DELETE)
  public ResponseEntity<ReqRes> eliminarUsuario(@PathVariable Long idUsuario) {
    return ResponseEntity.ok(userManagementService.eliminarUsuarioID(idUsuario));
  }
}
