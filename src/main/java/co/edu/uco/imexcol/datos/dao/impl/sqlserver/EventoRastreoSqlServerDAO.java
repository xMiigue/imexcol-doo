package co.edu.uco.imexcol.datos.dao.impl.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.ConexionSql;
import co.edu.uco.imexcol.datos.dao.EventoRastreoDAO;
import co.edu.uco.imexcol.datos.dao.impl.mapper.EventoRastreoMapper;
import co.edu.uco.imexcol.datos.dao.impl.sql.ClausulasSql;
import co.edu.uco.imexcol.datos.dao.impl.sql.EventoRastreoSql;
import co.edu.uco.imexcol.entidad.EventoRastreoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilConexionSql;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class EventoRastreoSqlServerDAO extends ConexionSql implements EventoRastreoDAO {

    private static final int POSICION_ID = 1;
    private static final int POSICION_ENVIO = 2;
    private static final int POSICION_ESTADO_LOGISTICO = 3;
    private static final int POSICION_FECHA_HORA = 4;
    private static final int POSICION_OBSERVACION = 5;
    private static final int POSICION_ID_ENVIO_ELIMINAR = 1;

    public EventoRastreoSqlServerDAO(final Connection conexion) {
        super(conexion);
    }

    @Override
    public void acceder(final EventoRastreoEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new EventoRastreoEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(EventoRastreoSql.ACCEDER)) {
            sentencia.setObject(POSICION_ID, entidadSegura.getId());
            sentencia.setObject(POSICION_ENVIO, entidadSegura.getEnvio().getId());
            sentencia.setString(POSICION_ESTADO_LOGISTICO, entidadSegura.getEstadoLogistico());
            sentencia.setTimestamp(POSICION_FECHA_HORA,
                    Timestamp.valueOf(UtilFecha.obtenerValorDefecto(entidadSegura.getFechaHora())));
            sentencia.setString(POSICION_OBSERVACION, entidadSegura.getObservacion());
            sentencia.executeUpdate();
        } catch (final SQLException excepcion) {
            throw envolverErrorDao(excepcion, MensajesEnum.ERROR_USUARIO_DAO_CREAR_GENERICO,
                    MensajesEnum.ERROR_TECNICO_DAO_CREAR_GENERICO);
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    @Override
    public void eliminarPorEnvio(final UUID idEnvio) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());

        try (var sentencia = obtenerConexion().prepareStatement(EventoRastreoSql.ELIMINAR_POR_ENVIO)) {
            sentencia.setObject(POSICION_ID_ENVIO_ELIMINAR, UtilUUID.obtenerValorDefecto(idEnvio));
            sentencia.executeUpdate();
        } catch (final SQLException excepcion) {
            throw envolverErrorDao(excepcion, MensajesEnum.ERROR_USUARIO_DAO_ELIMINAR_GENERICO,
                    MensajesEnum.ERROR_TECNICO_DAO_ELIMINAR_GENERICO);
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    @Override
    public EventoRastreoEntidad consultarPorId(final UUID id) {
        final var filtro = new EventoRastreoEntidad(UtilUUID.obtenerValorDefecto(id));
        return consultarPorFiltro(filtro).stream().findFirst().orElse(new EventoRastreoEntidad());
    }

    @Override
    public List<EventoRastreoEntidad> consultarTodos() {
        return consultarPorFiltro(new EventoRastreoEntidad());
    }

    @Override
    public List<EventoRastreoEntidad> consultarPorFiltro(final EventoRastreoEntidad filtro) {
        final var entidadFiltro = UtilObjeto.obtenerValorDefecto(filtro, new EventoRastreoEntidad());
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

    private String construirConsultaPorFiltro(final EventoRastreoEntidad filtro, final List<Object> parametros) {
        final var condiciones = new ArrayList<String>();

        agregarCondicion(condiciones, parametros, !UtilUUID.esUUIDPorDefecto(filtro.getId()),
                EventoRastreoSql.FILTRO_ID, filtro.getId());
        agregarCondicion(condiciones, parametros, !UtilUUID.esUUIDPorDefecto(filtro.getEnvio().getId()),
                EventoRastreoSql.FILTRO_ENVIO, filtro.getEnvio().getId());
        agregarCondicion(condiciones, parametros, !UtilTexto.estaVacia(filtro.getEstadoLogistico()),
                EventoRastreoSql.FILTRO_ESTADO_LOGISTICO, filtro.getEstadoLogistico());

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
        final var consulta = new StringBuilder(EventoRastreoSql.CONSULTAR_POR_FILTRO);
        if (!condiciones.isEmpty()) {
            consulta.append(ClausulasSql.DONDE).append(String.join(ClausulasSql.Y, condiciones));
        }
        return consulta.append(EventoRastreoSql.ORDENAMIENTO).toString();
    }

    private List<EventoRastreoEntidad> ejecutarConsultaPorFiltro(final PreparedStatement sentencia) {
        final var resultados = new ArrayList<EventoRastreoEntidad>();
        try (var resultSet = sentencia.executeQuery()) {
            while (resultSet.next()) {
                resultados.add(EventoRastreoMapper.mapear(resultSet));
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
