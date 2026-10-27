package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.casouso.EventoRastreoNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.eventorastreo.ValidarEstadoEventoRastreoEsValido;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarFechaNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.dominio.EventoRastreoDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class EventoRastreoNegocioImpl implements EventoRastreoNegocio {

    private static final String CASO_USO = "EventoRastreoNegocio";

    public EventoRastreoNegocioImpl() {
        super();
    }

    @Override
    public void registrar(final EventoRastreoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new EventoRastreoDominio());
        validarDatosComunes(dominioSeguro);
        throw operacionPendiente("registrar");
    }

    @Override
    public void actualizar(final EventoRastreoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new EventoRastreoDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "evento de rastreo");
        validarDatosComunes(dominioSeguro);
        throw operacionPendiente("actualizar");
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "evento de rastreo");
        throw operacionPendiente("eliminar");
    }

    @Override
    public List<EventoRastreoDominio> consultar(final EventoRastreoDominio filtros) {
        UtilObjeto.obtenerValorDefecto(filtros, new EventoRastreoDominio());
        return new ArrayList<>();
    }

    private void validarDatosComunes(final EventoRastreoDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getEnvio().getId(),
                "envío asociado al evento de rastreo");

        ValidarFechaNoEsValorPorDefecto.ejecutarValidacion(dominio.getFecha(),
                "fecha del evento de rastreo");

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getUbicacion(),
                "ubicación del evento de rastreo");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getUbicacion(),
                "ubicación del evento de rastreo", 2, 200);

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getDescripcion(),
                "descripción del evento de rastreo");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getDescripcion(),
                "descripción del evento de rastreo", 5, 500);

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getEstado(),
                "estado del evento de rastreo");
        ValidarEstadoEventoRastreoEsValido.ejecutarValidacion(dominio.getEstado());
    }

    private static ImexcolNegocioException operacionPendiente(final String operacion) {
        return ImexcolNegocioException.crear(
                MensajesEnum.ERROR_USUARIO_CASO_USO_DAO_PENDIENTE.getContenido(),
                MensajesEnum.ERROR_TECNICO_CASO_USO_DAO_PENDIENTE.getContenido().formatted(operacion, CASO_USO));
    }
}
