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
import co.edu.uco.imexcol.dto.AdministradorDTO;
import co.edu.uco.imexcol.negocio.fachada.AdministradorFachada;
import co.edu.uco.imexcol.negocio.fachada.impl.AdministradorFachadaImpl;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

@RestController
@RequestMapping("/api/v1/administradores")
public final class AdministradorControlador {

    private final AdministradorFachada fachada;

    public AdministradorControlador() {
        super();
        this.fachada = new AdministradorFachadaImpl();
    }

    @GetMapping
    public ResponseEntity<Respuesta<AdministradorDTO>> consultar(
            @RequestParam(required = false) final UUID id,
            @RequestParam(required = false) final String nombreUsuario,
            @RequestParam(required = false) final String correoElectronico) {

        final var filtros = new AdministradorDTO();
        filtros.setId(UtilUUID.obtenerValorDefecto(id));
        filtros.setNombreUsuario(UtilTexto.aplicarTrim(nombreUsuario));
        filtros.setCorreoElectronico(UtilTexto.aplicarTrim(correoElectronico));

        final var respuesta = Respuesta.crearExitosa(fachada.consultar(filtros));
        respuesta.agregarMensaje(MensajesEnum.EXITO_ADMINISTRADOR_CONSULTA.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta<AdministradorDTO>> consultarPorId(@PathVariable final UUID id) {
        final var filtros = new AdministradorDTO();
        filtros.setId(UtilUUID.obtenerValorDefecto(id));

        final var resultados = fachada.consultar(filtros);
        if (UtilObjeto.esNulo(resultados) || resultados.isEmpty()) {
            final var respuestaNoEncontrado = Respuesta.<AdministradorDTO>crearFallida();
            respuestaNoEncontrado.agregarMensaje(MensajesEnum.ERROR_USUARIO_ADMINISTRADOR_NO_ENCONTRADO.getContenido());
            return new ResponseEntity<>(respuestaNoEncontrado, HttpStatus.NOT_FOUND);
        }
        final var respuesta = Respuesta.crearExitosa(List.of(resultados.get(0)));
        respuesta.agregarMensaje(MensajesEnum.EXITO_ADMINISTRADOR_CONSULTA_POR_ID.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Respuesta<AdministradorDTO>> registrar(@RequestBody final AdministradorDTO dto) {
        fachada.registrar(dto);
        final var respuesta = Respuesta.<AdministradorDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_ADMINISTRADOR_REGISTRO.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Respuesta<AdministradorDTO>> actualizar(@PathVariable final UUID id,
            @RequestBody final AdministradorDTO dto) {
        final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new AdministradorDTO());
        dtoSeguro.setId(UtilUUID.obtenerValorDefecto(id));
        fachada.actualizar(dtoSeguro);
        final var respuesta = Respuesta.<AdministradorDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_ADMINISTRADOR_ACTUALIZACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Respuesta<AdministradorDTO>> eliminar(@PathVariable final UUID id) {
        fachada.eliminar(id);
        final var respuesta = Respuesta.<AdministradorDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_ADMINISTRADOR_ELIMINACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
