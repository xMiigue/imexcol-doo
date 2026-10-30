package co.edu.uco.imexcol.datos.dao.impl.sqlserver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.AdministradorDAO;
import co.edu.uco.imexcol.datos.dao.ConexionSql;
import co.edu.uco.imexcol.datos.dao.impl.mapper.AdministradorMapper;
import co.edu.uco.imexcol.datos.dao.impl.sql.ClausulasSql;
import co.edu.uco.imexcol.datos.dao.impl.sql.AdministradorSql;
import co.edu.uco.imexcol.entidad.AdministradorEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilConexionSql;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class AdministradorSqlServerDAO extends ConexionSql implements AdministradorDAO {

    private static final int POSICION_ID = 1;
    private static final int POSICION_NOMBRE_USUARIO = 2;
    private static final int POSICION_CORREO_ELECTRONICO = 3;
    private static final int POSICION_CONTRASENA = 4;
    private static final int POSICION_ESTADO = 5;
    private static final int POSICION_ACTUALIZAR_NOMBRE_USUARIO = 1;
    private static final int POSICION_ACTUALIZAR_CORREO_ELECTRONICO = 2;
    private static final int POSICION_ACTUALIZAR_CONTRASENA = 3;
    private static final int POSICION_ACTUALIZAR_ESTADO = 4;
    private static final int POSICION_ACTUALIZAR_ID = 5;
    private static final int POSICION_ID_ELIMINAR = 1;

    public AdministradorSqlServerDAO(final Connection conexion) {
        super(conexion);
    }

    @Override
    public void crear(final AdministradorEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new AdministradorEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(AdministradorSql.CREAR)) {
            sentencia.setObject(POSICION_ID, entidadSegura.getId());
            sentencia.setString(POSICION_NOMBRE_USUARIO, entidadSegura.getNombreUsuario());
            sentencia.setString(POSICION_CORREO_ELECTRONICO, entidadSegura.getCorreoElectronico());
            sentencia.setString(POSICION_CONTRASENA, entidadSegura.getContrasena());
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
    public void actualizar(final AdministradorEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new AdministradorEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(AdministradorSql.ACTUALIZAR)) {
            sentencia.setString(POSICION_ACTUALIZAR_NOMBRE_USUARIO, entidadSegura.getNombreUsuario());
            sentencia.setString(POSICION_ACTUALIZAR_CORREO_ELECTRONICO, entidadSegura.getCorreoElectronico());
            sentencia.setString(POSICION_ACTUALIZAR_CONTRASENA, entidadSegura.getContrasena());
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

        try (var sentencia = obtenerConexion().prepareStatement(AdministradorSql.ELIMINAR)) {
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
        return consultarConParametros(consulta, parametros, AdministradorMapper::mapear);
    }

    private String construirConsultaPorFiltro(final AdministradorEntidad filtro, final List<Object> parametros) {
        final var consulta = new StringBuilder(AdministradorSql.CONSULTAR_POR_FILTRO);
        final var condiciones = new ArrayList<String>();

        if (!UtilUUID.esUUIDPorDefecto(filtro.getId())) {
            condiciones.add(AdministradorSql.FILTRO_ID);
            parametros.add(filtro.getId());
        }
        if (!UtilTexto.estaVacia(filtro.getNombreUsuario())) {
            condiciones.add(AdministradorSql.FILTRO_NOMBRE_USUARIO);
            parametros.add(filtro.getNombreUsuario());
        }
        if (!UtilTexto.estaVacia(filtro.getCorreoElectronico())) {
            condiciones.add(AdministradorSql.FILTRO_CORREO_ELECTRONICO);
            parametros.add(filtro.getCorreoElectronico());
        }
        if (!UtilTexto.estaVacia(filtro.getContrasena())) {
            condiciones.add(AdministradorSql.FILTRO_CONTRASENA);
            parametros.add(filtro.getContrasena());
        }

        if (!condiciones.isEmpty()) {
            consulta.append(ClausulasSql.DONDE).append(String.join(ClausulasSql.Y, condiciones));
        }
        return consulta.toString();
    }
}
