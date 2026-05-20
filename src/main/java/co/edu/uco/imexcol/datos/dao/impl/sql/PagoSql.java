package co.edu.uco.imexcol.datos.dao.impl.sql;

public final class PagoSql {

    public static final String ACCEDER = """
            INSERT INTO imex_pago (
                id,
                pedido_id,
                metodo_pago_id,
                monto,
                fecha_pago,
                estado
            )
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    public static final String CONSULTAR_POR_FILTRO = """
            SELECT
                p.id             AS pago_id,
                p.pedido_id      AS pago_pedido_id,
                p.metodo_pago_id AS pago_metodo_pago_id,
                p.monto          AS pago_monto,
                p.fecha_pago     AS pago_fecha_pago,
                p.estado         AS pago_estado
            FROM imex_pago AS p
            """;

    public static final String ACTUALIZAR = """
            UPDATE imex_pago
            SET
                pedido_id      = ?,
                metodo_pago_id = ?,
                monto          = ?,
                fecha_pago     = ?,
                estado         = ?
            WHERE id = ?
            """;

    public static final String ELIMINAR = """
            DELETE FROM imex_pago
            WHERE id = ?
            """;

    private PagoSql() {
        super();
    }
}
