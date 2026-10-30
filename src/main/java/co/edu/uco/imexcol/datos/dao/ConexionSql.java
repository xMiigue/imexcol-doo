package co.edu.uco.imexcol.datos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import co.edu.uco.imexcol.datos.dao.impl.sql.ClausulasSql;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilConexionSql;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

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

    protected final <E> List<E> consultarConParametros(final String consulta, final List<Object> parametros,
            final Function<ResultSet, E> mapeador) {
        try (var sentencia = obtenerConexion().prepareStatement(consulta)) {
            asignarParametros(sentencia, parametros);
            return mapearResultados(sentencia, mapeador);
        } catch (final SQLException excepcion) {
            throw envolverErrorDao(excepcion, MensajesEnum.ERROR_USUARIO_DAO_CONSULTAR_GENERICO,
                    MensajesEnum.ERROR_TECNICO_DAO_CONSULTAR_GENERICO);
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    private static void asignarParametros(final PreparedStatement sentencia, final List<Object> parametros)
            throws SQLException {
        for (int indice = 0; indice < parametros.size(); indice++) {
            sentencia.setObject(indice + ClausulasSql.PRIMERA_POSICION_PARAMETRO, parametros.get(indice));
        }
    }

    private static <E> List<E> mapearResultados(final PreparedStatement sentencia,
            final Function<ResultSet, E> mapeador) {
        final var resultados = new ArrayList<E>();
        try (var resultSet = sentencia.executeQuery()) {
            while (resultSet.next()) {
                resultados.add(mapeador.apply(resultSet));
            }
        } catch (final SQLException excepcion) {
            throw envolverErrorDao(excepcion, MensajesEnum.ERROR_USUARIO_DAO_CONSULTAR_GENERICO,
                    MensajesEnum.ERROR_TECNICO_DAO_CONSULTAR_GENERICO);
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
        return resultados;
    }

    protected static ImexcolDatosException envolverErrorDao(final Exception excepcion,
            final MensajesEnum usuarioMsg, final MensajesEnum tecnicoMsg) {
        return ImexcolDatosException.crear(excepcion, usuarioMsg.getContenido(), tecnicoMsg.getContenido());
    }

    protected static ImexcolDatosException envolverErrorInesperado(final Exception excepcion) {
        return ImexcolDatosException.crear(excepcion,
                MensajesEnum.ERROR_USUARIO_DAO_OPERACION_INESPERADA.getContenido(),
                MensajesEnum.ERROR_TECNICO_DAO_OPERACION_INESPERADA.getContenido());
    }
}
