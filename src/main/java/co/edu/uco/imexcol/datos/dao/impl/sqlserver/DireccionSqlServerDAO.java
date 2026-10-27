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
import co.edu.uco.imexcol.datos.dao.impl.sql.ClausulasSql;
import co.edu.uco.imexcol.datos.dao.impl.sql.DireccionSql;
import co.edu.uco.imexcol.entidad.DireccionEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilConexionSql;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class DireccionSqlServerDAO extends ConexionSql implements DireccionDAO {

    private static final int POSICION_ID = 1;
    private static final int POSICION_CLIENTE = 2;
    private static final int POSICION_CALLE = 3;
    private static final int POSICION_CIUDAD = 4;
    private static final int POSICION_DEPARTAMENTO = 5;
    private static final int POSICION_CODIGO_POSTAL = 6;
    private static final int POSICION_PAIS = 7;
    private static final int POSICION_ES_PRINCIPAL = 8;
    private static final int POSICION_ACTUALIZAR_CLIENTE = 1;
    private static final int POSICION_ACTUALIZAR_CALLE = 2;
    private static final int POSICION_ACTUALIZAR_CIUDAD = 3;
    private static final int POSICION_ACTUALIZAR_DEPARTAMENTO = 4;
    private static final int POSICION_ACTUALIZAR_CODIGO_POSTAL = 5;
    private static final int POSICION_ACTUALIZAR_PAIS = 6;
    private static final int POSICION_ACTUALIZAR_ES_PRINCIPAL = 7;
    private static final int POSICION_ACTUALIZAR_ID = 8;
    private static final int POSICION_ID_ELIMINAR = 1;

    public DireccionSqlServerDAO(final Connection conexion) {
        super(conexion);
    }

    @Override
    public void crear(final DireccionEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new DireccionEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(DireccionSql.CREAR)) {
            sentencia.setObject(POSICION_ID, entidadSegura.getId());
            sentencia.setObject(POSICION_CLIENTE, entidadSegura.getCliente().getId());
            sentencia.setString(POSICION_CALLE, entidadSegura.getCalle());
            sentencia.setString(POSICION_CIUDAD, entidadSegura.getCiudad());
            sentencia.setString(POSICION_DEPARTAMENTO, entidadSegura.getDepartamento());
            sentencia.setString(POSICION_CODIGO_POSTAL, entidadSegura.getCodigoPostal());
            sentencia.setString(POSICION_PAIS, entidadSegura.getPais());
            sentencia.setBoolean(POSICION_ES_PRINCIPAL, entidadSegura.isEsPrincipal());
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
            sentencia.setObject(POSICION_ACTUALIZAR_CLIENTE, entidadSegura.getCliente().getId());
            sentencia.setString(POSICION_ACTUALIZAR_CALLE, entidadSegura.getCalle());
            sentencia.setString(POSICION_ACTUALIZAR_CIUDAD, entidadSegura.getCiudad());
            sentencia.setString(POSICION_ACTUALIZAR_DEPARTAMENTO, entidadSegura.getDepartamento());
            sentencia.setString(POSICION_ACTUALIZAR_CODIGO_POSTAL, entidadSegura.getCodigoPostal());
            sentencia.setString(POSICION_ACTUALIZAR_PAIS, entidadSegura.getPais());
            sentencia.setBoolean(POSICION_ACTUALIZAR_ES_PRINCIPAL, entidadSegura.isEsPrincipal());
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

        try (var sentencia = obtenerConexion().prepareStatement(DireccionSql.ELIMINAR)) {
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

    private String construirConsultaPorFiltro(final DireccionEntidad filtro, final List<Object> parametros) {
        final var consulta = new StringBuilder(DireccionSql.CONSULTAR_POR_FILTRO);
        final var condiciones = new ArrayList<String>();

        if (!UtilUUID.esUUIDPorDefecto(filtro.getId())) {
            condiciones.add(DireccionSql.FILTRO_ID);
            parametros.add(filtro.getId());
        }
        if (!UtilUUID.esUUIDPorDefecto(filtro.getCliente().getId())) {
            condiciones.add(DireccionSql.FILTRO_CLIENTE);
            parametros.add(filtro.getCliente().getId());
        }
        if (!UtilTexto.estaVacia(filtro.getCalle())) {
            condiciones.add(DireccionSql.FILTRO_CALLE);
            parametros.add(filtro.getCalle());
        }
        if (!UtilTexto.estaVacia(filtro.getCiudad())) {
            condiciones.add(DireccionSql.FILTRO_CIUDAD);
            parametros.add(filtro.getCiudad());
        }
        if (!UtilTexto.estaVacia(filtro.getDepartamento())) {
            condiciones.add(DireccionSql.FILTRO_DEPARTAMENTO);
            parametros.add(filtro.getDepartamento());
        }
        if (!UtilTexto.estaVacia(filtro.getPais())) {
            condiciones.add(DireccionSql.FILTRO_PAIS);
            parametros.add(filtro.getPais());
        }

        if (!condiciones.isEmpty()) {
            consulta.append(ClausulasSql.DONDE).append(String.join(ClausulasSql.Y, condiciones));
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
