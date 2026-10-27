package co.edu.uco.imexcol.datos.dao.impl.sqlserver;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.ConexionSql;
import co.edu.uco.imexcol.datos.dao.PagoDAO;
import co.edu.uco.imexcol.datos.dao.impl.mapper.PagoMapper;
import co.edu.uco.imexcol.datos.dao.impl.sql.ClausulasSql;
import co.edu.uco.imexcol.datos.dao.impl.sql.PagoSql;
import co.edu.uco.imexcol.entidad.PagoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilConexionSql;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class PagoSqlServerDAO extends ConexionSql implements PagoDAO {

    private static final int POSICION_ID = 1;
    private static final int POSICION_PEDIDO = 2;
    private static final int POSICION_METODO_PAGO = 3;
    private static final int POSICION_MONTO = 4;
    private static final int POSICION_FECHA_PAGO = 5;
    private static final int POSICION_ESTADO = 6;
    private static final int POSICION_ACTUALIZAR_PEDIDO = 1;
    private static final int POSICION_ACTUALIZAR_METODO_PAGO = 2;
    private static final int POSICION_ACTUALIZAR_MONTO = 3;
    private static final int POSICION_ACTUALIZAR_FECHA_PAGO = 4;
    private static final int POSICION_ACTUALIZAR_ESTADO = 5;
    private static final int POSICION_ACTUALIZAR_ID = 6;
    private static final int POSICION_ID_ELIMINAR = 1;

    public PagoSqlServerDAO(final Connection conexion) {
        super(conexion);
    }

    @Override
    public void crear(final PagoEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new PagoEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(PagoSql.CREAR)) {
            sentencia.setObject(POSICION_ID, entidadSegura.getId());
            sentencia.setObject(POSICION_PEDIDO, entidadSegura.getPedido().getId());
            sentencia.setObject(POSICION_METODO_PAGO, entidadSegura.getMetodoPago().getId());
            sentencia.setDouble(POSICION_MONTO, entidadSegura.getMonto());
            sentencia.setDate(POSICION_FECHA_PAGO, Date.valueOf(UtilFecha.obtenerValorDefecto(entidadSegura.getFechaPago())));
            sentencia.setString(POSICION_ESTADO, entidadSegura.getEstado());
            sentencia.executeUpdate();
        } catch (final SQLException excepcion) {
            throw envolverErrorDao(excepcion, MensajesEnum.ERROR_USUARIO_DAO_CREAR_GENERICO,
                    MensajesEnum.ERROR_TECNICO_DAO_CREAR_GENERICO);
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    @Override
    public void actualizar(final PagoEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new PagoEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(PagoSql.ACTUALIZAR)) {
            sentencia.setObject(POSICION_ACTUALIZAR_PEDIDO, entidadSegura.getPedido().getId());
            sentencia.setObject(POSICION_ACTUALIZAR_METODO_PAGO, entidadSegura.getMetodoPago().getId());
            sentencia.setDouble(POSICION_ACTUALIZAR_MONTO, entidadSegura.getMonto());
            sentencia.setDate(POSICION_ACTUALIZAR_FECHA_PAGO, Date.valueOf(UtilFecha.obtenerValorDefecto(entidadSegura.getFechaPago())));
            sentencia.setString(POSICION_ACTUALIZAR_ESTADO, entidadSegura.getEstado());
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

        try (var sentencia = obtenerConexion().prepareStatement(PagoSql.ELIMINAR)) {
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
    public PagoEntidad consultarPorId(final UUID id) {
        final var filtro = new PagoEntidad(UtilUUID.obtenerValorDefecto(id));
        return consultarPorFiltro(filtro).stream().findFirst().orElse(new PagoEntidad());
    }

    @Override
    public List<PagoEntidad> consultarTodos() {
        return consultarPorFiltro(new PagoEntidad());
    }

    @Override
    public List<PagoEntidad> consultarPorFiltro(final PagoEntidad filtro) {
        final var entidadFiltro = UtilObjeto.obtenerValorDefecto(filtro, new PagoEntidad());
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

    private String construirConsultaPorFiltro(final PagoEntidad filtro, final List<Object> parametros) {
        final var consulta = new StringBuilder(PagoSql.CONSULTAR_POR_FILTRO);
        final var condiciones = new ArrayList<String>();

        if (!UtilUUID.esUUIDPorDefecto(filtro.getId())) {
            condiciones.add(PagoSql.FILTRO_ID);
            parametros.add(filtro.getId());
        }
        if (!UtilUUID.esUUIDPorDefecto(filtro.getPedido().getId())) {
            condiciones.add(PagoSql.FILTRO_PEDIDO);
            parametros.add(filtro.getPedido().getId());
        }
        if (!UtilUUID.esUUIDPorDefecto(filtro.getMetodoPago().getId())) {
            condiciones.add(PagoSql.FILTRO_METODO_PAGO);
            parametros.add(filtro.getMetodoPago().getId());
        }
        if (!UtilTexto.estaVacia(filtro.getEstado())) {
            condiciones.add(PagoSql.FILTRO_ESTADO);
            parametros.add(filtro.getEstado());
        }

        if (!condiciones.isEmpty()) {
            consulta.append(ClausulasSql.DONDE).append(String.join(ClausulasSql.Y, condiciones));
        }
        return consulta.toString();
    }

    private List<PagoEntidad> ejecutarConsultaPorFiltro(final PreparedStatement sentencia) {
        final var resultados = new ArrayList<PagoEntidad>();
        try (var resultSet = sentencia.executeQuery()) {
            while (resultSet.next()) {
                resultados.add(PagoMapper.mapear(resultSet));
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
