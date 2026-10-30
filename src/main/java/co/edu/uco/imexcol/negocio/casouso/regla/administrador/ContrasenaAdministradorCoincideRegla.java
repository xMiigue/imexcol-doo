package co.edu.uco.imexcol.negocio.casouso.regla.administrador;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolAutenticacionException;

public final class ContrasenaAdministradorCoincideRegla implements Regla {

    private static final ContrasenaAdministradorCoincideRegla INSTANCIA = new ContrasenaAdministradorCoincideRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 3;
    private static final int POSICION_CONTRASENA_INGRESADA = 0;
    private static final int POSICION_CONTRASENA_REGISTRADA = 1;
    private static final int POSICION_NOMBRE_USUARIO = 2;

    private ContrasenaAdministradorCoincideRegla() {
        super();
    }

    public static ContrasenaAdministradorCoincideRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var contrasenaIngresada = UtilTexto.obtenerValorDefecto((String) datos[POSICION_CONTRASENA_INGRESADA]);
        final var contrasenaRegistrada = UtilTexto.obtenerValorDefecto((String) datos[POSICION_CONTRASENA_REGISTRADA]);
        final var nombreUsuario = UtilTexto.aplicarTrim((String) datos[POSICION_NOMBRE_USUARIO]);

        if (!UtilTexto.sonIguales(contrasenaIngresada, contrasenaRegistrada)) {
            throw ImexcolAutenticacionException.crear(
                    MensajesEnum.ERROR_USUARIO_AUTENTICACION_ADMINISTRADOR_CREDENCIALES_INVALIDAS.getContenido(),
                    MensajesEnum.ERROR_TECNICO_AUTENTICACION_ADMINISTRADOR_CONTRASENA_INCORRECTA.getContenido()
                            .formatted(nombreUsuario));
        }
    }
}
