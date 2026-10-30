package co.edu.uco.imexcol.negocio.casouso.regla;

import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class UtilRegla {

    private UtilRegla() {
        super();
    }

    public static void asegurarParametrosSuficientes(final Object[] datos, final int cantidadRequerida) {
        asegurarDatosNoNulos(datos);
        asegurarCantidadParametros(datos, cantidadRequerida);
    }

    private static void asegurarDatosNoNulos(final Object[] datos) {
        if (UtilObjeto.esNulo(datos)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_REGLA_DATOS_NULOS.getContenido(),
                    MensajesEnum.ERROR_TECNICO_REGLA_DATOS_NULOS.getContenido());
        }
    }

    private static void asegurarCantidadParametros(final Object[] datos, final int cantidadRequerida) {
        if (datos.length < cantidadRequerida) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_REGLA_PARAMETROS_INSUFICIENTES.getContenido(),
                    MensajesEnum.ERROR_TECNICO_REGLA_PARAMETROS_INSUFICIENTES.getContenido());
        }
    }
}
