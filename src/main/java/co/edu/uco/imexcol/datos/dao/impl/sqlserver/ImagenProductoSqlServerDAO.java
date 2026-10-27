package co.edu.uco.imexcol.datos.dao.impl.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.ConexionSql;
import co.edu.uco.imexcol.datos.dao.ImagenProductoDAO;
import co.edu.uco.imexcol.datos.dao.impl.mapper.ImagenProductoMapper;
import co.edu.uco.imexcol.datos.dao.impl.sql.ClausulasSql;
import co.edu.uco.imexcol.datos.dao.impl.sql.ImagenProductoSql;
import co.edu.uco.imexcol.entidad.ImagenProductoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilConexionSql;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class ImagenProductoSqlServerDAO extends ConexionSql implements ImagenProductoDAO {

    private static final int POSICION_ID = 1;
    private static final int POSICION_PRODUCTO = 2;
    private static final int POSICION_FOTO = 3;
    private static final int POSICION_ORDEN_VISUALIZACION = 4;
    private static final int POSICION_ACTUALIZAR_PRODUCTO = 1;
    private static final int POSICION_ACTUALIZAR_FOTO = 2;
    private static final int POSICION_ACTUALIZAR_ORDEN_VISUALIZACION = 3;
    private static final int POSICION_ACTUALIZAR_ID = 4;
    private static final int POSICION_ID_ELIMINAR = 1;

    public ImagenProductoSqlServerDAO(final Connection conexion) {
        super(conexion);
    }

    @Override
    public void acceder(final ImagenProductoEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new ImagenProductoEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(ImagenProductoSql.ACCEDER)) {
            sentencia.setObject(POSICION_ID, entidadSegura.getId());
            sentencia.setObject(POSICION_PRODUCTO, entidadSegura.getProducto().getId());
            sentencia.setString(POSICION_FOTO, entidadSegura.getFoto());
            sentencia.setInt(POSICION_ORDEN_VISUALIZACION, entidadSegura.getOrdenVisualizacion());
            sentencia.executeUpdate();
        } catch (final SQLException excepcion) {
            throw envolverErrorDao(excepcion, MensajesEnum.ERROR_USUARIO_DAO_CREAR_GENERICO,
                    MensajesEnum.ERROR_TECNICO_DAO_CREAR_GENERICO);
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    @Override
    public void actualizar(final ImagenProductoEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new ImagenProductoEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(ImagenProductoSql.ACTUALIZAR)) {
            sentencia.setObject(POSICION_ACTUALIZAR_PRODUCTO, entidadSegura.getProducto().getId());
            sentencia.setString(POSICION_ACTUALIZAR_FOTO, entidadSegura.getFoto());
            sentencia.setInt(POSICION_ACTUALIZAR_ORDEN_VISUALIZACION, entidadSegura.getOrdenVisualizacion());
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
        ejecutarEliminacion(ImagenProductoSql.ELIMINAR, id);
    }

    @Override
    public void eliminarPorProducto(final UUID idProducto) {
        ejecutarEliminacion(ImagenProductoSql.ELIMINAR_POR_PRODUCTO, idProducto);
    }

    private void ejecutarEliminacion(final String sentenciaEliminacion, final UUID id) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());

        try (var sentencia = obtenerConexion().prepareStatement(sentenciaEliminacion)) {
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
    public ImagenProductoEntidad consultarPorId(final UUID id) {
        final var filtro = new ImagenProductoEntidad(UtilUUID.obtenerValorDefecto(id));
        return consultarPorFiltro(filtro).stream().findFirst().orElse(new ImagenProductoEntidad());
    }

    @Override
    public List<ImagenProductoEntidad> consultarTodos() {
        return consultarPorFiltro(new ImagenProductoEntidad());
    }

    @Override
    public List<ImagenProductoEntidad> consultarPorFiltro(final ImagenProductoEntidad filtro) {
        final var entidadFiltro = UtilObjeto.obtenerValorDefecto(filtro, new ImagenProductoEntidad());
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

    private String construirConsultaPorFiltro(final ImagenProductoEntidad filtro, final List<Object> parametros) {
        final var condiciones = new ArrayList<String>();

        agregarCondicion(condiciones, parametros, !UtilUUID.esUUIDPorDefecto(filtro.getId()),
                ImagenProductoSql.FILTRO_ID, filtro.getId());
        agregarCondicion(condiciones, parametros, !UtilUUID.esUUIDPorDefecto(filtro.getProducto().getId()),
                ImagenProductoSql.FILTRO_PRODUCTO, filtro.getProducto().getId());
        agregarCondicion(condiciones, parametros, !UtilTexto.estaVacia(filtro.getFoto()),
                ImagenProductoSql.FILTRO_FOTO, filtro.getFoto());

        return armarConsulta(condiciones);
    }

    private static void agregarCondicion(final List<String> condiciones, final List<Object> parametros,
            final boolean aplica, final String clausula, final Object valor) {
        if (aplica) {
            condiciones.add(clausula);
            parametros.add(valor);
        }
    }

    private static String armarConsulta(final List<String> condiciones) {
        final var consulta = new StringBuilder(ImagenProductoSql.CONSULTAR_POR_FILTRO);
        if (!condiciones.isEmpty()) {
            consulta.append(ClausulasSql.DONDE).append(String.join(ClausulasSql.Y, condiciones));
        }
        return consulta.append(ImagenProductoSql.ORDENAMIENTO).toString();
    }

    private List<ImagenProductoEntidad> ejecutarConsultaPorFiltro(final PreparedStatement sentencia) {
        final var resultados = new ArrayList<ImagenProductoEntidad>();
        try (var resultSet = sentencia.executeQuery()) {
            while (resultSet.next()) {
                resultados.add(ImagenProductoMapper.mapear(resultSet));
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
