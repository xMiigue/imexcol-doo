package co.edu.uco.imexcol.negocio.casouso.validador.genericas;

import co.edu.uco.imexcol.negocio.casouso.regla.genericas.NumeroEsPositivoRegla;
import co.edu.uco.imexcol.negocio.casouso.validador.Validador;

public final class ValidarNumeroEsPositivo implements Validador {

    private static final ValidarNumeroEsPositivo INSTANCIA = new ValidarNumeroEsPositivo();

    private ValidarNumeroEsPositivo() {
        super();
    }

    public static void ejecutarValidacion(final Object... datos) {
        INSTANCIA.validar(datos);
    }

    @Override
    public void validar(final Object... datos) {
        NumeroEsPositivoRegla.ejecutarRegla(datos);
    }
}
