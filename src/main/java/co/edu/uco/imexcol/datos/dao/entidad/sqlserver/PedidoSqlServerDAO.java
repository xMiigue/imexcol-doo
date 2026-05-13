package co.edu.uco.imexcol.datos.dao.entidad.sqlserver;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.entidad.ConexionSql;
import co.edu.uco.imexcol.datos.dao.entidad.PedidoDAO;
import co.edu.uco.imexcol.datos.dao.entidad.mapper.PedidoMapper;
import co.edu.uco.imexcol.datos.dao.entidad.sql.PedidoSql;
import co.edu.uco.imexcol.entidad.PedidoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilConexionSql;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class PedidoSqlServerDAO extends ConexionSql implements PedidoDAO {

    public PedidoSqlServerDAO(final Connection conexion) {
        super(conexion);
    }

    @Override
    public void acceder(final PedidoEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new PedidoEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(PedidoSql.ACCEDER)) {
            sentencia.setObject(1, entidadSegura.getId());
            sentencia.setObject(2, entidadSegura.getCliente().getId());
            sentencia.setDate(3, Date.valueOf(UtilFecha.obtenerValorDefecto(entidadSegura.getFechaPedido())));
            sentencia.setDouble(4, entidadSegura.getTotal());
            sentencia.setString(5, entidadSegura.getEstado());
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
            sentencia.setObject(1, entidadSegura.getCliente().getId());
            sentencia.setDate(2, Date.valueOf(UtilFecha.obtenerValorDefecto(entidadSegura.getFechaPedido())));
            sentencia.setDouble(3, entidadSegura.getTotal());
            sentencia.setString(4, entidadSegura.getEstado());
            sentencia.setObject(5, entidadSegura.getId());
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

    private String construirConsultaPorFiltro(final PedidoEntidad filtro, final List<Object> parametros) {
        final var consulta = new StringBuilder(PedidoSql.CONSULTAR_POR_FILTRO);
        final var condiciones = new ArrayList<String>();

        if (!UtilUUID.esUUIDPorDefecto(filtro.getId())) {
            condiciones.add("p.id = ?");
            parametros.add(filtro.getId());
        }
        if (!UtilUUID.esUUIDPorDefecto(filtro.getCliente().getId())) {
            condiciones.add("p.cliente_id = ?");
            parametros.add(filtro.getCliente().getId());
        }
        if (!UtilTexto.estaVacia(filtro.getEstado())) {
            condiciones.add("p.estado = ?");
            parametros.add(filtro.getEstado());
        }

        if (!condiciones.isEmpty()) {
            consulta.append(" WHERE ").append(String.join(" AND ", condiciones));
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
