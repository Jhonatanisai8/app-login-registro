package com.isai.app_demo_backend.services;

import com.isai.app_demo_backend.dtos.ReqRes;

public interface UserManagementService {
  ReqRes registrar(ReqRes registrarRequest);

  ReqRes login(ReqRes loginRequest);

  ReqRes refreshToken(ReqRes refreshTokenRequest);

  ReqRes obtenerUsuarios();

  ReqRes obtenerUsuarioID(Long usuarioIDRequest);

  ReqRes eliminarUsuarioID(Long usuarioIDRequest);
}
