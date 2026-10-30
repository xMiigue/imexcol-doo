package co.edu.uco.imexcol.datos.dao.impl.sql;

public final class DireccionSql {

    public static final String CREAR = """
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

    public static final String FILTRO_ID = "d.id = ?";
    public static final String FILTRO_CLIENTE = "d.cliente_id = ?";
    public static final String FILTRO_CALLE = "d.calle = ?";
    public static final String FILTRO_CIUDAD = "d.ciudad = ?";
    public static final String FILTRO_DEPARTAMENTO = "d.departamento = ?";
    public static final String FILTRO_PAIS = "d.pais = ?";
    public static final String COLUMNA_ID = "direccion_id";
    public static final String COLUMNA_CLIENTE_ID = "direccion_cliente_id";
    public static final String COLUMNA_CALLE = "direccion_calle";
    public static final String COLUMNA_CIUDAD = "direccion_ciudad";
    public static final String COLUMNA_DEPARTAMENTO = "direccion_departamento";
    public static final String COLUMNA_CODIGO_POSTAL = "direccion_codigo_postal";
    public static final String COLUMNA_PAIS = "direccion_pais";
    public static final String COLUMNA_ES_PRINCIPAL = "direccion_es_principal";

    private DireccionSql() {
        super();
    }
}
