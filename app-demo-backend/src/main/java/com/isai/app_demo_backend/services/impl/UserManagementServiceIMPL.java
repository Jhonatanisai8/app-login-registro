package com.isai.app_demo_backend.services.impl;

import com.isai.app_demo_backend.dtos.ReqRes;
import com.isai.app_demo_backend.entitys.UsuarioEntidad;
import com.isai.app_demo_backend.repositorys.UsuarioEntidadRepositorio;
import com.isai.app_demo_backend.services.UserManagementService;
import com.isai.app_demo_backend.services.jwt.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserManagementServiceIMPL
    implements UserManagementService {

  private final UsuarioEntidadRepositorio usuarioEntidadRepositorio;
  private final JWTUtils utils;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  @Override
  public ReqRes registrar(ReqRes registrarRequest) {
    ReqRes resp = new ReqRes();
    try {
      UsuarioEntidad usuario = new UsuarioEntidad();
      usuario.setEmail(registrarRequest.getEmail());
      usuario.setCiudad(registrarRequest.getCiudad());
      usuario.setRol(registrarRequest.getRol());
      usuario.setNombre(registrarRequest.getNombre());
      usuario.setPassword(passwordEncoder.encode(registrarRequest.getPassword()));
      UsuarioEntidad usuarioGuardado = usuarioEntidadRepositorio.save(usuario);
      if (usuarioGuardado.getIdUsuario() > 0) {
        resp.setUsuario(usuarioGuardado);
        resp.setMensaje("Usuario registrado exitosamente");
        resp.setCodigoEstado(200);
      }
    } catch (Exception e) {
      resp.setCodigoEstado(500);
      resp.setError("Error al registrar el usuario: " + e.getMessage());
    }
    return resp;
  }

  @Override
  public ReqRes login(ReqRes loginRequest) {
    ReqRes response = new ReqRes();
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
      );
      var usuario = usuarioEntidadRepositorio.findByEmail(loginRequest.getEmail())
          .orElseThrow(() -> new Exception("Usuario no encontrado"));
      var jwtToken = utils.generarToken(usuario);
      var refreshToken = utils.generarRefreshToken(new HashMap<>(), usuario);
      response.setToken(jwtToken);
      response.setRefreshToken(refreshToken);
      response.setHoraDeVencimiento("24Hrs");
      response.setMensaje("Login exitoso");
    } catch (Exception e) {
      response.setCodigoEstado(500);
      response.setError("Error al autenticar el usuario: " + e.getMessage());
    }
    return response;
  }

  public ReqRes refreshToken(ReqRes refreshToken) {
    ReqRes response = new ReqRes();
    try {
      String email = utils.extraerNombreUsuario(refreshToken.getEmail());
      UsuarioEntidad usuario = usuarioEntidadRepositorio.findByEmail(email)
          .orElseThrow(() -> new Exception("Usuario no encontrado"));
      if (utils.esTokenValido(refreshToken.getRefreshToken(), usuario)) {
        var jwtToken = utils.generarToken(usuario);
        response.setCodigoEstado(200);
        response.setToken(jwtToken);
        response.setRefreshToken(refreshToken.getToken());
        response.setHoraDeVencimiento("24Hrs");
        response.setMensaje("Token refrescado exitosamente");
      }
      response.setCodigoEstado(200);
      return response;
    } catch (Exception e) {
      response.setCodigoEstado(500);
      response.setError("Error al refrescar el token: " + e.getMessage());
      return response;
    }
  }

  @Override
  public ReqRes obtenerUsuarios() {
    ReqRes response = new ReqRes();
    try {
      List<UsuarioEntidad> usuarioEntidadList = usuarioEntidadRepositorio.findAll();
      if (!usuarioEntidadList.isEmpty()) {
        response.setListaUsuarios(usuarioEntidadList);
        response.setCodigoEstado(200);
        response.setMensaje("Usuarios obtenidos exitosamente");
      } else {
        response.setCodigoEstado(404);
        response.setError("No se encontraron usuarios");
      }
      return response;
    } catch (Exception e) {
      response.setCodigoEstado(500);
      response.setError("Error al obtener los usuarios: " + e.getMessage());
      return response;
    }
  }

  @Override
  public ReqRes obtenerUsuarioID(Long usuarioIDRequest) {
    ReqRes response = new ReqRes();
    try {
      UsuarioEntidad usuario = usuarioEntidadRepositorio.findById(usuarioIDRequest)
          .orElseThrow(() -> new Exception("Usuario no encontrado"));
      response.setUsuario(usuario);
      response.setCodigoEstado(200);
      response.setMensaje("Usuario" + usuarioIDRequest + " obtenido exitosamente");
    } catch (Exception e) {
      response.setCodigoEstado(500);
      response.setError("Error al obtener el usuario: " + e.getMessage());
    }
    return response;
  }
}

