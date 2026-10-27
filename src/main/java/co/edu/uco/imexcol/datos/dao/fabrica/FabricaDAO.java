package co.edu.uco.imexcol.datos.dao.fabrica;

import java.sql.Connection;

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
import co.edu.uco.imexcol.datos.dao.fabrica.sqlserver.SqlServerFabricaDAO;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilConexionSql;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public abstract class FabricaDAO {

    protected Connection conexion;
    private static final FabricaEnum FABRICA_POR_DEFECTO = FabricaEnum.SQLSERVER;

    public static FabricaDAO obtenerFabrica() {
        return obtenerFabrica(FABRICA_POR_DEFECTO);
    }

    public static FabricaDAO obtenerFabrica(final FabricaEnum tipo) {
        if (FabricaEnum.SQLSERVER.equals(tipo)) {
            return new SqlServerFabricaDAO();
        }
        throw ImexcolDatosException.crear(
                MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido());
    }

    public abstract CategoriaDAO obtenerCategoriaDAO();

    public abstract ProductoDAO obtenerProductoDAO();

    public abstract ClienteDAO obtenerClienteDAO();

    public abstract PedidoDAO obtenerPedidoDAO();

    public abstract AdministradorDAO obtenerAdministradorDAO();

    public abstract MetodoPagoDAO obtenerMetodoPagoDAO();

    public abstract DireccionDAO obtenerDireccionDAO();

    public abstract CarritoComprasDAO obtenerCarritoComprasDAO();

    public abstract ItemCarritoDAO obtenerItemCarritoDAO();

    public abstract LineaPedidoDAO obtenerLineaPedidoDAO();

    public abstract PagoDAO obtenerPagoDAO();

    public abstract EnvioDAO obtenerEnvioDAO();

    public abstract FacturaDAO obtenerFacturaDAO();

    protected abstract void abrirConexion();

    public final void iniciarTransaccion() {
        UtilConexionSql.iniciarTransaccion(conexion);
    }

    public final void confirmarTransaccion() {
        UtilConexionSql.confirmarTransaccion(conexion);
    }

    public final void revertirTransaccion() {
        UtilConexionSql.revertirTransaccion(conexion);
    }

    public final void cerrarConexion() {
        UtilConexionSql.cerrarConexion(conexion);
    }
}
