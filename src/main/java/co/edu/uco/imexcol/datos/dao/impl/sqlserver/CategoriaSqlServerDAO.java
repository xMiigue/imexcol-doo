package co.edu.uco.imexcol.datos.dao.impl.sqlserver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.CategoriaDAO;
import co.edu.uco.imexcol.datos.dao.ConexionSql;
import co.edu.uco.imexcol.datos.dao.impl.mapper.CategoriaMapper;
import co.edu.uco.imexcol.datos.dao.impl.sql.ClausulasSql;
import co.edu.uco.imexcol.datos.dao.impl.sql.CategoriaSql;
import co.edu.uco.imexcol.entidad.CategoriaEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilConexionSql;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class CategoriaSqlServerDAO extends ConexionSql implements CategoriaDAO {

    private static final int POSICION_ID = 1;
    private static final int POSICION_NOMBRE = 2;
    private static final int POSICION_DESCRIPCION = 3;
    private static final int POSICION_ESTADO = 4;
    private static final int POSICION_ACTUALIZAR_NOMBRE = 1;
    private static final int POSICION_ACTUALIZAR_DESCRIPCION = 2;
    private static final int POSICION_ACTUALIZAR_ESTADO = 3;
    private static final int POSICION_ACTUALIZAR_ID = 4;
    private static final int POSICION_ID_ELIMINAR = 1;

    public CategoriaSqlServerDAO(final Connection conexion) {
        super(conexion);
    }

    @Override
    public void crear(final CategoriaEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new CategoriaEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(CategoriaSql.CREAR)) {
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
    public void actualizar(final CategoriaEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new CategoriaEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(CategoriaSql.ACTUALIZAR)) {
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

        try (var sentencia = obtenerConexion().prepareStatement(CategoriaSql.ELIMINAR)) {
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
    public CategoriaEntidad consultarPorId(final UUID id) {
        final var filtro = new CategoriaEntidad(UtilUUID.obtenerValorDefecto(id));
        return consultarPorFiltro(filtro).stream().findFirst().orElse(new CategoriaEntidad());
    }

    @Override
    public List<CategoriaEntidad> consultarTodos() {
        return consultarPorFiltro(new CategoriaEntidad());
    }

    @Override
    public List<CategoriaEntidad> consultarPorFiltro(final CategoriaEntidad filtro) {
        final var entidadFiltro = UtilObjeto.obtenerValorDefecto(filtro, new CategoriaEntidad());
        final var parametros = new ArrayList<Object>();
        final var consulta = construirConsultaPorFiltro(entidadFiltro, parametros);
        return consultarConParametros(consulta, parametros, CategoriaMapper::mapear);
    }

    private String construirConsultaPorFiltro(final CategoriaEntidad filtro, final List<Object> parametros) {
        final var consulta = new StringBuilder(CategoriaSql.CONSULTAR_POR_FILTRO);
        final var condiciones = new ArrayList<String>();

        if (!UtilUUID.esUUIDPorDefecto(filtro.getId())) {
            condiciones.add(CategoriaSql.FILTRO_ID);
            parametros.add(filtro.getId());
        }
        if (!UtilTexto.estaVacia(filtro.getNombre())) {
            condiciones.add(CategoriaSql.FILTRO_NOMBRE);
            parametros.add(filtro.getNombre());
        }

        if (!condiciones.isEmpty()) {
            consulta.append(ClausulasSql.DONDE).append(String.join(ClausulasSql.Y, condiciones));
        }
        return consulta.toString();
    }
}
