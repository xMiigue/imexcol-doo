package co.edu.uco.imexcol.datos.dao.impl.sql;

public final class EventoRastreoSql {

    public static final String CREAR = """
            INSERT INTO imex_evento_rastreo (
                id,
                envio_id,
                estado_logistico,
                fecha_hora,
                observacion
            )
            VALUES (?, ?, ?, ?, ?)
            """;

    public static final String CONSULTAR_POR_FILTRO = """
            SELECT
                ev.id               AS evento_id,
                ev.envio_id         AS evento_envio_id,
                ev.estado_logistico AS evento_estado_logistico,
                ev.fecha_hora       AS evento_fecha_hora,
                ev.observacion      AS evento_observacion
            FROM imex_evento_rastreo AS ev
            """;

    public static final String ELIMINAR_POR_ENVIO = """
            DELETE FROM imex_evento_rastreo
            WHERE envio_id = ?
            """;

    public static final String FILTRO_ID = "ev.id = ?";
    public static final String FILTRO_ENVIO = "ev.envio_id = ?";
    public static final String FILTRO_ESTADO_LOGISTICO = "ev.estado_logistico = ?";
    public static final String ORDENAMIENTO = " ORDER BY ev.fecha_hora ASC";

    public static final String COLUMNA_ID = "evento_id";
    public static final String COLUMNA_ENVIO_ID = "evento_envio_id";
    public static final String COLUMNA_ESTADO_LOGISTICO = "evento_estado_logistico";
    public static final String COLUMNA_FECHA_HORA = "evento_fecha_hora";
    public static final String COLUMNA_OBSERVACION = "evento_observacion";

    private EventoRastreoSql() {
        super();
    }
}
