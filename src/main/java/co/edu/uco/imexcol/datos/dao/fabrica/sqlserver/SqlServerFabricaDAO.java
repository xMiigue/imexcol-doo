package co.edu.uco.imexcol.datos.dao.fabrica.sqlserver;

import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import co.edu.uco.imexcol.datos.dao.AdministradorDAO;
import co.edu.uco.imexcol.datos.dao.CarritoComprasDAO;
import co.edu.uco.imexcol.datos.dao.CategoriaDAO;
import co.edu.uco.imexcol.datos.dao.ClienteDAO;
import co.edu.uco.imexcol.datos.dao.DireccionDAO;
import co.edu.uco.imexcol.datos.dao.EnvioDAO;
import co.edu.uco.imexcol.datos.dao.FacturaDAO;
import co.edu.uco.imexcol.datos.dao.ItemCarritoDAO;
import co.edu.uco.imexcol.datos.dao.LineaPedidoDAO;
import co.edu.uco.imexcol.datos.dao.MetodoPagoDAO;
import co.edu.uco.imexcol.datos.dao.PagoDAO;
import co.edu.uco.imexcol.datos.dao.PedidoDAO;
import co.edu.uco.imexcol.datos.dao.ProductoDAO;
import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.datos.dao.impl.sqlserver.AdministradorSqlServerDAO;
import co.edu.uco.imexcol.datos.dao.impl.sqlserver.CarritoComprasSqlServerDAO;
import co.edu.uco.imexcol.datos.dao.impl.sqlserver.CategoriaSqlServerDAO;
import co.edu.uco.imexcol.datos.dao.impl.sqlserver.ClienteSqlServerDAO;
import co.edu.uco.imexcol.datos.dao.impl.sqlserver.DireccionSqlServerDAO;
import co.edu.uco.imexcol.datos.dao.impl.sqlserver.EnvioSqlServerDAO;
import co.edu.uco.imexcol.datos.dao.impl.sqlserver.FacturaSqlServerDAO;
import co.edu.uco.imexcol.datos.dao.impl.sqlserver.ItemCarritoSqlServerDAO;
import co.edu.uco.imexcol.datos.dao.impl.sqlserver.LineaPedidoSqlServerDAO;
import co.edu.uco.imexcol.datos.dao.impl.sqlserver.MetodoPagoSqlServerDAO;
import co.edu.uco.imexcol.datos.dao.impl.sqlserver.PagoSqlServerDAO;
import co.edu.uco.imexcol.datos.dao.impl.sqlserver.PedidoSqlServerDAO;
import co.edu.uco.imexcol.datos.dao.impl.sqlserver.ProductoSqlServerDAO;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class SqlServerFabricaDAO extends FabricaDAO {

    private static final String ARCHIVO_PROPIEDADES = "/application.properties";
    private static final String PROPIEDAD_URL = "spring.datasource.url";
    private static final String PROPIEDAD_USUARIO = "spring.datasource.username";
    private static final String PROPIEDAD_CLAVE = "spring.datasource.password";
    private static final String INICIO_VARIABLE = "${";
    private static final String FIN_VARIABLE = "}";
    private static final String SEPARADOR_VALOR_DEFECTO = ":";
    private static final int POSICION_INICIAL = 0;

    public SqlServerFabricaDAO() {
        super();
        abrirConexion();
    }

    @Override
    protected void abrirConexion() {
        final var propiedades = cargarPropiedades();
        final var url = obtenerValorPropiedad(propiedades, PROPIEDAD_URL);
        final var usuario = obtenerValorPropiedad(propiedades, PROPIEDAD_USUARIO);
        final var clave = obtenerValorPropiedad(propiedades, PROPIEDAD_CLAVE);
        conectar(url, usuario, clave);
    }

    private static Properties cargarPropiedades() {
        try (final InputStream entrada = SqlServerFabricaDAO.class.getResourceAsStream(ARCHIVO_PROPIEDADES)) {
            final var propiedades = new Properties();
            propiedades.load(entrada);
            return propiedades;
        } catch (final Exception excepcion) {
            throw crearExcepcionAbrirConexion(excepcion);
        }
    }

    private void conectar(final String url, final String usuario, final String clave) {
        try {
            this.conexion = DriverManager.getConnection(url, usuario, clave);
        } catch (final SQLException excepcion) {
            throw crearExcepcionAbrirConexion(excepcion);
        }
    }

    private static ImexcolDatosException crearExcepcionAbrirConexion(final Exception excepcion) {
        return ImexcolDatosException.crear(excepcion,
                MensajesEnum.ERROR_USUARIO_CONEXION_SQL_ABRIR.getContenido(),
                MensajesEnum.ERROR_TECNICO_CONEXION_SQL_ABRIR.getContenido());
    }

    private static String obtenerValorPropiedad(final Properties propiedades, final String nombrePropiedad) {
        final var valor = UtilTexto.aplicarTrim(propiedades.getProperty(nombrePropiedad));
        return esReferenciaAVariable(valor) ? resolverVariable(extraerExpresion(valor)) : valor;
    }

    private static boolean esReferenciaAVariable(final String valor) {
        return valor.startsWith(INICIO_VARIABLE) && valor.endsWith(FIN_VARIABLE);
    }

    private static String extraerExpresion(final String valor) {
        return valor.substring(INICIO_VARIABLE.length(), valor.length() - FIN_VARIABLE.length());
    }

    private static String resolverVariable(final String expresion) {
        final var nombreVariable = obtenerNombreVariable(expresion);
        final var valorEntorno = System.getenv(nombreVariable);

        if (!UtilTexto.estaVacia(valorEntorno)) {
            return valorEntorno;
        }
        if (tieneValorDefecto(expresion)) {
            return obtenerValorDefecto(expresion);
        }
        throw crearExcepcionVariableNoDefinida(nombreVariable);
    }

    private static boolean tieneValorDefecto(final String expresion) {
        return expresion.contains(SEPARADOR_VALOR_DEFECTO);
    }

    private static String obtenerNombreVariable(final String expresion) {
        final var nombre = tieneValorDefecto(expresion)
                ? expresion.substring(POSICION_INICIAL, expresion.indexOf(SEPARADOR_VALOR_DEFECTO))
                : expresion;
        return UtilTexto.aplicarTrim(nombre);
    }

    private static String obtenerValorDefecto(final String expresion) {
        return expresion.substring(expresion.indexOf(SEPARADOR_VALOR_DEFECTO) + SEPARADOR_VALOR_DEFECTO.length());
    }

    private static ImexcolDatosException crearExcepcionVariableNoDefinida(final String nombreVariable) {
        return ImexcolDatosException.crear(
                MensajesEnum.ERROR_USUARIO_VARIABLE_ENTORNO_NO_DEFINIDA.getContenido(),
                MensajesEnum.ERROR_TECNICO_VARIABLE_ENTORNO_NO_DEFINIDA.getContenido().formatted(nombreVariable));
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

    @Override
    public MetodoPagoDAO obtenerMetodoPagoDAO() {
        return new MetodoPagoSqlServerDAO(conexion);
    }

    @Override
    public DireccionDAO obtenerDireccionDAO() {
        return new DireccionSqlServerDAO(conexion);
    }

    @Override
    public CarritoComprasDAO obtenerCarritoComprasDAO() {
        return new CarritoComprasSqlServerDAO(conexion);
    }

    @Override
    public ItemCarritoDAO obtenerItemCarritoDAO() {
        return new ItemCarritoSqlServerDAO(conexion);
    }

    @Override
    public LineaPedidoDAO obtenerLineaPedidoDAO() {
        return new LineaPedidoSqlServerDAO(conexion);
    }

    @Override
    public PagoDAO obtenerPagoDAO() {
        return new PagoSqlServerDAO(conexion);
    }

    @Override
    public EnvioDAO obtenerEnvioDAO() {
        return new EnvioSqlServerDAO(conexion);
    }

    @Override
    public FacturaDAO obtenerFacturaDAO() {
        return new FacturaSqlServerDAO(conexion);
    }
}
