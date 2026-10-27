package co.edu.uco.imexcol.negocio.dominio.enums;

import java.util.Arrays;

import co.edu.uco.imexcol.transversal.UtilTexto;

public enum EstadoEventoRastreoEnum {

    RECIBIDO("Recibido"),
    EN_TRANSITO("En transito"),
    EN_REPARTO("En reparto"),
    ENTREGADO("Entregado"),
    NOVEDAD("Novedad");

    private final String valor;

    EstadoEventoRastreoEnum(final String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public boolean esIgualA(final String estado) {
        return valor.equalsIgnoreCase(UtilTexto.aplicarTrim(estado));
    }

    public static String[] obtenerValoresPermitidos() {
        return Arrays.stream(values()).map(EstadoEventoRastreoEnum::getValor).toArray(String[]::new);
    }

    public static String normalizar(final String estado) {
        return Arrays.stream(values())
                .filter(valorEnum -> valorEnum.esIgualA(estado))
                .map(EstadoEventoRastreoEnum::getValor)
                .findFirst()
                .orElse(UtilTexto.aplicarTrim(estado));
    }
}
