package co.edu.uco.imexcol.transversal;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.excepcion.ImexcolTransversalException;

public final class UtilUUID {

    private static final String UUID_POR_DEFECTO_COMO_CADENA = "00000000-0000-0000-0000-000000000000";

    private UtilUUID() {
        super();
    }

    public static UUID obtenerValorPorDefecto() {
        return UUID.fromString(UUID_POR_DEFECTO_COMO_CADENA);
    }

    public static UUID obtenerValorDefecto(final UUID valor) {
        return UtilObjeto.obtenerValorDefecto(valor, obtenerValorPorDefecto());
    }

    public static UUID obtenerDesdeTexto(final String texto) {
        return UtilTexto.esNula(texto) ? obtenerValorPorDefecto() : convertirTextoAUUID(texto);
    }

    private static UUID convertirTextoAUUID(final String texto) {
        try {
            return UUID.fromString(texto);
        } catch (final IllegalArgumentException excepcion) {
            throw ImexcolTransversalException.crear(excepcion,
                    MensajesEnum.ERROR_USUARIO_UUID_FORMATO_INVALIDO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_UUID_FORMATO_INVALIDO.getContenido().formatted(texto));
        }
    }

    public static UUID generarNuevoUUID() {
        return UUID.randomUUID();
    }

    public static boolean esUUIDPorDefecto(final UUID id) {
        return obtenerValorPorDefecto().equals(obtenerValorDefecto(id));
    }
}
