package co.edu.uco.imexcol.negocio.casouso.regla.carritocompras;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.negocio.dominio.enums.EstadoCarritoComprasEnum;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class CarritoComprasNoEstaConvertidoRegla implements Regla {

    private static final CarritoComprasNoEstaConvertidoRegla INSTANCIA = new CarritoComprasNoEstaConvertidoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_ID_CARRITO = 0;
    private static final int POSICION_FABRICA = 1;

    private CarritoComprasNoEstaConvertidoRegla() {
        super();
    }

    public static CarritoComprasNoEstaConvertidoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idCarrito = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_CARRITO]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var estadoActual = fabrica.obtenerCarritoComprasDAO().consultarPorId(idCarrito).getEstado();
        if (EstadoCarritoComprasEnum.CONVERTIDO.esIgualA(estadoActual)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_CARRITO_COMPRAS_YA_CONVERTIDO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_CARRITO_COMPRAS_YA_CONVERTIDO.getContenido()
                            .formatted(idCarrito, estadoActual));
        }
    }
}
