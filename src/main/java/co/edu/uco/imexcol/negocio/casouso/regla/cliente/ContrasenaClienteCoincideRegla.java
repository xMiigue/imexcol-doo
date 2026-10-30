package co.edu.uco.imexcol.negocio.casouso.regla.cliente;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolAutenticacionException;

public final class ContrasenaClienteCoincideRegla implements Regla {

    private static final ContrasenaClienteCoincideRegla INSTANCIA = new ContrasenaClienteCoincideRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 3;
    private static final int POSICION_CONTRASENA_INGRESADA = 0;
    private static final int POSICION_CONTRASENA_REGISTRADA = 1;
    private static final int POSICION_CORREO = 2;

    private ContrasenaClienteCoincideRegla() {
        super();
    }

    public static ContrasenaClienteCoincideRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var contrasenaIngresada = UtilTexto.obtenerValorDefecto((String) datos[POSICION_CONTRASENA_INGRESADA]);
        final var contrasenaRegistrada = UtilTexto.obtenerValorDefecto((String) datos[POSICION_CONTRASENA_REGISTRADA]);
        final var correo = UtilTexto.aplicarTrim((String) datos[POSICION_CORREO]);

        if (!UtilTexto.sonIguales(contrasenaIngresada, contrasenaRegistrada)) {
            throw ImexcolAutenticacionException.crear(
                    MensajesEnum.ERROR_USUARIO_AUTENTICACION_CLIENTE_CREDENCIALES_INVALIDAS.getContenido(),
                    MensajesEnum.ERROR_TECNICO_AUTENTICACION_CLIENTE_CONTRASENA_INCORRECTA.getContenido()
                            .formatted(correo));
        }
    }
}
