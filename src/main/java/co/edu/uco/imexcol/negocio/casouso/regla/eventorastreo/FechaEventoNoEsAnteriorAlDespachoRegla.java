package co.edu.uco.imexcol.negocio.casouso.regla.eventorastreo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class FechaEventoNoEsAnteriorAlDespachoRegla implements Regla {

    private static final FechaEventoNoEsAnteriorAlDespachoRegla INSTANCIA =
            new FechaEventoNoEsAnteriorAlDespachoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 3;
    private static final int POSICION_FECHA_HORA = 0;
    private static final int POSICION_ID_ENVIO = 1;
    private static final int POSICION_FABRICA = 2;

    private FechaEventoNoEsAnteriorAlDespachoRegla() {
        super();
    }

    public static FechaEventoNoEsAnteriorAlDespachoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var fechaHora = UtilFecha.obtenerValorDefecto((LocalDateTime) datos[POSICION_FECHA_HORA]);
        final var idEnvio = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_ENVIO]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var fechaDespacho = consultarFechaDespacho(idEnvio, fabrica);
        if (UtilFecha.esFechaHoraAnteriorA(fechaHora, fechaDespacho)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_EVENTO_RASTREO_FECHA_ANTERIOR_AL_DESPACHO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_EVENTO_RASTREO_FECHA_ANTERIOR_AL_DESPACHO.getContenido()
                            .formatted(fechaHora, fechaDespacho, idEnvio));
        }
    }

    private static LocalDate consultarFechaDespacho(final UUID idEnvio, final FabricaDAO fabrica) {
        return fabrica.obtenerEnvioDAO().consultarPorId(idEnvio).getFechaEnvio();
    }
}
