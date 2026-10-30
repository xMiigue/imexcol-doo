package co.edu.uco.imexcol.controlador;

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
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.imexcol.controlador.dto.Respuesta;
import co.edu.uco.imexcol.dto.ImagenProductoDTO;
import co.edu.uco.imexcol.dto.ProductoDTO;
import co.edu.uco.imexcol.negocio.fachada.ImagenProductoFachada;
import co.edu.uco.imexcol.negocio.fachada.impl.ImagenProductoFachadaImpl;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;

@RestController
@RequestMapping("/api/v1/productos/{idProducto}/imagenes")
public final class ImagenProductoControlador {

    private final ImagenProductoFachada fachada;

    public ImagenProductoControlador() {
        super();
        this.fachada = new ImagenProductoFachadaImpl();
    }

    @GetMapping
    public ResponseEntity<Respuesta<ImagenProductoDTO>> consultarPorProducto(@PathVariable final UUID idProducto) {
        final var respuesta = Respuesta.crearExitosa(fachada.consultarPorProducto(idProducto));
        respuesta.agregarMensaje(MensajesEnum.EXITO_IMAGEN_PRODUCTO_CONSULTA.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Respuesta<ImagenProductoDTO>> registrar(@PathVariable final UUID idProducto,
            @RequestBody final ImagenProductoDTO dto) {
        final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new ImagenProductoDTO());
        dtoSeguro.setProducto(new ProductoDTO(UtilUUID.obtenerValorDefecto(idProducto)));
        fachada.registrar(dtoSeguro);
        final var respuesta = Respuesta.<ImagenProductoDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_IMAGEN_PRODUCTO_REGISTRO.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Respuesta<ImagenProductoDTO>> actualizar(@PathVariable final UUID idProducto,
            @PathVariable final UUID id, @RequestBody final ImagenProductoDTO dto) {
        final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new ImagenProductoDTO());
        dtoSeguro.setId(UtilUUID.obtenerValorDefecto(id));
        dtoSeguro.setProducto(new ProductoDTO(UtilUUID.obtenerValorDefecto(idProducto)));
        fachada.actualizar(dtoSeguro);
        final var respuesta = Respuesta.<ImagenProductoDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_IMAGEN_PRODUCTO_ACTUALIZACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Respuesta<ImagenProductoDTO>> eliminar(@PathVariable final UUID idProducto,
            @PathVariable final UUID id) {
        fachada.eliminar(idProducto, id);
        final var respuesta = Respuesta.<ImagenProductoDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_IMAGEN_PRODUCTO_ELIMINACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
