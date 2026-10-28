package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.EventoRastreoEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.EventoRastreoNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.envio.ValidarEnvioExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.envio.ValidarEnvioPerteneceAlCliente;
import co.edu.uco.imexcol.negocio.casouso.validador.eventorastreo.ValidarEstadoEventoRastreoEsValido;
import co.edu.uco.imexcol.negocio.casouso.validador.eventorastreo.ValidarEventoRastreoNoEstaDuplicado;
import co.edu.uco.imexcol.negocio.casouso.validador.eventorastreo.ValidarEventoRastreoPerteneceAEnvioActivo;
import co.edu.uco.imexcol.negocio.casouso.validador.eventorastreo.ValidarFechaEventoNoEsAnteriorAlDespacho;
import co.edu.uco.imexcol.negocio.casouso.validador.eventorastreo.ValidarObservacionObligatoriaEnNovedad;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.dominio.EnvioDominio;
import co.edu.uco.imexcol.negocio.dominio.EventoRastreoDominio;
import co.edu.uco.imexcol.negocio.dominio.enums.EstadoEventoRastreoEnum;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class EventoRastreoNegocioImpl implements EventoRastreoNegocio {

    private static final String DATO_ENVIO = "envío al que pertenece el evento de rastreo";
    private static final String DATO_CLIENTE = "cliente que consulta el rastreo";
    private static final String DATO_ESTADO_LOGISTICO = "estado logístico del evento de rastreo";
    private static final String DATO_OBSERVACION = "observación del evento de rastreo";
    private static final int LONGITUD_MINIMA_ESTADO_LOGISTICO = 5;
    private static final int LONGITUD_MAXIMA_ESTADO_LOGISTICO = 30;
    private static final int LONGITUD_MINIMA_OBSERVACION = 0;
    private static final int LONGITUD_MAXIMA_OBSERVACION = 255;

    private final FabricaDAO fabrica;
    private final EventoRastreoEntidadAssembler ensamblador;

    public EventoRastreoNegocioImpl(final FabricaDAO fabrica) {
        if (UtilObjeto.esNulo(fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido());
        }
        super();
        this.fabrica = fabrica;
        this.ensamblador = EventoRastreoEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final EventoRastreoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new EventoRastreoDominio());
        validarDatosComunes(dominioSeguro);
        asegurarEstadoLogisticoValido(dominioSeguro);
        normalizarEstadoLogistico(dominioSeguro);
        asegurarObservacionCuandoHayNovedad(dominioSeguro);
        asegurarEnvioExista(dominioSeguro.getEnvio().getId());
        asegurarEnvioEsteActivo(dominioSeguro);
        asignarFechaHoraDelEvento(dominioSeguro);
        asegurarFechaNoSeaAnteriorAlDespacho(dominioSeguro);
        asegurarEventoNoEsteDuplicado(dominioSeguro);
        asignarNuevoIdentificador(dominioSeguro);
        guardar(dominioSeguro);
    }

    @Override
    public List<EventoRastreoDominio> consultarHistorialPorEnvio(final UUID idEnvio) {
        validarIdentificador(idEnvio, DATO_ENVIO);
        asegurarEnvioExista(idEnvio);
        return consultarEventosDelEnvio(idEnvio);
    }

    @Override
    public List<EventoRastreoDominio> consultarHistorialDelCliente(final UUID idEnvio, final UUID idCliente) {
        validarIdentificador(idEnvio, DATO_ENVIO);
        validarIdentificador(idCliente, DATO_CLIENTE);
        asegurarEnvioExista(idEnvio);
        asegurarEnvioPertenezcaAlCliente(idEnvio, idCliente);
        return consultarEventosDelEnvio(idEnvio);
    }

    private void validarDatosComunes(final EventoRastreoDominio dominio) {
        validarIdentificador(dominio.getEnvio().getId(), DATO_ENVIO);
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getEstadoLogistico(), DATO_ESTADO_LOGISTICO);
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getEstadoLogistico(), DATO_ESTADO_LOGISTICO,
                LONGITUD_MINIMA_ESTADO_LOGISTICO, LONGITUD_MAXIMA_ESTADO_LOGISTICO);
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getObservacion(), DATO_OBSERVACION,
                LONGITUD_MINIMA_OBSERVACION, LONGITUD_MAXIMA_OBSERVACION);
    }

    private void validarIdentificador(final UUID id, final String nombreDato) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(UtilUUID.obtenerValorDefecto(id), nombreDato);
    }

    private void asegurarEstadoLogisticoValido(final EventoRastreoDominio dominio) {
        ValidarEstadoEventoRastreoEsValido.ejecutarValidacion(dominio.getEstadoLogistico());
    }

    private void normalizarEstadoLogistico(final EventoRastreoDominio dominio) {
        dominio.setEstadoLogistico(EstadoEventoRastreoEnum.normalizar(dominio.getEstadoLogistico()));
    }

    private void asegurarObservacionCuandoHayNovedad(final EventoRastreoDominio dominio) {
        ValidarObservacionObligatoriaEnNovedad.ejecutarValidacion(dominio.getEstadoLogistico(),
                dominio.getObservacion());
    }

    private void asegurarEnvioExista(final UUID idEnvio) {
        ValidarEnvioExistePorId.ejecutarValidacion(idEnvio, fabrica);
    }

    private void asegurarEnvioEsteActivo(final EventoRastreoDominio dominio) {
        ValidarEventoRastreoPerteneceAEnvioActivo.ejecutarValidacion(dominio.getEnvio().getId(), fabrica);
    }

    private void asegurarFechaNoSeaAnteriorAlDespacho(final EventoRastreoDominio dominio) {
        ValidarFechaEventoNoEsAnteriorAlDespacho.ejecutarValidacion(dominio.getFechaHora(),
                dominio.getEnvio().getId(), fabrica);
    }

    private void asegurarEventoNoEsteDuplicado(final EventoRastreoDominio dominio) {
        ValidarEventoRastreoNoEstaDuplicado.ejecutarValidacion(dominio.getEnvio().getId(),
                dominio.getEstadoLogistico(), dominio.getFechaHora(), fabrica);
    }

    private void asegurarEnvioPertenezcaAlCliente(final UUID idEnvio, final UUID idCliente) {
        ValidarEnvioPerteneceAlCliente.ejecutarValidacion(idEnvio, idCliente, fabrica);
    }

    private void asignarFechaHoraDelEvento(final EventoRastreoDominio dominio) {
        final var fechaHoraReportada = UtilFecha.esFechaHoraPorDefecto(dominio.getFechaHora())
                ? UtilFecha.obtenerFechaHoraActual()
                : dominio.getFechaHora();
        dominio.setFechaHora(UtilFecha.truncarASegundos(fechaHoraReportada));
    }

    private void asignarNuevoIdentificador(final EventoRastreoDominio dominio) {
        dominio.setId(UtilUUID.generarNuevoUUID());
    }

    private List<EventoRastreoDominio> consultarEventosDelEnvio(final UUID idEnvio) {
        final var filtro = new EventoRastreoDominio();
        filtro.setEnvio(new EnvioDominio(idEnvio));
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtro);
        final var entidades = fabrica.obtenerEventoRastreoDAO().consultarPorFiltro(entidadFiltro);
        return ensamblador.ensamblarDominio(entidades);
    }

    private void guardar(final EventoRastreoDominio dominio) {
        fabrica.obtenerEventoRastreoDAO().crear(ensamblador.ensamblarEntidad(dominio));
    }
}
