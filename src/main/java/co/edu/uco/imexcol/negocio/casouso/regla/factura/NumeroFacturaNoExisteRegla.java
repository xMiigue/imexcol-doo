package co.edu.uco.imexcol.negocio.casouso.regla.factura;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.entidad.FacturaEntidad;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class NumeroFacturaNoExisteRegla implements Regla {

    private static final NumeroFacturaNoExisteRegla INSTANCIA = new NumeroFacturaNoExisteRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_NUMERO_FACTURA = 0;
    private static final int POSICION_FABRICA = 1;

    private NumeroFacturaNoExisteRegla() {
        super();
    }

    public static NumeroFacturaNoExisteRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var numeroFactura = UtilTexto.aplicarTrim((String) datos[POSICION_NUMERO_FACTURA]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        if (existeNumero(numeroFactura, fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTURA_NUMERO_DUPLICADO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTURA_NUMERO_DUPLICADO.getContenido().formatted(numeroFactura));
        }
    }

    private static boolean existeNumero(final String numeroFactura, final FabricaDAO fabrica) {
        final var filtro = new FacturaEntidad();
        filtro.setNumeroFactura(numeroFactura);
        return !fabrica.obtenerFacturaDAO().consultarPorFiltro(filtro).isEmpty();
    }
}
