package co.edu.uco.imexcol.datos.dao.impl.sql;

public final class DireccionSql {

    public static final String ACCEDER = """
            INSERT INTO imex_direccion (
                id,
                cliente_id,
                calle,
                ciudad,
                departamento,
                codigo_postal,
                pais,
                es_principal
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

    public static final String CONSULTAR_POR_FILTRO = """
            SELECT
                d.id            AS direccion_id,
                d.cliente_id    AS direccion_cliente_id,
                d.calle         AS direccion_calle,
                d.ciudad        AS direccion_ciudad,
                d.departamento  AS direccion_departamento,
                d.codigo_postal AS direccion_codigo_postal,
                d.pais          AS direccion_pais,
                d.es_principal  AS direccion_es_principal
            FROM imex_direccion AS d
            """;

    public static final String ACTUALIZAR = """
            UPDATE imex_direccion
            SET
                cliente_id    = ?,
                calle         = ?,
                ciudad        = ?,
                departamento  = ?,
                codigo_postal = ?,
                pais          = ?,
                es_principal  = ?
            WHERE id = ?
            """;

    public static final String ELIMINAR = """
            DELETE FROM imex_direccion
            WHERE id = ?
            """;

    private DireccionSql() {
        super();
    }
}
