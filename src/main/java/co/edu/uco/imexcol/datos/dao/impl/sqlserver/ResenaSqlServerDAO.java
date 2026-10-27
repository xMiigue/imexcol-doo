package co.edu.uco.imexcol.datos.dao.impl.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.ConexionSql;
import co.edu.uco.imexcol.datos.dao.ResenaDAO;
import co.edu.uco.imexcol.datos.dao.impl.mapper.ResenaMapper;
import co.edu.uco.imexcol.datos.dao.impl.sql.ClausulasSql;
import co.edu.uco.imexcol.datos.dao.impl.sql.ResenaSql;
import co.edu.uco.imexcol.entidad.ResenaEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilConexionSql;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class ResenaSqlServerDAO extends ConexionSql implements ResenaDAO {

    private static final int POSICION_ID = 1;
    private static final int POSICION_CLIENTE = 2;
    private static final int POSICION_PRODUCTO = 3;
    private static final int POSICION_CALIFICACION = 4;
    private static final int POSICION_COMENTARIO = 5;
    private static final int POSICION_FECHA = 6;
    private static final int POSICION_ID_ELIMINAR = 1;

    public ResenaSqlServerDAO(final Connection conexion) {
        super(conexion);
    }

    @Override
    public void acceder(final ResenaEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new ResenaEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(ResenaSql.ACCEDER)) {
            sentencia.setObject(POSICION_ID, entidadSegura.getId());
            sentencia.setObject(POSICION_CLIENTE, entidadSegura.getCliente().getId());
            sentencia.setObject(POSICION_PRODUCTO, entidadSegura.getProducto().getId());
            sentencia.setInt(POSICION_CALIFICACION, entidadSegura.getCalificacion());
            sentencia.setString(POSICION_COMENTARIO, entidadSegura.getComentario());
            sentencia.setTimestamp(POSICION_FECHA,
                    Timestamp.valueOf(UtilFecha.obtenerValorDefecto(entidadSegura.getFecha())));
            sentencia.executeUpdate();
        } catch (final SQLException excepcion) {
            throw envolverErrorDao(excepcion, MensajesEnum.ERROR_USUARIO_DAO_CREAR_GENERICO,
                    MensajesEnum.ERROR_TECNICO_DAO_CREAR_GENERICO);
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    @Override
    public void eliminar(final UUID id) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());

        try (var sentencia = obtenerConexion().prepareStatement(ResenaSql.ELIMINAR)) {
            sentencia.setObject(POSICION_ID_ELIMINAR, UtilUUID.obtenerValorDefecto(id));
            sentencia.executeUpdate();
        } catch (final SQLException excepcion) {
            throw envolverErrorDao(excepcion, MensajesEnum.ERROR_USUARIO_DAO_ELIMINAR_GENERICO,
                    MensajesEnum.ERROR_TECNICO_DAO_ELIMINAR_GENERICO);
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    @Override
    public ResenaEntidad consultarPorId(final UUID id) {
        final var filtro = new ResenaEntidad(UtilUUID.obtenerValorDefecto(id));
        return consultarPorFiltro(filtro).stream().findFirst().orElse(new ResenaEntidad());
    }

    @Override
    public List<ResenaEntidad> consultarTodos() {
        return consultarPorFiltro(new ResenaEntidad());
    }

    @Override
    public List<ResenaEntidad> consultarPorFiltro(final ResenaEntidad filtro) {
        final var entidadFiltro = UtilObjeto.obtenerValorDefecto(filtro, new ResenaEntidad());
        final var parametros = new ArrayList<Object>();
        final var consulta = construirConsultaPorFiltro(entidadFiltro, parametros);

        try (var sentencia = obtenerConexion().prepareStatement(consulta)) {
            for (int indice = 0; indice < parametros.size(); indice++) {
                sentencia.setObject(indice + ClausulasSql.PRIMERA_POSICION_PARAMETRO, parametros.get(indice));
            }
            return ejecutarConsultaPorFiltro(sentencia);
        } catch (final SQLException excepcion) {
            throw envolverErrorDao(excepcion, MensajesEnum.ERROR_USUARIO_DAO_CONSULTAR_GENERICO,
                    MensajesEnum.ERROR_TECNICO_DAO_CONSULTAR_GENERICO);
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    private String construirConsultaPorFiltro(final ResenaEntidad filtro, final List<Object> parametros) {
        final var condiciones = new ArrayList<String>();

        agregarCondicion(condiciones, parametros, !UtilUUID.esUUIDPorDefecto(filtro.getId()),
                ResenaSql.FILTRO_ID, filtro.getId());
        agregarCondicion(condiciones, parametros, !UtilUUID.esUUIDPorDefecto(filtro.getCliente().getId()),
                ResenaSql.FILTRO_CLIENTE, filtro.getCliente().getId());
        agregarCondicion(condiciones, parametros, !UtilUUID.esUUIDPorDefecto(filtro.getProducto().getId()),
                ResenaSql.FILTRO_PRODUCTO, filtro.getProducto().getId());

        return armarConsulta(condiciones);
    }

    private static void agregarCondicion(final List<String> condiciones, final List<Object> parametros,
            final boolean aplica, final String clausula, final Object valor) {
        if (aplica) {
            condiciones.add(clausula);
            parametros.add(valor);
        }
    }

    private static String armarConsulta(final List<String> condiciones) {
        final var consulta = new StringBuilder(ResenaSql.CONSULTAR_POR_FILTRO);
        if (!condiciones.isEmpty()) {
            consulta.append(ClausulasSql.DONDE).append(String.join(ClausulasSql.Y, condiciones));
        }
        return consulta.append(ResenaSql.ORDENAMIENTO).toString();
    }

    private List<ResenaEntidad> ejecutarConsultaPorFiltro(final PreparedStatement sentencia) {
        final var resultados = new ArrayList<ResenaEntidad>();
        try (var resultSet = sentencia.executeQuery()) {
            while (resultSet.next()) {
                resultados.add(ResenaMapper.mapear(resultSet));
            }
        } catch (final SQLException excepcion) {
            throw envolverErrorDao(excepcion, MensajesEnum.ERROR_USUARIO_DAO_CONSULTAR_GENERICO,
                    MensajesEnum.ERROR_TECNICO_DAO_CONSULTAR_GENERICO);
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
        return resultados;
    }

    private static ImexcolDatosException envolverErrorDao(final Exception excepcion,
            final MensajesEnum usuarioMsg, final MensajesEnum tecnicoMsg) {
        return ImexcolDatosException.crear(excepcion, usuarioMsg.getContenido(), tecnicoMsg.getContenido());
    }

    private static ImexcolDatosException envolverErrorInesperado(final Exception excepcion) {
        return ImexcolDatosException.crear(excepcion,
                MensajesEnum.ERROR_USUARIO_DAO_OPERACION_INESPERADA.getContenido(),
                MensajesEnum.ERROR_TECNICO_DAO_OPERACION_INESPERADA.getContenido());
    }
}
