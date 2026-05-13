package co.edu.uco.imexcol.datos.dao.entidad;

import java.sql.Connection;

import co.edu.uco.imexcol.transversal.UtilConexionSql;

public abstract class ConexionSql {

    private Connection conexion;

    protected ConexionSql(final Connection conexion) {
        super();
        ajustarConexion(conexion);
    }

    public final Connection obtenerConexion() {
        return conexion;
    }

    private void ajustarConexion(final Connection conexion) {
        UtilConexionSql.asegurarConexionAbierta(conexion);
        this.conexion = conexion;
    }
}
