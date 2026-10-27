package co.edu.uco.imexcol.negocio.casouso.regla.imagenproducto;

import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class OrdenVisualizacionEnRangoRegla implements Regla {

    private static final OrdenVisualizacionEnRangoRegla INSTANCIA = new OrdenVisualizacionEnRangoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 1;
    private static final int POSICION_ORDEN = 0;
    private static final int ORDEN_MINIMO = 1;
    private static final int ORDEN_MAXIMO = 20;

    private OrdenVisualizacionEnRangoRegla() {
        super();
    }

    public static OrdenVisualizacionEnRangoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var orden = ((Number) datos[POSICION_ORDEN]).intValue();

        if (orden < ORDEN_MINIMO || orden > ORDEN_MAXIMO) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_IMAGEN_PRODUCTO_ORDEN_FUERA_DE_RANGO.getContenido()
                            .formatted(ORDEN_MINIMO, ORDEN_MAXIMO),
                    MensajesEnum.ERROR_TECNICO_IMAGEN_PRODUCTO_ORDEN_FUERA_DE_RANGO.getContenido()
                            .formatted(orden, ORDEN_MINIMO, ORDEN_MAXIMO));
        }
    }
}
