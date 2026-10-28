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
import co.edu.uco.imexcol.dto.CarritoComprasDTO;
import co.edu.uco.imexcol.dto.ClienteDTO;
import co.edu.uco.imexcol.negocio.fachada.CarritoComprasFachada;
import co.edu.uco.imexcol.negocio.fachada.impl.CarritoComprasFachadaImpl;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

@RestController
@RequestMapping("/api/v1/carritos-compras")
public final class CarritoComprasControlador {

    private final CarritoComprasFachada fachada;

    public CarritoComprasControlador() {
        super();
        this.fachada = new CarritoComprasFachadaImpl();
    }

    @GetMapping
    public ResponseEntity<Respuesta<CarritoComprasDTO>> consultar(
            @RequestParam(required = false) final UUID id,
            @RequestParam(required = false) final UUID idCliente,
            @RequestParam(required = false) final String estado) {

        final var filtros = new CarritoComprasDTO();
        filtros.setId(UtilUUID.obtenerValorDefecto(id));
        filtros.setEstado(UtilTexto.aplicarTrim(estado));

        final var clienteFiltro = new ClienteDTO();
        clienteFiltro.setId(UtilUUID.obtenerValorDefecto(idCliente));
        filtros.setCliente(clienteFiltro);

        final var respuesta = Respuesta.crearExitosa(fachada.consultar(filtros));
        respuesta.agregarMensaje(MensajesEnum.EXITO_CARRITO_COMPRAS_CONSULTA.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta<CarritoComprasDTO>> consultarPorId(@PathVariable final UUID id) {
        final var filtros = new CarritoComprasDTO();
        filtros.setId(UtilUUID.obtenerValorDefecto(id));

        final var resultados = fachada.consultar(filtros);
        if (UtilObjeto.esNulo(resultados) || resultados.isEmpty()) {
            final var respuestaNoEncontrado = Respuesta.<CarritoComprasDTO>crearFallida();
            respuestaNoEncontrado.agregarMensaje(
                    MensajesEnum.ERROR_USUARIO_CARRITO_COMPRAS_NO_ENCONTRADO.getContenido());
            return new ResponseEntity<>(respuestaNoEncontrado, HttpStatus.NOT_FOUND);
        }
        final var respuesta = Respuesta.crearExitosa(List.of(resultados.get(0)));
        respuesta.agregarMensaje(MensajesEnum.EXITO_CARRITO_COMPRAS_CONSULTA_POR_ID.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Respuesta<CarritoComprasDTO>> registrar(@RequestBody final CarritoComprasDTO dto) {
        fachada.registrar(dto);
        final var respuesta = Respuesta.<CarritoComprasDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_CARRITO_COMPRAS_REGISTRO.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Respuesta<CarritoComprasDTO>> actualizar(@PathVariable final UUID id,
            @RequestBody final CarritoComprasDTO dto) {
        final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new CarritoComprasDTO());
        dtoSeguro.setId(UtilUUID.obtenerValorDefecto(id));
        fachada.actualizar(dtoSeguro);
        final var respuesta = Respuesta.<CarritoComprasDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_CARRITO_COMPRAS_ACTUALIZACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Respuesta<CarritoComprasDTO>> eliminar(@PathVariable final UUID id) {
        fachada.eliminar(id);
        final var respuesta = Respuesta.<CarritoComprasDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_CARRITO_COMPRAS_ELIMINACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
