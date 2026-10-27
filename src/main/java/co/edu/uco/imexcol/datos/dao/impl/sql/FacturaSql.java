package co.edu.uco.imexcol.datos.dao.impl.sql;

public final class FacturaSql {

    public static final String ACCEDER = """
            INSERT INTO imex_factura (
                id,
                pago_id,
                numero_factura,
                fecha_emision,
                subtotal,
                total_impuestos,
                total_facturado
            )
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

    public static final String CONSULTAR_POR_FILTRO = """
            SELECT
                f.id              AS factura_id,
                f.pago_id         AS factura_pago_id,
                pg.pedido_id      AS factura_pedido_id,
                f.numero_factura  AS factura_numero_factura,
                f.fecha_emision   AS factura_fecha_emision,
                f.subtotal        AS factura_subtotal,
                f.total_impuestos AS factura_total_impuestos,
                f.total_facturado AS factura_total_facturado
            FROM imex_factura AS f
            INNER JOIN imex_pago AS pg ON pg.id = f.pago_id
            """;

    public static final String FILTRO_ID = "f.id = ?";
    public static final String FILTRO_PAGO = "f.pago_id = ?";
    public static final String FILTRO_PEDIDO = "pg.pedido_id = ?";
    public static final String FILTRO_NUMERO_FACTURA = "f.numero_factura = ?";
    public static final String ORDENAMIENTO = " ORDER BY f.fecha_emision DESC";

    public static final String COLUMNA_ID = "factura_id";
    public static final String COLUMNA_PAGO_ID = "factura_pago_id";
    public static final String COLUMNA_PEDIDO_ID = "factura_pedido_id";
    public static final String COLUMNA_NUMERO_FACTURA = "factura_numero_factura";
    public static final String COLUMNA_FECHA_EMISION = "factura_fecha_emision";
    public static final String COLUMNA_SUBTOTAL = "factura_subtotal";
    public static final String COLUMNA_TOTAL_IMPUESTOS = "factura_total_impuestos";
    public static final String COLUMNA_TOTAL_FACTURADO = "factura_total_facturado";

    private FacturaSql() {
        super();
    }
}
