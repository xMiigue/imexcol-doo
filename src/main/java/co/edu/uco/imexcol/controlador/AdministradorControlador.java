package co.edu.uco.imexcol.controlador;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.imexcol.controlador.dto.Respuesta;
import co.edu.uco.imexcol.dto.AdministradorDTO;
import co.edu.uco.imexcol.negocio.fachada.AdministradorFachada;
import co.edu.uco.imexcol.negocio.fachada.impl.AdministradorFachadaImpl;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/administradores")
public final class AdministradorControlador {

    private final AdministradorFachada fachada;

    public AdministradorControlador() {
        super();
        this.fachada = new AdministradorFachadaImpl();
    }

    @GetMapping
    public ResponseEntity<Respuesta<AdministradorDTO>> consultar(
            @RequestParam(required = false) final UUID id,
            @RequestParam(required = false) final String nombreUsuario,
            @RequestParam(required = false) final String correoElectronico) {

        var respuesta = Respuesta.<AdministradorDTO>crearExitosa();
        HttpStatusCode estado = HttpStatus.OK;
        try {
            final var filtros = new AdministradorDTO();
            filtros.setId(UtilUUID.obtenerValorDefecto(id));
            filtros.setNombreUsuario(UtilTexto.aplicarTrim(nombreUsuario));
            filtros.setCorreoElectronico(UtilTexto.aplicarTrim(correoElectronico));

            respuesta.setDatos(fachada.consultar(filtros));
            respuesta.agregarMensaje("Administradores consultados satisfactoriamente.");
        } catch (final ImexcolException excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(excepcion.getMensajeUsuario());
            estado = HttpStatus.BAD_REQUEST;
            excepcion.printStackTrace();
        } catch (final Exception excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(envolverErrorInesperado(excepcion).getMensajeUsuario());
            estado = HttpStatus.INTERNAL_SERVER_ERROR;
            excepcion.printStackTrace();
        }
        return new ResponseEntity<>(respuesta, estado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta<AdministradorDTO>> consultarPorId(@PathVariable final UUID id) {
        var respuesta = Respuesta.<AdministradorDTO>crearExitosa();
        HttpStatusCode estado = HttpStatus.OK;
        try {
            final var filtros = new AdministradorDTO();
            filtros.setId(UtilUUID.obtenerValorDefecto(id));

            final var resultados = fachada.consultar(filtros);
            if (UtilObjeto.esNulo(resultados) || resultados.isEmpty()) {
                respuesta = Respuesta.crearFallida();
                respuesta.agregarMensaje("No se encontró el administrador con el identificador solicitado.");
                estado = HttpStatus.NOT_FOUND;
            } else {
                respuesta.setDatos(List.of(resultados.get(0)));
                respuesta.agregarMensaje("Administrador consultado satisfactoriamente.");
            }
        } catch (final ImexcolException excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(excepcion.getMensajeUsuario());
            estado = HttpStatus.BAD_REQUEST;
            excepcion.printStackTrace();
        } catch (final Exception excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(envolverErrorInesperado(excepcion).getMensajeUsuario());
            estado = HttpStatus.INTERNAL_SERVER_ERROR;
            excepcion.printStackTrace();
        }
        return new ResponseEntity<>(respuesta, estado);
    }

    @PostMapping
    public ResponseEntity<Respuesta<AdministradorDTO>> registrar(@RequestBody final AdministradorDTO dto) {
        var respuesta = Respuesta.<AdministradorDTO>crearExitosa();
        HttpStatusCode estado = HttpStatus.CREATED;
        try {
            fachada.registrar(dto);
            respuesta.agregarMensaje("Administrador registrado satisfactoriamente.");
        } catch (final ImexcolException excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(excepcion.getMensajeUsuario());
            estado = HttpStatus.BAD_REQUEST;
            excepcion.printStackTrace();
        } catch (final Exception excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(envolverErrorInesperado(excepcion).getMensajeUsuario());
            estado = HttpStatus.INTERNAL_SERVER_ERROR;
            excepcion.printStackTrace();
        }
        return new ResponseEntity<>(respuesta, estado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Respuesta<AdministradorDTO>> actualizar(@PathVariable final UUID id,
            @RequestBody final AdministradorDTO dto) {
        var respuesta = Respuesta.<AdministradorDTO>crearExitosa();
        HttpStatusCode estado = HttpStatus.OK;
        try {
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new AdministradorDTO());
            dtoSeguro.setId(UtilUUID.obtenerValorDefecto(id));
            fachada.actualizar(dtoSeguro);
            respuesta.agregarMensaje("Administrador actualizado satisfactoriamente.");
        } catch (final ImexcolException excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(excepcion.getMensajeUsuario());
            estado = HttpStatus.BAD_REQUEST;
            excepcion.printStackTrace();
        } catch (final Exception excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(envolverErrorInesperado(excepcion).getMensajeUsuario());
            estado = HttpStatus.INTERNAL_SERVER_ERROR;
            excepcion.printStackTrace();
        }
        return new ResponseEntity<>(respuesta, estado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Respuesta<AdministradorDTO>> eliminar(@PathVariable final UUID id) {
        var respuesta = Respuesta.<AdministradorDTO>crearExitosa();
        HttpStatusCode estado = HttpStatus.OK;
        try {
            fachada.eliminar(id);
            respuesta.agregarMensaje("Administrador eliminado satisfactoriamente.");
        } catch (final ImexcolException excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(excepcion.getMensajeUsuario());
            estado = HttpStatus.BAD_REQUEST;
            excepcion.printStackTrace();
        } catch (final Exception excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(envolverErrorInesperado(excepcion).getMensajeUsuario());
            estado = HttpStatus.INTERNAL_SERVER_ERROR;
            excepcion.printStackTrace();
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
