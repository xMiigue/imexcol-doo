package co.edu.uco.imexcol.transversal;

public final class UtilTexto {

    public static final String VACIO = "";

    private UtilTexto() {
        super();
    }

    public static boolean esNula(final String texto) {
        return UtilObjeto.esNulo(texto);
    }

    public static String obtenerValorDefecto(final String texto, final String valorDefecto) {
        return UtilObjeto.obtenerValorDefecto(texto, valorDefecto);
    }

    public static String obtenerValorDefecto(final String texto) {
        return obtenerValorDefecto(texto, VACIO);
    }

    public static String aplicarTrim(final String texto) {
        return obtenerValorDefecto(texto).trim();
    }

    public static boolean estaVacia(final String texto) {
        return VACIO.equals(aplicarTrim(texto));
    }

    public static boolean longitudEsValida(final String texto, final int min, final int max) {
        var longitud = aplicarTrim(texto).length();
        return longitud >= min && longitud <= max;
    }
}
