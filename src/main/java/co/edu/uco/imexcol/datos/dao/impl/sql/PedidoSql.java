package co.edu.uco.imexcol.datos.dao.impl.sql;

public final class PedidoSql {

    public static final String CREAR = """
            INSERT INTO imex_pedido (
                id,
                cliente_id,
                fecha_pedido,
                total,
                estado,
                direccion_id
            )
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    public static final String CONSULTAR_POR_FILTRO = """
            SELECT
                p.id            AS pedido_id,
                p.cliente_id    AS pedido_cliente_id,
                p.fecha_pedido  AS pedido_fecha_pedido,
                p.total         AS pedido_total,
                p.estado        AS pedido_estado,
                p.direccion_id  AS pedido_direccion_id
            FROM imex_pedido AS p
            """;

    public static final String ACTUALIZAR = """
            UPDATE imex_pedido
            SET
                cliente_id   = ?,
                fecha_pedido = ?,
                total        = ?,
                estado       = ?,
                direccion_id = ?
            WHERE id = ?
            """;

    public static final String ELIMINAR = """
            DELETE FROM imex_pedido
            WHERE id = ?
            """;

    public static final String FILTRO_ID = "p.id = ?";
    public static final String FILTRO_CLIENTE = "p.cliente_id = ?";
    public static final String FILTRO_ESTADO = "p.estado = ?";
    public static final String FILTRO_DIRECCION = "p.direccion_id = ?";
    public static final String COLUMNA_ID = "pedido_id";
    public static final String COLUMNA_CLIENTE_ID = "pedido_cliente_id";
    public static final String COLUMNA_FECHA_PEDIDO = "pedido_fecha_pedido";
    public static final String COLUMNA_TOTAL = "pedido_total";
    public static final String COLUMNA_ESTADO = "pedido_estado";
    public static final String COLUMNA_DIRECCION_ID = "pedido_direccion_id";

    private PedidoSql() {
        super();
    }
}
