package co.edu.uco.imexcol.transversal.excepcion;

import co.edu.uco.imexcol.transversal.excepcion.enums.Capa;

public final class ImexcolFachadaException extends ImexcolException {

    private static final long serialVersionUID = 1L;

    private ImexcolFachadaException(final Throwable excepcionRaiz, final String mensajeUsuario,
            final String mensajeTecnico) {
        super(Capa.FACHADA, excepcionRaiz, mensajeUsuario, mensajeTecnico);
    }

    public static ImexcolFachadaException crear(final String mensajeUsuario) {
        return new ImexcolFachadaException(new Exception(mensajeUsuario), mensajeUsuario, mensajeUsuario);
    }

    public static ImexcolFachadaException crear(final String mensajeUsuario, final String mensajeTecnico) {
        return new ImexcolFachadaException(new Exception(mensajeTecnico), mensajeUsuario, mensajeTecnico);
    }

    public static ImexcolFachadaException crear(final Throwable excepcionRaiz, final String mensajeUsuario,
            final String mensajeTecnico) {
        return new ImexcolFachadaException(excepcionRaiz, mensajeUsuario, mensajeTecnico);
    }
}
