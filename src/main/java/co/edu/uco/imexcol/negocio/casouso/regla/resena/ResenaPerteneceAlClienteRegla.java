package co.edu.uco.imexcol.negocio.casouso.regla.resena;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class ResenaPerteneceAlClienteRegla implements Regla {

    private static final ResenaPerteneceAlClienteRegla INSTANCIA = new ResenaPerteneceAlClienteRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 3;
    private static final int POSICION_ID_RESENA = 0;
    private static final int POSICION_ID_CLIENTE = 1;
    private static final int POSICION_FABRICA = 2;

    private ResenaPerteneceAlClienteRegla() {
        super();
    }

    public static ResenaPerteneceAlClienteRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idResena = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_RESENA]);
        final var idCliente = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_CLIENTE]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var idAutor = consultarIdClienteAutor(idResena, fabrica);
        if (!idAutor.equals(idCliente)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_RESENA_NO_PERTENECE_AL_CLIENTE.getContenido(),
                    MensajesEnum.ERROR_TECNICO_RESENA_NO_PERTENECE_AL_CLIENTE.getContenido()
                            .formatted(idResena, idAutor, idCliente));
        }
    }

    private static UUID consultarIdClienteAutor(final UUID idResena, final FabricaDAO fabrica) {
        return fabrica.obtenerResenaDAO().consultarPorId(idResena).getCliente().getId();
    }
}
