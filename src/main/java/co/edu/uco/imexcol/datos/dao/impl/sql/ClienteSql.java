package co.edu.uco.imexcol.datos.dao.impl.sql;

public final class ClienteSql {

    public static final String CREAR = """
            INSERT INTO imex_cliente (
                id,
                nombre,
                apellido,
                correo_electronico,
                contrasena,
                telefono,
                estado
            )
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

    public static final String CONSULTAR_POR_FILTRO = """
            SELECT
                c.id                  AS cliente_id,
                c.nombre              AS cliente_nombre,
                c.apellido            AS cliente_apellido,
                c.correo_electronico  AS cliente_correo_electronico,
                c.contrasena          AS cliente_contrasena,
                c.telefono            AS cliente_telefono,
                c.estado              AS cliente_estado
            FROM imex_cliente AS c
            """;

    public static final String ACTUALIZAR = """
            UPDATE imex_cliente
            SET
                nombre             = ?,
                apellido           = ?,
                correo_electronico = ?,
                contrasena         = ?,
                telefono           = ?,
                estado             = ?
            WHERE id = ?
            """;

    public static final String ELIMINAR = """
            DELETE FROM imex_cliente
            WHERE id = ?
            """;

    public static final String FILTRO_ID = "c.id = ?";
    public static final String FILTRO_NOMBRE = "c.nombre = ?";
    public static final String FILTRO_APELLIDO = "c.apellido = ?";
    public static final String FILTRO_CORREO_ELECTRONICO = "c.correo_electronico = ?";
    public static final String FILTRO_CONTRASENA = "c.contrasena = ?";
    public static final String COLUMNA_ID = "cliente_id";
    public static final String COLUMNA_NOMBRE = "cliente_nombre";
    public static final String COLUMNA_APELLIDO = "cliente_apellido";
    public static final String COLUMNA_CORREO_ELECTRONICO = "cliente_correo_electronico";
    public static final String COLUMNA_CONTRASENA = "cliente_contrasena";
    public static final String COLUMNA_TELEFONO = "cliente_telefono";
    public static final String COLUMNA_ESTADO = "cliente_estado";

    private ClienteSql() {
        super();
    }
}
