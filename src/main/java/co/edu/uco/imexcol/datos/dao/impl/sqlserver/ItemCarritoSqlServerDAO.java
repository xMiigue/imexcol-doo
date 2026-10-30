package co.edu.uco.imexcol.datos.dao.impl.sqlserver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.ConexionSql;
import co.edu.uco.imexcol.datos.dao.ItemCarritoDAO;
import co.edu.uco.imexcol.datos.dao.impl.mapper.ItemCarritoMapper;
import co.edu.uco.imexcol.datos.dao.impl.sql.ClausulasSql;
import co.edu.uco.imexcol.datos.dao.impl.sql.ItemCarritoSql;
import co.edu.uco.imexcol.entidad.ItemCarritoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilConexionSql;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class ItemCarritoSqlServerDAO extends ConexionSql implements ItemCarritoDAO {

    private static final int POSICION_ID = 1;
    private static final int POSICION_CARRITO = 2;
    private static final int POSICION_PRODUCTO = 3;
    private static final int POSICION_CANTIDAD = 4;
    private static final int POSICION_PRECIO_UNITARIO = 5;
    private static final int POSICION_ACTUALIZAR_CARRITO = 1;
    private static final int POSICION_ACTUALIZAR_PRODUCTO = 2;
    private static final int POSICION_ACTUALIZAR_CANTIDAD = 3;
    private static final int POSICION_ACTUALIZAR_PRECIO_UNITARIO = 4;
    private static final int POSICION_ACTUALIZAR_ID = 5;
    private static final int POSICION_ID_ELIMINAR = 1;
    private static final int POSICION_ID_CARRITO_ELIMINAR = 1;

    public ItemCarritoSqlServerDAO(final Connection conexion) {
        super(conexion);
    }

    @Override
    public void crear(final ItemCarritoEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new ItemCarritoEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(ItemCarritoSql.CREAR)) {
            sentencia.setObject(POSICION_ID, entidadSegura.getId());
            sentencia.setObject(POSICION_CARRITO, entidadSegura.getCarrito().getId());
            sentencia.setObject(POSICION_PRODUCTO, entidadSegura.getProducto().getId());
            sentencia.setInt(POSICION_CANTIDAD, entidadSegura.getCantidad());
            sentencia.setDouble(POSICION_PRECIO_UNITARIO, entidadSegura.getPrecioUnitario());
            sentencia.executeUpdate();
        } catch (final SQLException excepcion) {
            throw envolverErrorDao(excepcion, MensajesEnum.ERROR_USUARIO_DAO_CREAR_GENERICO,
                    MensajesEnum.ERROR_TECNICO_DAO_CREAR_GENERICO);
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    @Override
    public void actualizar(final ItemCarritoEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new ItemCarritoEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(ItemCarritoSql.ACTUALIZAR)) {
            sentencia.setObject(POSICION_ACTUALIZAR_CARRITO, entidadSegura.getCarrito().getId());
            sentencia.setObject(POSICION_ACTUALIZAR_PRODUCTO, entidadSegura.getProducto().getId());
            sentencia.setInt(POSICION_ACTUALIZAR_CANTIDAD, entidadSegura.getCantidad());
            sentencia.setDouble(POSICION_ACTUALIZAR_PRECIO_UNITARIO, entidadSegura.getPrecioUnitario());
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

        try (var sentencia = obtenerConexion().prepareStatement(ItemCarritoSql.ELIMINAR)) {
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
    public void eliminarPorCarrito(final UUID idCarrito) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());

        try (var sentencia = obtenerConexion().prepareStatement(ItemCarritoSql.ELIMINAR_POR_CARRITO)) {
            sentencia.setObject(POSICION_ID_CARRITO_ELIMINAR, UtilUUID.obtenerValorDefecto(idCarrito));
            sentencia.executeUpdate();
        } catch (final SQLException excepcion) {
            throw envolverErrorDao(excepcion, MensajesEnum.ERROR_USUARIO_DAO_ELIMINAR_GENERICO,
                    MensajesEnum.ERROR_TECNICO_DAO_ELIMINAR_GENERICO);
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    @Override
    public ItemCarritoEntidad consultarPorId(final UUID id) {
        final var filtro = new ItemCarritoEntidad(UtilUUID.obtenerValorDefecto(id));
        return consultarPorFiltro(filtro).stream().findFirst().orElse(new ItemCarritoEntidad());
    }

    @Override
    public List<ItemCarritoEntidad> consultarTodos() {
        return consultarPorFiltro(new ItemCarritoEntidad());
    }

    @Override
    public List<ItemCarritoEntidad> consultarPorFiltro(final ItemCarritoEntidad filtro) {
        final var entidadFiltro = UtilObjeto.obtenerValorDefecto(filtro, new ItemCarritoEntidad());
        final var parametros = new ArrayList<Object>();
        final var consulta = construirConsultaPorFiltro(entidadFiltro, parametros);
        return consultarConParametros(consulta, parametros, ItemCarritoMapper::mapear);
    }

    private String construirConsultaPorFiltro(final ItemCarritoEntidad filtro, final List<Object> parametros) {
        final var consulta = new StringBuilder(ItemCarritoSql.CONSULTAR_POR_FILTRO);
        final var condiciones = new ArrayList<String>();

        if (!UtilUUID.esUUIDPorDefecto(filtro.getId())) {
            condiciones.add(ItemCarritoSql.FILTRO_ID);
            parametros.add(filtro.getId());
        }
        if (!UtilUUID.esUUIDPorDefecto(filtro.getCarrito().getId())) {
            condiciones.add(ItemCarritoSql.FILTRO_CARRITO);
            parametros.add(filtro.getCarrito().getId());
        }
        if (!UtilUUID.esUUIDPorDefecto(filtro.getProducto().getId())) {
            condiciones.add(ItemCarritoSql.FILTRO_PRODUCTO);
            parametros.add(filtro.getProducto().getId());
        }

        if (!condiciones.isEmpty()) {
            consulta.append(ClausulasSql.DONDE).append(String.join(ClausulasSql.Y, condiciones));
        }
        return consulta.toString();
    }
}
