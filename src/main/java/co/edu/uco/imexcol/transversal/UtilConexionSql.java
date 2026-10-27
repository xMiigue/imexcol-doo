package co.edu.uco.imexcol.transversal;

import java.sql.Connection;
import java.sql.SQLException;

import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class UtilConexionSql {

    private UtilConexionSql() {
        super();
    }

    public static void asegurarConexionNoNula(final Connection conexion) {
        if (UtilObjeto.esNulo(conexion)) {
            throw ImexcolDatosException.crear(
                    MensajesEnum.ERROR_USUARIO_CONEXION_SQL_NULA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_CONEXION_SQL_NULA.getContenido());
        }
    }

    public static void asegurarConexionAbierta(final Connection conexion) {
        asegurarConexionNoNula(conexion);
        try {
            if (conexion.isClosed()) {
                throw ImexcolDatosException.crear(
                        MensajesEnum.ERROR_USUARIO_CONEXION_SQL_CERRADA.getContenido(),
                        MensajesEnum.ERROR_TECNICO_CONEXION_SQL_CERRADA.getContenido());
            }
        } catch (final SQLException excepcion) {
            throw ImexcolDatosException.crear(excepcion,
                    MensajesEnum.ERROR_USUARIO_CONEXION_SQL_VALIDACION_INESPERADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_CONEXION_SQL_VALIDACION_INESPERADA.getContenido());
        }
    }

    public static void asegurarTransaccionIniciada(final Connection conexion) {
        asegurarConexionAbierta(conexion);
        try {
            if (conexion.getAutoCommit()) {
                throw ImexcolDatosException.crear(
                        MensajesEnum.ERROR_USUARIO_TRANSACCION_NO_INICIADA.getContenido(),
                        MensajesEnum.ERROR_TECNICO_TRANSACCION_NO_INICIADA.getContenido());
            }
        } catch (final SQLException excepcion) {
            throw ImexcolDatosException.crear(excepcion,
                    MensajesEnum.ERROR_USUARIO_TRANSACCION_VALIDACION_INESPERADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_TRANSACCION_VALIDACION_INESPERADA.getContenido());
        }
    }

    public static void asegurarTransaccionNoIniciada(final Connection conexion) {
        asegurarConexionAbierta(conexion);
        try {
            if (!conexion.getAutoCommit()) {
                throw ImexcolDatosException.crear(
                        MensajesEnum.ERROR_USUARIO_TRANSACCION_YA_INICIADA.getContenido(),
                        MensajesEnum.ERROR_TECNICO_TRANSACCION_YA_INICIADA.getContenido());
            }
        } catch (final SQLException excepcion) {
            throw ImexcolDatosException.crear(excepcion,
                    MensajesEnum.ERROR_USUARIO_TRANSACCION_VALIDACION_INESPERADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_TRANSACCION_VALIDACION_INESPERADA.getContenido());
        }
    }

    public static void iniciarTransaccion(final Connection conexion) {
        asegurarConexionAbierta(conexion);
        asegurarTransaccionNoIniciada(conexion);
        try {
            conexion.setAutoCommit(false);
        } catch (final SQLException excepcion) {
            throw ImexcolDatosException.crear(excepcion,
                    MensajesEnum.ERROR_USUARIO_TRANSACCION_INICIAR.getContenido(),
                    MensajesEnum.ERROR_TECNICO_TRANSACCION_INICIAR.getContenido());
        }
    }

    public static void confirmarTransaccion(final Connection conexion) {
        asegurarTransaccionIniciada(conexion);
        try {
            conexion.commit();
        } catch (final SQLException excepcion) {
            throw ImexcolDatosException.crear(excepcion,
                    MensajesEnum.ERROR_USUARIO_TRANSACCION_CONFIRMAR.getContenido(),
                    MensajesEnum.ERROR_TECNICO_TRANSACCION_CONFIRMAR.getContenido());
        }
    }

    public static void revertirTransaccion(final Connection conexion) {
        asegurarTransaccionIniciada(conexion);
        try {
            conexion.rollback();
        } catch (final SQLException excepcion) {
            throw ImexcolDatosException.crear(excepcion,
                    MensajesEnum.ERROR_USUARIO_TRANSACCION_REVERTIR.getContenido(),
                    MensajesEnum.ERROR_TECNICO_TRANSACCION_REVERTIR.getContenido());
        }
    }

    public static void cerrarConexion(final Connection conexion) {
        asegurarConexionAbierta(conexion);
        try {
            conexion.close();
        } catch (final SQLException excepcion) {
            throw ImexcolDatosException.crear(excepcion,
                    MensajesEnum.ERROR_USUARIO_CONEXION_SQL_CERRAR.getContenido(),
                    MensajesEnum.ERROR_TECNICO_CONEXION_SQL_CERRAR.getContenido());
        }
    }
}
