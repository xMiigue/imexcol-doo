package co.edu.uco.imexcol.negocio.assembler.dto.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.dto.ItemCarritoDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.imexcol.negocio.dominio.ItemCarritoDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class ItemCarritoDTOAssembler implements DTOAssembler<ItemCarritoDominio, ItemCarritoDTO> {

    private static final ItemCarritoDTOAssembler INSTANCIA = new ItemCarritoDTOAssembler();

    private ItemCarritoDTOAssembler() {
        super();
    }

    public static ItemCarritoDTOAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public ItemCarritoDominio ensamblarDominio(final ItemCarritoDTO dto) {
        var dtoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new ItemCarritoDTO());
        return new ItemCarritoDominio(
                dtoAEnsamblar.getId(),
                CarritoComprasDTOAssembler.obtenerInstancia().ensamblarDominio(dtoAEnsamblar.getCarrito()),
                ProductoDTOAssembler.obtenerInstancia().ensamblarDominio(dtoAEnsamblar.getProducto()),
                dtoAEnsamblar.getCantidad(),
                dtoAEnsamblar.getPrecioUnitario());
    }

    @Override
    public ItemCarritoDTO ensamblarDTO(final ItemCarritoDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new ItemCarritoDominio());
        return new ItemCarritoDTO(
                dominioAEnsamblar.getId(),
                CarritoComprasDTOAssembler.obtenerInstancia().ensamblarDTO(dominioAEnsamblar.getCarrito()),
                ProductoDTOAssembler.obtenerInstancia().ensamblarDTO(dominioAEnsamblar.getProducto()),
                dominioAEnsamblar.getCantidad(),
                dominioAEnsamblar.getPrecioUnitario());
    }

    @Override
    public List<ItemCarritoDTO> ensamblarDTO(final List<ItemCarritoDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<ItemCarritoDominio>());
        var listaDTO = new ArrayList<ItemCarritoDTO>();
        for (var dominio : listaSegura) {
            listaDTO.add(ensamblarDTO(dominio));
        }
        return listaDTO;
    }

    @Override
    public List<ItemCarritoDominio> ensamblarDominio(final List<ItemCarritoDTO> listaDTO) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDTO, new ArrayList<ItemCarritoDTO>());
        var listaDominios = new ArrayList<ItemCarritoDominio>();
        for (var dto : listaSegura) {
            listaDominios.add(ensamblarDominio(dto));
        }
        return listaDominios;
    }
}
