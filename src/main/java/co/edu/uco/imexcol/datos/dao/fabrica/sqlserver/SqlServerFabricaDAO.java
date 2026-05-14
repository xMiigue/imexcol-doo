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

            final var url = propiedades.getProperty("spring.datasource.url");
            final var usuario = propiedades.getProperty("spring.datasource.username");
            final var clave = propiedades.getProperty("spring.datasource.password");

            this.conexion = DriverManager.getConnection(url, usuario, clave);
        } catch (final Exception excepcion) {
            throw ImexcolException.crear(excepcion,
                    MensajesEnum.ERROR_USUARIO_CONEXION_SQL_ABRIR.getContenido(),
                    MensajesEnum.ERROR_TECNICO_CONEXION_SQL_ABRIR.getContenido(),
                    Lugar.DATOS);
        }
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
