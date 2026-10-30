package co.edu.uco.imexcol.negocio.casouso.regla.cliente;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class CredencialesClienteSonObligatoriasRegla implements Regla {

    private static final CredencialesClienteSonObligatoriasRegla INSTANCIA = new CredencialesClienteSonObligatoriasRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_CORREO = 0;
    private static final int POSICION_CONTRASENA = 1;

    private CredencialesClienteSonObligatoriasRegla() {
        super();
    }

    public static CredencialesClienteSonObligatoriasRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var correo = UtilTexto.aplicarTrim((String) datos[POSICION_CORREO]);
        final var contrasena = UtilTexto.obtenerValorDefecto((String) datos[POSICION_CONTRASENA]);

        if (UtilTexto.estaVacia(correo) || UtilTexto.estaVacia(contrasena)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_AUTENTICACION_CLIENTE_CREDENCIALES_OBLIGATORIAS.getContenido(),
                    MensajesEnum.ERROR_TECNICO_AUTENTICACION_CLIENTE_CREDENCIALES_OBLIGATORIAS.getContenido());
        }
    }
}
