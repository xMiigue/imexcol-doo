package co.edu.uco.imexcol.controlador;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
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

        final var filtros = new CategoriaDTO();
        filtros.setId(UtilUUID.obtenerValorDefecto(id));
        filtros.setNombre(UtilTexto.aplicarTrim(nombre));

        final var respuesta = Respuesta.crearExitosa(fachada.consultar(filtros));
        respuesta.agregarMensaje(MensajesEnum.EXITO_CATEGORIA_CONSULTA.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta<CategoriaDTO>> consultarPorId(@PathVariable final UUID id) {
        final var filtros = new CategoriaDTO();
        filtros.setId(UtilUUID.obtenerValorDefecto(id));

        final var resultados = fachada.consultar(filtros);
        if (UtilObjeto.esNulo(resultados) || resultados.isEmpty()) {
            final var respuestaNoEncontrado = Respuesta.<CategoriaDTO>crearFallida();
            respuestaNoEncontrado.agregarMensaje(MensajesEnum.ERROR_USUARIO_CATEGORIA_NO_ENCONTRADO.getContenido());
            return new ResponseEntity<>(respuestaNoEncontrado, HttpStatus.NOT_FOUND);
        }
        final var respuesta = Respuesta.crearExitosa(List.of(resultados.get(0)));
        respuesta.agregarMensaje(MensajesEnum.EXITO_CATEGORIA_CONSULTA_POR_ID.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Respuesta<CategoriaDTO>> registrar(@RequestBody final CategoriaDTO dto) {
        fachada.registrar(dto);
        final var respuesta = Respuesta.<CategoriaDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_CATEGORIA_REGISTRO.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Respuesta<CategoriaDTO>> actualizar(@PathVariable final UUID id,
            @RequestBody final CategoriaDTO dto) {
        final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new CategoriaDTO());
        dtoSeguro.setId(UtilUUID.obtenerValorDefecto(id));
        fachada.actualizar(dtoSeguro);
        final var respuesta = Respuesta.<CategoriaDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_CATEGORIA_ACTUALIZACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Respuesta<CategoriaDTO>> eliminar(@PathVariable final UUID id) {
        fachada.eliminar(id);
        final var respuesta = Respuesta.<CategoriaDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_CATEGORIA_ELIMINACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
