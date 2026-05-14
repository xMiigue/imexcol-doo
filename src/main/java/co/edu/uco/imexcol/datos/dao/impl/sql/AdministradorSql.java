package co.edu.uco.imexcol.datos.dao.impl.sql;

public final class AdministradorSql {

    public static final String ACCEDER = """
            INSERT INTO imex_administrador (
                id,
                nombre_usuario,
                correo_electronico,
                contrasena,
                estado
            )
            VALUES (?, ?, ?, ?, ?)
            """;

    public static final String CONSULTAR_POR_FILTRO = """
            SELECT
                a.id                 AS administrador_id,
                a.nombre_usuario     AS administrador_nombre_usuario,
                a.correo_electronico AS administrador_correo_electronico,
                a.contrasena         AS administrador_contrasena,
                a.estado             AS administrador_estado
            FROM imex_administrador AS a
            """;

    public static final String ACTUALIZAR = """
            UPDATE imex_administrador
            SET
                nombre_usuario     = ?,
                correo_electronico = ?,
                contrasena         = ?,
                estado             = ?
            WHERE id = ?
            """;

    public static final String ELIMINAR = """
            DELETE FROM imex_administrador
            WHERE id = ?
            """;

    private AdministradorSql() {
        super();
    }
}
