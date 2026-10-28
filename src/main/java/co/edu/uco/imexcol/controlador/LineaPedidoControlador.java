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
import co.edu.uco.imexcol.dto.LineaPedidoDTO;
import co.edu.uco.imexcol.dto.PedidoDTO;
import co.edu.uco.imexcol.dto.ProductoDTO;
import co.edu.uco.imexcol.negocio.fachada.LineaPedidoFachada;
import co.edu.uco.imexcol.negocio.fachada.impl.LineaPedidoFachadaImpl;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;

@RestController
@RequestMapping("/api/v1/lineas-pedido")
public final class LineaPedidoControlador {

    private final LineaPedidoFachada fachada;

    public LineaPedidoControlador() {
        super();
        this.fachada = new LineaPedidoFachadaImpl();
    }

    @GetMapping
    public ResponseEntity<Respuesta<LineaPedidoDTO>> consultar(
            @RequestParam(required = false) final UUID id,
            @RequestParam(required = false) final UUID idPedido,
            @RequestParam(required = false) final UUID idProducto) {

        final var filtros = new LineaPedidoDTO();
        filtros.setId(UtilUUID.obtenerValorDefecto(id));

        final var pedidoFiltro = new PedidoDTO();
        pedidoFiltro.setId(UtilUUID.obtenerValorDefecto(idPedido));
        filtros.setPedido(pedidoFiltro);

        final var productoFiltro = new ProductoDTO();
        productoFiltro.setId(UtilUUID.obtenerValorDefecto(idProducto));
        filtros.setProducto(productoFiltro);

        final var respuesta = Respuesta.crearExitosa(fachada.consultar(filtros));
        respuesta.agregarMensaje(MensajesEnum.EXITO_LINEA_PEDIDO_CONSULTA.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta<LineaPedidoDTO>> consultarPorId(@PathVariable final UUID id) {
        final var filtros = new LineaPedidoDTO();
        filtros.setId(UtilUUID.obtenerValorDefecto(id));

        final var resultados = fachada.consultar(filtros);
        if (UtilObjeto.esNulo(resultados) || resultados.isEmpty()) {
            final var respuestaNoEncontrado = Respuesta.<LineaPedidoDTO>crearFallida();
            respuestaNoEncontrado.agregarMensaje(MensajesEnum.ERROR_USUARIO_LINEA_PEDIDO_NO_ENCONTRADO.getContenido());
            return new ResponseEntity<>(respuestaNoEncontrado, HttpStatus.NOT_FOUND);
        }
        final var respuesta = Respuesta.crearExitosa(List.of(resultados.get(0)));
        respuesta.agregarMensaje(MensajesEnum.EXITO_LINEA_PEDIDO_CONSULTA_POR_ID.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Respuesta<LineaPedidoDTO>> registrar(@RequestBody final LineaPedidoDTO dto) {
        fachada.registrar(dto);
        final var respuesta = Respuesta.<LineaPedidoDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_LINEA_PEDIDO_REGISTRO.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Respuesta<LineaPedidoDTO>> actualizar(@PathVariable final UUID id,
            @RequestBody final LineaPedidoDTO dto) {
        final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new LineaPedidoDTO());
        dtoSeguro.setId(UtilUUID.obtenerValorDefecto(id));
        fachada.actualizar(dtoSeguro);
        final var respuesta = Respuesta.<LineaPedidoDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_LINEA_PEDIDO_ACTUALIZACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Respuesta<LineaPedidoDTO>> eliminar(@PathVariable final UUID id) {
        fachada.eliminar(id);
        final var respuesta = Respuesta.<LineaPedidoDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_LINEA_PEDIDO_ELIMINACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
