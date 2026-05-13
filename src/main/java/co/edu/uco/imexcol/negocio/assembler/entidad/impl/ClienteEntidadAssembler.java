package co.edu.uco.imexcol.negocio.assembler.entidad.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.entidad.ClienteEntidad;
import co.edu.uco.imexcol.negocio.assembler.entidad.EntidadAssembler;
import co.edu.uco.imexcol.negocio.dominio.ClienteDominio;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class ClienteEntidadAssembler implements EntidadAssembler<ClienteDominio, ClienteEntidad> {

    private static final ClienteEntidadAssembler INSTANCIA = new ClienteEntidadAssembler();

    private ClienteEntidadAssembler() {
        super();
    }

    public static ClienteEntidadAssembler obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public ClienteEntidad ensamblarEntidad(final ClienteDominio dominio) {
        var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new ClienteDominio());
        return new ClienteEntidad(
                dominioAEnsamblar.getId(),
                dominioAEnsamblar.getNombre(),
                dominioAEnsamblar.getApellido(),
                dominioAEnsamblar.getCorreoElectronico(),
                dominioAEnsamblar.getContrasena(),
                dominioAEnsamblar.getTelefono(),
                dominioAEnsamblar.isEstado());
    }

    @Override
    public ClienteDominio ensamblarDominio(final ClienteEntidad entidad) {
        var entidadAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new ClienteEntidad());
        return new ClienteDominio(
                entidadAEnsamblar.getId(),
                entidadAEnsamblar.getNombre(),
                entidadAEnsamblar.getApellido(),
                entidadAEnsamblar.getCorreoElectronico(),
                entidadAEnsamblar.getContrasena(),
                entidadAEnsamblar.getTelefono(),
                entidadAEnsamblar.isEstado());
    }

    @Override
    public List<ClienteEntidad> ensamblarEntidad(final List<ClienteDominio> listaDominios) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaDominios, new ArrayList<ClienteDominio>());
        var listaEntidades = new ArrayList<ClienteEntidad>();
        for (var dominio : listaSegura) {
            listaEntidades.add(ensamblarEntidad(dominio));
        }
        return listaEntidades;
    }

    @Override
    public List<ClienteDominio> ensamblarDominio(final List<ClienteEntidad> listaEntidades) {
        var listaSegura = UtilObjeto.obtenerValorDefecto(listaEntidades, new ArrayList<ClienteEntidad>());
        var listaDominios = new ArrayList<ClienteDominio>();
        for (var entidad : listaSegura) {
            listaDominios.add(ensamblarDominio(entidad));
        }
        return listaDominios;
    }
}
