package co.edu.uco.imexcol.datos.dao.impl.sqlserver;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.ConexionSql;
import co.edu.uco.imexcol.datos.dao.EnvioDAO;
import co.edu.uco.imexcol.datos.dao.impl.mapper.EnvioMapper;
import co.edu.uco.imexcol.datos.dao.impl.sql.EnvioSql;
import co.edu.uco.imexcol.entidad.EnvioEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilConexionSql;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class EnvioSqlServerDAO extends ConexionSql implements EnvioDAO {

    public EnvioSqlServerDAO(final Connection conexion) {
        super(conexion);
    }

    @Override
    public void acceder(final EnvioEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new EnvioEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(EnvioSql.ACCEDER)) {
            sentencia.setObject(1, entidadSegura.getId());
            sentencia.setObject(2, entidadSegura.getPedido().getId());
            sentencia.setDate(3, Date.valueOf(UtilFecha.obtenerValorDefecto(entidadSegura.getFechaEnvio())));
            sentencia.setDate(4, Date.valueOf(UtilFecha.obtenerValorDefecto(entidadSegura.getFechaEntrega())));
            sentencia.setString(5, entidadSegura.getTransportadora());
            sentencia.setString(6, entidadSegura.getNumeroGuia());
            sentencia.setString(7, entidadSegura.getEstado());
            sentencia.executeUpdate();
        } catch (final SQLException excepcion) {
            throw envolverErrorDao(excepcion, MensajesEnum.ERROR_USUARIO_DAO_CREAR_GENERICO,
                    MensajesEnum.ERROR_TECNICO_DAO_CREAR_GENERICO);
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    @Override
    public void actualizar(final EnvioEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new EnvioEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(EnvioSql.ACTUALIZAR)) {
            sentencia.setObject(1, entidadSegura.getPedido().getId());
            sentencia.setDate(2, Date.valueOf(UtilFecha.obtenerValorDefecto(entidadSegura.getFechaEnvio())));
            sentencia.setDate(3, Date.valueOf(UtilFecha.obtenerValorDefecto(entidadSegura.getFechaEntrega())));
            sentencia.setString(4, entidadSegura.getTransportadora());
            sentencia.setString(5, entidadSegura.getNumeroGuia());
            sentencia.setString(6, entidadSegura.getEstado());
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

        try (var sentencia = obtenerConexion().prepareStatement(EnvioSql.ELIMINAR)) {
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
    public EnvioEntidad consultarPorId(final UUID id) {
        final var filtro = new EnvioEntidad(UtilUUID.obtenerValorDefecto(id));
        return consultarPorFiltro(filtro).stream().findFirst().orElse(new EnvioEntidad());
    }

    @Override
    public List<EnvioEntidad> consultarTodos() {
        return consultarPorFiltro(new EnvioEntidad());
    }

    @Override
    public List<EnvioEntidad> consultarPorFiltro(final EnvioEntidad filtro) {
        final var entidadFiltro = UtilObjeto.obtenerValorDefecto(filtro, new EnvioEntidad());
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

    private String construirConsultaPorFiltro(final EnvioEntidad filtro, final List<Object> parametros) {
        final var consulta = new StringBuilder(EnvioSql.CONSULTAR_POR_FILTRO);
        final var condiciones = new ArrayList<String>();

        if (!UtilUUID.esUUIDPorDefecto(filtro.getId())) {
            condiciones.add("e.id = ?");
            parametros.add(filtro.getId());
        }
        if (!UtilUUID.esUUIDPorDefecto(filtro.getPedido().getId())) {
            condiciones.add("e.pedido_id = ?");
            parametros.add(filtro.getPedido().getId());
        }
        if (!UtilTexto.estaVacia(filtro.getNumeroGuia())) {
            condiciones.add("e.numero_guia = ?");
            parametros.add(filtro.getNumeroGuia());
        }
        if (!UtilTexto.estaVacia(filtro.getEstado())) {
            condiciones.add("e.estado = ?");
            parametros.add(filtro.getEstado());
        }

        if (!condiciones.isEmpty()) {
            consulta.append(" WHERE ").append(String.join(" AND ", condiciones));
        }
        return consulta.toString();
    }

    private List<EnvioEntidad> ejecutarConsultaPorFiltro(final PreparedStatement sentencia) {
        final var resultados = new ArrayList<EnvioEntidad>();
        try (var resultSet = sentencia.executeQuery()) {
            while (resultSet.next()) {
                resultados.add(EnvioMapper.mapear(resultSet));
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
