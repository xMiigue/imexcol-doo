package co.edu.uco.imexcol.transversal;

import java.util.UUID;

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
        return UtilTexto.esNula(texto) ? obtenerValorPorDefecto() : UUID.fromString(texto);
    }

    public static UUID generarNuevoUUID() {
        return UUID.randomUUID();
    }

    public static boolean esUUIDPorDefecto(final UUID id) {
        return obtenerValorPorDefecto().equals(id);
    }
}
