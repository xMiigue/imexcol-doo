package co.edu.uco.imexcol.datos.dao.impl.sql;

public final class ImagenProductoSql {

    public static final String ACCEDER = """
            INSERT INTO imex_imagen_producto (
                id,
                producto_id,
                foto,
                orden_visualizacion
            )
            VALUES (?, ?, ?, ?)
            """;

    public static final String CONSULTAR_POR_FILTRO = """
            SELECT
                i.id                  AS imagen_id,
                i.producto_id         AS imagen_producto_id,
                i.foto                AS imagen_foto,
                i.orden_visualizacion AS imagen_orden_visualizacion
            FROM imex_imagen_producto AS i
            """;

    public static final String ACTUALIZAR = """
            UPDATE imex_imagen_producto
            SET
                producto_id         = ?,
                foto                = ?,
                orden_visualizacion = ?
            WHERE id = ?
            """;

    public static final String ELIMINAR = """
            DELETE FROM imex_imagen_producto
            WHERE id = ?
            """;

    public static final String ELIMINAR_POR_PRODUCTO = """
            DELETE FROM imex_imagen_producto
            WHERE producto_id = ?
            """;

    public static final String FILTRO_ID = "i.id = ?";
    public static final String FILTRO_PRODUCTO = "i.producto_id = ?";
    public static final String FILTRO_FOTO = "i.foto = ?";
    public static final String ORDENAMIENTO = " ORDER BY i.orden_visualizacion ASC";

    public static final String COLUMNA_ID = "imagen_id";
    public static final String COLUMNA_PRODUCTO_ID = "imagen_producto_id";
    public static final String COLUMNA_FOTO = "imagen_foto";
    public static final String COLUMNA_ORDEN_VISUALIZACION = "imagen_orden_visualizacion";

    private ImagenProductoSql() {
        super();
    }
}
