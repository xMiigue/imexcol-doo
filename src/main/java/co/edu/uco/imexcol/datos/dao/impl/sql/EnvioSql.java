package co.edu.uco.imexcol.datos.dao.impl.sql;

public final class EnvioSql {

    public static final String ACCEDER = """
            INSERT INTO imex_envio (
                id,
                pedido_id,
                fecha_envio,
                fecha_entrega,
                transportadora,
                numero_guia,
                estado
            )
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

    public static final String CONSULTAR_POR_FILTRO = """
            SELECT
                e.id             AS envio_id,
                e.pedido_id      AS envio_pedido_id,
                e.fecha_envio    AS envio_fecha_envio,
                e.fecha_entrega  AS envio_fecha_entrega,
                e.transportadora AS envio_transportadora,
                e.numero_guia    AS envio_numero_guia,
                e.estado         AS envio_estado
            FROM imex_envio AS e
            """;

    public static final String ACTUALIZAR = """
            UPDATE imex_envio
            SET
                pedido_id      = ?,
                fecha_envio    = ?,
                fecha_entrega  = ?,
                transportadora = ?,
                numero_guia    = ?,
                estado         = ?
            WHERE id = ?
            """;

    public static final String ELIMINAR = """
            DELETE FROM imex_envio
            WHERE id = ?
            """;

    private EnvioSql() {
        super();
    }
}
