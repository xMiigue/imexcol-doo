package co.edu.uco.imexcol.negocio.casouso.regla.metodopago;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class MetodoPagoEstaActivoRegla implements Regla {

    private static final MetodoPagoEstaActivoRegla INSTANCIA = new MetodoPagoEstaActivoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_ID_METODO_PAGO = 0;
    private static final int POSICION_FABRICA = 1;

    private MetodoPagoEstaActivoRegla() {
        super();
    }

    public static MetodoPagoEstaActivoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idMetodoPago = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_METODO_PAGO]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        if (!fabrica.obtenerMetodoPagoDAO().consultarPorId(idMetodoPago).isEstado()) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_METODO_PAGO_INACTIVO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_METODO_PAGO_INACTIVO.getContenido().formatted(idMetodoPago));
        }
    }
}
