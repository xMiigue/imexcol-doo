package co.edu.uco.imexcol.negocio.casouso.regla.cliente;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.entidad.ClienteEntidad;
import co.edu.uco.imexcol.entidad.ProductoEntidad;
import co.edu.uco.imexcol.entidad.ResenaEntidad;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class ClienteNoHaResenadoProductoRegla implements Regla {

    private static final ClienteNoHaResenadoProductoRegla INSTANCIA = new ClienteNoHaResenadoProductoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 3;
    private static final int POSICION_ID_CLIENTE = 0;
    private static final int POSICION_ID_PRODUCTO = 1;
    private static final int POSICION_FABRICA = 2;

    private ClienteNoHaResenadoProductoRegla() {
        super();
    }

    public static ClienteNoHaResenadoProductoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idCliente = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_CLIENTE]);
        final var idProducto = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_PRODUCTO]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var resenaExistente = buscarResena(idCliente, idProducto, fabrica);
        if (!UtilUUID.esUUIDPorDefecto(resenaExistente.getId())) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_RESENA_DUPLICADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_RESENA_DUPLICADA.getContenido()
                            .formatted(idCliente, resenaExistente.getId(), idProducto));
        }
    }

    private static ResenaEntidad buscarResena(final UUID idCliente, final UUID idProducto,
            final FabricaDAO fabrica) {
        final var filtro = new ResenaEntidad();
        filtro.setCliente(new ClienteEntidad(idCliente));
        filtro.setProducto(new ProductoEntidad(idProducto));
        return fabrica.obtenerResenaDAO().consultarPorFiltro(filtro).stream()
                .findFirst()
                .orElse(new ResenaEntidad());
    }
}
