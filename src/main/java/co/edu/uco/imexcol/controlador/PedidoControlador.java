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
import co.edu.uco.imexcol.dto.ClienteDTO;
import co.edu.uco.imexcol.dto.PedidoDTO;
import co.edu.uco.imexcol.negocio.fachada.PedidoFachada;
import co.edu.uco.imexcol.negocio.fachada.impl.PedidoFachadaImpl;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

@RestController
@RequestMapping("/api/v1/pedidos")
public final class PedidoControlador {

    private final PedidoFachada fachada;

    public PedidoControlador() {
        super();
        this.fachada = new PedidoFachadaImpl();
    }

    @GetMapping
    public ResponseEntity<Respuesta<PedidoDTO>> consultar(
            @RequestParam(required = false) final UUID id,
            @RequestParam(required = false) final UUID idCliente,
            @RequestParam(required = false) final String estado) {

        final var filtros = new PedidoDTO();
        filtros.setId(UtilUUID.obtenerValorDefecto(id));
        filtros.setEstado(UtilTexto.aplicarTrim(estado));

        final var clienteFiltro = new ClienteDTO();
        clienteFiltro.setId(UtilUUID.obtenerValorDefecto(idCliente));
        filtros.setCliente(clienteFiltro);

        final var respuesta = Respuesta.crearExitosa(fachada.consultar(filtros));
        respuesta.agregarMensaje(MensajesEnum.EXITO_PEDIDO_CONSULTA.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta<PedidoDTO>> consultarPorId(@PathVariable final UUID id) {
        final var filtros = new PedidoDTO();
        filtros.setId(UtilUUID.obtenerValorDefecto(id));

        final var resultados = fachada.consultar(filtros);
        if (UtilObjeto.esNulo(resultados) || resultados.isEmpty()) {
            final var respuestaNoEncontrado = Respuesta.<PedidoDTO>crearFallida();
            respuestaNoEncontrado.agregarMensaje(MensajesEnum.ERROR_USUARIO_PEDIDO_NO_ENCONTRADO.getContenido());
            return new ResponseEntity<>(respuestaNoEncontrado, HttpStatus.NOT_FOUND);
        }
        final var respuesta = Respuesta.crearExitosa(List.of(resultados.get(0)));
        respuesta.agregarMensaje(MensajesEnum.EXITO_PEDIDO_CONSULTA_POR_ID.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Respuesta<PedidoDTO>> registrar(@RequestBody final PedidoDTO dto) {
        fachada.registrar(dto);
        final var respuesta = Respuesta.<PedidoDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_PEDIDO_REGISTRO.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Respuesta<PedidoDTO>> actualizar(@PathVariable final UUID id,
            @RequestBody final PedidoDTO dto) {
        final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new PedidoDTO());
        dtoSeguro.setId(UtilUUID.obtenerValorDefecto(id));
        fachada.actualizar(dtoSeguro);
        final var respuesta = Respuesta.<PedidoDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_PEDIDO_ACTUALIZACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Respuesta<PedidoDTO>> eliminar(@PathVariable final UUID id) {
        fachada.eliminar(id);
        final var respuesta = Respuesta.<PedidoDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_PEDIDO_ELIMINACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
