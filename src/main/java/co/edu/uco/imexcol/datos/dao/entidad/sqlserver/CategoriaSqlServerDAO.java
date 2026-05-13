package co.edu.uco.imexcol.datos.dao.entidad.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.entidad.CategoriaDAO;
import co.edu.uco.imexcol.datos.dao.entidad.ConexionSql;
import co.edu.uco.imexcol.datos.dao.entidad.mapper.CategoriaMapper;
import co.edu.uco.imexcol.datos.dao.entidad.sql.CategoriaSql;
import co.edu.uco.imexcol.entidad.CategoriaEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilConexionSql;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class CategoriaSqlServerDAO extends ConexionSql implements CategoriaDAO {

    public CategoriaSqlServerDAO(final Connection conexion) {
        super(conexion);
    }

    @Override
    public void acceder(final CategoriaEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new CategoriaEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(CategoriaSql.ACCEDER)) {
            sentencia.setObject(1, entidadSegura.getId());
            sentencia.setString(2, entidadSegura.getNombre());
            sentencia.setString(3, entidadSegura.getDescripcion());
            sentencia.setBoolean(4, entidadSegura.isEstado());
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
            sentencia.setString(1, entidadSegura.getNombre());
            sentencia.setString(2, entidadSegura.getDescripcion());
            sentencia.setBoolean(3, entidadSegura.isEstado());
            sentencia.setObject(4, entidadSegura.getId());
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
            sentencia.setObject(1, UtilUUID.obtenerValorDefecto(id));
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

        try (var sentencia = obtenerConexion().prepareStatement(consulta)) {
            for (int indice = 0; indice < parametros.size(); indice++) {
                sentencia.setObject(indice + 1, parametros.get(indice));
            }
            return ejecutarConsultaPorFiltro(sentencia);
        } catch (final SQLException excepcion) {
            throw envolverErrorDao(excepcion, MensajesEnum.ERROR_USUARIO_DAO_CONSULTAR_GENERICO,
                    MensajesEnum.ERROR_TECNICO_DAO_CONSULTAR_GENERICO);
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    private String construirConsultaPorFiltro(final CategoriaEntidad filtro, final List<Object> parametros) {
        final var consulta = new StringBuilder(CategoriaSql.CONSULTAR_POR_FILTRO);
        final var condiciones = new ArrayList<String>();

        if (!UtilUUID.esUUIDPorDefecto(filtro.getId())) {
            condiciones.add("c.id = ?");
            parametros.add(filtro.getId());
        }
        if (!UtilTexto.estaVacia(filtro.getNombre())) {
            condiciones.add("c.nombre = ?");
            parametros.add(filtro.getNombre());
        }

        if (!condiciones.isEmpty()) {
            consulta.append(" WHERE ").append(String.join(" AND ", condiciones));
        }
        return consulta.toString();
    }

    private List<CategoriaEntidad> ejecutarConsultaPorFiltro(final PreparedStatement sentencia) {
        final var resultados = new ArrayList<CategoriaEntidad>();
        try (var resultSet = sentencia.executeQuery()) {
            while (resultSet.next()) {
                resultados.add(CategoriaMapper.mapear(resultSet));
            }
        } catch (final SQLException excepcion) {
            throw envolverErrorDao(excepcion, MensajesEnum.ERROR_USUARIO_DAO_CONSULTAR_GENERICO,
                    MensajesEnum.ERROR_TECNICO_DAO_CONSULTAR_GENERICO);
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
        return resultados;
    }

    private static ImexcolException envolverErrorDao(final Exception excepcion,
            final MensajesEnum usuarioMsg, final MensajesEnum tecnicoMsg) {
        return ImexcolException.crear(excepcion, usuarioMsg.getContenido(), tecnicoMsg.getContenido(), Lugar.DATOS);
    }

    private static ImexcolException envolverErrorInesperado(final Exception excepcion) {
        return ImexcolException.crear(excepcion,
                MensajesEnum.ERROR_USUARIO_DAO_OPERACION_INESPERADA.getContenido(),
                MensajesEnum.ERROR_TECNICO_DAO_OPERACION_INESPERADA.getContenido(),
                Lugar.DATOS);
    }
}
