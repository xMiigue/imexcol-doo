package co.edu.uco.imexcol.negocio.casouso.regla.cliente;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilBooleano;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolAutenticacionException;

public final class ClienteEstaActivoRegla implements Regla {

    private static final ClienteEstaActivoRegla INSTANCIA = new ClienteEstaActivoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_ESTADO = 0;
    private static final int POSICION_CORREO = 1;

    private ClienteEstaActivoRegla() {
        super();
    }

    public static ClienteEstaActivoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var estaActivo = UtilBooleano.obtenerValorDefecto((Boolean) datos[POSICION_ESTADO]);
        final var correo = UtilTexto.aplicarTrim((String) datos[POSICION_CORREO]);

        if (!estaActivo) {
            throw ImexcolAutenticacionException.crear(
                    MensajesEnum.ERROR_USUARIO_AUTENTICACION_CLIENTE_INACTIVO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_AUTENTICACION_CLIENTE_INACTIVO.getContenido().formatted(correo));
        }
    }
}
