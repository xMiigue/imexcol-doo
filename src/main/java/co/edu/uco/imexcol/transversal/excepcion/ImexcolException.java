package co.edu.uco.imexcol.transversal.excepcion;

import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;

public final class ImexcolException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Throwable excepcionRaiz;
    private final String mensajeUsuario;
    private final String mensajeTecnico;

    private ImexcolException(final Throwable excepcionRaiz, final String mensajeUsuario,
            final String mensajeTecnico) {
        super(mensajeTecnico);
        this.excepcionRaiz = UtilObjeto.obtenerValorDefecto(excepcionRaiz, new Exception());
        this.mensajeUsuario = UtilTexto.aplicarTrim(mensajeUsuario);
        this.mensajeTecnico = UtilTexto.aplicarTrim(mensajeTecnico);
    }

    public static ImexcolException crear(final String mensajeUsuario) {
        return new ImexcolException(new Exception(), mensajeUsuario, mensajeUsuario);
    }

    public static ImexcolException crear(final String mensajeUsuario, final String mensajeTecnico) {
        return new ImexcolException(new Exception(), mensajeUsuario, mensajeTecnico);
    }

    public static ImexcolException crear(final Throwable excepcionRaiz, final String mensajeUsuario,
            final String mensajeTecnico) {
        return new ImexcolException(excepcionRaiz, mensajeUsuario, mensajeTecnico);
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
}
