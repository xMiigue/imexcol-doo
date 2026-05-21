package co.edu.uco.imexcol.controlador;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

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
        var respuesta = Respuesta.<ClienteDTO>crearExitosa();
        HttpStatusCode estado = HttpStatus.OK;
        try {
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(credenciales, new ClienteDTO());

            if (UtilTexto.estaVacia(dtoSeguro.getCorreoElectronico())
                    || UtilTexto.estaVacia(dtoSeguro.getContrasena())) {
                respuesta = Respuesta.crearFallida();
                respuesta.agregarMensaje("Correo electrónico y contraseña son obligatorios.");
                return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
            }

            final var filtros = new ClienteDTO();
            filtros.setCorreoElectronico(dtoSeguro.getCorreoElectronico());
            filtros.setContrasena(dtoSeguro.getContrasena());

            final var resultados = clienteFachada.consultar(filtros);
            if (UtilObjeto.esNulo(resultados) || resultados.isEmpty()) {
                respuesta = Respuesta.crearFallida();
                respuesta.agregarMensaje("Correo o contraseña inválidos.");
                estado = HttpStatus.UNAUTHORIZED;
            } else {
                final var cliente = resultados.get(0);
                cliente.setContrasena("");
                respuesta.setDatos(List.of(cliente));
                respuesta.agregarMensaje("Autenticación exitosa.");
            }
        } catch (final ImexcolException excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(excepcion.getMensajeUsuario());
            estado = HttpStatus.BAD_REQUEST;
        } catch (final Exception excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(envolverErrorInesperado(excepcion).getMensajeUsuario());
            estado = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(respuesta, estado);
    }

    @PostMapping("/administrador")
    public ResponseEntity<Respuesta<AdministradorDTO>> autenticarAdministrador(
            @RequestBody final AdministradorDTO credenciales) {
        var respuesta = Respuesta.<AdministradorDTO>crearExitosa();
        HttpStatusCode estado = HttpStatus.OK;
        try {
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(credenciales, new AdministradorDTO());

            if (UtilTexto.estaVacia(dtoSeguro.getNombreUsuario())
                    || UtilTexto.estaVacia(dtoSeguro.getContrasena())) {
                respuesta = Respuesta.crearFallida();
                respuesta.agregarMensaje("Nombre de usuario y contraseña son obligatorios.");
                return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
            }

            final var filtros = new AdministradorDTO();
            filtros.setNombreUsuario(dtoSeguro.getNombreUsuario());
            filtros.setContrasena(dtoSeguro.getContrasena());

            final var resultados = administradorFachada.consultar(filtros);
            if (UtilObjeto.esNulo(resultados) || resultados.isEmpty()) {
                respuesta = Respuesta.crearFallida();
                respuesta.agregarMensaje("Usuario o contraseña inválidos.");
                estado = HttpStatus.UNAUTHORIZED;
            } else {
                final var administrador = resultados.get(0);
                administrador.setContrasena("");
                respuesta.setDatos(List.of(administrador));
                respuesta.agregarMensaje("Autenticación exitosa.");
            }
        } catch (final ImexcolException excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(excepcion.getMensajeUsuario());
            estado = HttpStatus.BAD_REQUEST;
        } catch (final Exception excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(envolverErrorInesperado(excepcion).getMensajeUsuario());
            estado = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(respuesta, estado);
    }

    @PostMapping("/cliente/registro")
    public ResponseEntity<Respuesta<ClienteDTO>> registrarCliente(@RequestBody final ClienteDTO dto) {
        var respuesta = Respuesta.<ClienteDTO>crearExitosa();
        HttpStatusCode estado = HttpStatus.CREATED;
        try {
            clienteFachada.registrar(dto);
            respuesta.agregarMensaje("Cliente registrado satisfactoriamente. Ya puede iniciar sesión.");
        } catch (final ImexcolException excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(excepcion.getMensajeUsuario());
            estado = HttpStatus.BAD_REQUEST;
        } catch (final Exception excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(envolverErrorInesperado(excepcion).getMensajeUsuario());
            estado = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(respuesta, estado);
    }

    private static ImexcolException envolverErrorInesperado(final Exception excepcion) {
        return ImexcolException.crear(excepcion,
                MensajesEnum.ERROR_USUARIO_CONTROLADOR_OPERACION_INESPERADA.getContenido(),
                MensajesEnum.ERROR_TECNICO_CONTROLADOR_OPERACION_INESPERADA.getContenido(),
                Lugar.CONTROLADOR);
    }
}
