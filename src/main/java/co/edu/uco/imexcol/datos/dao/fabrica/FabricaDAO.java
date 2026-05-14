package co.edu.uco.imexcol.datos.dao.fabrica;

import java.sql.Connection;

import co.edu.uco.imexcol.datos.dao.AdministradorDAO;
import co.edu.uco.imexcol.datos.dao.CategoriaDAO;
import co.edu.uco.imexcol.datos.dao.ClienteDAO;
import co.edu.uco.imexcol.datos.dao.PedidoDAO;
import co.edu.uco.imexcol.datos.dao.ProductoDAO;
import co.edu.uco.imexcol.datos.dao.fabrica.sqlserver.SqlServerFabricaDAO;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilConexionSql;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public abstract class FabricaDAO {

    protected Connection conexion;
    protected static FabricaEnum fabrica = FabricaEnum.SQLSERVER;

    public static FabricaDAO obtenerFabrica() {
        return obtenerFabrica(fabrica);
    }

    public static FabricaDAO obtenerFabrica(final FabricaEnum tipo) {
        if (FabricaEnum.SQLSERVER.equals(tipo)) {
            return new SqlServerFabricaDAO();
        }
        throw ImexcolException.crear(
                MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido(),
                Lugar.DATOS);
    }

    public abstract CategoriaDAO obtenerCategoriaDAO();

    public abstract ProductoDAO obtenerProductoDAO();

    public abstract ClienteDAO obtenerClienteDAO();

    public abstract PedidoDAO obtenerPedidoDAO();

    public abstract AdministradorDAO obtenerAdministradorDAO();

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
