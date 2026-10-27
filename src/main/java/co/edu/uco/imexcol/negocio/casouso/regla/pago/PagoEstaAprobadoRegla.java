package co.edu.uco.imexcol.negocio.casouso.regla.pago;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.negocio.dominio.enums.EstadoPagoEnum;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class PagoEstaAprobadoRegla implements Regla {

    private static final PagoEstaAprobadoRegla INSTANCIA = new PagoEstaAprobadoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_ID_PAGO = 0;
    private static final int POSICION_FABRICA = 1;

    private PagoEstaAprobadoRegla() {
        super();
    }

    public static PagoEstaAprobadoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idPago = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_PAGO]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var estadoActual = consultarEstadoPago(idPago, fabrica);
        if (!EstadoPagoEnum.APROBADO.esIgualA(estadoActual)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_PAGO_NO_APROBADO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_PAGO_NO_APROBADO.getContenido()
                            .formatted(idPago, estadoActual, EstadoPagoEnum.APROBADO.getValor()));
        }
    }

    private static String consultarEstadoPago(final UUID idPago, final FabricaDAO fabrica) {
        return fabrica.obtenerPagoDAO().consultarPorId(idPago).getEstado();
    }
}
