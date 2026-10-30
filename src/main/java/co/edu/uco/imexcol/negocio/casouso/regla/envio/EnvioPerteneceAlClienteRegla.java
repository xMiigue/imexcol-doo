package co.edu.uco.imexcol.negocio.casouso.regla.envio;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class EnvioPerteneceAlClienteRegla implements Regla {

    private static final EnvioPerteneceAlClienteRegla INSTANCIA = new EnvioPerteneceAlClienteRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 3;
    private static final int POSICION_ID_ENVIO = 0;
    private static final int POSICION_ID_CLIENTE = 1;
    private static final int POSICION_FABRICA = 2;

    private EnvioPerteneceAlClienteRegla() {
        super();
    }

    public static EnvioPerteneceAlClienteRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idEnvio = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_ENVIO]);
        final var idCliente = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_CLIENTE]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var idPedido = consultarIdPedidoDelEnvio(idEnvio, fabrica);
        final var idPropietario = consultarIdClienteDelPedido(idPedido, fabrica);
        if (!idPropietario.equals(idCliente)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_ENVIO_NO_PERTENECE_AL_CLIENTE.getContenido(),
                    MensajesEnum.ERROR_TECNICO_ENVIO_NO_PERTENECE_AL_CLIENTE.getContenido()
                            .formatted(idEnvio, idPedido, idPropietario, idCliente));
        }
    }

    private static UUID consultarIdPedidoDelEnvio(final UUID idEnvio, final FabricaDAO fabrica) {
        return fabrica.obtenerEnvioDAO().consultarPorId(idEnvio).getPedido().getId();
    }

    private static UUID consultarIdClienteDelPedido(final UUID idPedido, final FabricaDAO fabrica) {
        return fabrica.obtenerPedidoDAO().consultarPorId(idPedido).getCliente().getId();
    }
}
