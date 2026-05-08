package co.edu.uco.imexcol.transversal;

public final class UtilBooleano {

    private UtilBooleano() {
        super();
    }

    public static boolean obtenerValorPorDefecto() {
        return false;
    }

    public static boolean obtenerValorDefecto(final Boolean valor) {
        return UtilObjeto.obtenerValorDefecto(valor, obtenerValorPorDefecto());
    }
}
