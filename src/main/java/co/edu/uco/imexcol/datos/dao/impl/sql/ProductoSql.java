package co.edu.uco.imexcol.datos.dao.impl.sql;

public final class ProductoSql {

    public static final String CREAR = """
            INSERT INTO imex_producto (
                id,
                categoria_id,
                nombre,
                descripcion,
                precio,
                stock,
                imagen_url,
                estado
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

    public static final String CONSULTAR_POR_FILTRO = """
            SELECT
                p.id            AS producto_id,
                p.categoria_id  AS producto_categoria_id,
                p.nombre        AS producto_nombre,
                p.descripcion   AS producto_descripcion,
                p.precio        AS producto_precio,
                p.stock         AS producto_stock,
                p.imagen_url    AS producto_imagen_url,
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
                imagen_url   = ?,
                estado       = ?
            WHERE id = ?
            """;

    public static final String ELIMINAR = """
            DELETE FROM imex_producto
            WHERE id = ?
            """;

    public static final String FILTRO_ID = "p.id = ?";
    public static final String FILTRO_CATEGORIA = "p.categoria_id = ?";
    public static final String FILTRO_NOMBRE = "p.nombre = ?";
    public static final String COLUMNA_ID = "producto_id";
    public static final String COLUMNA_CATEGORIA_ID = "producto_categoria_id";
    public static final String COLUMNA_NOMBRE = "producto_nombre";
    public static final String COLUMNA_DESCRIPCION = "producto_descripcion";
    public static final String COLUMNA_PRECIO = "producto_precio";
    public static final String COLUMNA_STOCK = "producto_stock";
    public static final String COLUMNA_IMAGEN_URL = "producto_imagen_url";
    public static final String COLUMNA_ESTADO = "producto_estado";

    private ProductoSql() {
        super();
    }
}
