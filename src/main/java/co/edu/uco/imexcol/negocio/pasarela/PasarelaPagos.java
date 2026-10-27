package co.edu.uco.imexcol.negocio.pasarela;

import co.edu.uco.imexcol.negocio.dominio.MetodoPagoDominio;
import co.edu.uco.imexcol.negocio.dominio.enums.EstadoPagoEnum;

public interface PasarelaPagos {

    EstadoPagoEnum solicitarCobro(MetodoPagoDominio metodoPago, double monto);
}
