package co.edu.uco.imexcol.negocio.dominio.enums;

import java.util.Arrays;

import co.edu.uco.imexcol.transversal.UtilTexto;

public enum EstadoPagoEnum {

    PENDIENTE("PENDIENTE"),
    EN_PROCESO("EN_PROCESO"),
    APROBADO("APROBADO"),
    RECHAZADO("RECHAZADO"),
    REEMBOLSADO("REEMBOLSADO");

    private final String valor;

    EstadoPagoEnum(final String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public boolean esIgualA(final String estado) {
        return valor.equalsIgnoreCase(UtilTexto.aplicarTrim(estado));
    }

    public static String[] obtenerValoresPermitidos() {
        return Arrays.stream(values()).map(EstadoPagoEnum::getValor).toArray(String[]::new);
    }
}
