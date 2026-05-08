package co.edu.uco.imexcol.negocio.assembler.entidad.impl;

import co.edu.uco.imexcol.entidad.MetodoPagoEntidad;
import co.edu.uco.imexcol.negocio.assembler.entidad.EntidadAssembler;
import co.edu.uco.imexcol.negocio.dominio.MetodoPagoDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class MetodoPagoEntidadAssembler implements EntidadAssembler<MetodoPagoDominio, MetodoPagoEntidad> {

    private static final MetodoPagoEntidadAssembler INSTANCIA = new MetodoPagoEntidadAssembler();

    private MetodoPagoEntidadAssembler() {
        super();
    }

    public static final MetodoPagoEntidadAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public MetodoPagoEntidad ensamblarEntidad(final MetodoPagoDominio dominio) {
        var metodoPagoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new MetodoPagoDominio.Builder().build());
        return new MetodoPagoEntidad.Builder()
                .id(metodoPagoAEnsamblar.getId())
                .nombre(metodoPagoAEnsamblar.getNombre())
                .descripcion(metodoPagoAEnsamblar.getDescripcion())
                .estado(metodoPagoAEnsamblar.isEstado())
                .build();
    }

    @Override
    public MetodoPagoDominio ensamblarDominio(final MetodoPagoEntidad entidad) {
        var metodoPagoAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new MetodoPagoEntidad.Builder().build());
        return new MetodoPagoDominio.Builder()
                .id(metodoPagoAEnsamblar.getId())
                .nombre(metodoPagoAEnsamblar.getNombre())
                .descripcion(metodoPagoAEnsamblar.getDescripcion())
                .estado(metodoPagoAEnsamblar.isEstado())
                .build();
    }
}
