package co.edu.uco.imexcol.datos.dao.impl.sqlserver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.ConexionSql;
import co.edu.uco.imexcol.datos.dao.MetodoPagoDAO;
import co.edu.uco.imexcol.datos.dao.impl.mapper.MetodoPagoMapper;
import co.edu.uco.imexcol.datos.dao.impl.sql.ClausulasSql;
import co.edu.uco.imexcol.datos.dao.impl.sql.MetodoPagoSql;
import co.edu.uco.imexcol.entidad.MetodoPagoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilConexionSql;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class MetodoPagoSqlServerDAO extends ConexionSql implements MetodoPagoDAO {

    private static final int POSICION_ID = 1;
    private static final int POSICION_NOMBRE = 2;
    private static final int POSICION_DESCRIPCION = 3;
    private static final int POSICION_ESTADO = 4;
    private static final int POSICION_ACTUALIZAR_NOMBRE = 1;
    private static final int POSICION_ACTUALIZAR_DESCRIPCION = 2;
    private static final int POSICION_ACTUALIZAR_ESTADO = 3;
    private static final int POSICION_ACTUALIZAR_ID = 4;
    private static final int POSICION_ID_ELIMINAR = 1;

    public MetodoPagoSqlServerDAO(final Connection conexion) {
        super(conexion);
    }

    @Override
    public void crear(final MetodoPagoEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new MetodoPagoEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(MetodoPagoSql.CREAR)) {
            sentencia.setObject(POSICION_ID, entidadSegura.getId());
            sentencia.setString(POSICION_NOMBRE, entidadSegura.getNombre());
            sentencia.setString(POSICION_DESCRIPCION, entidadSegura.getDescripcion());
            sentencia.setBoolean(POSICION_ESTADO, entidadSegura.isEstado());
            sentencia.executeUpdate();
        } catch (final SQLException excepcion) {
            throw envolverErrorDao(excepcion, MensajesEnum.ERROR_USUARIO_DAO_CREAR_GENERICO,
                    MensajesEnum.ERROR_TECNICO_DAO_CREAR_GENERICO);
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    @Override
    public void actualizar(final MetodoPagoEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new MetodoPagoEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(MetodoPagoSql.ACTUALIZAR)) {
            sentencia.setString(POSICION_ACTUALIZAR_NOMBRE, entidadSegura.getNombre());
            sentencia.setString(POSICION_ACTUALIZAR_DESCRIPCION, entidadSegura.getDescripcion());
            sentencia.setBoolean(POSICION_ACTUALIZAR_ESTADO, entidadSegura.isEstado());
            sentencia.setObject(POSICION_ACTUALIZAR_ID, entidadSegura.getId());
            sentencia.executeUpdate();
        } catch (final SQLException excepcion) {
            throw envolverErrorDao(excepcion, MensajesEnum.ERROR_USUARIO_DAO_MODIFICAR_GENERICO,
                    MensajesEnum.ERROR_TECNICO_DAO_MODIFICAR_GENERICO);
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    @Override
    public void eliminar(final UUID id) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());

        try (var sentencia = obtenerConexion().prepareStatement(MetodoPagoSql.ELIMINAR)) {
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
    public MetodoPagoEntidad consultarPorId(final UUID id) {
        final var filtro = new MetodoPagoEntidad(UtilUUID.obtenerValorDefecto(id));
        return consultarPorFiltro(filtro).stream().findFirst().orElse(new MetodoPagoEntidad());
    }

    @Override
    public List<MetodoPagoEntidad> consultarTodos() {
        return consultarPorFiltro(new MetodoPagoEntidad());
    }

    @Override
    public List<MetodoPagoEntidad> consultarPorFiltro(final MetodoPagoEntidad filtro) {
        final var entidadFiltro = UtilObjeto.obtenerValorDefecto(filtro, new MetodoPagoEntidad());
        final var parametros = new ArrayList<Object>();
        final var consulta = construirConsultaPorFiltro(entidadFiltro, parametros);
        return consultarConParametros(consulta, parametros, MetodoPagoMapper::mapear);
    }

    private String construirConsultaPorFiltro(final MetodoPagoEntidad filtro, final List<Object> parametros) {
        final var consulta = new StringBuilder(MetodoPagoSql.CONSULTAR_POR_FILTRO);
        final var condiciones = new ArrayList<String>();

        if (!UtilUUID.esUUIDPorDefecto(filtro.getId())) {
            condiciones.add(MetodoPagoSql.FILTRO_ID);
            parametros.add(filtro.getId());
        }
        if (!UtilTexto.estaVacia(filtro.getNombre())) {
            condiciones.add(MetodoPagoSql.FILTRO_NOMBRE);
            parametros.add(filtro.getNombre());
        }

        if (!condiciones.isEmpty()) {
            consulta.append(ClausulasSql.DONDE).append(String.join(ClausulasSql.Y, condiciones));
        }
        return consulta.toString();
    }
}
