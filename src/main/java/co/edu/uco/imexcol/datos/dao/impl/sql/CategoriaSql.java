package co.edu.uco.imexcol.datos.dao.impl.sql;

public final class CategoriaSql {

    public static final String CREAR = """
            INSERT INTO imex_categoria (
                id,
                nombre,
                descripcion,
                estado
            )
            VALUES (?, ?, ?, ?)
            """;

    public static final String CONSULTAR_POR_FILTRO = """
            SELECT
                c.id           AS categoria_id,
                c.nombre       AS categoria_nombre,
                c.descripcion  AS categoria_descripcion,
                c.estado       AS categoria_estado
            FROM imex_categoria AS c
            """;

    public static final String ACTUALIZAR = """
            UPDATE imex_categoria
            SET
                nombre      = ?,
                descripcion = ?,
                estado      = ?
            WHERE id = ?
            """;

    public static final String ELIMINAR = """
            DELETE FROM imex_categoria
            WHERE id = ?
            """;

    public static final String FILTRO_ID = "c.id = ?";
    public static final String FILTRO_NOMBRE = "c.nombre = ?";
    public static final String COLUMNA_ID = "categoria_id";
    public static final String COLUMNA_NOMBRE = "categoria_nombre";
    public static final String COLUMNA_DESCRIPCION = "categoria_descripcion";
    public static final String COLUMNA_ESTADO = "categoria_estado";

    private CategoriaSql() {
        super();
    }
}
