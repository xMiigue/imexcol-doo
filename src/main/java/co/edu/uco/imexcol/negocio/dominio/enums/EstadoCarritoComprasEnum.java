package co.edu.uco.imexcol.negocio.dominio.enums;

import java.util.Arrays;

import co.edu.uco.imexcol.transversal.UtilTexto;

public enum EstadoCarritoComprasEnum {

    ACTIVO("Activo"),
    ABANDONADO("Abandonado"),
    CONVERTIDO("Convertido");

    private final String valor;

    EstadoCarritoComprasEnum(final String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public boolean esIgualA(final String estado) {
        return valor.equalsIgnoreCase(UtilTexto.aplicarTrim(estado));
    }

    public static String[] obtenerValoresPermitidos() {
        return Arrays.stream(values()).map(EstadoCarritoComprasEnum::getValor).toArray(String[]::new);
    }
}
