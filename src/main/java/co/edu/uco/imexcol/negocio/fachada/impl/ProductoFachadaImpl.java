package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.ProductoDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.ProductoDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.ProductoNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.ProductoFachada;

public final class ProductoFachadaImpl implements ProductoFachada {

    private final ProductoDTOAssembler ensamblador;

    public ProductoFachadaImpl() {
        super();
        this.ensamblador = ProductoDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final ProductoDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new ProductoNegocioImpl(fabrica).registrar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void actualizar(final ProductoDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new ProductoNegocioImpl(fabrica).actualizar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void eliminar(final UUID id) {
        EjecutorTransaccionFachada.ejecutar(fabrica -> new ProductoNegocioImpl(fabrica).eliminar(id));
    }

    @Override
    public List<ProductoDTO> consultar(final ProductoDTO filtros) {
        return EjecutorTransaccionFachada.ejecutarConResultado(fabrica -> ensamblador.ensamblarDTO(
                new ProductoNegocioImpl(fabrica).consultar(ensamblador.ensamblarDominio(filtros))));
    }
}
