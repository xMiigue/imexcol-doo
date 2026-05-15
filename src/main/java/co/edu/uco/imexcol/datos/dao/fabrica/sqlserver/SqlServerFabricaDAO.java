package co.edu.uco.imexcol.datos.dao.fabrica.sqlserver;

import java.io.InputStream;
import java.sql.DriverManager;
import java.util.Properties;

import co.edu.uco.imexcol.datos.dao.AdministradorDAO;
import co.edu.uco.imexcol.datos.dao.CategoriaDAO;
import co.edu.uco.imexcol.datos.dao.ClienteDAO;
import co.edu.uco.imexcol.datos.dao.PedidoDAO;
import co.edu.uco.imexcol.datos.dao.ProductoDAO;
import co.edu.uco.imexcol.datos.dao.impl.sqlserver.AdministradorSqlServerDAO;
import co.edu.uco.imexcol.datos.dao.impl.sqlserver.CategoriaSqlServerDAO;
import co.edu.uco.imexcol.datos.dao.impl.sqlserver.ClienteSqlServerDAO;
import co.edu.uco.imexcol.datos.dao.impl.sqlserver.PedidoSqlServerDAO;
import co.edu.uco.imexcol.datos.dao.impl.sqlserver.ProductoSqlServerDAO;
import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class SqlServerFabricaDAO extends FabricaDAO {

    private static final String ARCHIVO_PROPIEDADES = "/application.properties";

    public SqlServerFabricaDAO() {
        super();
        abrirConexion();
    }

    @Override
    protected void abrirConexion() {
        try (final InputStream entrada = SqlServerFabricaDAO.class.getResourceAsStream(ARCHIVO_PROPIEDADES)) {
            final var propiedades = new Properties();
            propiedades.load(entrada);

            final var url = resolverPlaceholder(propiedades.getProperty("spring.datasource.url"));
            final var usuario = resolverPlaceholder(propiedades.getProperty("spring.datasource.username"));
            final var clave = resolverPlaceholder(propiedades.getProperty("spring.datasource.password"));

            this.conexion = DriverManager.getConnection(url, usuario, clave);
        } catch (final Exception excepcion) {
            throw ImexcolException.crear(excepcion,
                    MensajesEnum.ERROR_USUARIO_CONEXION_SQL_ABRIR.getContenido(),
                    MensajesEnum.ERROR_TECNICO_CONEXION_SQL_ABRIR.getContenido(),
                    Lugar.DATOS);
        }
    }

    // Resuelve el patron ${VARIABLE:valorDefecto} (o ${VARIABLE}) leyendo primero
    // la variable de entorno y, si no existe, el valor por defecto declarado en
    // application.properties. Si el valor no es un placeholder, lo retorna sin cambios.
    private static String resolverPlaceholder(final String valor) {
        final var valorSeguro = UtilTexto.aplicarTrim(valor);
        if (!valorSeguro.startsWith("${") || !valorSeguro.endsWith("}")) {
            return valorSeguro;
        }

        final var contenido = valorSeguro.substring(2, valorSeguro.length() - 1);
        final var posicionSeparador = contenido.indexOf(':');

        final String nombreVariable;
        final String valorDefecto;
        if (posicionSeparador >= 0) {
            nombreVariable = contenido.substring(0, posicionSeparador).trim();
            valorDefecto = contenido.substring(posicionSeparador + 1);
        } else {
            nombreVariable = contenido.trim();
            valorDefecto = UtilTexto.VACIO;
        }

        final var valorEntorno = System.getenv(nombreVariable);
        return UtilTexto.estaVacia(valorEntorno) ? valorDefecto : valorEntorno;
    }

    @Override
    public CategoriaDAO obtenerCategoriaDAO() {
        return new CategoriaSqlServerDAO(conexion);
    }

    @Override
    public ProductoDAO obtenerProductoDAO() {
        return new ProductoSqlServerDAO(conexion);
    }

    @Override
    public ClienteDAO obtenerClienteDAO() {
        return new ClienteSqlServerDAO(conexion);
    }

    @Override
    public PedidoDAO obtenerPedidoDAO() {
        return new PedidoSqlServerDAO(conexion);
    }

    @Override
    public AdministradorDAO obtenerAdministradorDAO() {
        return new AdministradorSqlServerDAO(conexion);
    }
}
