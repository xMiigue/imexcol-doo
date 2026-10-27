package co.edu.uco.imexcol.negocio.casouso.regla.pago;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.entidad.FacturaEntidad;
import co.edu.uco.imexcol.entidad.PagoEntidad;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class PagoNoTieneFacturaRegla implements Regla {

    private static final PagoNoTieneFacturaRegla INSTANCIA = new PagoNoTieneFacturaRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_ID_PAGO = 0;
    private static final int POSICION_FABRICA = 1;

    private PagoNoTieneFacturaRegla() {
        super();
    }

    public static PagoNoTieneFacturaRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idPago = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_PAGO]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var facturaExistente = buscarFacturaDelPago(idPago, fabrica);
        if (!UtilUUID.esUUIDPorDefecto(facturaExistente.getId())) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_PAGO_CON_FACTURA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_PAGO_CON_FACTURA.getContenido()
                            .formatted(idPago, facturaExistente.getId()));
        }
    }

    private static FacturaEntidad buscarFacturaDelPago(final UUID idPago, final FabricaDAO fabrica) {
        final var filtro = new FacturaEntidad();
        filtro.setPago(new PagoEntidad(idPago));
        return fabrica.obtenerFacturaDAO().consultarPorFiltro(filtro).stream()
                .findFirst()
                .orElse(new FacturaEntidad());
    }
}
