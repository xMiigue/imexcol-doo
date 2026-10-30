package co.edu.uco.imexcol.negocio.dominio.enums;

import java.util.Arrays;

import co.edu.uco.imexcol.transversal.UtilTexto;

public enum EstadoPedidoEnum {

    PENDIENTE("PENDIENTE", false, true),
    CONFIRMADO("CONFIRMADO", true, true),
    PREPARANDO("PREPARANDO", true, false),
    ENVIADO("ENVIADO", true, false),
    ENTREGADO("ENTREGADO", true, false),
    CANCELADO("CANCELADO", false, false);

    private static final int PASO_AL_SIGUIENTE_ESTADO = 1;

    private final String valor;
    private final boolean compraExitosa;
    private final boolean cancelable;

    EstadoPedidoEnum(final String valor, final boolean compraExitosa, final boolean cancelable) {
        this.valor = valor;
        this.compraExitosa = compraExitosa;
        this.cancelable = cancelable;
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

    public boolean esCancelable() {
        return cancelable;
    }

    public boolean estaEnCurso() {
        return ENTREGADO != this && CANCELADO != this;
    }

    public boolean permiteTransicionA(final EstadoPedidoEnum destino) {
        return this == destino || esSiguienteEnElFlujo(destino) || esCancelacionPermitida(destino);
    }

    private boolean esSiguienteEnElFlujo(final EstadoPedidoEnum destino) {
        return CANCELADO != this && CANCELADO != destino
                && destino.ordinal() == ordinal() + PASO_AL_SIGUIENTE_ESTADO;
    }

    private boolean esCancelacionPermitida(final EstadoPedidoEnum destino) {
        return CANCELADO == destino && esCancelable();
    }

    public static boolean esTransicionValida(final String estadoActual, final String estadoNuevo) {
        return Arrays.stream(values())
                .filter(origen -> origen.esIgualA(estadoActual))
                .anyMatch(origen -> Arrays.stream(values())
                        .filter(destino -> destino.esIgualA(estadoNuevo))
                        .anyMatch(origen::permiteTransicionA));
    }

    public static boolean representaCompraExitosa(final String estado) {
        return Arrays.stream(values())
                .anyMatch(valorEnum -> valorEnum.esCompraExitosa() && valorEnum.esIgualA(estado));
    }

    public static boolean representaPedidoEnCurso(final String estado) {
        return Arrays.stream(values())
                .anyMatch(valorEnum -> valorEnum.estaEnCurso() && valorEnum.esIgualA(estado));
    }

    public static String[] obtenerValoresPermitidos() {
        return Arrays.stream(values()).map(EstadoPedidoEnum::getValor).toArray(String[]::new);
    }
}
