package co.edu.uco.imexcol.datos.dao.impl.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.ConexionSql;
import co.edu.uco.imexcol.datos.dao.DireccionDAO;
import co.edu.uco.imexcol.datos.dao.impl.mapper.DireccionMapper;
import co.edu.uco.imexcol.datos.dao.impl.sql.DireccionSql;
import co.edu.uco.imexcol.entidad.DireccionEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilConexionSql;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class DireccionSqlServerDAO extends ConexionSql implements DireccionDAO {

    public DireccionSqlServerDAO(final Connection conexion) {
        super(conexion);
    }

    @Override
    public void acceder(final DireccionEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new DireccionEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(DireccionSql.ACCEDER)) {
            sentencia.setObject(1, entidadSegura.getId());
            sentencia.setObject(2, entidadSegura.getCliente().getId());
            sentencia.setString(3, entidadSegura.getCalle());
            sentencia.setString(4, entidadSegura.getCiudad());
            sentencia.setString(5, entidadSegura.getDepartamento());
            sentencia.setString(6, entidadSegura.getCodigoPostal());
            sentencia.setString(7, entidadSegura.getPais());
            sentencia.setBoolean(8, entidadSegura.isEsPrincipal());
            sentencia.executeUpdate();
        } catch (final SQLException excepcion) {
            throw envolverErrorDao(excepcion, MensajesEnum.ERROR_USUARIO_DAO_CREAR_GENERICO,
                    MensajesEnum.ERROR_TECNICO_DAO_CREAR_GENERICO);
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    @Override
    public void actualizar(final DireccionEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new DireccionEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(DireccionSql.ACTUALIZAR)) {
            sentencia.setObject(1, entidadSegura.getCliente().getId());
            sentencia.setString(2, entidadSegura.getCalle());
            sentencia.setString(3, entidadSegura.getCiudad());
            sentencia.setString(4, entidadSegura.getDepartamento());
            sentencia.setString(5, entidadSegura.getCodigoPostal());
            sentencia.setString(6, entidadSegura.getPais());
            sentencia.setBoolean(7, entidadSegura.isEsPrincipal());
            sentencia.setObject(8, entidadSegura.getId());
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

        try (var sentencia = obtenerConexion().prepareStatement(DireccionSql.ELIMINAR)) {
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
    public DireccionEntidad consultarPorId(final UUID id) {
        final var filtro = new DireccionEntidad(UtilUUID.obtenerValorDefecto(id));
        return consultarPorFiltro(filtro).stream().findFirst().orElse(new DireccionEntidad());
    }

    @Override
    public List<DireccionEntidad> consultarTodos() {
        return consultarPorFiltro(new DireccionEntidad());
    }

    @Override
    public List<DireccionEntidad> consultarPorFiltro(final DireccionEntidad filtro) {
        final var entidadFiltro = UtilObjeto.obtenerValorDefecto(filtro, new DireccionEntidad());
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

    private String construirConsultaPorFiltro(final DireccionEntidad filtro, final List<Object> parametros) {
        final var consulta = new StringBuilder(DireccionSql.CONSULTAR_POR_FILTRO);
        final var condiciones = new ArrayList<String>();

        if (!UtilUUID.esUUIDPorDefecto(filtro.getId())) {
            condiciones.add("d.id = ?");
            parametros.add(filtro.getId());
        }
        if (!UtilUUID.esUUIDPorDefecto(filtro.getCliente().getId())) {
            condiciones.add("d.cliente_id = ?");
            parametros.add(filtro.getCliente().getId());
        }
        if (!UtilTexto.estaVacia(filtro.getCiudad())) {
            condiciones.add("d.ciudad = ?");
            parametros.add(filtro.getCiudad());
        }

        if (!condiciones.isEmpty()) {
            consulta.append(" WHERE ").append(String.join(" AND ", condiciones));
        }
        return consulta.toString();
    }

    private List<DireccionEntidad> ejecutarConsultaPorFiltro(final PreparedStatement sentencia) {
        final var resultados = new ArrayList<DireccionEntidad>();
        try (var resultSet = sentencia.executeQuery()) {
            while (resultSet.next()) {
                resultados.add(DireccionMapper.mapear(resultSet));
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
