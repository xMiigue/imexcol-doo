package co.edu.uco.imexcol.negocio.pasarela.impl;

import co.edu.uco.imexcol.negocio.dominio.MetodoPagoDominio;
import co.edu.uco.imexcol.negocio.dominio.enums.EstadoPagoEnum;
import co.edu.uco.imexcol.negocio.pasarela.PasarelaPagos;
import co.edu.uco.imexcol.transversal.UtilObjeto;

public final class PasarelaPagosSimulada implements PasarelaPagos {

    public static final double MONTO_MAXIMO_APROBACION = 1_000_000.0;

    private static final PasarelaPagosSimulada INSTANCIA = new PasarelaPagosSimulada();

    private PasarelaPagosSimulada() {
        super();
    }

    public static PasarelaPagosSimulada obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public EstadoPagoEnum solicitarCobro(final MetodoPagoDominio metodoPago, final double monto) {
        final var metodoPagoSeguro = UtilObjeto.obtenerValorDefecto(metodoPago, new MetodoPagoDominio());
        if (metodoPagoSeguro.isEstado() && montoEsAprobable(monto)) {
            return EstadoPagoEnum.APROBADO;
        }
        return EstadoPagoEnum.RECHAZADO;
    }

    private static boolean montoEsAprobable(final double monto) {
        return monto <= MONTO_MAXIMO_APROBACION;
    }
}
