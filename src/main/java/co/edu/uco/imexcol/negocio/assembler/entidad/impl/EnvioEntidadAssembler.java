package co.edu.uco.imexcol.negocio.assembler.entidad.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.entidad.EnvioEntidad;
import co.edu.uco.imexcol.negocio.assembler.entidad.EntidadAssembler;
import co.edu.uco.imexcol.negocio.dominio.EnvioDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class EnvioEntidadAssembler implements EntidadAssembler<EnvioDominio, EnvioEntidad> {

    private static final EnvioEntidadAssembler INSTANCIA = new EnvioEntidadAssembler();

    private EnvioEntidadAssembler() {
        super();
    }

    public static EnvioEntidadAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public EnvioEntidad ensamblarEntidad(final EnvioDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new EnvioDominio());
        return new EnvioEntidad(
                dominioAEnsamblar.getId(),
                PedidoEntidadAssembler.obtenerInstancia().ensamblarEntidad(dominioAEnsamblar.getPedido()),
                dominioAEnsamblar.getFechaEnvio(),
                dominioAEnsamblar.getFechaEntrega(),
                dominioAEnsamblar.getTransportadora(),
                dominioAEnsamblar.getNumeroGuia(),
                dominioAEnsamblar.getEstado());
    }

    @Override
    public EnvioDominio ensamblarDominio(final EnvioEntidad entidad) {
        var entidadAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new EnvioEntidad());
        return new EnvioDominio(
                entidadAEnsamblar.getId(),
                PedidoEntidadAssembler.obtenerInstancia().ensamblarDominio(entidadAEnsamblar.getPedido()),
                entidadAEnsamblar.getFechaEnvio(),
                entidadAEnsamblar.getFechaEntrega(),
                entidadAEnsamblar.getTransportadora(),
                entidadAEnsamblar.getNumeroGuia(),
                entidadAEnsamblar.getEstado());
    }

    @Override
    public List<EnvioEntidad> ensamblarEntidad(final List<EnvioDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<EnvioDominio>());
        var listaEntidades = new ArrayList<EnvioEntidad>();
        for (var dominio : listaSegura) {
            listaEntidades.add(ensamblarEntidad(dominio));
        }
        return listaEntidades;
    }

    @Override
    public List<EnvioDominio> ensamblarDominio(final List<EnvioEntidad> listaEntidades) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaEntidades, new ArrayList<EnvioEntidad>());
        var listaDominios = new ArrayList<EnvioDominio>();
        for (var entidad : listaSegura) {
            listaDominios.add(ensamblarDominio(entidad));
        }
        return listaDominios;
    }
}
