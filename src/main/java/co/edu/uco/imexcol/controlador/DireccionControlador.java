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
import co.edu.uco.imexcol.dto.ClienteDTO;
import co.edu.uco.imexcol.dto.DireccionDTO;
import co.edu.uco.imexcol.negocio.fachada.DireccionFachada;
import co.edu.uco.imexcol.negocio.fachada.impl.DireccionFachadaImpl;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/direcciones")
public final class DireccionControlador {

    private final DireccionFachada fachada;

    public DireccionControlador() {
        super();
        this.fachada = new DireccionFachadaImpl();
    }

    @GetMapping
    public ResponseEntity<Respuesta<DireccionDTO>> consultar(
            @RequestParam(required = false) final UUID id,
            @RequestParam(required = false) final UUID idCliente,
            @RequestParam(required = false) final String ciudad) {

        final var filtros = new DireccionDTO();
        filtros.setId(UtilUUID.obtenerValorDefecto(id));
        filtros.setCiudad(UtilTexto.aplicarTrim(ciudad));

        final var clienteFiltro = new ClienteDTO();
        clienteFiltro.setId(UtilUUID.obtenerValorDefecto(idCliente));
        filtros.setCliente(clienteFiltro);

        final var respuesta = Respuesta.crearExitosa(fachada.consultar(filtros));
        respuesta.agregarMensaje(MensajesEnum.EXITO_DIRECCION_CONSULTA.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta<DireccionDTO>> consultarPorId(@PathVariable final UUID id) {
        final var filtros = new DireccionDTO();
        filtros.setId(UtilUUID.obtenerValorDefecto(id));

        final var resultados = fachada.consultar(filtros);
        if (UtilObjeto.esNulo(resultados) || resultados.isEmpty()) {
            final var respuestaNoEncontrado = Respuesta.<DireccionDTO>crearFallida();
            respuestaNoEncontrado.agregarMensaje(MensajesEnum.ERROR_USUARIO_DIRECCION_NO_ENCONTRADO.getContenido());
            return new ResponseEntity<>(respuestaNoEncontrado, HttpStatus.NOT_FOUND);
        }
        final var respuesta = Respuesta.crearExitosa(List.of(resultados.get(0)));
        respuesta.agregarMensaje(MensajesEnum.EXITO_DIRECCION_CONSULTA_POR_ID.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Respuesta<DireccionDTO>> registrar(@RequestBody final DireccionDTO dto) {
        fachada.registrar(dto);
        final var respuesta = Respuesta.<DireccionDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_DIRECCION_REGISTRO.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Respuesta<DireccionDTO>> actualizar(@PathVariable final UUID id,
            @RequestBody final DireccionDTO dto) {
        final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new DireccionDTO());
        dtoSeguro.setId(UtilUUID.obtenerValorDefecto(id));
        fachada.actualizar(dtoSeguro);
        final var respuesta = Respuesta.<DireccionDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_DIRECCION_ACTUALIZACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Respuesta<DireccionDTO>> eliminar(@PathVariable final UUID id) {
        fachada.eliminar(id);
        final var respuesta = Respuesta.<DireccionDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_DIRECCION_ELIMINACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
