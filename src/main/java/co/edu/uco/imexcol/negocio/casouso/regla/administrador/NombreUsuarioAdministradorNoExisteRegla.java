package co.edu.uco.imexcol.negocio.casouso.regla.administrador;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.entidad.AdministradorEntidad;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class NombreUsuarioAdministradorNoExisteRegla implements Regla {

    private static final NombreUsuarioAdministradorNoExisteRegla INSTANCIA =
            new NombreUsuarioAdministradorNoExisteRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 3;
    private static final int POSICION_NOMBRE_USUARIO = 0;
    private static final int POSICION_ID_ADMINISTRADOR = 1;
    private static final int POSICION_FABRICA = 2;

    private NombreUsuarioAdministradorNoExisteRegla() {
        super();
    }

    public static NombreUsuarioAdministradorNoExisteRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var nombreUsuario = UtilTexto.aplicarTrim((String) datos[POSICION_NOMBRE_USUARIO]);
        final var idAdministrador = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_ADMINISTRADOR]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        if (existeOtroAdministradorConNombreUsuario(nombreUsuario, idAdministrador, fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_ADMINISTRADOR_NOMBRE_USUARIO_DUPLICADO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_ADMINISTRADOR_NOMBRE_USUARIO_DUPLICADO.getContenido()
                            .formatted(nombreUsuario));
        }
    }

    private static boolean existeOtroAdministradorConNombreUsuario(final String nombreUsuario,
            final UUID idAdministrador, final FabricaDAO fabrica) {
        final var filtro = new AdministradorEntidad();
        filtro.setNombreUsuario(nombreUsuario);
        return fabrica.obtenerAdministradorDAO().consultarPorFiltro(filtro).stream()
                .anyMatch(administrador -> !idAdministrador.equals(administrador.getId()));
    }
}
