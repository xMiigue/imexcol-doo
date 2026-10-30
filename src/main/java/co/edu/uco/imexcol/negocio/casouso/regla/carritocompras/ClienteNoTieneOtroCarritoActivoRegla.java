package co.edu.uco.imexcol.negocio.casouso.regla.carritocompras;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.entidad.CarritoComprasEntidad;
import co.edu.uco.imexcol.entidad.ClienteEntidad;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.negocio.dominio.CarritoComprasDominio;
import co.edu.uco.imexcol.negocio.dominio.enums.EstadoCarritoComprasEnum;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class ClienteNoTieneOtroCarritoActivoRegla implements Regla {

    private static final ClienteNoTieneOtroCarritoActivoRegla INSTANCIA = new ClienteNoTieneOtroCarritoActivoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_CARRITO = 0;
    private static final int POSICION_FABRICA = 1;

    private ClienteNoTieneOtroCarritoActivoRegla() {
        super();
    }

    public static ClienteNoTieneOtroCarritoActivoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var carrito = UtilObjeto.obtenerValorDefecto((CarritoComprasDominio) datos[POSICION_CARRITO],
                new CarritoComprasDominio());
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        if (EstadoCarritoComprasEnum.ACTIVO.esIgualA(carrito.getEstado())
                && clienteTieneOtroCarritoActivo(carrito, fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_CARRITO_COMPRAS_ACTIVO_DUPLICADO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_CARRITO_COMPRAS_ACTIVO_DUPLICADO.getContenido()
                            .formatted(carrito.getCliente().getId(), EstadoCarritoComprasEnum.ACTIVO.getValor()));
        }
    }

    private static boolean clienteTieneOtroCarritoActivo(final CarritoComprasDominio carrito,
            final FabricaDAO fabrica) {
        final var filtro = new CarritoComprasEntidad();
        filtro.setCliente(new ClienteEntidad(carrito.getCliente().getId()));
        return fabrica.obtenerCarritoComprasDAO().consultarPorFiltro(filtro).stream()
                .anyMatch(existente -> !carrito.getId().equals(existente.getId())
                        && EstadoCarritoComprasEnum.ACTIVO.esIgualA(existente.getEstado()));
    }
}
