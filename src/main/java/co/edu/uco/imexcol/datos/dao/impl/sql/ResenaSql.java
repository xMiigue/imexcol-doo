package co.edu.uco.imexcol.datos.dao.impl.sql;

public final class ResenaSql {

    public static final String ACCEDER = """
            INSERT INTO imex_resena (
                id,
                cliente_id,
                producto_id,
                calificacion,
                comentario,
                fecha
            )
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    public static final String CONSULTAR_POR_FILTRO = """
            SELECT
                r.id           AS resena_id,
                r.cliente_id   AS resena_cliente_id,
                r.producto_id  AS resena_producto_id,
                r.calificacion AS resena_calificacion,
                r.comentario   AS resena_comentario,
                r.fecha        AS resena_fecha
            FROM imex_resena AS r
            """;

    public static final String ELIMINAR = """
            DELETE FROM imex_resena
            WHERE id = ?
            """;

    public static final String FILTRO_ID = "r.id = ?";
    public static final String FILTRO_CLIENTE = "r.cliente_id = ?";
    public static final String FILTRO_PRODUCTO = "r.producto_id = ?";
    public static final String ORDENAMIENTO = " ORDER BY r.fecha DESC";

    public static final String COLUMNA_ID = "resena_id";
    public static final String COLUMNA_CLIENTE_ID = "resena_cliente_id";
    public static final String COLUMNA_PRODUCTO_ID = "resena_producto_id";
    public static final String COLUMNA_CALIFICACION = "resena_calificacion";
    public static final String COLUMNA_COMENTARIO = "resena_comentario";
    public static final String COLUMNA_FECHA = "resena_fecha";

    private ResenaSql() {
        super();
    }
}
