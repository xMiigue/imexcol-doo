package co.edu.uco.imexcol.negocio.casouso.regla.administrador;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class CredencialesAdministradorSonObligatoriasRegla implements Regla {

    private static final CredencialesAdministradorSonObligatoriasRegla INSTANCIA = new CredencialesAdministradorSonObligatoriasRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_NOMBRE_USUARIO = 0;
    private static final int POSICION_CONTRASENA = 1;

    private CredencialesAdministradorSonObligatoriasRegla() {
        super();
    }

    public static CredencialesAdministradorSonObligatoriasRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var nombreUsuario = UtilTexto.aplicarTrim((String) datos[POSICION_NOMBRE_USUARIO]);
        final var contrasena = UtilTexto.obtenerValorDefecto((String) datos[POSICION_CONTRASENA]);

        if (UtilTexto.estaVacia(nombreUsuario) || UtilTexto.estaVacia(contrasena)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_AUTENTICACION_ADMINISTRADOR_CREDENCIALES_OBLIGATORIAS.getContenido(),
                    MensajesEnum.ERROR_TECNICO_AUTENTICACION_ADMINISTRADOR_CREDENCIALES_OBLIGATORIAS.getContenido());
        }
    }
}
