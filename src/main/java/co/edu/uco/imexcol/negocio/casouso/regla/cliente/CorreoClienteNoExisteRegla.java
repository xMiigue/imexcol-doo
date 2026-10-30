package co.edu.uco.imexcol.negocio.casouso.regla.cliente;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.entidad.ClienteEntidad;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class CorreoClienteNoExisteRegla implements Regla {

    private static final CorreoClienteNoExisteRegla INSTANCIA = new CorreoClienteNoExisteRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 3;
    private static final int POSICION_CORREO = 0;
    private static final int POSICION_ID_CLIENTE = 1;
    private static final int POSICION_FABRICA = 2;

    private CorreoClienteNoExisteRegla() {
        super();
    }

    public static CorreoClienteNoExisteRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var correo = UtilTexto.aplicarTrim((String) datos[POSICION_CORREO]);
        final var idCliente = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_CLIENTE]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        if (existeOtroClienteConCorreo(correo, idCliente, fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_CLIENTE_CORREO_DUPLICADO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_CLIENTE_CORREO_DUPLICADO.getContenido().formatted(correo));
        }
    }

    private static boolean existeOtroClienteConCorreo(final String correo, final UUID idCliente,
            final FabricaDAO fabrica) {
        final var filtro = new ClienteEntidad();
        filtro.setCorreoElectronico(correo);
        return fabrica.obtenerClienteDAO().consultarPorFiltro(filtro).stream()
                .anyMatch(cliente -> !idCliente.equals(cliente.getId()));
    }
}
