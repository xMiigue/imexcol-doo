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
import co.edu.uco.imexcol.dto.CarritoComprasDTO;
import co.edu.uco.imexcol.dto.ItemCarritoDTO;
import co.edu.uco.imexcol.dto.ProductoDTO;
import co.edu.uco.imexcol.negocio.fachada.ItemCarritoFachada;
import co.edu.uco.imexcol.negocio.fachada.impl.ItemCarritoFachadaImpl;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;

@RestController
@RequestMapping("/api/v1/items-carrito")
public final class ItemCarritoControlador {

    private final ItemCarritoFachada fachada;

    public ItemCarritoControlador() {
        super();
        this.fachada = new ItemCarritoFachadaImpl();
    }

    @GetMapping
    public ResponseEntity<Respuesta<ItemCarritoDTO>> consultar(
            @RequestParam(required = false) final UUID id,
            @RequestParam(required = false) final UUID idCarrito,
            @RequestParam(required = false) final UUID idProducto) {

        final var filtros = new ItemCarritoDTO();
        filtros.setId(UtilUUID.obtenerValorDefecto(id));

        final var carritoFiltro = new CarritoComprasDTO();
        carritoFiltro.setId(UtilUUID.obtenerValorDefecto(idCarrito));
        filtros.setCarrito(carritoFiltro);

        final var productoFiltro = new ProductoDTO();
        productoFiltro.setId(UtilUUID.obtenerValorDefecto(idProducto));
        filtros.setProducto(productoFiltro);

        final var respuesta = Respuesta.crearExitosa(fachada.consultar(filtros));
        respuesta.agregarMensaje(MensajesEnum.EXITO_ITEM_CARRITO_CONSULTA.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta<ItemCarritoDTO>> consultarPorId(@PathVariable final UUID id) {
        final var filtros = new ItemCarritoDTO();
        filtros.setId(UtilUUID.obtenerValorDefecto(id));

        final var resultados = fachada.consultar(filtros);
        if (UtilObjeto.esNulo(resultados) || resultados.isEmpty()) {
            final var respuestaNoEncontrado = Respuesta.<ItemCarritoDTO>crearFallida();
            respuestaNoEncontrado.agregarMensaje(MensajesEnum.ERROR_USUARIO_ITEM_CARRITO_NO_ENCONTRADO.getContenido());
            return new ResponseEntity<>(respuestaNoEncontrado, HttpStatus.NOT_FOUND);
        }
        final var respuesta = Respuesta.crearExitosa(List.of(resultados.get(0)));
        respuesta.agregarMensaje(MensajesEnum.EXITO_ITEM_CARRITO_CONSULTA_POR_ID.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Respuesta<ItemCarritoDTO>> registrar(@RequestBody final ItemCarritoDTO dto) {
        fachada.registrar(dto);
        final var respuesta = Respuesta.<ItemCarritoDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_ITEM_CARRITO_REGISTRO.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Respuesta<ItemCarritoDTO>> actualizar(@PathVariable final UUID id,
            @RequestBody final ItemCarritoDTO dto) {
        final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new ItemCarritoDTO());
        dtoSeguro.setId(UtilUUID.obtenerValorDefecto(id));
        fachada.actualizar(dtoSeguro);
        final var respuesta = Respuesta.<ItemCarritoDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_ITEM_CARRITO_ACTUALIZACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Respuesta<ItemCarritoDTO>> eliminar(@PathVariable final UUID id) {
        fachada.eliminar(id);
        final var respuesta = Respuesta.<ItemCarritoDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_ITEM_CARRITO_ELIMINACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
