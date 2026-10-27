package co.edu.uco.imexcol.controlador;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.imexcol.controlador.dto.Respuesta;
import co.edu.uco.imexcol.dto.EnvioDTO;
import co.edu.uco.imexcol.dto.EventoRastreoDTO;
import co.edu.uco.imexcol.negocio.fachada.EventoRastreoFachada;
import co.edu.uco.imexcol.negocio.fachada.impl.EventoRastreoFachadaImpl;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/envios/{idEnvio}/eventos-rastreo")
public final class EventoRastreoControlador {

    private final EventoRastreoFachada fachada;

    public EventoRastreoControlador() {
        super();
        this.fachada = new EventoRastreoFachadaImpl();
    }

    @GetMapping
    public ResponseEntity<Respuesta<EventoRastreoDTO>> consultarHistorialPorEnvio(
            @PathVariable final UUID idEnvio) {
        final var respuesta = Respuesta.crearExitosa(fachada.consultarHistorialPorEnvio(idEnvio));
        respuesta.agregarMensaje(MensajesEnum.EXITO_EVENTO_RASTREO_CONSULTA.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<Respuesta<EventoRastreoDTO>> consultarHistorialDelCliente(
            @PathVariable final UUID idEnvio, @PathVariable final UUID idCliente) {
        final var respuesta = Respuesta.crearExitosa(fachada.consultarHistorialDelCliente(idEnvio, idCliente));
        respuesta.agregarMensaje(MensajesEnum.EXITO_EVENTO_RASTREO_CONSULTA.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Respuesta<EventoRastreoDTO>> registrar(@PathVariable final UUID idEnvio,
            @RequestBody final EventoRastreoDTO dto) {
        final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new EventoRastreoDTO());
        dtoSeguro.setEnvio(new EnvioDTO(UtilUUID.obtenerValorDefecto(idEnvio)));
        fachada.registrar(dtoSeguro);
        final var respuesta = Respuesta.<EventoRastreoDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_EVENTO_RASTREO_REGISTRO.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }
}
