package co.edu.uco.imexcol.datos.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import co.edu.uco.imexcol.datos.dao.impl.sql.EventoRastreoSql;
import co.edu.uco.imexcol.entidad.EnvioEntidad;
import co.edu.uco.imexcol.entidad.EventoRastreoEntidad;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;

public final class EventoRastreoMapper {

    private EventoRastreoMapper() {
        super();
    }

    public static EventoRastreoEntidad mapear(final ResultSet resultSet) {
        final var entidad = new EventoRastreoEntidad();
        try {
            entidad.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(EventoRastreoSql.COLUMNA_ID)));

            final var envio = new EnvioEntidad();
            envio.setId(UtilUUID.obtenerDesdeTexto(resultSet.getString(EventoRastreoSql.COLUMNA_ENVIO_ID)));
            entidad.setEnvio(envio);

            entidad.setEstadoLogistico(resultSet.getString(EventoRastreoSql.COLUMNA_ESTADO_LOGISTICO));

            final Timestamp fechaHoraSql = resultSet.getTimestamp(EventoRastreoSql.COLUMNA_FECHA_HORA);
            entidad.setFechaHora(UtilObjeto.esNulo(fechaHoraSql)
                    ? UtilFecha.obtenerFechaHoraPorDefecto()
                    : fechaHoraSql.toLocalDateTime());

            entidad.setObservacion(resultSet.getString(EventoRastreoSql.COLUMNA_OBSERVACION));
        } catch (final SQLException excepcion) {
            throw ImexcolDatosException.crear(excepcion,
                    MensajesEnum.ERROR_USUARIO_MAPPER_GENERICO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_MAPPER_GENERICO.getContenido());
        } catch (final Exception excepcion) {
            throw ImexcolDatosException.crear(excepcion,
                    MensajesEnum.ERROR_USUARIO_MAPPER_INESPERADO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_MAPPER_INESPERADO.getContenido());
        }
        return entidad;
    }
}
