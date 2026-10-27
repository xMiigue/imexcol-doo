package co.edu.uco.imexcol.negocio.dominio.enums;

import co.edu.uco.imexcol.transversal.UtilTexto;

public enum EstadoPedidoEnum {

    PENDIENTE("PENDIENTE"),
    CONFIRMADO("CONFIRMADO"),
    PREPARANDO("PREPARANDO"),
    ENVIADO("ENVIADO"),
    ENTREGADO("ENTREGADO"),
    CANCELADO("CANCELADO");

    private final String valor;

    EstadoPedidoEnum(final String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public boolean esIgualA(final String estado) {
        return valor.equalsIgnoreCase(UtilTexto.aplicarTrim(estado));
    }
}
