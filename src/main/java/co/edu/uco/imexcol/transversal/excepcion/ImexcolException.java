package co.edu.uco.imexcol.transversal.excepcion;

import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class ImexcolException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Throwable excepcionRaiz;
    private final String mensajeUsuario;
    private final String mensajeTecnico;
    private final Lugar lugar;

    private ImexcolException(final Throwable excepcionRaiz, final String mensajeUsuario,
            final String mensajeTecnico, final Lugar lugar) {
        super(mensajeTecnico);
        this.excepcionRaiz = UtilObjeto.obtenerValorDefecto(excepcionRaiz, new Exception());
        this.mensajeUsuario = UtilTexto.aplicarTrim(mensajeUsuario);
        this.mensajeTecnico = UtilTexto.aplicarTrim(mensajeTecnico);
        this.lugar = UtilObjeto.obtenerValorDefecto(lugar, Lugar.GENERAL);
    }

    public static ImexcolException crear(final String mensajeUsuario) {
        return new ImexcolException(new Exception(), mensajeUsuario, mensajeUsuario, Lugar.GENERAL);
    }

    public static ImexcolException crear(final String mensajeUsuario, final String mensajeTecnico) {
        return new ImexcolException(new Exception(), mensajeUsuario, mensajeTecnico, Lugar.GENERAL);
    }

    public static ImexcolException crear(final Throwable excepcionRaiz, final String mensajeUsuario,
            final String mensajeTecnico) {
        return new ImexcolException(excepcionRaiz, mensajeUsuario, mensajeTecnico, Lugar.GENERAL);
    }

    public static ImexcolException crear(final String mensajeUsuario, final String mensajeTecnico,
            final Lugar lugar) {
        return new ImexcolException(new Exception(), mensajeUsuario, mensajeTecnico, lugar);
    }

    public static ImexcolException crear(final Throwable excepcionRaiz, final String mensajeUsuario,
            final String mensajeTecnico, final Lugar lugar) {
        return new ImexcolException(excepcionRaiz, mensajeUsuario, mensajeTecnico, lugar);
    }

    public Throwable getExcepcionRaiz() {
        return excepcionRaiz;
    }

    public String getMensajeUsuario() {
        return mensajeUsuario;
    }

    public String getMensajeTecnico() {
        return mensajeTecnico;
    }

    public Lugar getLugar() {
        return lugar;
    }
}
