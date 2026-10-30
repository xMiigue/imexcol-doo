package co.edu.uco.imexcol.negocio.casouso.regla.envio;

import java.time.LocalDate;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class FechaEntregaNoEsAnteriorAFechaEnvioRegla implements Regla {

    private static final FechaEntregaNoEsAnteriorAFechaEnvioRegla INSTANCIA =
            new FechaEntregaNoEsAnteriorAFechaEnvioRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_FECHA_ENTREGA = 0;
    private static final int POSICION_FECHA_ENVIO = 1;

    private FechaEntregaNoEsAnteriorAFechaEnvioRegla() {
        super();
    }

    public static FechaEntregaNoEsAnteriorAFechaEnvioRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var fechaEntrega = UtilFecha.obtenerValorDefecto((LocalDate) datos[POSICION_FECHA_ENTREGA]);
        final var fechaEnvio = UtilFecha.obtenerValorDefecto((LocalDate) datos[POSICION_FECHA_ENVIO]);

        if (!UtilFecha.esFechaPorDefecto(fechaEntrega) && UtilFecha.esFechaAnteriorA(fechaEntrega, fechaEnvio)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_ENVIO_FECHA_ENTREGA_ANTERIOR_A_FECHA_ENVIO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_ENVIO_FECHA_ENTREGA_ANTERIOR_A_FECHA_ENVIO.getContenido()
                            .formatted(fechaEntrega, fechaEnvio));
        }
    }
}
