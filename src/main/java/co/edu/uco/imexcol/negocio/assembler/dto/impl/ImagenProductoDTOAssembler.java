package co.edu.uco.imexcol.negocio.assembler.dto.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.dto.ImagenProductoDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.imexcol.negocio.dominio.ImagenProductoDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class ImagenProductoDTOAssembler implements DTOAssembler<ImagenProductoDominio, ImagenProductoDTO> {

    private static final ImagenProductoDTOAssembler INSTANCIA = new ImagenProductoDTOAssembler();

    private ImagenProductoDTOAssembler() {
        super();
    }

    public static ImagenProductoDTOAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public ImagenProductoDominio ensamblarDominio(final ImagenProductoDTO dto) {
        var dtoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new ImagenProductoDTO());
        return new ImagenProductoDominio(
                dtoAEnsamblar.getId(),
                ProductoDTOAssembler.obtenerInstancia().ensamblarDominio(dtoAEnsamblar.getProducto()),
                dtoAEnsamblar.getUrl(),
                dtoAEnsamblar.getDescripcion(),
                dtoAEnsamblar.isEsPrincipal());
    }

    @Override
    public ImagenProductoDTO ensamblarDTO(final ImagenProductoDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new ImagenProductoDominio());
        return new ImagenProductoDTO(
                dominioAEnsamblar.getId(),
                ProductoDTOAssembler.obtenerInstancia().ensamblarDTO(dominioAEnsamblar.getProducto()),
                dominioAEnsamblar.getUrl(),
                dominioAEnsamblar.getDescripcion(),
                dominioAEnsamblar.isEsPrincipal());
    }

    @Override
    public List<ImagenProductoDTO> ensamblarDTO(final List<ImagenProductoDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<ImagenProductoDominio>());
        var listaDTO = new ArrayList<ImagenProductoDTO>();
        for (var dominio : listaSegura) {
            listaDTO.add(ensamblarDTO(dominio));
        }
        return listaDTO;
    }

    @Override
    public List<ImagenProductoDominio> ensamblarDominio(final List<ImagenProductoDTO> listaDTO) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDTO, new ArrayList<ImagenProductoDTO>());
        var listaDominios = new ArrayList<ImagenProductoDominio>();
        for (var dto : listaSegura) {
            listaDominios.add(ensamblarDominio(dto));
        }
        return listaDominios;
    }
}
