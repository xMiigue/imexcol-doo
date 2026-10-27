package co.edu.uco.imexcol.negocio.dominio.enums;

import java.util.Arrays;

import co.edu.uco.imexcol.transversal.UtilTexto;

public enum EstadoEnvioEnum {

    PREPARANDO("PREPARANDO", true),
    EN_TRANSITO("EN_TRANSITO", true),
    ENTREGADO("ENTREGADO", false),
    DEVUELTO("DEVUELTO", false),
    EXTRAVIADO("EXTRAVIADO", false);

    private final String valor;
    private final boolean activo;

    EstadoEnvioEnum(final String valor, final boolean activo) {
        this.valor = valor;
        this.activo = activo;
    }

    public String getValor() {
        return valor;
    }

    public boolean estaActivo() {
        return activo;
    }

    public boolean esIgualA(final String estado) {
        return valor.equalsIgnoreCase(UtilTexto.aplicarTrim(estado));
    }

    public static boolean esEstadoActivo(final String estado) {
        return Arrays.stream(values()).anyMatch(valorEnum -> valorEnum.estaActivo() && valorEnum.esIgualA(estado));
    }
}
