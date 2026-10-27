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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.imexcol.controlador.dto.Respuesta;
import co.edu.uco.imexcol.dto.ClienteDTO;
import co.edu.uco.imexcol.dto.ProductoDTO;
import co.edu.uco.imexcol.dto.ResenaDTO;
import co.edu.uco.imexcol.negocio.fachada.ResenaFachada;
import co.edu.uco.imexcol.negocio.fachada.impl.ResenaFachadaImpl;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/resenas")
public final class ResenaControlador {

    private final ResenaFachada fachada;

    public ResenaControlador() {
        super();
        this.fachada = new ResenaFachadaImpl();
    }

    @GetMapping
    public ResponseEntity<Respuesta<ResenaDTO>> consultar(
            @RequestParam(required = false) final UUID id,
            @RequestParam(required = false) final UUID idCliente,
            @RequestParam(required = false) final UUID idProducto) {

        final var filtros = new ResenaDTO(UtilUUID.obtenerValorDefecto(id));
        filtros.setCliente(new ClienteDTO(UtilUUID.obtenerValorDefecto(idCliente)));
        filtros.setProducto(new ProductoDTO(UtilUUID.obtenerValorDefecto(idProducto)));

        final var respuesta = Respuesta.crearExitosa(fachada.consultar(filtros));
        respuesta.agregarMensaje(MensajesEnum.EXITO_RESENA_CONSULTA.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta<ResenaDTO>> consultarPorId(@PathVariable final UUID id) {
        final var filtros = new ResenaDTO(UtilUUID.obtenerValorDefecto(id));

        final var resultados = fachada.consultar(filtros);
        if (UtilObjeto.esNulo(resultados) || resultados.isEmpty()) {
            final var respuestaNoEncontrado = Respuesta.<ResenaDTO>crearFallida();
            respuestaNoEncontrado.agregarMensaje(MensajesEnum.ERROR_USUARIO_RESENA_NO_ENCONTRADA.getContenido());
            return new ResponseEntity<>(respuestaNoEncontrado, HttpStatus.NOT_FOUND);
        }
        final var respuesta = Respuesta.crearExitosa(List.of(resultados.get(0)));
        respuesta.agregarMensaje(MensajesEnum.EXITO_RESENA_CONSULTA_POR_ID.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<Respuesta<ResenaDTO>> consultarPorProducto(@PathVariable final UUID idProducto) {
        final var respuesta = Respuesta.crearExitosa(fachada.consultarPorProducto(idProducto));
        respuesta.agregarMensaje(MensajesEnum.EXITO_RESENA_CONSULTA.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Respuesta<ResenaDTO>> registrar(@RequestBody final ResenaDTO dto) {
        fachada.registrar(dto);
        final var respuesta = Respuesta.<ResenaDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_RESENA_REGISTRO.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/cliente/{idCliente}")
    public ResponseEntity<Respuesta<ResenaDTO>> eliminarPorCliente(@PathVariable final UUID id,
            @PathVariable final UUID idCliente) {
        fachada.eliminarPorCliente(id, idCliente);
        final var respuesta = Respuesta.<ResenaDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_RESENA_ELIMINACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/administrador/{idAdministrador}")
    public ResponseEntity<Respuesta<ResenaDTO>> eliminarPorAdministrador(@PathVariable final UUID id,
            @PathVariable final UUID idAdministrador) {
        fachada.eliminarPorAdministrador(id, idAdministrador);
        final var respuesta = Respuesta.<ResenaDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_RESENA_ELIMINACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
