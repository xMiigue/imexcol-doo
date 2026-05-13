package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.casouso.DireccionNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.dominio.DireccionDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class DireccionNegocioImpl implements DireccionNegocio {

    private static final String CASO_USO = "DireccionNegocio";

    public DireccionNegocioImpl() {
        super();
        // TODO: recibir DAOFactory cuando exista la capa de datos.
    }

    @Override
    public void registrar(final DireccionDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new DireccionDominio());
        validarDatosComunes(dominioSeguro);
        // TODO: integrar con DAO — daoFactory.obtenerDireccionDAO().crear(...)
        throw operacionPendiente("registrar");
    }

    @Override
    public void actualizar(final DireccionDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new DireccionDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "dirección");
        validarDatosComunes(dominioSeguro);
        // TODO: integrar con DAO — daoFactory.obtenerDireccionDAO().modificar(...)
        throw operacionPendiente("actualizar");
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "dirección");
        // TODO: integrar con DAO — daoFactory.obtenerDireccionDAO().eliminar(id);
        throw operacionPendiente("eliminar");
    }

    @Override
    public List<DireccionDominio> consultar(final DireccionDominio filtros) {
        UtilObjeto.obtenerValorDefecto(filtros, new DireccionDominio());
        // TODO: integrar con DAO — daoFactory.obtenerDireccionDAO().consultarPorFiltro(...)
        return new ArrayList<>();
    }

    private void validarDatosComunes(final DireccionDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getCliente().getId(),
                "cliente asociado a la dirección");

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getCalle(), "calle de la dirección");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getCalle(), "calle de la dirección", 5, 200);

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getCiudad(), "ciudad de la dirección");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getCiudad(), "ciudad de la dirección", 2, 100);

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getDepartamento(),
                "departamento de la dirección");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getDepartamento(),
                "departamento de la dirección", 2, 100);

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getPais(), "país de la dirección");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getPais(), "país de la dirección", 2, 100);

        if (!UtilTexto.estaVacia(dominio.getCodigoPostal())) {
            ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getCodigoPostal(),
                    "código postal de la dirección", 3, 10);
        }
    }

    private static ImexcolException operacionPendiente(final String operacion) {
        return ImexcolException.crear(
                MensajesEnum.ERROR_USUARIO_CASO_USO_DAO_PENDIENTE.getContenido(),
                MensajesEnum.ERROR_TECNICO_CASO_USO_DAO_PENDIENTE.getContenido().formatted(operacion, CASO_USO),
                Lugar.NEGOCIO);
    }
}
