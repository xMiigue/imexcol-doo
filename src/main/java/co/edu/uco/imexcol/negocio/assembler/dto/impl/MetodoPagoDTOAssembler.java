package co.edu.uco.imexcol.negocio.assembler.dto.impl;

import co.edu.uco.imexcol.dto.MetodoPagoDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.imexcol.negocio.dominio.MetodoPagoDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class MetodoPagoDTOAssembler implements DTOAssembler<MetodoPagoDominio, MetodoPagoDTO> {

    private static final MetodoPagoDTOAssembler INSTANCIA = new MetodoPagoDTOAssembler();

    private MetodoPagoDTOAssembler() {
        super();
    }

    public static final MetodoPagoDTOAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public MetodoPagoDominio ensamblarDominio(final MetodoPagoDTO dto) {
        var metodoPagoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new MetodoPagoDTO.Builder().build());
        return new MetodoPagoDominio.Builder()
                .id(metodoPagoAEnsamblar.getId())
                .nombre(metodoPagoAEnsamblar.getNombre())
                .descripcion(metodoPagoAEnsamblar.getDescripcion())
                .estado(metodoPagoAEnsamblar.isEstado())
                .build();
    }

    @Override
    public MetodoPagoDTO ensamblarDTO(final MetodoPagoDominio dominio) {
        var metodoPagoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new MetodoPagoDominio.Builder().build());
        return new MetodoPagoDTO.Builder()
                .id(metodoPagoAEnsamblar.getId())
                .nombre(metodoPagoAEnsamblar.getNombre())
                .descripcion(metodoPagoAEnsamblar.getDescripcion())
                .estado(metodoPagoAEnsamblar.isEstado())
                .build();
    }
}
