package co.edu.uco.imexcol.datos.dao.impl.sql;

public final class CarritoComprasSql {

    public static final String ACCEDER = """
            INSERT INTO imex_carrito_compras (
                id,
                cliente_id,
                fecha_creacion,
                estado
            )
            VALUES (?, ?, ?, ?)
            """;

    public static final String CONSULTAR_POR_FILTRO = """
            SELECT
                c.id              AS carrito_compras_id,
                c.cliente_id      AS carrito_compras_cliente_id,
                c.fecha_creacion  AS carrito_compras_fecha_creacion,
                c.estado          AS carrito_compras_estado
            FROM imex_carrito_compras AS c
            """;

    public static final String ACTUALIZAR = """
            UPDATE imex_carrito_compras
            SET
                cliente_id     = ?,
                fecha_creacion = ?,
                estado         = ?
            WHERE id = ?
            """;

    public static final String ELIMINAR = """
            DELETE FROM imex_carrito_compras
            WHERE id = ?
            """;

    private CarritoComprasSql() {
        super();
    }
}
