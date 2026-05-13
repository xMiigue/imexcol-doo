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
import co.edu.uco.imexcol.dto.CategoriaDTO;
import co.edu.uco.imexcol.dto.ProductoDTO;
import co.edu.uco.imexcol.negocio.fachada.ProductoFachada;
import co.edu.uco.imexcol.negocio.fachada.impl.ProductoFachadaImpl;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/productos")
public final class ProductoControlador {

    private final ProductoFachada fachada;

    public ProductoControlador() {
        super();
        this.fachada = new ProductoFachadaImpl();
    }

    @GetMapping
    public ResponseEntity<Respuesta<ProductoDTO>> consultar(
            @RequestParam(required = false) final UUID id,
            @RequestParam(required = false) final UUID idCategoria,
            @RequestParam(required = false) final String nombre) {

        var respuesta = Respuesta.<ProductoDTO>crearExitosa();
        HttpStatusCode estado = HttpStatus.OK;
        try {
            final var filtros = new ProductoDTO();
            filtros.setId(UtilUUID.obtenerValorDefecto(id));
            filtros.setNombre(UtilTexto.aplicarTrim(nombre));

            final var categoriaFiltro = new CategoriaDTO();
            categoriaFiltro.setId(UtilUUID.obtenerValorDefecto(idCategoria));
            filtros.setCategoria(categoriaFiltro);

            respuesta.setDatos(fachada.consultar(filtros));
            respuesta.agregarMensaje("Productos consultados satisfactoriamente.");
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
    public ResponseEntity<Respuesta<ProductoDTO>> consultarPorId(@PathVariable final UUID id) {
        var respuesta = Respuesta.<ProductoDTO>crearExitosa();
        HttpStatusCode estado = HttpStatus.OK;
        try {
            final var filtros = new ProductoDTO();
            filtros.setId(UtilUUID.obtenerValorDefecto(id));

            final var resultados = fachada.consultar(filtros);
            if (UtilObjeto.esNulo(resultados) || resultados.isEmpty()) {
                respuesta = Respuesta.crearFallida();
                respuesta.agregarMensaje("No se encontró el producto con el identificador solicitado.");
                estado = HttpStatus.NOT_FOUND;
            } else {
                respuesta.setDatos(List.of(resultados.get(0)));
                respuesta.agregarMensaje("Producto consultado satisfactoriamente.");
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
    public ResponseEntity<Respuesta<ProductoDTO>> registrar(@RequestBody final ProductoDTO dto) {
        var respuesta = Respuesta.<ProductoDTO>crearExitosa();
        HttpStatusCode estado = HttpStatus.CREATED;
        try {
            fachada.registrar(dto);
            respuesta.agregarMensaje("Producto registrado satisfactoriamente.");
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
    public ResponseEntity<Respuesta<ProductoDTO>> actualizar(@PathVariable final UUID id,
            @RequestBody final ProductoDTO dto) {
        var respuesta = Respuesta.<ProductoDTO>crearExitosa();
        HttpStatusCode estado = HttpStatus.OK;
        try {
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new ProductoDTO());
            dtoSeguro.setId(UtilUUID.obtenerValorDefecto(id));
            fachada.actualizar(dtoSeguro);
            respuesta.agregarMensaje("Producto actualizado satisfactoriamente.");
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
    public ResponseEntity<Respuesta<ProductoDTO>> eliminar(@PathVariable final UUID id) {
        var respuesta = Respuesta.<ProductoDTO>crearExitosa();
        HttpStatusCode estado = HttpStatus.OK;
        try {
            fachada.eliminar(id);
            respuesta.agregarMensaje("Producto eliminado satisfactoriamente.");
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
