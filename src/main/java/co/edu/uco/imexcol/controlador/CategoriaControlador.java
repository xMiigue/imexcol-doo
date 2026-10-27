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
import co.edu.uco.imexcol.negocio.fachada.CategoriaFachada;
import co.edu.uco.imexcol.negocio.fachada.impl.CategoriaFachadaImpl;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolControladorException;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/categorias")
public final class CategoriaControlador {

    private final CategoriaFachada fachada;

    public CategoriaControlador() {
        super();
        this.fachada = new CategoriaFachadaImpl();
    }

    @GetMapping
    public ResponseEntity<Respuesta<CategoriaDTO>> consultar(
            @RequestParam(required = false) final UUID id,
            @RequestParam(required = false) final String nombre) {

        var respuesta = Respuesta.<CategoriaDTO>crearExitosa();
        HttpStatusCode estado = HttpStatus.OK;
        try {
            final var filtros = new CategoriaDTO();
            filtros.setId(UtilUUID.obtenerValorDefecto(id));
            filtros.setNombre(UtilTexto.aplicarTrim(nombre));

            respuesta.setDatos(fachada.consultar(filtros));
            respuesta.agregarMensaje(MensajesEnum.EXITO_CATEGORIA_CONSULTA.getContenido());
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
    public ResponseEntity<Respuesta<CategoriaDTO>> consultarPorId(@PathVariable final UUID id) {
        var respuesta = Respuesta.<CategoriaDTO>crearExitosa();
        HttpStatusCode estado = HttpStatus.OK;
        try {
            final var filtros = new CategoriaDTO();
            filtros.setId(UtilUUID.obtenerValorDefecto(id));

            final var resultados = fachada.consultar(filtros);
            if (UtilObjeto.esNulo(resultados) || resultados.isEmpty()) {
                respuesta = Respuesta.crearFallida();
                respuesta.agregarMensaje(MensajesEnum.ERROR_USUARIO_CATEGORIA_NO_ENCONTRADO.getContenido());
                estado = HttpStatus.NOT_FOUND;
            } else {
                respuesta.setDatos(List.of(resultados.get(0)));
                respuesta.agregarMensaje(MensajesEnum.EXITO_CATEGORIA_CONSULTA_POR_ID.getContenido());
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
    public ResponseEntity<Respuesta<CategoriaDTO>> registrar(@RequestBody final CategoriaDTO dto) {
        var respuesta = Respuesta.<CategoriaDTO>crearExitosa();
        HttpStatusCode estado = HttpStatus.CREATED;
        try {
            fachada.registrar(dto);
            respuesta.agregarMensaje(MensajesEnum.EXITO_CATEGORIA_REGISTRO.getContenido());
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
    public ResponseEntity<Respuesta<CategoriaDTO>> actualizar(@PathVariable final UUID id,
            @RequestBody final CategoriaDTO dto) {
        var respuesta = Respuesta.<CategoriaDTO>crearExitosa();
        HttpStatusCode estado = HttpStatus.OK;
        try {
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new CategoriaDTO());
            dtoSeguro.setId(UtilUUID.obtenerValorDefecto(id));
            fachada.actualizar(dtoSeguro);
            respuesta.agregarMensaje(MensajesEnum.EXITO_CATEGORIA_ACTUALIZACION.getContenido());
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
    public ResponseEntity<Respuesta<CategoriaDTO>> eliminar(@PathVariable final UUID id) {
        var respuesta = Respuesta.<CategoriaDTO>crearExitosa();
        HttpStatusCode estado = HttpStatus.OK;
        try {
            fachada.eliminar(id);
            respuesta.agregarMensaje(MensajesEnum.EXITO_CATEGORIA_ELIMINACION.getContenido());
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

    private static ImexcolControladorException envolverErrorInesperado(final Exception excepcion) {
        return ImexcolControladorException.crear(excepcion,
                MensajesEnum.ERROR_USUARIO_CONTROLADOR_OPERACION_INESPERADA.getContenido(),
                MensajesEnum.ERROR_TECNICO_CONTROLADOR_OPERACION_INESPERADA.getContenido());
    }
}
