package co.edu.uco.imexcol.negocio.casouso.regla.genericas;

import java.util.UUID;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class IdNoEsValorPorDefectoRegla implements Regla {

    private static final IdNoEsValorPorDefectoRegla INSTANCIA = new IdNoEsValorPorDefectoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_ID = 0;
    private static final int POSICION_NOMBRE_DATO = 1;

    private IdNoEsValorPorDefectoRegla() {
        super();
    }

    public static IdNoEsValorPorDefectoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var id = (UUID) datos[POSICION_ID];
        final var nombreDato = UtilTexto.aplicarTrim((String) datos[POSICION_NOMBRE_DATO]);

        if (UtilUUID.esUUIDPorDefecto(id)) {
            final var mensajeUsuario = MensajesEnum.ERROR_USUARIO_DATO_ID_VALOR_DEFECTO.getContenido()
                    .formatted(nombreDato);
            final var mensajeTecnico = MensajesEnum.ERROR_TECNICO_DATO_ID_VALOR_DEFECTO.getContenido()
                    .formatted(nombreDato);
            throw ImexcolNegocioException.crear(mensajeUsuario, mensajeTecnico);
        }
    }
}
