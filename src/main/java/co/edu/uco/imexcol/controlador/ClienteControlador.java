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
import co.edu.uco.imexcol.dto.ClienteDTO;
import co.edu.uco.imexcol.negocio.fachada.ClienteFachada;
import co.edu.uco.imexcol.negocio.fachada.impl.ClienteFachadaImpl;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/clientes")
public final class ClienteControlador {

    private final ClienteFachada fachada;

    public ClienteControlador() {
        super();
        this.fachada = new ClienteFachadaImpl();
    }

    @GetMapping
    public ResponseEntity<Respuesta<ClienteDTO>> consultar(
            @RequestParam(required = false) final UUID id,
            @RequestParam(required = false) final String nombre,
            @RequestParam(required = false) final String apellido,
            @RequestParam(required = false) final String correoElectronico) {

        var respuesta = Respuesta.<ClienteDTO>crearExitosa();
        HttpStatusCode estado = HttpStatus.OK;
        try {
            final var filtros = new ClienteDTO();
            filtros.setId(UtilUUID.obtenerValorDefecto(id));
            filtros.setNombre(UtilTexto.aplicarTrim(nombre));
            filtros.setApellido(UtilTexto.aplicarTrim(apellido));
            filtros.setCorreoElectronico(UtilTexto.aplicarTrim(correoElectronico));

            respuesta.setDatos(fachada.consultar(filtros));
            respuesta.agregarMensaje("Clientes consultados satisfactoriamente.");
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

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta<ClienteDTO>> consultarPorId(@PathVariable final UUID id) {
        var respuesta = Respuesta.<ClienteDTO>crearExitosa();
        HttpStatusCode estado = HttpStatus.OK;
        try {
            final var filtros = new ClienteDTO();
            filtros.setId(UtilUUID.obtenerValorDefecto(id));

            final var resultados = fachada.consultar(filtros);
            if (UtilObjeto.esNulo(resultados) || resultados.isEmpty()) {
                respuesta = Respuesta.crearFallida();
                respuesta.agregarMensaje("No se encontró el cliente con el identificador solicitado.");
                estado = HttpStatus.NOT_FOUND;
            } else {
                respuesta.setDatos(List.of(resultados.get(0)));
                respuesta.agregarMensaje("Cliente consultado satisfactoriamente.");
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

    @PostMapping
    public ResponseEntity<Respuesta<ClienteDTO>> registrar(@RequestBody final ClienteDTO dto) {
        var respuesta = Respuesta.<ClienteDTO>crearExitosa();
        HttpStatusCode estado = HttpStatus.CREATED;
        try {
            fachada.registrar(dto);
            respuesta.agregarMensaje("Cliente registrado satisfactoriamente.");
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

    @PutMapping("/{id}")
    public ResponseEntity<Respuesta<ClienteDTO>> actualizar(@PathVariable final UUID id,
            @RequestBody final ClienteDTO dto) {
        var respuesta = Respuesta.<ClienteDTO>crearExitosa();
        HttpStatusCode estado = HttpStatus.OK;
        try {
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new ClienteDTO());
            dtoSeguro.setId(UtilUUID.obtenerValorDefecto(id));
            fachada.actualizar(dtoSeguro);
            respuesta.agregarMensaje("Cliente actualizado satisfactoriamente.");
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Respuesta<ClienteDTO>> eliminar(@PathVariable final UUID id) {
        var respuesta = Respuesta.<ClienteDTO>crearExitosa();
        HttpStatusCode estado = HttpStatus.OK;
        try {
            fachada.eliminar(id);
            respuesta.agregarMensaje("Cliente eliminado satisfactoriamente.");
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
