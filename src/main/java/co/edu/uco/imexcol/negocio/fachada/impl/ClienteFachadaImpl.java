package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.ClienteDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.ClienteDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.ClienteNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.ClienteFachada;

public final class ClienteFachadaImpl implements ClienteFachada {

    private final ClienteDTOAssembler ensamblador;

    public ClienteFachadaImpl() {
        super();
        this.ensamblador = ClienteDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final ClienteDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new ClienteNegocioImpl(fabrica).registrar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void actualizar(final ClienteDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new ClienteNegocioImpl(fabrica).actualizar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void eliminar(final UUID id) {
        EjecutorTransaccionFachada.ejecutar(fabrica -> new ClienteNegocioImpl(fabrica).eliminar(id));
    }

    @Override
    public List<ClienteDTO> consultar(final ClienteDTO filtros) {
        return EjecutorTransaccionFachada.ejecutarConResultado(fabrica -> ensamblador.ensamblarDTO(
                new ClienteNegocioImpl(fabrica).consultar(ensamblador.ensamblarDominio(filtros))));
    }

    @Override
    public ClienteDTO autenticar(final ClienteDTO credenciales) {
        return EjecutorTransaccionFachada.ejecutarConResultado(fabrica -> ensamblador.ensamblarDTO(
                new ClienteNegocioImpl(fabrica).autenticar(ensamblador.ensamblarDominio(credenciales))));
    }
}
