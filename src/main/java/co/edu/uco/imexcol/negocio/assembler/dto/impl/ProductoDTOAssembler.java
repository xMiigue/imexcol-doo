package co.edu.uco.imexcol.negocio.assembler.dto.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.dto.ProductoDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.imexcol.negocio.dominio.ProductoDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class ProductoDTOAssembler implements DTOAssembler<ProductoDominio, ProductoDTO> {

    private static final ProductoDTOAssembler INSTANCIA = new ProductoDTOAssembler();

    private ProductoDTOAssembler() {
        super();
    }

    public static ProductoDTOAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public ProductoDominio ensamblarDominio(final ProductoDTO dto) {
        var dtoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new ProductoDTO());
        return new ProductoDominio(
                dtoAEnsamblar.getId(),
                CategoriaDTOAssembler.obtenerInstancia().ensamblarDominio(dtoAEnsamblar.getCategoria()),
                dtoAEnsamblar.getNombre(),
                dtoAEnsamblar.getDescripcion(),
                dtoAEnsamblar.getPrecio(),
                dtoAEnsamblar.getStock(),
                dtoAEnsamblar.getImagenUrl(),
                dtoAEnsamblar.isEstado());
    }

    @Override
    public ProductoDTO ensamblarDTO(final ProductoDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new ProductoDominio());
        return new ProductoDTO(
                dominioAEnsamblar.getId(),
                CategoriaDTOAssembler.obtenerInstancia().ensamblarDTO(dominioAEnsamblar.getCategoria()),
                dominioAEnsamblar.getNombre(),
                dominioAEnsamblar.getDescripcion(),
                dominioAEnsamblar.getPrecio(),
                dominioAEnsamblar.getStock(),
                dominioAEnsamblar.getImagenUrl(),
                dominioAEnsamblar.isEstado());
    }

    @Override
    public List<ProductoDTO> ensamblarDTO(final List<ProductoDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<ProductoDominio>());
        var listaDTO = new ArrayList<ProductoDTO>();
        for (var dominio : listaSegura) {
            listaDTO.add(ensamblarDTO(dominio));
        }
        return listaDTO;
    }

    @Override
    public List<ProductoDominio> ensamblarDominio(final List<ProductoDTO> listaDTO) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDTO, new ArrayList<ProductoDTO>());
        var listaDominios = new ArrayList<ProductoDominio>();
        for (var dto : listaSegura) {
            listaDominios.add(ensamblarDominio(dto));
        }
        return listaDominios;
    }
}
