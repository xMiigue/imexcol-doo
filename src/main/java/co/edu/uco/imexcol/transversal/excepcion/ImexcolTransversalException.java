package co.edu.uco.imexcol.transversal.excepcion;

import co.edu.uco.imexcol.transversal.excepcion.enums.Capa;

public final class ImexcolTransversalException extends ImexcolException {

    private static final long serialVersionUID = 1L;

    private ImexcolTransversalException(final Throwable excepcionRaiz, final String mensajeUsuario,
            final String mensajeTecnico) {
        super(Capa.TRANSVERSAL, excepcionRaiz, mensajeUsuario, mensajeTecnico);
    }

    public static ImexcolTransversalException crear(final String mensajeUsuario) {
        return new ImexcolTransversalException(new Exception(mensajeUsuario), mensajeUsuario, mensajeUsuario);
    }

    public static ImexcolTransversalException crear(final String mensajeUsuario, final String mensajeTecnico) {
        return new ImexcolTransversalException(new Exception(mensajeTecnico), mensajeUsuario, mensajeTecnico);
    }

    public static ImexcolTransversalException crear(final Throwable excepcionRaiz, final String mensajeUsuario,
            final String mensajeTecnico) {
        return new ImexcolTransversalException(excepcionRaiz, mensajeUsuario, mensajeTecnico);
    }
}
