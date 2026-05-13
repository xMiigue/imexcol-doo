package co.edu.uco.imexcol.datos.dao.entidad.sql;

public final class PedidoSql {

    public static final String ACCEDER = """
            INSERT INTO imex_pedido (
                id,
                cliente_id,
                fecha_pedido,
                total,
                estado
            )
            VALUES (?, ?, ?, ?, ?)
            """;

    public static final String CONSULTAR_POR_FILTRO = """
            SELECT
                p.id            AS pedido_id,
                p.cliente_id    AS pedido_cliente_id,
                p.fecha_pedido  AS pedido_fecha_pedido,
                p.total         AS pedido_total,
                p.estado        AS pedido_estado
            FROM imex_pedido AS p
            """;

    public static final String ACTUALIZAR = """
            UPDATE imex_pedido
            SET
                cliente_id   = ?,
                fecha_pedido = ?,
                total        = ?,
                estado       = ?
            WHERE id = ?
            """;

    public static final String ELIMINAR = """
            DELETE FROM imex_pedido
            WHERE id = ?
            """;

    private PedidoSql() {
        super();
    }
}
