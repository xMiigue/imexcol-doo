package co.edu.uco.imexcol.datos.dao.entidad.sql;

public final class ProductoSql {

    public static final String ACCEDER = """
            INSERT INTO imex_producto (
                id,
                categoria_id,
                nombre,
                descripcion,
                precio,
                stock,
                estado
            )
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

    public static final String CONSULTAR_POR_FILTRO = """
            SELECT
                p.id            AS producto_id,
                p.categoria_id  AS producto_categoria_id,
                p.nombre        AS producto_nombre,
                p.descripcion   AS producto_descripcion,
                p.precio        AS producto_precio,
                p.stock         AS producto_stock,
                p.estado        AS producto_estado
            FROM imex_producto AS p
            """;

    public static final String ACTUALIZAR = """
            UPDATE imex_producto
            SET
                categoria_id = ?,
                nombre       = ?,
                descripcion  = ?,
                precio       = ?,
                stock        = ?,
                estado       = ?
            WHERE id = ?
            """;

    public static final String ELIMINAR = """
            DELETE FROM imex_producto
            WHERE id = ?
            """;

    private ProductoSql() {
        super();
    }
}
