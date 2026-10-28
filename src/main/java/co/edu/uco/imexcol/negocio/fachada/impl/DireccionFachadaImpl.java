package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.DireccionDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.DireccionDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.DireccionNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.DireccionFachada;

public final class DireccionFachadaImpl implements DireccionFachada {

    private final DireccionDTOAssembler ensamblador;

    public DireccionFachadaImpl() {
        super();
        this.ensamblador = DireccionDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final DireccionDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new DireccionNegocioImpl(fabrica).registrar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void actualizar(final DireccionDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new DireccionNegocioImpl(fabrica).actualizar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void eliminar(final UUID id) {
        EjecutorTransaccionFachada.ejecutar(fabrica -> new DireccionNegocioImpl(fabrica).eliminar(id));
    }

    @Override
    public List<DireccionDTO> consultar(final DireccionDTO filtros) {
        return EjecutorTransaccionFachada.ejecutarConResultado(fabrica -> ensamblador.ensamblarDTO(
                new DireccionNegocioImpl(fabrica).consultar(ensamblador.ensamblarDominio(filtros))));
    }
}
