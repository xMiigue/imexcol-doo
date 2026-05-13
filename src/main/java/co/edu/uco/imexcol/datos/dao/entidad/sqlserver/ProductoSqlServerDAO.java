package co.edu.uco.imexcol.datos.dao.entidad.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.entidad.ConexionSql;
import co.edu.uco.imexcol.datos.dao.entidad.ProductoDAO;
import co.edu.uco.imexcol.datos.dao.entidad.mapper.ProductoMapper;
import co.edu.uco.imexcol.datos.dao.entidad.sql.ProductoSql;
import co.edu.uco.imexcol.entidad.ProductoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilConexionSql;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class ProductoSqlServerDAO extends ConexionSql implements ProductoDAO {

    public ProductoSqlServerDAO(final Connection conexion) {
        super(conexion);
    }

    @Override
    public void acceder(final ProductoEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new ProductoEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(ProductoSql.ACCEDER)) {
            sentencia.setObject(1, entidadSegura.getId());
            sentencia.setObject(2, entidadSegura.getCategoria().getId());
            sentencia.setString(3, entidadSegura.getNombre());
            sentencia.setString(4, entidadSegura.getDescripcion());
            sentencia.setDouble(5, entidadSegura.getPrecio());
            sentencia.setInt(6, entidadSegura.getStock());
            sentencia.setBoolean(7, entidadSegura.isEstado());
            sentencia.executeUpdate();
        } catch (final SQLException excepcion) {
            throw envolverErrorDao(excepcion, MensajesEnum.ERROR_USUARIO_DAO_CREAR_GENERICO,
                    MensajesEnum.ERROR_TECNICO_DAO_CREAR_GENERICO);
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    @Override
    public void actualizar(final ProductoEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new ProductoEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(ProductoSql.ACTUALIZAR)) {
            sentencia.setObject(1, entidadSegura.getCategoria().getId());
            sentencia.setString(2, entidadSegura.getNombre());
            sentencia.setString(3, entidadSegura.getDescripcion());
            sentencia.setDouble(4, entidadSegura.getPrecio());
            sentencia.setInt(5, entidadSegura.getStock());
            sentencia.setBoolean(6, entidadSegura.isEstado());
            sentencia.setObject(7, entidadSegura.getId());
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

        try (var sentencia = obtenerConexion().prepareStatement(ProductoSql.ELIMINAR)) {
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
    public ProductoEntidad consultarPorId(final UUID id) {
        final var filtro = new ProductoEntidad(UtilUUID.obtenerValorDefecto(id));
        return consultarPorFiltro(filtro).stream().findFirst().orElse(new ProductoEntidad());
    }

    @Override
    public List<ProductoEntidad> consultarTodos() {
        return consultarPorFiltro(new ProductoEntidad());
    }

    @Override
    public List<ProductoEntidad> consultarPorFiltro(final ProductoEntidad filtro) {
        final var entidadFiltro = UtilObjeto.obtenerValorDefecto(filtro, new ProductoEntidad());
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

    private String construirConsultaPorFiltro(final ProductoEntidad filtro, final List<Object> parametros) {
        final var consulta = new StringBuilder(ProductoSql.CONSULTAR_POR_FILTRO);
        final var condiciones = new ArrayList<String>();

        if (!UtilUUID.esUUIDPorDefecto(filtro.getId())) {
            condiciones.add("p.id = ?");
            parametros.add(filtro.getId());
        }
        if (!UtilUUID.esUUIDPorDefecto(filtro.getCategoria().getId())) {
            condiciones.add("p.categoria_id = ?");
            parametros.add(filtro.getCategoria().getId());
        }
        if (!UtilTexto.estaVacia(filtro.getNombre())) {
            condiciones.add("p.nombre = ?");
            parametros.add(filtro.getNombre());
        }

        if (!condiciones.isEmpty()) {
            consulta.append(" WHERE ").append(String.join(" AND ", condiciones));
        }
        return consulta.toString();
    }

    private List<ProductoEntidad> ejecutarConsultaPorFiltro(final PreparedStatement sentencia) {
        final var resultados = new ArrayList<ProductoEntidad>();
        try (var resultSet = sentencia.executeQuery()) {
            while (resultSet.next()) {
                resultados.add(ProductoMapper.mapear(resultSet));
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
