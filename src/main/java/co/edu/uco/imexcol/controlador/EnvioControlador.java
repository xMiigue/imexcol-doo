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
import co.edu.uco.imexcol.dto.EnvioDTO;
import co.edu.uco.imexcol.dto.PedidoDTO;
import co.edu.uco.imexcol.negocio.fachada.EnvioFachada;
import co.edu.uco.imexcol.negocio.fachada.impl.EnvioFachadaImpl;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/envios")
public final class EnvioControlador {

    private final EnvioFachada fachada;

    public EnvioControlador() {
        super();
        this.fachada = new EnvioFachadaImpl();
    }

    @GetMapping
    public ResponseEntity<Respuesta<EnvioDTO>> consultar(
            @RequestParam(required = false) final UUID id,
            @RequestParam(required = false) final UUID idPedido,
            @RequestParam(required = false) final String numeroGuia,
            @RequestParam(required = false) final String estado) {

        final var filtros = new EnvioDTO();
        filtros.setId(UtilUUID.obtenerValorDefecto(id));
        filtros.setNumeroGuia(UtilTexto.aplicarTrim(numeroGuia));
        filtros.setEstado(UtilTexto.aplicarTrim(estado));

        final var pedidoFiltro = new PedidoDTO();
        pedidoFiltro.setId(UtilUUID.obtenerValorDefecto(idPedido));
        filtros.setPedido(pedidoFiltro);

        final var respuesta = Respuesta.crearExitosa(fachada.consultar(filtros));
        respuesta.agregarMensaje(MensajesEnum.EXITO_ENVIO_CONSULTA.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta<EnvioDTO>> consultarPorId(@PathVariable final UUID id) {
        final var filtros = new EnvioDTO();
        filtros.setId(UtilUUID.obtenerValorDefecto(id));

        final var resultados = fachada.consultar(filtros);
        if (UtilObjeto.esNulo(resultados) || resultados.isEmpty()) {
            final var respuestaNoEncontrado = Respuesta.<EnvioDTO>crearFallida();
            respuestaNoEncontrado.agregarMensaje(MensajesEnum.ERROR_USUARIO_ENVIO_NO_ENCONTRADO.getContenido());
            return new ResponseEntity<>(respuestaNoEncontrado, HttpStatus.NOT_FOUND);
        }
        final var respuesta = Respuesta.crearExitosa(List.of(resultados.get(0)));
        respuesta.agregarMensaje(MensajesEnum.EXITO_ENVIO_CONSULTA_POR_ID.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Respuesta<EnvioDTO>> registrar(@RequestBody final EnvioDTO dto) {
        fachada.registrar(dto);
        final var respuesta = Respuesta.<EnvioDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_ENVIO_REGISTRO.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Respuesta<EnvioDTO>> actualizar(@PathVariable final UUID id,
            @RequestBody final EnvioDTO dto) {
        final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new EnvioDTO());
        dtoSeguro.setId(UtilUUID.obtenerValorDefecto(id));
        fachada.actualizar(dtoSeguro);
        final var respuesta = Respuesta.<EnvioDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_ENVIO_ACTUALIZACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Respuesta<EnvioDTO>> eliminar(@PathVariable final UUID id) {
        fachada.eliminar(id);
        final var respuesta = Respuesta.<EnvioDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_ENVIO_ELIMINACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
