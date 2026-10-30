package co.edu.uco.imexcol.transversal.excepcion;

import co.edu.uco.imexcol.transversal.excepcion.enums.Capa;

public final class ImexcolDatosException extends ImexcolException {

    private static final long serialVersionUID = 1L;

    private ImexcolDatosException(final Throwable excepcionRaiz, final String mensajeUsuario,
            final String mensajeTecnico) {
        super(Capa.DATOS, excepcionRaiz, mensajeUsuario, mensajeTecnico);
    }

    public static ImexcolDatosException crear(final String mensajeUsuario) {
        return new ImexcolDatosException(new Exception(mensajeUsuario), mensajeUsuario, mensajeUsuario);
    }

    public static ImexcolDatosException crear(final String mensajeUsuario, final String mensajeTecnico) {
        return new ImexcolDatosException(new Exception(mensajeTecnico), mensajeUsuario, mensajeTecnico);
    }

    public static ImexcolDatosException crear(final Throwable excepcionRaiz, final String mensajeUsuario,
            final String mensajeTecnico) {
        return new ImexcolDatosException(excepcionRaiz, mensajeUsuario, mensajeTecnico);
    }
}
