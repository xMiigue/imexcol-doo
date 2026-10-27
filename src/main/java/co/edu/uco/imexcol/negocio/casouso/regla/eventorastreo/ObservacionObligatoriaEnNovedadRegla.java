package co.edu.uco.imexcol.negocio.casouso.regla.eventorastreo;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.negocio.dominio.enums.EstadoEventoRastreoEnum;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class ObservacionObligatoriaEnNovedadRegla implements Regla {

    private static final ObservacionObligatoriaEnNovedadRegla INSTANCIA = new ObservacionObligatoriaEnNovedadRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_ESTADO_LOGISTICO = 0;
    private static final int POSICION_OBSERVACION = 1;

    private ObservacionObligatoriaEnNovedadRegla() {
        super();
    }

    public static ObservacionObligatoriaEnNovedadRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var estadoLogistico = (String) datos[POSICION_ESTADO_LOGISTICO];
        final var observacion = (String) datos[POSICION_OBSERVACION];

        if (EstadoEventoRastreoEnum.NOVEDAD.esIgualA(estadoLogistico) && UtilTexto.estaVacia(observacion)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_EVENTO_RASTREO_OBSERVACION_REQUERIDA.getContenido()
                            .formatted(EstadoEventoRastreoEnum.NOVEDAD.getValor()),
                    MensajesEnum.ERROR_TECNICO_EVENTO_RASTREO_OBSERVACION_REQUERIDA.getContenido()
                            .formatted(EstadoEventoRastreoEnum.NOVEDAD.getValor()));
        }
    }
}
