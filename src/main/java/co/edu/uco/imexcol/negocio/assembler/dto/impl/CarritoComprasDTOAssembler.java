package co.edu.uco.imexcol.negocio.assembler.dto.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.dto.CarritoComprasDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.imexcol.negocio.dominio.CarritoComprasDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class CarritoComprasDTOAssembler implements DTOAssembler<CarritoComprasDominio, CarritoComprasDTO> {

    private static final CarritoComprasDTOAssembler INSTANCIA = new CarritoComprasDTOAssembler();

    private CarritoComprasDTOAssembler() {
        super();
    }

    public static CarritoComprasDTOAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public CarritoComprasDominio ensamblarDominio(final CarritoComprasDTO dto) {
        var dtoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new CarritoComprasDTO());
        return new CarritoComprasDominio(
                dtoAEnsamblar.getId(),
                ClienteDTOAssembler.obtenerInstancia().ensamblarDominio(dtoAEnsamblar.getCliente()),
                dtoAEnsamblar.getFechaCreacion(),
                dtoAEnsamblar.isEstado());
    }

    @Override
    public CarritoComprasDTO ensamblarDTO(final CarritoComprasDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new CarritoComprasDominio());
        return new CarritoComprasDTO(
                dominioAEnsamblar.getId(),
                ClienteDTOAssembler.obtenerInstancia().ensamblarDTO(dominioAEnsamblar.getCliente()),
                dominioAEnsamblar.getFechaCreacion(),
                dominioAEnsamblar.isEstado());
    }

    @Override
    public List<CarritoComprasDTO> ensamblarDTO(final List<CarritoComprasDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<CarritoComprasDominio>());
        var listaDTO = new ArrayList<CarritoComprasDTO>();
        for (var dominio : listaSegura) {
            listaDTO.add(ensamblarDTO(dominio));
        }
        return listaDTO;
    }

    @Override
    public List<CarritoComprasDominio> ensamblarDominio(final List<CarritoComprasDTO> listaDTO) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDTO, new ArrayList<CarritoComprasDTO>());
        var listaDominios = new ArrayList<CarritoComprasDominio>();
        for (var dto : listaSegura) {
            listaDominios.add(ensamblarDominio(dto));
        }
        return listaDominios;
    }
}
