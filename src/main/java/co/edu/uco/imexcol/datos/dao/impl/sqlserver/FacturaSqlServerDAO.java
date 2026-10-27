package co.edu.uco.imexcol.datos.dao.impl.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.ConexionSql;
import co.edu.uco.imexcol.datos.dao.FacturaDAO;
import co.edu.uco.imexcol.datos.dao.impl.mapper.FacturaMapper;
import co.edu.uco.imexcol.datos.dao.impl.sql.ClausulasSql;
import co.edu.uco.imexcol.datos.dao.impl.sql.FacturaSql;
import co.edu.uco.imexcol.entidad.FacturaEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilConexionSql;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class FacturaSqlServerDAO extends ConexionSql implements FacturaDAO {

    private static final int POSICION_ID = 1;
    private static final int POSICION_PAGO = 2;
    private static final int POSICION_NUMERO_FACTURA = 3;
    private static final int POSICION_FECHA_EMISION = 4;
    private static final int POSICION_SUBTOTAL = 5;
    private static final int POSICION_TOTAL_IMPUESTOS = 6;
    private static final int POSICION_TOTAL_FACTURADO = 7;

    public FacturaSqlServerDAO(final Connection conexion) {
        super(conexion);
    }

    @Override
    public void acceder(final FacturaEntidad entidad) {
        UtilConexionSql.asegurarTransaccionIniciada(obtenerConexion());
        final var entidadSegura = UtilObjeto.obtenerValorDefecto(entidad, new FacturaEntidad());

        try (var sentencia = obtenerConexion().prepareStatement(FacturaSql.ACCEDER)) {
            sentencia.setObject(POSICION_ID, entidadSegura.getId());
            sentencia.setObject(POSICION_PAGO, entidadSegura.getPago().getId());
            sentencia.setString(POSICION_NUMERO_FACTURA, entidadSegura.getNumeroFactura());
            sentencia.setTimestamp(POSICION_FECHA_EMISION,
                    Timestamp.valueOf(UtilFecha.obtenerValorDefecto(entidadSegura.getFechaEmision())));
            sentencia.setDouble(POSICION_SUBTOTAL, entidadSegura.getSubtotal());
            sentencia.setDouble(POSICION_TOTAL_IMPUESTOS, entidadSegura.getTotalImpuestos());
            sentencia.setDouble(POSICION_TOTAL_FACTURADO, entidadSegura.getTotalFacturado());
            sentencia.executeUpdate();
        } catch (final SQLException excepcion) {
            throw envolverErrorDao(excepcion, MensajesEnum.ERROR_USUARIO_DAO_CREAR_GENERICO,
                    MensajesEnum.ERROR_TECNICO_DAO_CREAR_GENERICO);
        } catch (final Exception excepcion) {
            throw envolverErrorInesperado(excepcion);
        }
    }

    @Override
    public FacturaEntidad consultarPorId(final UUID id) {
        final var filtro = new FacturaEntidad(UtilUUID.obtenerValorDefecto(id));
        return consultarPorFiltro(filtro).stream().findFirst().orElse(new FacturaEntidad());
    }

    @Override
    public List<FacturaEntidad> consultarTodos() {
        return consultarPorFiltro(new FacturaEntidad());
    }

    @Override
    public List<FacturaEntidad> consultarPorFiltro(final FacturaEntidad filtro) {
        final var entidadFiltro = UtilObjeto.obtenerValorDefecto(filtro, new FacturaEntidad());
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

    private String construirConsultaPorFiltro(final FacturaEntidad filtro, final List<Object> parametros) {
        final var condiciones = new ArrayList<String>();

        agregarCondicion(condiciones, parametros, !UtilUUID.esUUIDPorDefecto(filtro.getId()),
                FacturaSql.FILTRO_ID, filtro.getId());
        agregarCondicion(condiciones, parametros, !UtilUUID.esUUIDPorDefecto(filtro.getPago().getId()),
                FacturaSql.FILTRO_PAGO, filtro.getPago().getId());
        agregarCondicion(condiciones, parametros,
                !UtilUUID.esUUIDPorDefecto(filtro.getPago().getPedido().getId()),
                FacturaSql.FILTRO_PEDIDO, filtro.getPago().getPedido().getId());
        agregarCondicion(condiciones, parametros, !UtilTexto.estaVacia(filtro.getNumeroFactura()),
                FacturaSql.FILTRO_NUMERO_FACTURA, filtro.getNumeroFactura());

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
        final var consulta = new StringBuilder(FacturaSql.CONSULTAR_POR_FILTRO);
        if (!condiciones.isEmpty()) {
            consulta.append(ClausulasSql.DONDE).append(String.join(ClausulasSql.Y, condiciones));
        }
        return consulta.append(FacturaSql.ORDENAMIENTO).toString();
    }

    private List<FacturaEntidad> ejecutarConsultaPorFiltro(final PreparedStatement sentencia) {
        final var resultados = new ArrayList<FacturaEntidad>();
        try (var resultSet = sentencia.executeQuery()) {
            while (resultSet.next()) {
                resultados.add(FacturaMapper.mapear(resultSet));
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
