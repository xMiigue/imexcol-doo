package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.MetodoPagoDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.MetodoPagoDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.MetodoPagoNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.MetodoPagoFachada;

public final class MetodoPagoFachadaImpl implements MetodoPagoFachada {

    private final MetodoPagoDTOAssembler ensamblador;

    public MetodoPagoFachadaImpl() {
        super();
        this.ensamblador = MetodoPagoDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final MetodoPagoDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new MetodoPagoNegocioImpl(fabrica).registrar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void actualizar(final MetodoPagoDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new MetodoPagoNegocioImpl(fabrica).actualizar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void eliminar(final UUID id) {
        EjecutorTransaccionFachada.ejecutar(fabrica -> new MetodoPagoNegocioImpl(fabrica).eliminar(id));
    }

    @Override
    public List<MetodoPagoDTO> consultar(final MetodoPagoDTO filtros) {
        return EjecutorTransaccionFachada.ejecutarConResultado(fabrica -> ensamblador.ensamblarDTO(
                new MetodoPagoNegocioImpl(fabrica).consultar(ensamblador.ensamblarDominio(filtros))));
    }
}
