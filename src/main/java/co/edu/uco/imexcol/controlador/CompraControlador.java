package co.edu.uco.imexcol.controlador;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.imexcol.controlador.dto.Respuesta;
import co.edu.uco.imexcol.dto.ResultadoCompraDTO;
import co.edu.uco.imexcol.dto.SolicitudCompraDTO;
import co.edu.uco.imexcol.negocio.fachada.CompraFachada;
import co.edu.uco.imexcol.negocio.fachada.impl.CompraFachadaImpl;
import co.edu.uco.imexcol.transversal.MensajesEnum;

@RestController
@RequestMapping("/api/v1/compras")
public final class CompraControlador {

    private final CompraFachada fachada;

    public CompraControlador() {
        super();
        this.fachada = new CompraFachadaImpl();
    }

    @PostMapping
    public ResponseEntity<Respuesta<ResultadoCompraDTO>> realizarCompra(
            @RequestBody final SolicitudCompraDTO solicitud) {
        final var resultado = fachada.realizarCompra(solicitud);
        final var respuesta = Respuesta.crearExitosa(List.of(resultado));
        respuesta.agregarMensaje(MensajesEnum.EXITO_COMPRA_REALIZADA.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }
}
