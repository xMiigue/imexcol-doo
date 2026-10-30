package co.edu.uco.imexcol.negocio.casouso.regla.eventorastreo;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.negocio.dominio.enums.EstadoEnvioEnum;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class EventoRastreoPerteneceAEnvioActivoRegla implements Regla {

    private static final EventoRastreoPerteneceAEnvioActivoRegla INSTANCIA =
            new EventoRastreoPerteneceAEnvioActivoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_ID_ENVIO = 0;
    private static final int POSICION_FABRICA = 1;

    private EventoRastreoPerteneceAEnvioActivoRegla() {
        super();
    }

    public static EventoRastreoPerteneceAEnvioActivoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idEnvio = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_ENVIO]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var estadoEnvio = consultarEstadoEnvio(idEnvio, fabrica);
        if (!EstadoEnvioEnum.esEstadoActivo(estadoEnvio)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_EVENTO_RASTREO_ENVIO_NO_ACTIVO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_EVENTO_RASTREO_ENVIO_NO_ACTIVO.getContenido()
                            .formatted(idEnvio, estadoEnvio));
        }
    }

    private static String consultarEstadoEnvio(final UUID idEnvio, final FabricaDAO fabrica) {
        return fabrica.obtenerEnvioDAO().consultarPorId(idEnvio).getEstado();
    }
}
