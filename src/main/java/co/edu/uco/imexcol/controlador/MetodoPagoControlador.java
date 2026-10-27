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
import co.edu.uco.imexcol.dto.MetodoPagoDTO;
import co.edu.uco.imexcol.negocio.fachada.MetodoPagoFachada;
import co.edu.uco.imexcol.negocio.fachada.impl.MetodoPagoFachadaImpl;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/metodos-pago")
public final class MetodoPagoControlador {

    private final MetodoPagoFachada fachada;

    public MetodoPagoControlador() {
        super();
        this.fachada = new MetodoPagoFachadaImpl();
    }

    @GetMapping
    public ResponseEntity<Respuesta<MetodoPagoDTO>> consultar(
            @RequestParam(required = false) final UUID id,
            @RequestParam(required = false) final String nombre) {

        final var filtros = new MetodoPagoDTO();
        filtros.setId(UtilUUID.obtenerValorDefecto(id));
        filtros.setNombre(UtilTexto.aplicarTrim(nombre));

        final var respuesta = Respuesta.crearExitosa(fachada.consultar(filtros));
        respuesta.agregarMensaje(MensajesEnum.EXITO_METODO_PAGO_CONSULTA.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta<MetodoPagoDTO>> consultarPorId(@PathVariable final UUID id) {
        final var filtros = new MetodoPagoDTO();
        filtros.setId(UtilUUID.obtenerValorDefecto(id));

        final var resultados = fachada.consultar(filtros);
        if (UtilObjeto.esNulo(resultados) || resultados.isEmpty()) {
            final var respuestaNoEncontrado = Respuesta.<MetodoPagoDTO>crearFallida();
            respuestaNoEncontrado.agregarMensaje(MensajesEnum.ERROR_USUARIO_METODO_PAGO_NO_ENCONTRADO.getContenido());
            return new ResponseEntity<>(respuestaNoEncontrado, HttpStatus.NOT_FOUND);
        }
        final var respuesta = Respuesta.crearExitosa(List.of(resultados.get(0)));
        respuesta.agregarMensaje(MensajesEnum.EXITO_METODO_PAGO_CONSULTA_POR_ID.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Respuesta<MetodoPagoDTO>> registrar(@RequestBody final MetodoPagoDTO dto) {
        fachada.registrar(dto);
        final var respuesta = Respuesta.<MetodoPagoDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_METODO_PAGO_REGISTRO.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Respuesta<MetodoPagoDTO>> actualizar(@PathVariable final UUID id,
            @RequestBody final MetodoPagoDTO dto) {
        final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new MetodoPagoDTO());
        dtoSeguro.setId(UtilUUID.obtenerValorDefecto(id));
        fachada.actualizar(dtoSeguro);
        final var respuesta = Respuesta.<MetodoPagoDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_METODO_PAGO_ACTUALIZACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Respuesta<MetodoPagoDTO>> eliminar(@PathVariable final UUID id) {
        fachada.eliminar(id);
        final var respuesta = Respuesta.<MetodoPagoDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_METODO_PAGO_ELIMINACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
