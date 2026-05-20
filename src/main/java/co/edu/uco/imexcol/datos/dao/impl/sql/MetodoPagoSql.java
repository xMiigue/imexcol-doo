package co.edu.uco.imexcol.datos.dao.impl.sql;

public final class MetodoPagoSql {

    public static final String ACCEDER = """
            INSERT INTO imex_metodo_pago (
                id,
                nombre,
                descripcion,
                estado
            )
            VALUES (?, ?, ?, ?)
            """;

    public static final String CONSULTAR_POR_FILTRO = """
            SELECT
                m.id           AS metodo_pago_id,
                m.nombre       AS metodo_pago_nombre,
                m.descripcion  AS metodo_pago_descripcion,
                m.estado       AS metodo_pago_estado
            FROM imex_metodo_pago AS m
            """;

    public static final String ACTUALIZAR = """
            UPDATE imex_metodo_pago
            SET
                nombre      = ?,
                descripcion = ?,
                estado      = ?
            WHERE id = ?
            """;

    public static final String ELIMINAR = """
            DELETE FROM imex_metodo_pago
            WHERE id = ?
            """;

    private MetodoPagoSql() {
        super();
    }
}
