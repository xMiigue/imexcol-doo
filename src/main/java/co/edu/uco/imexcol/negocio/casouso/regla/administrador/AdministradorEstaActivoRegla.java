package co.edu.uco.imexcol.negocio.casouso.regla.administrador;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilBooleano;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolAutenticacionException;

public final class AdministradorEstaActivoRegla implements Regla {

    private static final AdministradorEstaActivoRegla INSTANCIA = new AdministradorEstaActivoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_ESTADO = 0;
    private static final int POSICION_NOMBRE_USUARIO = 1;

    private AdministradorEstaActivoRegla() {
        super();
    }

    public static AdministradorEstaActivoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var estaActivo = UtilBooleano.obtenerValorDefecto((Boolean) datos[POSICION_ESTADO]);
        final var nombreUsuario = UtilTexto.aplicarTrim((String) datos[POSICION_NOMBRE_USUARIO]);

        if (!estaActivo) {
            throw ImexcolAutenticacionException.crear(
                    MensajesEnum.ERROR_USUARIO_AUTENTICACION_ADMINISTRADOR_INACTIVO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_AUTENTICACION_ADMINISTRADOR_INACTIVO.getContenido().formatted(nombreUsuario));
        }
    }
}
