package co.edu.uco.imexcol.negocio.casouso.regla.categoria;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.entidad.CategoriaEntidad;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class NombreCategoriaNoExisteRegla implements Regla {

    private static final NombreCategoriaNoExisteRegla INSTANCIA = new NombreCategoriaNoExisteRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 3;
    private static final int POSICION_NOMBRE = 0;
    private static final int POSICION_ID_CATEGORIA = 1;
    private static final int POSICION_FABRICA = 2;

    private NombreCategoriaNoExisteRegla() {
        super();
    }

    public static NombreCategoriaNoExisteRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var nombre = UtilTexto.aplicarTrim((String) datos[POSICION_NOMBRE]);
        final var idCategoria = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_CATEGORIA]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        if (existeOtraCategoriaConNombre(nombre, idCategoria, fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_CATEGORIA_NOMBRE_DUPLICADO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_CATEGORIA_NOMBRE_DUPLICADO.getContenido().formatted(nombre));
        }
    }

    private static boolean existeOtraCategoriaConNombre(final String nombre, final UUID idCategoria,
            final FabricaDAO fabrica) {
        final var filtro = new CategoriaEntidad();
        filtro.setNombre(nombre);
        return fabrica.obtenerCategoriaDAO().consultarPorFiltro(filtro).stream()
                .anyMatch(categoria -> !idCategoria.equals(categoria.getId()));
    }
}
