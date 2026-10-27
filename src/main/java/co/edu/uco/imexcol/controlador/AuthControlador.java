package co.edu.uco.imexcol.controlador;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.imexcol.controlador.dto.Respuesta;
import co.edu.uco.imexcol.dto.AdministradorDTO;
import co.edu.uco.imexcol.dto.ClienteDTO;
import co.edu.uco.imexcol.negocio.fachada.AdministradorFachada;
import co.edu.uco.imexcol.negocio.fachada.ClienteFachada;
import co.edu.uco.imexcol.negocio.fachada.impl.AdministradorFachadaImpl;
import co.edu.uco.imexcol.negocio.fachada.impl.ClienteFachadaImpl;
import co.edu.uco.imexcol.transversal.MensajesEnum;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
public final class AuthControlador {

    private final ClienteFachada clienteFachada;
    private final AdministradorFachada administradorFachada;

    public AuthControlador() {
        super();
        this.clienteFachada = new ClienteFachadaImpl();
        this.administradorFachada = new AdministradorFachadaImpl();
    }

    @PostMapping("/cliente")
    public ResponseEntity<Respuesta<ClienteDTO>> autenticarCliente(@RequestBody final ClienteDTO credenciales) {
        final var respuesta = Respuesta.crearExitosa(List.of(clienteFachada.autenticar(credenciales)));
        respuesta.agregarMensaje(MensajesEnum.EXITO_AUTENTICACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/administrador")
    public ResponseEntity<Respuesta<AdministradorDTO>> autenticarAdministrador(
            @RequestBody final AdministradorDTO credenciales) {
        final var respuesta = Respuesta.crearExitosa(List.of(administradorFachada.autenticar(credenciales)));
        respuesta.agregarMensaje(MensajesEnum.EXITO_AUTENTICACION.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/cliente/registro")
    public ResponseEntity<Respuesta<ClienteDTO>> registrarCliente(@RequestBody final ClienteDTO dto) {
        clienteFachada.registrar(dto);
        final var respuesta = Respuesta.<ClienteDTO>crearExitosa();
        respuesta.agregarMensaje(MensajesEnum.EXITO_AUTENTICACION_REGISTRO_CLIENTE.getContenido());
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }
}
