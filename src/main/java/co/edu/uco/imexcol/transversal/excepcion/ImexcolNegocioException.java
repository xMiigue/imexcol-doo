package co.edu.uco.imexcol.transversal.excepcion;

import co.edu.uco.imexcol.transversal.excepcion.enums.Capa;

public final class ImexcolNegocioException extends ImexcolException {

    private static final long serialVersionUID = 1L;

    private ImexcolNegocioException(final Throwable excepcionRaiz, final String mensajeUsuario,
            final String mensajeTecnico) {
        super(Capa.NEGOCIO, excepcionRaiz, mensajeUsuario, mensajeTecnico);
    }

    public static ImexcolNegocioException crear(final String mensajeUsuario) {
        return new ImexcolNegocioException(new Exception(mensajeUsuario), mensajeUsuario, mensajeUsuario);
    }

    public static ImexcolNegocioException crear(final String mensajeUsuario, final String mensajeTecnico) {
        return new ImexcolNegocioException(new Exception(mensajeTecnico), mensajeUsuario, mensajeTecnico);
    }

    public static ImexcolNegocioException crear(final Throwable excepcionRaiz, final String mensajeUsuario,
            final String mensajeTecnico) {
        return new ImexcolNegocioException(excepcionRaiz, mensajeUsuario, mensajeTecnico);
    }
}
