package co.edu.uco.imexcol.datos.dao.impl.sql;

public final class LineaPedidoSql {

    public static final String CREAR = """
            INSERT INTO imex_linea_pedido (
                id,
                pedido_id,
                producto_id,
                cantidad,
                precio_unitario,
                subtotal
            )
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    public static final String CONSULTAR_POR_FILTRO = """
            SELECT
                l.id              AS linea_pedido_id,
                l.pedido_id       AS linea_pedido_pedido_id,
                l.producto_id     AS linea_pedido_producto_id,
                l.cantidad        AS linea_pedido_cantidad,
                l.precio_unitario AS linea_pedido_precio_unitario,
                l.subtotal        AS linea_pedido_subtotal
            FROM imex_linea_pedido AS l
            """;

    public static final String ACTUALIZAR = """
            UPDATE imex_linea_pedido
            SET
                pedido_id       = ?,
                producto_id     = ?,
                cantidad        = ?,
                precio_unitario = ?,
                subtotal        = ?
            WHERE id = ?
            """;

    public static final String ELIMINAR = """
            DELETE FROM imex_linea_pedido
            WHERE id = ?
            """;

    public static final String FILTRO_ID = "l.id = ?";
    public static final String FILTRO_PEDIDO = "l.pedido_id = ?";
    public static final String FILTRO_PRODUCTO = "l.producto_id = ?";
    public static final String COLUMNA_ID = "linea_pedido_id";
    public static final String COLUMNA_PEDIDO_ID = "linea_pedido_pedido_id";
    public static final String COLUMNA_PRODUCTO_ID = "linea_pedido_producto_id";
    public static final String COLUMNA_CANTIDAD = "linea_pedido_cantidad";
    public static final String COLUMNA_PRECIO_UNITARIO = "linea_pedido_precio_unitario";
    public static final String COLUMNA_SUBTOTAL = "linea_pedido_subtotal";

    private LineaPedidoSql() {
        super();
    }
}
