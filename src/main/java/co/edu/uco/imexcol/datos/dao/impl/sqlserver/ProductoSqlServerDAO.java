package co.edu.uco.imexcol.datos.dao.impl.sqlserver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.ConexionSql;
import co.edu.uco.imexcol.datos.dao.ProductoDAO;
import co.edu.uco.imexcol.datos.dao.impl.mapper.ProductoMapper;
import co.edu.uco.imexcol.datos.dao.impl.sql.ClausulasSql;
import co.edu.uco.imexcol.datos.dao.impl.sql.ProductoSql;
import co.edu.uco.imexcol.entidad.ProductoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilConexionSql;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class ProductoSqlServerDAO extends ConexionSql implements ProductoDAO {

    private static final int POSICION_ID = 1;
    private static final int POSICION_CATEGORIA = 2;
    private static final int POSICION_NOMBRE = 3;
    private static final int POSICION_DESCRIPCION = 4;
    private static final int POSICION_PRECIO = 5;
    private static final int POSICION_STOCK = 6;
    private static final int POSICION_IMAGEN_URL = 7;
    private static final int POSICION_ESTADO = 8;
    private static final int POSICION_ACTUALIZAR_CATEGORIA = 1;
    private static final int POSICION_ACTUALIZAR_NOMBRE = 2;
    private static final int POSICION_ACTUALIZAR_DESCRIPCION = 3;
    private static final int POSICION_ACTUALIZAR_PRECIO = 4;
    private static final int POSICION_ACTUALIZAR_STOCK = 5;
    private static final int POSICION_ACTUALIZAR_IMAGEN_URL = 6;
    private static final int POSICION_ACTUALIZAR_ESTADO = 7;
    private static final int POSICION_ACTUALIZAR_ID = 8;
    private static final int POSICION_ID_ELIMINAR = 1;

    public ProductoSqlServerDAO(final Connection conexion) {
        super(conexion);
    }

    @Override
    public void crear(final ProductoEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new ProductoEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(ProductoSql.CREAR)) {
            sentencia.setObject(POSICION_ID, entidadSegura.getId());
            sentencia.setObject(POSICION_CATEGORIA, entidadSegura.getCategoria().getId());
            sentencia.setString(POSICION_NOMBRE, entidadSegura.getNombre());
            sentencia.setString(POSICION_DESCRIPCION, entidadSegura.getDescripcion());
            sentencia.setDouble(POSICION_PRECIO, entidadSegura.getPrecio());
            sentencia.setInt(POSICION_STOCK, entidadSegura.getStock());
            sentencia.setString(POSICION_IMAGEN_URL, entidadSegura.getImagenUrl());
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
    public void actualizar(final ProductoEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new ProductoEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(ProductoSql.ACTUALIZAR)) {
            sentencia.setObject(POSICION_ACTUALIZAR_CATEGORIA, entidadSegura.getCategoria().getId());
            sentencia.setString(POSICION_ACTUALIZAR_NOMBRE, entidadSegura.getNombre());
            sentencia.setString(POSICION_ACTUALIZAR_DESCRIPCION, entidadSegura.getDescripcion());
            sentencia.setDouble(POSICION_ACTUALIZAR_PRECIO, entidadSegura.getPrecio());
            sentencia.setInt(POSICION_ACTUALIZAR_STOCK, entidadSegura.getStock());
            sentencia.setString(POSICION_ACTUALIZAR_IMAGEN_URL, entidadSegura.getImagenUrl());
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

        try (var sentencia = obtenerConexion().prepareStatement(ProductoSql.ELIMINAR)) {
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
        return consultarConParametros(consulta, parametros, ProductoMapper::mapear);
    }

    private String construirConsultaPorFiltro(final ProductoEntidad filtro, final List<Object> parametros) {
        final var consulta = new StringBuilder(ProductoSql.CONSULTAR_POR_FILTRO);
        final var condiciones = new ArrayList<String>();

        if (!UtilUUID.esUUIDPorDefecto(filtro.getId())) {
            condiciones.add(ProductoSql.FILTRO_ID);
            parametros.add(filtro.getId());
        }
        if (!UtilUUID.esUUIDPorDefecto(filtro.getCategoria().getId())) {
            condiciones.add(ProductoSql.FILTRO_CATEGORIA);
            parametros.add(filtro.getCategoria().getId());
        }
        if (!UtilTexto.estaVacia(filtro.getNombre())) {
            condiciones.add(ProductoSql.FILTRO_NOMBRE);
            parametros.add(filtro.getNombre());
        }

        if (!condiciones.isEmpty()) {
            consulta.append(ClausulasSql.DONDE).append(String.join(ClausulasSql.Y, condiciones));
        }
        return consulta.toString();
    }
}
