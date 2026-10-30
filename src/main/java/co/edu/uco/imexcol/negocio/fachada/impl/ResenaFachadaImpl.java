package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.ResenaDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.ResenaDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.ResenaNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.ResenaFachada;

public final class ResenaFachadaImpl implements ResenaFachada {

    private final ResenaDTOAssembler ensamblador;

    public ResenaFachadaImpl() {
        super();
        this.ensamblador = ResenaDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final ResenaDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new ResenaNegocioImpl(fabrica).registrar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void eliminarPorCliente(final UUID idResena, final UUID idCliente) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new ResenaNegocioImpl(fabrica).eliminarPorCliente(idResena, idCliente));
    }

    @Override
    public void eliminarPorAdministrador(final UUID idResena, final UUID idAdministrador) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new ResenaNegocioImpl(fabrica).eliminarPorAdministrador(idResena, idAdministrador));
    }

    @Override
    public List<ResenaDTO> consultarPorProducto(final UUID idProducto) {
        return EjecutorTransaccionFachada.ejecutarConResultado(fabrica -> ensamblador.ensamblarDTO(
                new ResenaNegocioImpl(fabrica).consultarPorProducto(idProducto)));
    }

    @Override
    public List<ResenaDTO> consultar(final ResenaDTO filtros) {
        return EjecutorTransaccionFachada.ejecutarConResultado(fabrica -> ensamblador.ensamblarDTO(
                new ResenaNegocioImpl(fabrica).consultar(ensamblador.ensamblarDominio(filtros))));
    }
}
