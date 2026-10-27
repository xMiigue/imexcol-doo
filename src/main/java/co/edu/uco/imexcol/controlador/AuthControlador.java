package co.edu.uco.imexcol.controlador;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.imexcol.controlador.dto.Respuesta;
import co.edu.uco.imexcol.dto.AdministradorDTO;
import co.edu.uco.imexcol.dto.ClienteDTO;
import co.edu.uco.imexcol.negocio.fachada.AdministradorFachada;
import co.edu.uco.imexcol.negocio.fachada.ClienteFachada;
import co.edu.uco.imexcol.negocio.fachada.impl.AdministradorFachadaImpl;
import co.edu.uco.imexcol.negocio.fachada.impl.ClienteFachadaImpl;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
public final class AuthControlador {

    private final ClienteFachada clienteFachada;
    private final AdministradorFachada administradorFachada;

    public AuthControlador() {
        super();
        this.clienteFachada = new ClienteFachadaImpl();
        this.administradorFachada = new AdministradorFachadaImpl();
    }

    @PostMapping("/cliente")
    public ResponseEntity<Respuesta<ClienteDTO>> autenticarCliente(@RequestBody final ClienteDTO credenciales) {
        final var dtoSeguro = UtilObjeto.obtenerValorDefecto(credenciales, new ClienteDTO());

        if (UtilTexto.estaVacia(dtoSeguro.getCorreoElectronico())
                || UtilTexto.estaVacia(dtoSeguro.getContrasena())) {
            return responderFallida(
                    MensajesEnum.ERROR_USUARIO_AUTENTICACION_CLIENTE_CREDENCIALES_OBLIGATORIAS,
                    HttpStatus.BAD_REQUEST);
        }

        final var filtros = new ClienteDTO();
        filtros.setCorreoElectronico(dtoSeguro.getCorreoElectronico());
        filtros.setContrasena(dtoSeguro.getContrasena());

        final var resultados = clienteFachada.consultar(filtros);
        if (UtilObjeto.esNulo(resultados) || resultados.isEmpty()) {
            return responderFallida(
                    MensajesEnum.ERROR_USUARIO_AUTENTICACION_CLIENTE_CREDENCIALES_INVALIDAS,
                    HttpStatus.UNAUTHORIZED);
        }

        final var cliente = resultados.get(0);
        cliente.setContrasena(UtilTexto.VACIO);
        final var respuesta = Respuesta.crearExitosa(List.of(cliente));
        respuesta.agregarMensaje(MensajesEnum.EXITO_AUTENTICACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/administrador")
    public ResponseEntity<Respuesta<AdministradorDTO>> autenticarAdministrador(
            @RequestBody final AdministradorDTO credenciales) {
        final var dtoSeguro = UtilObjeto.obtenerValorDefecto(credenciales, new AdministradorDTO());

        if (UtilTexto.estaVacia(dtoSeguro.getNombreUsuario())
                || UtilTexto.estaVacia(dtoSeguro.getContrasena())) {
            return responderFallida(
                    MensajesEnum.ERROR_USUARIO_AUTENTICACION_ADMINISTRADOR_CREDENCIALES_OBLIGATORIAS,
                    HttpStatus.BAD_REQUEST);
        }

        final var filtros = new AdministradorDTO();
        filtros.setNombreUsuario(dtoSeguro.getNombreUsuario());
        filtros.setContrasena(dtoSeguro.getContrasena());

        final var resultados = administradorFachada.consultar(filtros);
        if (UtilObjeto.esNulo(resultados) || resultados.isEmpty()) {
            return responderFallida(
                    MensajesEnum.ERROR_USUARIO_AUTENTICACION_ADMINISTRADOR_CREDENCIALES_INVALIDAS,
                    HttpStatus.UNAUTHORIZED);
        }

        final var administrador = resultados.get(0);
        administrador.setContrasena(UtilTexto.VACIO);
        final var respuesta = Respuesta.crearExitosa(List.of(administrador));
        respuesta.agregarMensaje(MensajesEnum.EXITO_AUTENTICACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/cliente/registro")
    public ResponseEntity<Respuesta<ClienteDTO>> registrarCliente(@RequestBody final ClienteDTO dto) {
        clienteFachada.registrar(dto);
        final var respuesta = Respuesta.<ClienteDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_AUTENTICACION_REGISTRO_CLIENTE.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    private static <T> ResponseEntity<Respuesta<T>> responderFallida(final MensajesEnum mensaje,
            final HttpStatus estado) {
        final var respuesta = Respuesta.<T>crearFallida();
        respuesta.agregarMensaje(mensaje.getContenido());
        return new ResponseEntity<>(respuesta, estado);
    }
}
