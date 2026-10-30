package co.edu.uco.imexcol.negocio.casouso.regla.genericas;

import java.util.Arrays;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class EstadoEsValidoRegla implements Regla {

    private static final EstadoEsValidoRegla INSTANCIA = new EstadoEsValidoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 3;
    private static final int POSICION_ESTADO = 0;
    private static final int POSICION_NOMBRE_DATO = 1;
    private static final int POSICION_ESTADOS_PERMITIDOS = 2;
    private static final String SEPARADOR_ESTADOS = ", ";

    private EstadoEsValidoRegla() {
        super();
    }

    public static EstadoEsValidoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var estado = UtilTexto.aplicarTrim((String) datos[POSICION_ESTADO]);
        final var nombreDato = UtilTexto.aplicarTrim((String) datos[POSICION_NOMBRE_DATO]);
        final var estadosPermitidos = UtilObjeto.obtenerValorDefecto((String[]) datos[POSICION_ESTADOS_PERMITIDOS],
                new String[0]);

        if (!esEstadoPermitido(estado, estadosPermitidos)) {
            final var listaPermitidos = String.join(SEPARADOR_ESTADOS, estadosPermitidos);
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_DATO_ESTADO_INVALIDO.getContenido()
                            .formatted(estado, nombreDato, listaPermitidos),
                    MensajesEnum.ERROR_TECNICO_DATO_ESTADO_INVALIDO.getContenido()
                            .formatted(estado, nombreDato, listaPermitidos));
        }
    }

    private static boolean esEstadoPermitido(final String estado, final String[] estadosPermitidos) {
        return Arrays.stream(estadosPermitidos)
                .anyMatch(permitido -> estado.equalsIgnoreCase(UtilTexto.aplicarTrim(permitido)));
    }
}
