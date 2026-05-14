package co.edu.uco.imexcol.datos.dao.impl.sql;

public final class ClienteSql {

    public static final String ACCEDER = """
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

    private ClienteSql() {
        super();
    }
}
