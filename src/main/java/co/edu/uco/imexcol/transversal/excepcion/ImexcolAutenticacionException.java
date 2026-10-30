package co.edu.uco.imexcol.transversal.excepcion;

import co.edu.uco.imexcol.transversal.excepcion.enums.Capa;

public final class ImexcolAutenticacionException extends ImexcolException {

    private static final long serialVersionUID = 1L;

    private ImexcolAutenticacionException(final Throwable excepcionRaiz, final String mensajeUsuario,
            final String mensajeTecnico) {
        super(Capa.NEGOCIO, excepcionRaiz, mensajeUsuario, mensajeTecnico);
    }

    public static ImexcolAutenticacionException crear(final String mensajeUsuario, final String mensajeTecnico) {
        return new ImexcolAutenticacionException(new Exception(mensajeTecnico), mensajeUsuario, mensajeTecnico);
    }
}
