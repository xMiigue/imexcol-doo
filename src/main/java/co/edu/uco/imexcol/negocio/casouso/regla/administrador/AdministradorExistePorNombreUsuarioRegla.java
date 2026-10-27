package co.edu.uco.imexcol.negocio.casouso.regla.administrador;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.entidad.AdministradorEntidad;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolAutenticacionException;

public final class AdministradorExistePorNombreUsuarioRegla implements Regla {

    private static final AdministradorExistePorNombreUsuarioRegla INSTANCIA = new AdministradorExistePorNombreUsuarioRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_NOMBRE_USUARIO = 0;
    private static final int POSICION_FABRICA = 1;

    private AdministradorExistePorNombreUsuarioRegla() {
        super();
    }

    public static AdministradorExistePorNombreUsuarioRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var nombreUsuario = UtilTexto.aplicarTrim((String) datos[POSICION_NOMBRE_USUARIO]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        if (!existeAdministradorConNombreUsuario(nombreUsuario, fabrica)) {
            throw ImexcolAutenticacionException.crear(
                    MensajesEnum.ERROR_USUARIO_AUTENTICACION_ADMINISTRADOR_CREDENCIALES_INVALIDAS.getContenido(),
                    MensajesEnum.ERROR_TECNICO_AUTENTICACION_ADMINISTRADOR_NO_REGISTRADO.getContenido().formatted(nombreUsuario));
        }
    }

    private static boolean existeAdministradorConNombreUsuario(final String nombreUsuario, final FabricaDAO fabrica) {
        final var filtro = new AdministradorEntidad();
        filtro.setNombreUsuario(nombreUsuario);
        return !fabrica.obtenerAdministradorDAO().consultarPorFiltro(filtro).isEmpty();
    }
}
