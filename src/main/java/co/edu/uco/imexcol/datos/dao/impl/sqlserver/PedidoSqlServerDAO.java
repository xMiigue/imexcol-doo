package co.edu.uco.imexcol.datos.dao.impl.sqlserver;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.ConexionSql;
import co.edu.uco.imexcol.datos.dao.PedidoDAO;
import co.edu.uco.imexcol.datos.dao.impl.mapper.PedidoMapper;
import co.edu.uco.imexcol.datos.dao.impl.sql.ClausulasSql;
import co.edu.uco.imexcol.datos.dao.impl.sql.PedidoSql;
import co.edu.uco.imexcol.entidad.PedidoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilConexionSql;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class PedidoSqlServerDAO extends ConexionSql implements PedidoDAO {

    private static final int POSICION_ID = 1;
    private static final int POSICION_CLIENTE = 2;
    private static final int POSICION_FECHA_PEDIDO = 3;
    private static final int POSICION_TOTAL = 4;
    private static final int POSICION_ESTADO = 5;
    private static final int POSICION_DIRECCION = 6;
    private static final int POSICION_ACTUALIZAR_CLIENTE = 1;
    private static final int POSICION_ACTUALIZAR_FECHA_PEDIDO = 2;
    private static final int POSICION_ACTUALIZAR_TOTAL = 3;
    private static final int POSICION_ACTUALIZAR_ESTADO = 4;
    private static final int POSICION_ACTUALIZAR_DIRECCION = 5;
    private static final int POSICION_ACTUALIZAR_ID = 6;
    private static final int POSICION_ID_ELIMINAR = 1;

    public PedidoSqlServerDAO(final Connection conexion) {
        super(conexion);
    }

    @Override
    public void crear(final PedidoEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new PedidoEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(PedidoSql.CREAR)) {
            sentencia.setObject(POSICION_ID, entidadSegura.getId());
            sentencia.setObject(POSICION_CLIENTE, entidadSegura.getCliente().getId());
            sentencia.setDate(POSICION_FECHA_PEDIDO, Date.valueOf(UtilFecha.obtenerValorDefecto(entidadSegura.getFechaPedido())));
            sentencia.setDouble(POSICION_TOTAL, entidadSegura.getTotal());
            sentencia.setString(POSICION_ESTADO, entidadSegura.getEstado());
            asignarDireccion(sentencia, POSICION_DIRECCION, entidadSegura.getDireccion().getId());
            sentencia.executeUpdate();
        } catch (final SQLException excepcion) {
            throw envolverErrorDao(excepcion, MensajesEnum.ERROR_USUARIO_DAO_CREAR_GENERICO,
                    MensajesEnum.ERROR_TECNICO_DAO_CREAR_GENERICO);
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    @Override
    public void actualizar(final PedidoEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new PedidoEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(PedidoSql.ACTUALIZAR)) {
            sentencia.setObject(POSICION_ACTUALIZAR_CLIENTE, entidadSegura.getCliente().getId());
            sentencia.setDate(POSICION_ACTUALIZAR_FECHA_PEDIDO, Date.valueOf(UtilFecha.obtenerValorDefecto(entidadSegura.getFechaPedido())));
            sentencia.setDouble(POSICION_ACTUALIZAR_TOTAL, entidadSegura.getTotal());
            sentencia.setString(POSICION_ACTUALIZAR_ESTADO, entidadSegura.getEstado());
            asignarDireccion(sentencia, POSICION_ACTUALIZAR_DIRECCION, entidadSegura.getDireccion().getId());
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

        try (var sentencia = obtenerConexion().prepareStatement(PedidoSql.ELIMINAR)) {
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
    public PedidoEntidad consultarPorId(final UUID id) {
        final var filtro = new PedidoEntidad(UtilUUID.obtenerValorDefecto(id));
        return consultarPorFiltro(filtro).stream().findFirst().orElse(new PedidoEntidad());
    }

    @Override
    public List<PedidoEntidad> consultarTodos() {
        return consultarPorFiltro(new PedidoEntidad());
    }

    @Override
    public List<PedidoEntidad> consultarPorFiltro(final PedidoEntidad filtro) {
        final var entidadFiltro = UtilObjeto.obtenerValorDefecto(filtro, new PedidoEntidad());
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

    private static void asignarDireccion(final PreparedStatement sentencia, final int posicion,
            final UUID idDireccion) throws SQLException {
        if (UtilUUID.esUUIDPorDefecto(idDireccion)) {
            sentencia.setNull(posicion, Types.CHAR);
        } else {
            sentencia.setObject(posicion, idDireccion);
        }
    }

    private String construirConsultaPorFiltro(final PedidoEntidad filtro, final List<Object> parametros) {
        final var consulta = new StringBuilder(PedidoSql.CONSULTAR_POR_FILTRO);
        final var condiciones = new ArrayList<String>();

        if (!UtilUUID.esUUIDPorDefecto(filtro.getId())) {
            condiciones.add(PedidoSql.FILTRO_ID);
            parametros.add(filtro.getId());
        }
        if (!UtilUUID.esUUIDPorDefecto(filtro.getCliente().getId())) {
            condiciones.add(PedidoSql.FILTRO_CLIENTE);
            parametros.add(filtro.getCliente().getId());
        }
        if (!UtilTexto.estaVacia(filtro.getEstado())) {
            condiciones.add(PedidoSql.FILTRO_ESTADO);
            parametros.add(filtro.getEstado());
        }
        if (!UtilUUID.esUUIDPorDefecto(filtro.getDireccion().getId())) {
            condiciones.add(PedidoSql.FILTRO_DIRECCION);
            parametros.add(filtro.getDireccion().getId());
        }

        if (!condiciones.isEmpty()) {
            consulta.append(ClausulasSql.DONDE).append(String.join(ClausulasSql.Y, condiciones));
        }
        return consulta.toString();
    }

    private List<PedidoEntidad> ejecutarConsultaPorFiltro(final PreparedStatement sentencia) {
        final var resultados = new ArrayList<PedidoEntidad>();
        try (var resultSet = sentencia.executeQuery()) {
            while (resultSet.next()) {
                resultados.add(PedidoMapper.mapear(resultSet));
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
