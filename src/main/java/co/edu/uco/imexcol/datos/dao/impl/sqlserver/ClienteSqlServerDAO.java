package co.edu.uco.imexcol.datos.dao.impl.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.ClienteDAO;
import co.edu.uco.imexcol.datos.dao.ConexionSql;
import co.edu.uco.imexcol.datos.dao.impl.mapper.ClienteMapper;
import co.edu.uco.imexcol.datos.dao.impl.sql.ClausulasSql;
import co.edu.uco.imexcol.datos.dao.impl.sql.ClienteSql;
import co.edu.uco.imexcol.entidad.ClienteEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilConexionSql;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class ClienteSqlServerDAO extends ConexionSql implements ClienteDAO {

    private static final int POSICION_ID = 1;
    private static final int POSICION_NOMBRE = 2;
    private static final int POSICION_APELLIDO = 3;
    private static final int POSICION_CORREO_ELECTRONICO = 4;
    private static final int POSICION_CONTRASENA = 5;
    private static final int POSICION_TELEFONO = 6;
    private static final int POSICION_ESTADO = 7;
    private static final int POSICION_ACTUALIZAR_NOMBRE = 1;
    private static final int POSICION_ACTUALIZAR_APELLIDO = 2;
    private static final int POSICION_ACTUALIZAR_CORREO_ELECTRONICO = 3;
    private static final int POSICION_ACTUALIZAR_CONTRASENA = 4;
    private static final int POSICION_ACTUALIZAR_TELEFONO = 5;
    private static final int POSICION_ACTUALIZAR_ESTADO = 6;
    private static final int POSICION_ACTUALIZAR_ID = 7;
    private static final int POSICION_ID_ELIMINAR = 1;

    public ClienteSqlServerDAO(final Connection conexion) {
        super(conexion);
    }

    @Override
    public void crear(final ClienteEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new ClienteEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(ClienteSql.CREAR)) {
            sentencia.setObject(POSICION_ID, entidadSegura.getId());
            sentencia.setString(POSICION_NOMBRE, entidadSegura.getNombre());
            sentencia.setString(POSICION_APELLIDO, entidadSegura.getApellido());
            sentencia.setString(POSICION_CORREO_ELECTRONICO, entidadSegura.getCorreoElectronico());
            sentencia.setString(POSICION_CONTRASENA, entidadSegura.getContrasena());
            sentencia.setString(POSICION_TELEFONO, entidadSegura.getTelefono());
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
    public void actualizar(final ClienteEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new ClienteEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(ClienteSql.ACTUALIZAR)) {
            sentencia.setString(POSICION_ACTUALIZAR_NOMBRE, entidadSegura.getNombre());
            sentencia.setString(POSICION_ACTUALIZAR_APELLIDO, entidadSegura.getApellido());
            sentencia.setString(POSICION_ACTUALIZAR_CORREO_ELECTRONICO, entidadSegura.getCorreoElectronico());
            sentencia.setString(POSICION_ACTUALIZAR_CONTRASENA, entidadSegura.getContrasena());
            sentencia.setString(POSICION_ACTUALIZAR_TELEFONO, entidadSegura.getTelefono());
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

        try (var sentencia = obtenerConexion().prepareStatement(ClienteSql.ELIMINAR)) {
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
    public ClienteEntidad consultarPorId(final UUID id) {
        final var filtro = new ClienteEntidad(UtilUUID.obtenerValorDefecto(id));
        return consultarPorFiltro(filtro).stream().findFirst().orElse(new ClienteEntidad());
    }

    @Override
    public List<ClienteEntidad> consultarTodos() {
        return consultarPorFiltro(new ClienteEntidad());
    }

    @Override
    public List<ClienteEntidad> consultarPorFiltro(final ClienteEntidad filtro) {
        final var entidadFiltro = UtilObjeto.obtenerValorDefecto(filtro, new ClienteEntidad());
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

    private String construirConsultaPorFiltro(final ClienteEntidad filtro, final List<Object> parametros) {
        final var consulta = new StringBuilder(ClienteSql.CONSULTAR_POR_FILTRO);
        final var condiciones = new ArrayList<String>();

        if (!UtilUUID.esUUIDPorDefecto(filtro.getId())) {
            condiciones.add(ClienteSql.FILTRO_ID);
            parametros.add(filtro.getId());
        }
        if (!UtilTexto.estaVacia(filtro.getNombre())) {
            condiciones.add(ClienteSql.FILTRO_NOMBRE);
            parametros.add(filtro.getNombre());
        }
        if (!UtilTexto.estaVacia(filtro.getApellido())) {
            condiciones.add(ClienteSql.FILTRO_APELLIDO);
            parametros.add(filtro.getApellido());
        }
        if (!UtilTexto.estaVacia(filtro.getCorreoElectronico())) {
            condiciones.add(ClienteSql.FILTRO_CORREO_ELECTRONICO);
            parametros.add(filtro.getCorreoElectronico());
        }
        if (!UtilTexto.estaVacia(filtro.getContrasena())) {
            condiciones.add(ClienteSql.FILTRO_CONTRASENA);
            parametros.add(filtro.getContrasena());
        }

        if (!condiciones.isEmpty()) {
            consulta.append(ClausulasSql.DONDE).append(String.join(ClausulasSql.Y, condiciones));
        }
        return consulta.toString();
    }

    private List<ClienteEntidad> ejecutarConsultaPorFiltro(final PreparedStatement sentencia) {
        final var resultados = new ArrayList<ClienteEntidad>();
        try (var resultSet = sentencia.executeQuery()) {
            while (resultSet.next()) {
                resultados.add(ClienteMapper.mapear(resultSet));
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
