package co.edu.uco.imexcol.negocio.casouso.regla.carritocompras;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class CarritoComprasPerteneceAlClienteRegla implements Regla {

    private static final CarritoComprasPerteneceAlClienteRegla INSTANCIA =
            new CarritoComprasPerteneceAlClienteRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 3;
    private static final int POSICION_ID_CARRITO = 0;
    private static final int POSICION_ID_CLIENTE = 1;
    private static final int POSICION_FABRICA = 2;

    private CarritoComprasPerteneceAlClienteRegla() {
        super();
    }

    public static CarritoComprasPerteneceAlClienteRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idCarrito = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_CARRITO]);
        final var idCliente = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_CLIENTE]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var idPropietario = consultarIdClientePropietario(idCarrito, fabrica);
        if (!idPropietario.equals(idCliente)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_CARRITO_COMPRAS_NO_PERTENECE_AL_CLIENTE.getContenido(),
                    MensajesEnum.ERROR_TECNICO_CARRITO_COMPRAS_NO_PERTENECE_AL_CLIENTE.getContenido()
                            .formatted(idCarrito, idPropietario, idCliente));
        }
    }

    private static UUID consultarIdClientePropietario(final UUID idCarrito, final FabricaDAO fabrica) {
        return fabrica.obtenerCarritoComprasDAO().consultarPorId(idCarrito).getCliente().getId();
    }
}
