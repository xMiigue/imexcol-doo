package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.casouso.AdministradorNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.dominio.AdministradorDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class AdministradorNegocioImpl implements AdministradorNegocio {

    private static final String CASO_USO = "AdministradorNegocio";

    public AdministradorNegocioImpl() {
        super();
        // TODO: recibir DAOFactory cuando exista la capa de datos.
    }

    @Override
    public void registrar(final AdministradorDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new AdministradorDominio());
        validarDatosComunes(dominioSeguro);
        // TODO: integrar con DAO — daoFactory.obtenerAdministradorDAO().crear(...)
        throw operacionPendiente("registrar");
    }

    @Override
    public void actualizar(final AdministradorDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new AdministradorDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "administrador");
        validarDatosComunes(dominioSeguro);
        // TODO: integrar con DAO — daoFactory.obtenerAdministradorDAO().modificar(...)
        throw operacionPendiente("actualizar");
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "administrador");
        // TODO: integrar con DAO — daoFactory.obtenerAdministradorDAO().eliminar(id);
        throw operacionPendiente("eliminar");
    }

    @Override
    public List<AdministradorDominio> consultar(final AdministradorDominio filtros) {
        UtilObjeto.obtenerValorDefecto(filtros, new AdministradorDominio());
        // TODO: integrar con DAO — daoFactory.obtenerAdministradorDAO().consultarPorFiltro(...)
        return new ArrayList<>();
    }

    private void validarDatosComunes(final AdministradorDominio dominio) {
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getNombreUsuario(),
                "nombre de usuario del administrador");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getNombreUsuario(),
                "nombre de usuario del administrador", 4, 50);

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getCorreoElectronico(),
                "correo electrónico del administrador");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getCorreoElectronico(),
                "correo electrónico del administrador", 6, 150);

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getContrasena(),
                "contraseña del administrador");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getContrasena(),
                "contraseña del administrador", 8, 255);
    }

    private static ImexcolException operacionPendiente(final String operacion) {
        return ImexcolException.crear(
                MensajesEnum.ERROR_USUARIO_CASO_USO_DAO_PENDIENTE.getContenido(),
                MensajesEnum.ERROR_TECNICO_CASO_USO_DAO_PENDIENTE.getContenido().formatted(operacion, CASO_USO),
                Lugar.NEGOCIO);
    }
}
