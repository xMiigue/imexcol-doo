package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.CategoriaDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.CategoriaDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.CategoriaNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.CategoriaFachada;

public final class CategoriaFachadaImpl implements CategoriaFachada {

    private final CategoriaDTOAssembler ensamblador;

    public CategoriaFachadaImpl() {
        super();
        this.ensamblador = CategoriaDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final CategoriaDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new CategoriaNegocioImpl(fabrica).registrar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void actualizar(final CategoriaDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new CategoriaNegocioImpl(fabrica).actualizar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void eliminar(final UUID id) {
        EjecutorTransaccionFachada.ejecutar(fabrica -> new CategoriaNegocioImpl(fabrica).eliminar(id));
    }

    @Override
    public List<CategoriaDTO> consultar(final CategoriaDTO filtros) {
        return EjecutorTransaccionFachada.ejecutarConResultado(fabrica -> ensamblador.ensamblarDTO(
                new CategoriaNegocioImpl(fabrica).consultar(ensamblador.ensamblarDominio(filtros))));
    }
}
