package co.edu.uco.imexcol.negocio.casouso.regla.administrador;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class AdministradorNoEsElUnicoRegla implements Regla {

    private static final AdministradorNoEsElUnicoRegla INSTANCIA = new AdministradorNoEsElUnicoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_ID_ADMINISTRADOR = 0;
    private static final int POSICION_FABRICA = 1;
    private static final int CANTIDAD_MINIMA_ADMINISTRADORES = 1;

    private AdministradorNoEsElUnicoRegla() {
        super();
    }

    public static AdministradorNoEsElUnicoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idAdministrador = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_ADMINISTRADOR]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        if (esElUnicoAdministrador(fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_ADMINISTRADOR_UNICO_NO_ELIMINABLE.getContenido(),
                    MensajesEnum.ERROR_TECNICO_ADMINISTRADOR_UNICO_NO_ELIMINABLE.getContenido()
                            .formatted(idAdministrador));
        }
    }

    private static boolean esElUnicoAdministrador(final FabricaDAO fabrica) {
        return fabrica.obtenerAdministradorDAO().consultarTodos().size() <= CANTIDAD_MINIMA_ADMINISTRADORES;
    }
}
