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
import co.edu.uco.imexcol.dto.PedidoDTO;
import co.edu.uco.imexcol.negocio.fachada.PedidoFachada;
import co.edu.uco.imexcol.negocio.fachada.impl.PedidoFachadaImpl;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/pedidos")
public final class PedidoControlador {

    private final PedidoFachada fachada;

    public PedidoControlador() {
        super();
        this.fachada = new PedidoFachadaImpl();
    }

    @GetMapping
    public ResponseEntity<Respuesta<PedidoDTO>> consultar(
            @RequestParam(required = false) final UUID id,
            @RequestParam(required = false) final UUID idCliente,
            @RequestParam(required = false) final String estado) {

        var respuesta = Respuesta.<PedidoDTO>crearExitosa();
        HttpStatusCode estadoHttp = HttpStatus.OK;
        try {
            final var filtros = new PedidoDTO();
            filtros.setId(UtilUUID.obtenerValorDefecto(id));
            filtros.setEstado(UtilTexto.aplicarTrim(estado));

            final var clienteFiltro = new ClienteDTO();
            clienteFiltro.setId(UtilUUID.obtenerValorDefecto(idCliente));
            filtros.setCliente(clienteFiltro);

            respuesta.setDatos(fachada.consultar(filtros));
            respuesta.agregarMensaje("Pedidos consultados satisfactoriamente.");
        } catch (final ImexcolException excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(excepcion.getMensajeUsuario());
            estadoHttp = HttpStatus.BAD_REQUEST;
            excepcion.printStackTrace();
        } catch (final Exception excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(envolverErrorInesperado(excepcion).getMensajeUsuario());
            estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
            excepcion.printStackTrace();
        }
        return new ResponseEntity<>(respuesta, estadoHttp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta<PedidoDTO>> consultarPorId(@PathVariable final UUID id) {
        var respuesta = Respuesta.<PedidoDTO>crearExitosa();
        HttpStatusCode estadoHttp = HttpStatus.OK;
        try {
            final var filtros = new PedidoDTO();
            filtros.setId(UtilUUID.obtenerValorDefecto(id));

            final var resultados = fachada.consultar(filtros);
            if (UtilObjeto.esNulo(resultados) || resultados.isEmpty()) {
                respuesta = Respuesta.crearFallida();
                respuesta.agregarMensaje("No se encontró el pedido con el identificador solicitado.");
                estadoHttp = HttpStatus.NOT_FOUND;
            } else {
                respuesta.setDatos(List.of(resultados.get(0)));
                respuesta.agregarMensaje("Pedido consultado satisfactoriamente.");
            }
        } catch (final ImexcolException excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(excepcion.getMensajeUsuario());
            estadoHttp = HttpStatus.BAD_REQUEST;
            excepcion.printStackTrace();
        } catch (final Exception excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(envolverErrorInesperado(excepcion).getMensajeUsuario());
            estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
            excepcion.printStackTrace();
        }
        return new ResponseEntity<>(respuesta, estadoHttp);
    }

    @PostMapping
    public ResponseEntity<Respuesta<PedidoDTO>> registrar(@RequestBody final PedidoDTO dto) {
        var respuesta = Respuesta.<PedidoDTO>crearExitosa();
        HttpStatusCode estadoHttp = HttpStatus.CREATED;
        try {
            fachada.registrar(dto);
            respuesta.agregarMensaje("Pedido registrado satisfactoriamente.");
        } catch (final ImexcolException excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(excepcion.getMensajeUsuario());
            estadoHttp = HttpStatus.BAD_REQUEST;
            excepcion.printStackTrace();
        } catch (final Exception excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(envolverErrorInesperado(excepcion).getMensajeUsuario());
            estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
            excepcion.printStackTrace();
        }
        return new ResponseEntity<>(respuesta, estadoHttp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Respuesta<PedidoDTO>> actualizar(@PathVariable final UUID id,
            @RequestBody final PedidoDTO dto) {
        var respuesta = Respuesta.<PedidoDTO>crearExitosa();
        HttpStatusCode estadoHttp = HttpStatus.OK;
        try {
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new PedidoDTO());
            dtoSeguro.setId(UtilUUID.obtenerValorDefecto(id));
            fachada.actualizar(dtoSeguro);
            respuesta.agregarMensaje("Pedido actualizado satisfactoriamente.");
        } catch (final ImexcolException excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(excepcion.getMensajeUsuario());
            estadoHttp = HttpStatus.BAD_REQUEST;
            excepcion.printStackTrace();
        } catch (final Exception excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(envolverErrorInesperado(excepcion).getMensajeUsuario());
            estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
            excepcion.printStackTrace();
        }
        return new ResponseEntity<>(respuesta, estadoHttp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Respuesta<PedidoDTO>> eliminar(@PathVariable final UUID id) {
        var respuesta = Respuesta.<PedidoDTO>crearExitosa();
        HttpStatusCode estadoHttp = HttpStatus.OK;
        try {
            fachada.eliminar(id);
            respuesta.agregarMensaje("Pedido eliminado satisfactoriamente.");
        } catch (final ImexcolException excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(excepcion.getMensajeUsuario());
            estadoHttp = HttpStatus.BAD_REQUEST;
            excepcion.printStackTrace();
        } catch (final Exception excepcion) {
            respuesta = Respuesta.crearFallida();
            respuesta.agregarMensaje(envolverErrorInesperado(excepcion).getMensajeUsuario());
            estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
            excepcion.printStackTrace();
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
