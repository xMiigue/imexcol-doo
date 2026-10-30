package co.edu.uco.imexcol.transversal.excepcion;

import co.edu.uco.imexcol.transversal.excepcion.enums.Capa;

public final class ImexcolControladorException extends ImexcolException {

    private static final long serialVersionUID = 1L;

    private ImexcolControladorException(final Throwable excepcionRaiz, final String mensajeUsuario,
            final String mensajeTecnico) {
        super(Capa.CONTROLADOR, excepcionRaiz, mensajeUsuario, mensajeTecnico);
    }

    public static ImexcolControladorException crear(final String mensajeUsuario) {
        return new ImexcolControladorException(new Exception(mensajeUsuario), mensajeUsuario, mensajeUsuario);
    }

    public static ImexcolControladorException crear(final String mensajeUsuario, final String mensajeTecnico) {
        return new ImexcolControladorException(new Exception(mensajeTecnico), mensajeUsuario, mensajeTecnico);
    }

    public static ImexcolControladorException crear(final Throwable excepcionRaiz, final String mensajeUsuario,
            final String mensajeTecnico) {
        return new ImexcolControladorException(excepcionRaiz, mensajeUsuario, mensajeTecnico);
    }
}
