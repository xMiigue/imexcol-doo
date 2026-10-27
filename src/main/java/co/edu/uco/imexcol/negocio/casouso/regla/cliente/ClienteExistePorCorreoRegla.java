package co.edu.uco.imexcol.negocio.casouso.regla.cliente;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.entidad.ClienteEntidad;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolAutenticacionException;

public final class ClienteExistePorCorreoRegla implements Regla {

    private static final ClienteExistePorCorreoRegla INSTANCIA = new ClienteExistePorCorreoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_CORREO = 0;
    private static final int POSICION_FABRICA = 1;

    private ClienteExistePorCorreoRegla() {
        super();
    }

    public static ClienteExistePorCorreoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var correo = UtilTexto.aplicarTrim((String) datos[POSICION_CORREO]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        if (!existeClienteConCorreo(correo, fabrica)) {
            throw ImexcolAutenticacionException.crear(
                    MensajesEnum.ERROR_USUARIO_AUTENTICACION_CLIENTE_CREDENCIALES_INVALIDAS.getContenido(),
                    MensajesEnum.ERROR_TECNICO_AUTENTICACION_CLIENTE_NO_REGISTRADO.getContenido().formatted(correo));
        }
    }

    private static boolean existeClienteConCorreo(final String correo, final FabricaDAO fabrica) {
        final var filtro = new ClienteEntidad();
        filtro.setCorreoElectronico(correo);
        return !fabrica.obtenerClienteDAO().consultarPorFiltro(filtro).isEmpty();
    }
}
