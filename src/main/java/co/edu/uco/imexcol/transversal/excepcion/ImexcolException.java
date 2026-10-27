package co.edu.uco.imexcol.transversal.excepcion;

import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.enums.Capa;

public class ImexcolException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Capa capa;
    private final String mensajeUsuario;
    private final String mensajeTecnico;
    private final Throwable excepcionRaiz;

    protected ImexcolException(final Capa capa, final Throwable excepcionRaiz, final String mensajeUsuario,
            final String mensajeTecnico) {
        super(obtenerMensajeTecnicoSeguro(mensajeTecnico, mensajeUsuario));
        this.capa = UtilObjeto.obtenerValorDefecto(capa, Capa.GENERAL);
        this.mensajeUsuario = UtilTexto.aplicarTrim(mensajeUsuario);
        this.mensajeTecnico = obtenerMensajeTecnicoSeguro(mensajeTecnico, mensajeUsuario);
        this.excepcionRaiz = UtilObjeto.obtenerValorDefecto(excepcionRaiz, new Exception(this.mensajeTecnico));
    }

    private static String obtenerMensajeTecnicoSeguro(final String mensajeTecnico, final String mensajeUsuario) {
        return UtilTexto.estaVacia(mensajeTecnico) ? UtilTexto.aplicarTrim(mensajeUsuario)
                : UtilTexto.aplicarTrim(mensajeTecnico);
    }

    public Capa getCapa() {
        return capa;
    }

    public String getMensajeUsuario() {
        return mensajeUsuario;
    }

    public String getMensajeTecnico() {
        return mensajeTecnico;
    }

    public Throwable getExcepcionRaiz() {
        return excepcionRaiz;
    }
}
