package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.casouso.EnvioNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.envio.ValidarEstadoEnvioEsValido;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarFechaNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.dominio.EnvioDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class EnvioNegocioImpl implements EnvioNegocio {

    private static final String CASO_USO = "EnvioNegocio";

    public EnvioNegocioImpl() {
        super();
        // TODO: recibir DAOFactory cuando exista la capa de datos.
    }

    @Override
    public void registrar(final EnvioDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new EnvioDominio());
        validarDatosComunes(dominioSeguro);
        validarCoherenciaFechas(dominioSeguro);
        // TODO: integrar con DAO — daoFactory.obtenerEnvioDAO().crear(...)
        throw operacionPendiente("registrar");
    }

    @Override
    public void actualizar(final EnvioDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new EnvioDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "envío");
        validarDatosComunes(dominioSeguro);
        validarCoherenciaFechas(dominioSeguro);
        // TODO: integrar con DAO — daoFactory.obtenerEnvioDAO().modificar(...)
        throw operacionPendiente("actualizar");
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "envío");
        // TODO: integrar con DAO — daoFactory.obtenerEnvioDAO().eliminar(id);
        throw operacionPendiente("eliminar");
    }

    @Override
    public List<EnvioDominio> consultar(final EnvioDominio filtros) {
        UtilObjeto.obtenerValorDefecto(filtros, new EnvioDominio());
        // TODO: integrar con DAO — daoFactory.obtenerEnvioDAO().consultarPorFiltro(...)
        return new ArrayList<>();
    }

    private void validarDatosComunes(final EnvioDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getPedido().getId(),
                "pedido asociado al envío");

        ValidarFechaNoEsValorPorDefecto.ejecutarValidacion(dominio.getFechaEnvio(),
                "fecha de envío");

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getTransportadora(),
                "transportadora del envío");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getTransportadora(),
                "transportadora del envío", 2, 100);

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getNumeroGuia(),
                "número de guía del envío");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getNumeroGuia(),
                "número de guía del envío", 5, 100);

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getEstado(), "estado del envío");
        ValidarEstadoEnvioEsValido.ejecutarValidacion(dominio.getEstado());
    }

    private void validarCoherenciaFechas(final EnvioDominio dominio) {
        // Regla de negocio: si la fecha de entrega no es la fecha por defecto, debe ser >= fechaEnvio.
        if (!UtilFecha.esFechaPorDefecto(dominio.getFechaEntrega())
                && dominio.getFechaEntrega().isBefore(dominio.getFechaEnvio())) {
            throw ImexcolException.crear(
                    "La fecha de entrega no puede ser anterior a la fecha de envío.",
                    "Inconsistencia detectada en Envio: fechaEntrega=%s anterior a fechaEnvio=%s."
                            .formatted(dominio.getFechaEntrega(), dominio.getFechaEnvio()),
                    Lugar.NEGOCIO);
        }
    }

    private static ImexcolException operacionPendiente(final String operacion) {
        return ImexcolException.crear(
                MensajesEnum.ERROR_USUARIO_CASO_USO_DAO_PENDIENTE.getContenido(),
                MensajesEnum.ERROR_TECNICO_CASO_USO_DAO_PENDIENTE.getContenido().formatted(operacion, CASO_USO),
                Lugar.NEGOCIO);
    }
}
