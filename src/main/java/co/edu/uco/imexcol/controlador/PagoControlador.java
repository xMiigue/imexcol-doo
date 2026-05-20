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
import co.edu.uco.imexcol.dto.MetodoPagoDTO;
import co.edu.uco.imexcol.dto.PagoDTO;
import co.edu.uco.imexcol.dto.PedidoDTO;
import co.edu.uco.imexcol.negocio.fachada.PagoFachada;
import co.edu.uco.imexcol.negocio.fachada.impl.PagoFachadaImpl;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/pagos")
public final class PagoControlador {

    private final PagoFachada fachada;

    public PagoControlador() {
        super();
        this.fachada = new PagoFachadaImpl();
    }

    @GetMapping
    public ResponseEntity<Respuesta<PagoDTO>> consultar(
            @RequestParam(required = false) final UUID id,
            @RequestParam(required = false) final UUID idPedido,
            @RequestParam(required = false) final UUID idMetodoPago,
            @RequestParam(required = false) final String estado) {

        var respuesta = Respuesta.<PagoDTO>crearExitosa();
        HttpStatusCode estadoHttp = HttpStatus.OK;
        try {
            final var filtros = new PagoDTO();
            filtros.setId(UtilUUID.obtenerValorDefecto(id));
            filtros.setEstado(UtilTexto.aplicarTrim(estado));

            final var pedidoFiltro = new PedidoDTO();
            pedidoFiltro.setId(UtilUUID.obtenerValorDefecto(idPedido));
            filtros.setPedido(pedidoFiltro);

            final var metodoPagoFiltro = new MetodoPagoDTO();
            metodoPagoFiltro.setId(UtilUUID.obtenerValorDefecto(idMetodoPago));
            filtros.setMetodoPago(metodoPagoFiltro);

            respuesta.setDatos(fachada.consultar(filtros));
            respuesta.agregarMensaje("Pagos consultados satisfactoriamente.");
        } catch (final ImexcolException excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(excepcion.getMensajeUsuario());
            estadoHttp = HttpStatus.BAD_REQUEST;
        } catch (final Exception excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(envolverErrorInesperado(excepcion).getMensajeUsuario());
            estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(respuesta, estadoHttp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta<PagoDTO>> consultarPorId(@PathVariable final UUID id) {
        var respuesta = Respuesta.<PagoDTO>crearExitosa();
        HttpStatusCode estadoHttp = HttpStatus.OK;
        try {
            final var filtros = new PagoDTO();
            filtros.setId(UtilUUID.obtenerValorDefecto(id));

            final var resultados = fachada.consultar(filtros);
            if (UtilObjeto.esNulo(resultados) || resultados.isEmpty()) {
                respuesta = Respuesta.crearFallida();
                respuesta.agregarMensaje("No se encontró el pago con el identificador solicitado.");
                estadoHttp = HttpStatus.NOT_FOUND;
            } else {
                respuesta.setDatos(List.of(resultados.get(0)));
                respuesta.agregarMensaje("Pago consultado satisfactoriamente.");
            }
        } catch (final ImexcolException excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(excepcion.getMensajeUsuario());
            estadoHttp = HttpStatus.BAD_REQUEST;
        } catch (final Exception excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(envolverErrorInesperado(excepcion).getMensajeUsuario());
            estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(respuesta, estadoHttp);
    }

    @PostMapping
    public ResponseEntity<Respuesta<PagoDTO>> registrar(@RequestBody final PagoDTO dto) {
        var respuesta = Respuesta.<PagoDTO>crearExitosa();
        HttpStatusCode estadoHttp = HttpStatus.CREATED;
        try {
            fachada.registrar(dto);
            respuesta.agregarMensaje("Pago registrado satisfactoriamente.");
        } catch (final ImexcolException excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(excepcion.getMensajeUsuario());
            estadoHttp = HttpStatus.BAD_REQUEST;
        } catch (final Exception excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(envolverErrorInesperado(excepcion).getMensajeUsuario());
            estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(respuesta, estadoHttp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Respuesta<PagoDTO>> actualizar(@PathVariable final UUID id,
            @RequestBody final PagoDTO dto) {
        var respuesta = Respuesta.<PagoDTO>crearExitosa();
        HttpStatusCode estadoHttp = HttpStatus.OK;
        try {
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new PagoDTO());
            dtoSeguro.setId(UtilUUID.obtenerValorDefecto(id));
            fachada.actualizar(dtoSeguro);
            respuesta.agregarMensaje("Pago actualizado satisfactoriamente.");
        } catch (final ImexcolException excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(excepcion.getMensajeUsuario());
            estadoHttp = HttpStatus.BAD_REQUEST;
        } catch (final Exception excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(envolverErrorInesperado(excepcion).getMensajeUsuario());
            estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(respuesta, estadoHttp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Respuesta<PagoDTO>> eliminar(@PathVariable final UUID id) {
        var respuesta = Respuesta.<PagoDTO>crearExitosa();
        HttpStatusCode estadoHttp = HttpStatus.OK;
        try {
            fachada.eliminar(id);
            respuesta.agregarMensaje("Pago eliminado satisfactoriamente.");
        } catch (final ImexcolException excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(excepcion.getMensajeUsuario());
            estadoHttp = HttpStatus.BAD_REQUEST;
        } catch (final Exception excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(envolverErrorInesperado(excepcion).getMensajeUsuario());
            estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(respuesta, estadoHttp);
    }

    private static ImexcolException envolverErrorInesperado(final Exception excepcion) {
        return ImexcolException.crear(excepcion,
                MensajesEnum.ERROR_USUARIO_CONTROLADOR_OPERACION_INESPERADA.getContenido(),
                MensajesEnum.ERROR_TECNICO_CONTROLADOR_OPERACION_INESPERADA.getContenido(),
                Lugar.CONTROLADOR);
    }
}
