package co.edu.uco.imexcol.negocio.casouso.regla.pedido;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.entidad.EnvioEntidad;
import co.edu.uco.imexcol.entidad.PedidoEntidad;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.negocio.dominio.enums.EstadoEnvioEnum;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class PedidoNoTieneEnvioActivoRegla implements Regla {

    private static final PedidoNoTieneEnvioActivoRegla INSTANCIA = new PedidoNoTieneEnvioActivoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 3;
    private static final int POSICION_ID_PEDIDO = 0;
    private static final int POSICION_ID_ENVIO = 1;
    private static final int POSICION_FABRICA = 2;

    private PedidoNoTieneEnvioActivoRegla() {
        super();
    }

    public static PedidoNoTieneEnvioActivoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idPedido = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_PEDIDO]);
        final var idEnvio = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_ENVIO]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var envioActivo = buscarOtroEnvioActivo(idPedido, idEnvio, fabrica);
        if (!UtilUUID.esUUIDPorDefecto(envioActivo.getId())) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_PEDIDO_CON_ENVIO_ACTIVO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_PEDIDO_CON_ENVIO_ACTIVO.getContenido()
                            .formatted(idPedido, envioActivo.getId(), envioActivo.getEstado()));
        }
    }

    private static EnvioEntidad buscarOtroEnvioActivo(final UUID idPedido, final UUID idEnvio,
            final FabricaDAO fabrica) {
        final var filtro = new EnvioEntidad();
        filtro.setPedido(new PedidoEntidad(idPedido));
        return fabrica.obtenerEnvioDAO().consultarPorFiltro(filtro).stream()
                .filter(envio -> !idEnvio.equals(envio.getId()))
                .filter(envio -> EstadoEnvioEnum.esEstadoActivo(envio.getEstado()))
                .findFirst()
                .orElse(new EnvioEntidad());
    }
}
