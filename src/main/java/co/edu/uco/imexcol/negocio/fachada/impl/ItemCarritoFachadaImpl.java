package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.ItemCarritoDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.ItemCarritoDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.ItemCarritoNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.ItemCarritoFachada;

public final class ItemCarritoFachadaImpl implements ItemCarritoFachada {

    private final ItemCarritoDTOAssembler ensamblador;

    public ItemCarritoFachadaImpl() {
        super();
        this.ensamblador = ItemCarritoDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final ItemCarritoDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new ItemCarritoNegocioImpl(fabrica).registrar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void actualizar(final ItemCarritoDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new ItemCarritoNegocioImpl(fabrica).actualizar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void eliminar(final UUID id) {
        EjecutorTransaccionFachada.ejecutar(fabrica -> new ItemCarritoNegocioImpl(fabrica).eliminar(id));
    }

    @Override
    public List<ItemCarritoDTO> consultar(final ItemCarritoDTO filtros) {
        return EjecutorTransaccionFachada.ejecutarConResultado(fabrica -> ensamblador.ensamblarDTO(
                new ItemCarritoNegocioImpl(fabrica).consultar(ensamblador.ensamblarDominio(filtros))));
    }
}
