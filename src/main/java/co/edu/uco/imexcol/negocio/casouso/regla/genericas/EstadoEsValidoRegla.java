package co.edu.uco.imexcol.negocio.casouso.regla.genericas;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class EstadoEsValidoRegla implements Regla {

    private static final EstadoEsValidoRegla INSTANCIA = new EstadoEsValidoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 3;

    private EstadoEsValidoRegla() {
        super();
    }

    public static void ejecutarRegla(final Object... datos) {
        INSTANCIA.ejecutar(datos);
    }

    @Override
    public void ejecutar(final Object... datos) {
        if (UtilObjeto.esNulo(datos)) {
            throw ImexcolException.crear(
                    MensajesEnum.ERROR_USUARIO_REGLA_DATOS_NULOS.getContenido(),
                    MensajesEnum.ERROR_TECNICO_REGLA_DATOS_NULOS.getContenido(),
                    Lugar.NEGOCIO);
        }
        if (datos.length < CANTIDAD_PARAMETROS_REQUERIDOS) {
            throw ImexcolException.crear(
                    MensajesEnum.ERROR_USUARIO_REGLA_PARAMETROS_INSUFICIENTES.getContenido(),
                    MensajesEnum.ERROR_TECNICO_REGLA_PARAMETROS_INSUFICIENTES.getContenido(),
                    Lugar.NEGOCIO);
        }

        final var estado = UtilTexto.aplicarTrim((String) datos[0]);
        final var nombreDato = (String) datos[1];
        final var estadosPermitidos = (String[]) datos[2];

        for (final var permitido : estadosPermitidos) {
            if (estado.equalsIgnoreCase(UtilTexto.aplicarTrim(permitido))) {
                return;
            }
        }

        final var listaPermitidos = String.join(", ", estadosPermitidos);
        final var mensajeUsuario = MensajesEnum.ERROR_USUARIO_DATO_ESTADO_INVALIDO.getContenido()
                .formatted(estado, nombreDato, listaPermitidos);
        final var mensajeTecnico = MensajesEnum.ERROR_TECNICO_DATO_ESTADO_INVALIDO.getContenido()
                .formatted(estado, nombreDato, listaPermitidos);
        throw ImexcolException.crear(mensajeUsuario, mensajeTecnico, Lugar.NEGOCIO);
    }
}
