package co.edu.uco.imexcol.controlador;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        final var filtros = new PagoDTO();
        filtros.setId(UtilUUID.obtenerValorDefecto(id));
        filtros.setEstado(UtilTexto.aplicarTrim(estado));

        final var pedidoFiltro = new PedidoDTO();
        pedidoFiltro.setId(UtilUUID.obtenerValorDefecto(idPedido));
        filtros.setPedido(pedidoFiltro);

        final var metodoPagoFiltro = new MetodoPagoDTO();
        metodoPagoFiltro.setId(UtilUUID.obtenerValorDefecto(idMetodoPago));
        filtros.setMetodoPago(metodoPagoFiltro);

        final var respuesta = Respuesta.crearExitosa(fachada.consultar(filtros));
        respuesta.agregarMensaje(MensajesEnum.EXITO_PAGO_CONSULTA.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta<PagoDTO>> consultarPorId(@PathVariable final UUID id) {
        final var filtros = new PagoDTO();
        filtros.setId(UtilUUID.obtenerValorDefecto(id));

        final var resultados = fachada.consultar(filtros);
        if (UtilObjeto.esNulo(resultados) || resultados.isEmpty()) {
            final var respuestaNoEncontrado = Respuesta.<PagoDTO>crearFallida();
            respuestaNoEncontrado.agregarMensaje(MensajesEnum.ERROR_USUARIO_PAGO_NO_ENCONTRADO.getContenido());
            return new ResponseEntity<>(respuestaNoEncontrado, HttpStatus.NOT_FOUND);
        }
        final var respuesta = Respuesta.crearExitosa(List.of(resultados.get(0)));
        respuesta.agregarMensaje(MensajesEnum.EXITO_PAGO_CONSULTA_POR_ID.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Respuesta<PagoDTO>> registrar(@RequestBody final PagoDTO dto) {
        fachada.registrar(dto);
        final var respuesta = Respuesta.<PagoDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_PAGO_REGISTRO.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Respuesta<PagoDTO>> actualizar(@PathVariable final UUID id,
            @RequestBody final PagoDTO dto) {
        final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new PagoDTO());
        dtoSeguro.setId(UtilUUID.obtenerValorDefecto(id));
        fachada.actualizar(dtoSeguro);
        final var respuesta = Respuesta.<PagoDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_PAGO_ACTUALIZACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Respuesta<PagoDTO>> eliminar(@PathVariable final UUID id) {
        fachada.eliminar(id);
        final var respuesta = Respuesta.<PagoDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_PAGO_ELIMINACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
