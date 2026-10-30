package co.edu.uco.imexcol.datos.dao.impl.sql;

public final class PagoSql {

    public static final String CREAR = """
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

    public static final String FILTRO_ID = "p.id = ?";
    public static final String FILTRO_PEDIDO = "p.pedido_id = ?";
    public static final String FILTRO_METODO_PAGO = "p.metodo_pago_id = ?";
    public static final String FILTRO_ESTADO = "p.estado = ?";
    public static final String COLUMNA_ID = "pago_id";
    public static final String COLUMNA_PEDIDO_ID = "pago_pedido_id";
    public static final String COLUMNA_METODO_PAGO_ID = "pago_metodo_pago_id";
    public static final String COLUMNA_MONTO = "pago_monto";
    public static final String COLUMNA_FECHA_PAGO = "pago_fecha_pago";
    public static final String COLUMNA_ESTADO = "pago_estado";

    private PagoSql() {
        super();
    }
}
