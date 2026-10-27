package co.edu.uco.imexcol.negocio.casouso.regla.metodopago;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.entidad.MetodoPagoEntidad;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class NombreMetodoPagoNoExisteRegla implements Regla {

    private static final NombreMetodoPagoNoExisteRegla INSTANCIA = new NombreMetodoPagoNoExisteRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 3;
    private static final int POSICION_NOMBRE = 0;
    private static final int POSICION_ID_METODO_PAGO = 1;
    private static final int POSICION_FABRICA = 2;

    private NombreMetodoPagoNoExisteRegla() {
        super();
    }

    public static NombreMetodoPagoNoExisteRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var nombre = UtilTexto.aplicarTrim((String) datos[POSICION_NOMBRE]);
        final var idMetodoPago = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_METODO_PAGO]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        if (existeOtroMetodoPagoConNombre(nombre, idMetodoPago, fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_METODO_PAGO_NOMBRE_DUPLICADO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_METODO_PAGO_NOMBRE_DUPLICADO.getContenido().formatted(nombre));
        }
    }

    private static boolean existeOtroMetodoPagoConNombre(final String nombre, final UUID idMetodoPago,
            final FabricaDAO fabrica) {
        final var filtro = new MetodoPagoEntidad();
        filtro.setNombre(nombre);
        return fabrica.obtenerMetodoPagoDAO().consultarPorFiltro(filtro).stream()
                .anyMatch(metodoPago -> !idMetodoPago.equals(metodoPago.getId()));
    }
}
