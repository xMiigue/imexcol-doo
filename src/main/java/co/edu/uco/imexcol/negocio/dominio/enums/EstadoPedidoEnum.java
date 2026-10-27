package co.edu.uco.imexcol.negocio.dominio.enums;

import java.util.Arrays;

import co.edu.uco.imexcol.transversal.UtilTexto;

public enum EstadoPedidoEnum {

    PENDIENTE("PENDIENTE", false),
    CONFIRMADO("CONFIRMADO", true),
    PREPARANDO("PREPARANDO", true),
    ENVIADO("ENVIADO", true),
    ENTREGADO("ENTREGADO", true),
    CANCELADO("CANCELADO", false);

    private final String valor;
    private final boolean compraExitosa;

    EstadoPedidoEnum(final String valor, final boolean compraExitosa) {
        this.valor = valor;
        this.compraExitosa = compraExitosa;
    }

    public String getValor() {
        return valor;
    }

    public boolean esCompraExitosa() {
        return compraExitosa;
    }

    public boolean esIgualA(final String estado) {
        return valor.equalsIgnoreCase(UtilTexto.aplicarTrim(estado));
    }

    public static boolean representaCompraExitosa(final String estado) {
        return Arrays.stream(values())
                .anyMatch(valorEnum -> valorEnum.esCompraExitosa() && valorEnum.esIgualA(estado));
    }

    public static String[] obtenerValoresPermitidos() {
        return Arrays.stream(values()).map(EstadoPedidoEnum::getValor).toArray(String[]::new);
    }
}
