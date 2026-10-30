package co.edu.uco.imexcol.negocio.casouso.regla.eventorastreo;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.entidad.EnvioEntidad;
import co.edu.uco.imexcol.entidad.EventoRastreoEntidad;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class EventoRastreoNoEstaDuplicadoRegla implements Regla {

    private static final EventoRastreoNoEstaDuplicadoRegla INSTANCIA = new EventoRastreoNoEstaDuplicadoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 4;
    private static final int POSICION_ID_ENVIO = 0;
    private static final int POSICION_ESTADO_LOGISTICO = 1;
    private static final int POSICION_FECHA_HORA = 2;
    private static final int POSICION_FABRICA = 3;

    private EventoRastreoNoEstaDuplicadoRegla() {
        super();
    }

    public static EventoRastreoNoEstaDuplicadoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idEnvio = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_ENVIO]);
        final var estadoLogistico = UtilTexto.aplicarTrim((String) datos[POSICION_ESTADO_LOGISTICO]);
        final var fechaHora = UtilFecha.obtenerValorDefecto((LocalDateTime) datos[POSICION_FECHA_HORA]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var eventoExistente = buscarEventoIgual(idEnvio, estadoLogistico, fechaHora, fabrica);
        if (!UtilUUID.esUUIDPorDefecto(eventoExistente.getId())) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_EVENTO_RASTREO_DUPLICADO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_EVENTO_RASTREO_DUPLICADO.getContenido()
                            .formatted(idEnvio, eventoExistente.getId(), estadoLogistico, fechaHora));
        }
    }

    private static EventoRastreoEntidad buscarEventoIgual(final UUID idEnvio, final String estadoLogistico,
            final LocalDateTime fechaHora, final FabricaDAO fabrica) {
        final var filtro = new EventoRastreoEntidad();
        filtro.setEnvio(new EnvioEntidad(idEnvio));
        return fabrica.obtenerEventoRastreoDAO().consultarPorFiltro(filtro).stream()
                .filter(evento -> estadoLogistico.equalsIgnoreCase(evento.getEstadoLogistico()))
                .filter(evento -> UtilFecha.sonFechasHoraIguales(fechaHora, evento.getFechaHora()))
                .findFirst()
                .orElse(new EventoRastreoEntidad());
    }
}
