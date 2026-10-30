package co.edu.uco.imexcol.negocio.casouso.regla.direccion;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class DireccionPerteneceAlClienteRegla implements Regla {

    private static final DireccionPerteneceAlClienteRegla INSTANCIA = new DireccionPerteneceAlClienteRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 3;
    private static final int POSICION_ID_DIRECCION = 0;
    private static final int POSICION_ID_CLIENTE = 1;
    private static final int POSICION_FABRICA = 2;

    private DireccionPerteneceAlClienteRegla() {
        super();
    }

    public static DireccionPerteneceAlClienteRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idDireccion = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_DIRECCION]);
        final var idCliente = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_CLIENTE]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var idPropietario = consultarIdClientePropietario(idDireccion, fabrica);
        if (!idPropietario.equals(idCliente)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_DIRECCION_NO_PERTENECE_AL_CLIENTE.getContenido(),
                    MensajesEnum.ERROR_TECNICO_DIRECCION_NO_PERTENECE_AL_CLIENTE.getContenido()
                            .formatted(idDireccion, idPropietario, idCliente));
        }
    }

    private static UUID consultarIdClientePropietario(final UUID idDireccion, final FabricaDAO fabrica) {
        return fabrica.obtenerDireccionDAO().consultarPorId(idDireccion).getCliente().getId();
    }
}
