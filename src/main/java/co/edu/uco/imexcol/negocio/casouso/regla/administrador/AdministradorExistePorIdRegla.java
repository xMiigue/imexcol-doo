package co.edu.uco.imexcol.negocio.casouso.regla.administrador;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class AdministradorExistePorIdRegla implements Regla {

    private static final AdministradorExistePorIdRegla INSTANCIA = new AdministradorExistePorIdRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_ID = 0;
    private static final int POSICION_FABRICA = 1;

    private AdministradorExistePorIdRegla() {
        super();
    }

    public static AdministradorExistePorIdRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var id = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        if (!existe(id, fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_ADMINISTRADOR_NO_ENCONTRADO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_ADMINISTRADOR_NO_ENCONTRADO.getContenido().formatted(id));
        }
    }

    private static boolean existe(final UUID id, final FabricaDAO fabrica) {
        return !UtilUUID.esUUIDPorDefecto(id)
                && !UtilUUID.esUUIDPorDefecto(fabrica.obtenerAdministradorDAO().consultarPorId(id).getId());
    }
}
