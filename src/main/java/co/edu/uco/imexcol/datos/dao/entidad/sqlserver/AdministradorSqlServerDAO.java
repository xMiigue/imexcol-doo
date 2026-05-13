package co.edu.uco.imexcol.datos.dao.entidad.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.entidad.AdministradorDAO;
import co.edu.uco.imexcol.datos.dao.entidad.ConexionSql;
import co.edu.uco.imexcol.datos.dao.entidad.mapper.AdministradorMapper;
import co.edu.uco.imexcol.datos.dao.entidad.sql.AdministradorSql;
import co.edu.uco.imexcol.entidad.AdministradorEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilConexionSql;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class AdministradorSqlServerDAO extends ConexionSql implements AdministradorDAO {

    public AdministradorSqlServerDAO(final Connection conexion) {
        super(conexion);
    }

    @Override
    public void acceder(final AdministradorEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new AdministradorEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(AdministradorSql.ACCEDER)) {
            sentencia.setObject(1, entidadSegura.getId());
            sentencia.setString(2, entidadSegura.getNombreUsuario());
            sentencia.setString(3, entidadSegura.getCorreoElectronico());
            sentencia.setString(4, entidadSegura.getContrasena());
            sentencia.setBoolean(5, entidadSegura.isEstado());
            sentencia.executeUpdate();
        } catch (final SQLException excepcion) {
            throw envolverErrorDao(excepcion, MensajesEnum.ERROR_USUARIO_DAO_CREAR_GENERICO,
                    MensajesEnum.ERROR_TECNICO_DAO_CREAR_GENERICO);
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    @Override
    public void actualizar(final AdministradorEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new AdministradorEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(AdministradorSql.ACTUALIZAR)) {
            sentencia.setString(1, entidadSegura.getNombreUsuario());
            sentencia.setString(2, entidadSegura.getCorreoElectronico());
            sentencia.setString(3, entidadSegura.getContrasena());
            sentencia.setBoolean(4, entidadSegura.isEstado());
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

        try (var sentencia = obtenerConexion().prepareStatement(AdministradorSql.ELIMINAR)) {
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
    public AdministradorEntidad consultarPorId(final UUID id) {
        final var filtro = new AdministradorEntidad(UtilUUID.obtenerValorDefecto(id));
        return consultarPorFiltro(filtro).stream().findFirst().orElse(new AdministradorEntidad());
    }

    @Override
    public List<AdministradorEntidad> consultarTodos() {
        return consultarPorFiltro(new AdministradorEntidad());
    }

    @Override
    public List<AdministradorEntidad> consultarPorFiltro(final AdministradorEntidad filtro) {
        final var entidadFiltro = UtilObjeto.obtenerValorDefecto(filtro, new AdministradorEntidad());
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

    private String construirConsultaPorFiltro(final AdministradorEntidad filtro, final List<Object> parametros) {
        final var consulta = new StringBuilder(AdministradorSql.CONSULTAR_POR_FILTRO);
        final var condiciones = new ArrayList<String>();

        if (!UtilUUID.esUUIDPorDefecto(filtro.getId())) {
            condiciones.add("a.id = ?");
            parametros.add(filtro.getId());
        }
        if (!UtilTexto.estaVacia(filtro.getNombreUsuario())) {
            condiciones.add("a.nombre_usuario = ?");
            parametros.add(filtro.getNombreUsuario());
        }
        if (!UtilTexto.estaVacia(filtro.getCorreoElectronico())) {
            condiciones.add("a.correo_electronico = ?");
            parametros.add(filtro.getCorreoElectronico());
        }

        if (!condiciones.isEmpty()) {
            consulta.append(" WHERE ").append(String.join(" AND ", condiciones));
        }
        return consulta.toString();
    }

    private List<AdministradorEntidad> ejecutarConsultaPorFiltro(final PreparedStatement sentencia) {
        final var resultados = new ArrayList<AdministradorEntidad>();
        try (var resultSet = sentencia.executeQuery()) {
            while (resultSet.next()) {
                resultados.add(AdministradorMapper.mapear(resultSet));
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
