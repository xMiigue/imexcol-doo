package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.AdministradorDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.AdministradorDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.AdministradorNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.AdministradorFachada;

public final class AdministradorFachadaImpl implements AdministradorFachada {

    private final AdministradorDTOAssembler ensamblador;

    public AdministradorFachadaImpl() {
        super();
        this.ensamblador = AdministradorDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final AdministradorDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new AdministradorNegocioImpl(fabrica).registrar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void actualizar(final AdministradorDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new AdministradorNegocioImpl(fabrica).actualizar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void eliminar(final UUID id) {
        EjecutorTransaccionFachada.ejecutar(fabrica -> new AdministradorNegocioImpl(fabrica).eliminar(id));
    }

    @Override
    public List<AdministradorDTO> consultar(final AdministradorDTO filtros) {
        return EjecutorTransaccionFachada.ejecutarConResultado(fabrica -> ensamblador.ensamblarDTO(
                new AdministradorNegocioImpl(fabrica).consultar(ensamblador.ensamblarDominio(filtros))));
    }

    @Override
    public AdministradorDTO autenticar(final AdministradorDTO credenciales) {
        return EjecutorTransaccionFachada.ejecutarConResultado(fabrica -> ensamblador.ensamblarDTO(
                new AdministradorNegocioImpl(fabrica).autenticar(ensamblador.ensamblarDominio(credenciales))));
    }
}
