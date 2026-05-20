package co.edu.uco.imexcol.datos.dao.impl.sql;

public final class ItemCarritoSql {

    public static final String ACCEDER = """
            INSERT INTO imex_item_carrito (
                id,
                carrito_id,
                producto_id,
                cantidad,
                precio_unitario
            )
            VALUES (?, ?, ?, ?, ?)
            """;

    public static final String CONSULTAR_POR_FILTRO = """
            SELECT
                i.id              AS item_carrito_id,
                i.carrito_id      AS item_carrito_carrito_id,
                i.producto_id     AS item_carrito_producto_id,
                i.cantidad        AS item_carrito_cantidad,
                i.precio_unitario AS item_carrito_precio_unitario
            FROM imex_item_carrito AS i
            """;

    public static final String ACTUALIZAR = """
            UPDATE imex_item_carrito
            SET
                carrito_id      = ?,
                producto_id     = ?,
                cantidad        = ?,
                precio_unitario = ?
            WHERE id = ?
            """;

    public static final String ELIMINAR = """
            DELETE FROM imex_item_carrito
            WHERE id = ?
            """;

    private ItemCarritoSql() {
        super();
    }
}
