package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.EnvioDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.EnvioDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.EnvioNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.EnvioFachada;

public final class EnvioFachadaImpl implements EnvioFachada {

    private final EnvioDTOAssembler ensamblador;

    public EnvioFachadaImpl() {
        super();
        this.ensamblador = EnvioDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final EnvioDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new EnvioNegocioImpl(fabrica).registrar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void actualizar(final EnvioDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new EnvioNegocioImpl(fabrica).actualizar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void eliminar(final UUID id) {
        EjecutorTransaccionFachada.ejecutar(fabrica -> new EnvioNegocioImpl(fabrica).eliminar(id));
    }

    @Override
    public List<EnvioDTO> consultar(final EnvioDTO filtros) {
        return EjecutorTransaccionFachada.ejecutarConResultado(fabrica -> ensamblador.ensamblarDTO(
                new EnvioNegocioImpl(fabrica).consultar(ensamblador.ensamblarDominio(filtros))));
    }
}
