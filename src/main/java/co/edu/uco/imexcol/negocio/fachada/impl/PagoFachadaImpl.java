package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.PagoDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.PagoDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.PagoNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.PagoFachada;

public final class PagoFachadaImpl implements PagoFachada {

    private final PagoDTOAssembler ensamblador;

    public PagoFachadaImpl() {
        super();
        this.ensamblador = PagoDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final PagoDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new PagoNegocioImpl(fabrica).registrar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void actualizar(final PagoDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new PagoNegocioImpl(fabrica).actualizar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void eliminar(final UUID id) {
        EjecutorTransaccionFachada.ejecutar(fabrica -> new PagoNegocioImpl(fabrica).eliminar(id));
    }

    @Override
    public List<PagoDTO> consultar(final PagoDTO filtros) {
        return EjecutorTransaccionFachada.ejecutarConResultado(fabrica -> ensamblador.ensamblarDTO(
                new PagoNegocioImpl(fabrica).consultar(ensamblador.ensamblarDominio(filtros))));
    }
}
