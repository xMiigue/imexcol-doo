package co.edu.uco.imexcol.datos.dao.impl.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.ConexionSql;
import co.edu.uco.imexcol.datos.dao.LineaPedidoDAO;
import co.edu.uco.imexcol.datos.dao.impl.mapper.LineaPedidoMapper;
import co.edu.uco.imexcol.datos.dao.impl.sql.ClausulasSql;
import co.edu.uco.imexcol.datos.dao.impl.sql.LineaPedidoSql;
import co.edu.uco.imexcol.entidad.LineaPedidoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilConexionSql;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class LineaPedidoSqlServerDAO extends ConexionSql implements LineaPedidoDAO {

    private static final int POSICION_ID = 1;
    private static final int POSICION_PEDIDO = 2;
    private static final int POSICION_PRODUCTO = 3;
    private static final int POSICION_CANTIDAD = 4;
    private static final int POSICION_PRECIO_UNITARIO = 5;
    private static final int POSICION_SUBTOTAL = 6;
    private static final int POSICION_ACTUALIZAR_PEDIDO = 1;
    private static final int POSICION_ACTUALIZAR_PRODUCTO = 2;
    private static final int POSICION_ACTUALIZAR_CANTIDAD = 3;
    private static final int POSICION_ACTUALIZAR_PRECIO_UNITARIO = 4;
    private static final int POSICION_ACTUALIZAR_SUBTOTAL = 5;
    private static final int POSICION_ACTUALIZAR_ID = 6;
    private static final int POSICION_ID_ELIMINAR = 1;

    public LineaPedidoSqlServerDAO(final Connection conexion) {
        super(conexion);
    }

    @Override
    public void crear(final LineaPedidoEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new LineaPedidoEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(LineaPedidoSql.CREAR)) {
            sentencia.setObject(POSICION_ID, entidadSegura.getId());
            sentencia.setObject(POSICION_PEDIDO, entidadSegura.getPedido().getId());
            sentencia.setObject(POSICION_PRODUCTO, entidadSegura.getProducto().getId());
            sentencia.setInt(POSICION_CANTIDAD, entidadSegura.getCantidad());
            sentencia.setDouble(POSICION_PRECIO_UNITARIO, entidadSegura.getPrecioUnitario());
            sentencia.setDouble(POSICION_SUBTOTAL, entidadSegura.getSubtotal());
            sentencia.executeUpdate();
        } catch (final SQLException excepcion) {
            throw envolverErrorDao(excepcion, MensajesEnum.ERROR_USUARIO_DAO_CREAR_GENERICO,
                    MensajesEnum.ERROR_TECNICO_DAO_CREAR_GENERICO);
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    @Override
    public void actualizar(final LineaPedidoEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new LineaPedidoEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(LineaPedidoSql.ACTUALIZAR)) {
            sentencia.setObject(POSICION_ACTUALIZAR_PEDIDO, entidadSegura.getPedido().getId());
            sentencia.setObject(POSICION_ACTUALIZAR_PRODUCTO, entidadSegura.getProducto().getId());
            sentencia.setInt(POSICION_ACTUALIZAR_CANTIDAD, entidadSegura.getCantidad());
            sentencia.setDouble(POSICION_ACTUALIZAR_PRECIO_UNITARIO, entidadSegura.getPrecioUnitario());
            sentencia.setDouble(POSICION_ACTUALIZAR_SUBTOTAL, entidadSegura.getSubtotal());
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

        try (var sentencia = obtenerConexion().prepareStatement(LineaPedidoSql.ELIMINAR)) {
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
    public LineaPedidoEntidad consultarPorId(final UUID id) {
        final var filtro = new LineaPedidoEntidad(UtilUUID.obtenerValorDefecto(id));
        return consultarPorFiltro(filtro).stream().findFirst().orElse(new LineaPedidoEntidad());
    }

    @Override
    public List<LineaPedidoEntidad> consultarTodos() {
        return consultarPorFiltro(new LineaPedidoEntidad());
    }

    @Override
    public List<LineaPedidoEntidad> consultarPorFiltro(final LineaPedidoEntidad filtro) {
        final var entidadFiltro = UtilObjeto.obtenerValorDefecto(filtro, new LineaPedidoEntidad());
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

    private String construirConsultaPorFiltro(final LineaPedidoEntidad filtro, final List<Object> parametros) {
        final var consulta = new StringBuilder(LineaPedidoSql.CONSULTAR_POR_FILTRO);
        final var condiciones = new ArrayList<String>();

        if (!UtilUUID.esUUIDPorDefecto(filtro.getId())) {
            condiciones.add(LineaPedidoSql.FILTRO_ID);
            parametros.add(filtro.getId());
        }
        if (!UtilUUID.esUUIDPorDefecto(filtro.getPedido().getId())) {
            condiciones.add(LineaPedidoSql.FILTRO_PEDIDO);
            parametros.add(filtro.getPedido().getId());
        }
        if (!UtilUUID.esUUIDPorDefecto(filtro.getProducto().getId())) {
            condiciones.add(LineaPedidoSql.FILTRO_PRODUCTO);
            parametros.add(filtro.getProducto().getId());
        }

        if (!condiciones.isEmpty()) {
            consulta.append(ClausulasSql.DONDE).append(String.join(ClausulasSql.Y, condiciones));
        }
        return consulta.toString();
    }

    private List<LineaPedidoEntidad> ejecutarConsultaPorFiltro(final PreparedStatement sentencia) {
        final var resultados = new ArrayList<LineaPedidoEntidad>();
        try (var resultSet = sentencia.executeQuery()) {
            while (resultSet.next()) {
                resultados.add(LineaPedidoMapper.mapear(resultSet));
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
