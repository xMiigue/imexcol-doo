package co.edu.uco.imexcol.datos.dao.impl.sql;

public final class AdministradorSql {

    public static final String CREAR = """
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

    public static final String FILTRO_ID = "a.id = ?";
    public static final String FILTRO_NOMBRE_USUARIO = "a.nombre_usuario = ?";
    public static final String FILTRO_CORREO_ELECTRONICO = "a.correo_electronico = ?";
    public static final String FILTRO_CONTRASENA = "a.contrasena = ?";
    public static final String COLUMNA_ID = "administrador_id";
    public static final String COLUMNA_NOMBRE_USUARIO = "administrador_nombre_usuario";
    public static final String COLUMNA_CORREO_ELECTRONICO = "administrador_correo_electronico";
    public static final String COLUMNA_CONTRASENA = "administrador_contrasena";
    public static final String COLUMNA_ESTADO = "administrador_estado";

    private AdministradorSql() {
        super();
    }
}
