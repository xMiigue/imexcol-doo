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
import co.edu.uco.imexcol.negocio.fachada.ClienteFachada;
import co.edu.uco.imexcol.negocio.fachada.impl.ClienteFachadaImpl;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/clientes")
public final class ClienteControlador {

    private final ClienteFachada fachada;

    public ClienteControlador() {
        super();
        this.fachada = new ClienteFachadaImpl();
    }

    @GetMapping
    public ResponseEntity<Respuesta<ClienteDTO>> consultar(
            @RequestParam(required = false) final UUID id,
            @RequestParam(required = false) final String nombre,
            @RequestParam(required = false) final String apellido,
            @RequestParam(required = false) final String correoElectronico) {

        final var filtros = new ClienteDTO();
        filtros.setId(UtilUUID.obtenerValorDefecto(id));
        filtros.setNombre(UtilTexto.aplicarTrim(nombre));
        filtros.setApellido(UtilTexto.aplicarTrim(apellido));
        filtros.setCorreoElectronico(UtilTexto.aplicarTrim(correoElectronico));

        final var respuesta = Respuesta.crearExitosa(fachada.consultar(filtros));
        respuesta.agregarMensaje(MensajesEnum.EXITO_CLIENTE_CONSULTA.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta<ClienteDTO>> consultarPorId(@PathVariable final UUID id) {
        final var filtros = new ClienteDTO();
        filtros.setId(UtilUUID.obtenerValorDefecto(id));

        final var resultados = fachada.consultar(filtros);
        if (UtilObjeto.esNulo(resultados) || resultados.isEmpty()) {
            final var respuestaNoEncontrado = Respuesta.<ClienteDTO>crearFallida();
            respuestaNoEncontrado.agregarMensaje(MensajesEnum.ERROR_USUARIO_CLIENTE_NO_ENCONTRADO.getContenido());
            return new ResponseEntity<>(respuestaNoEncontrado, HttpStatus.NOT_FOUND);
        }
        final var respuesta = Respuesta.crearExitosa(List.of(resultados.get(0)));
        respuesta.agregarMensaje(MensajesEnum.EXITO_CLIENTE_CONSULTA_POR_ID.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Respuesta<ClienteDTO>> registrar(@RequestBody final ClienteDTO dto) {
        fachada.registrar(dto);
        final var respuesta = Respuesta.<ClienteDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_CLIENTE_REGISTRO.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Respuesta<ClienteDTO>> actualizar(@PathVariable final UUID id,
            @RequestBody final ClienteDTO dto) {
        final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new ClienteDTO());
        dtoSeguro.setId(UtilUUID.obtenerValorDefecto(id));
        fachada.actualizar(dtoSeguro);
        final var respuesta = Respuesta.<ClienteDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_CLIENTE_ACTUALIZACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Respuesta<ClienteDTO>> eliminar(@PathVariable final UUID id) {
        fachada.eliminar(id);
        final var respuesta = Respuesta.<ClienteDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_CLIENTE_ELIMINACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
