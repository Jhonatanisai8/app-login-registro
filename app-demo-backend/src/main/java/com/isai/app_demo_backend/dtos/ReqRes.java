package com.isai.app_demo_backend.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.isai.app_demo_backend.entitys.UsuarioEntidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqRes {
  private Integer codigoEstado;
  private String error;
  private String mensaje;
  private String token;
  private String refreshToken;
  private String horaDeVencimiento; //expirationTime
  private String nombre;
  private String ciudad;
  private String rol;
  private String email;
  private String password;
  private UsuarioEntidad usuario;
  private List<UsuarioEntidad> listaUsuarios;
}
