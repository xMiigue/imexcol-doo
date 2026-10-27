package co.edu.uco.imexcol.controlador;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.imexcol.controlador.dto.Respuesta;
import co.edu.uco.imexcol.dto.FacturaDTO;
import co.edu.uco.imexcol.dto.PagoDTO;
import co.edu.uco.imexcol.dto.PedidoDTO;
import co.edu.uco.imexcol.negocio.fachada.FacturaFachada;
import co.edu.uco.imexcol.negocio.fachada.impl.FacturaFachadaImpl;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/facturas")
public final class FacturaControlador {

    private final FacturaFachada fachada;

    public FacturaControlador() {
        super();
        this.fachada = new FacturaFachadaImpl();
    }

    @GetMapping
    public ResponseEntity<Respuesta<FacturaDTO>> consultar(
            @RequestParam(required = false) final UUID id,
            @RequestParam(required = false) final UUID idPago,
            @RequestParam(required = false) final UUID idPedido,
            @RequestParam(required = false) final String numeroFactura) {

        final var pagoFiltro = new PagoDTO(UtilUUID.obtenerValorDefecto(idPago));
        pagoFiltro.setPedido(new PedidoDTO(UtilUUID.obtenerValorDefecto(idPedido)));

        final var filtros = new FacturaDTO(UtilUUID.obtenerValorDefecto(id));
        filtros.setPago(pagoFiltro);
        filtros.setNumeroFactura(UtilTexto.aplicarTrim(numeroFactura));

        final var respuesta = Respuesta.crearExitosa(fachada.consultar(filtros));
        respuesta.agregarMensaje(MensajesEnum.EXITO_FACTURA_CONSULTA.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta<FacturaDTO>> consultarPorId(@PathVariable final UUID id) {
        final var filtros = new FacturaDTO(UtilUUID.obtenerValorDefecto(id));

        final var resultados = fachada.consultar(filtros);
        if (UtilObjeto.esNulo(resultados) || resultados.isEmpty()) {
            final var respuestaNoEncontrado = Respuesta.<FacturaDTO>crearFallida();
            respuestaNoEncontrado.agregarMensaje(MensajesEnum.ERROR_USUARIO_FACTURA_NO_ENCONTRADA.getContenido());
            return new ResponseEntity<>(respuestaNoEncontrado, HttpStatus.NOT_FOUND);
        }
        final var respuesta = Respuesta.crearExitosa(List.of(resultados.get(0)));
        respuesta.agregarMensaje(MensajesEnum.EXITO_FACTURA_CONSULTA_POR_ID.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/pedido/{idPedido}/cliente/{idCliente}")
    public ResponseEntity<Respuesta<FacturaDTO>> consultarPorPedidoDelCliente(
            @PathVariable final UUID idPedido, @PathVariable final UUID idCliente) {
        final var factura = fachada.consultarPorPedidoDelCliente(idPedido, idCliente);
        final var respuesta = Respuesta.crearExitosa(List.of(factura));
        respuesta.agregarMensaje(MensajesEnum.EXITO_FACTURA_CONSULTA_POR_PEDIDO.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Respuesta<FacturaDTO>> registrar(@RequestBody final FacturaDTO dto) {
        fachada.registrar(dto);
        final var respuesta = Respuesta.<FacturaDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_FACTURA_REGISTRO.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }
}
