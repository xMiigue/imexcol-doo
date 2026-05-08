package co.edu.uco.imexcol.transversal;

public enum MensajesEnum {

    // Categoria — nombre
    ERROR_USUARIO_CATEGORIA_NOMBRE_NULO(
            "El nombre de la categoría es obligatorio. Por favor, ingréselo."),
    ERROR_TECNICO_CATEGORIA_NOMBRE_NULO(
            "El nombre recibido para la categoría es nulo."),

    ERROR_USUARIO_CATEGORIA_NOMBRE_VACIO(
            "El nombre de la categoría no puede estar vacío. Por favor, ingréselo."),
    ERROR_TECNICO_CATEGORIA_NOMBRE_VACIO(
            "El nombre recibido para la categoría está vacío o contiene solo espacios."),

    ERROR_USUARIO_CATEGORIA_NOMBRE_LONGITUD_INVALIDA(
            "El nombre de la categoría debe tener entre 3 y 100 caracteres."),
    ERROR_TECNICO_CATEGORIA_NOMBRE_LONGITUD_INVALIDA(
            "La longitud del nombre de la categoría no se encuentra en el rango permitido [3, 100]."),

    // Categoria — descripcion
    ERROR_USUARIO_CATEGORIA_DESCRIPCION_LONGITUD_INVALIDA(
            "La descripción de la categoría no puede superar los 255 caracteres."),
    ERROR_TECNICO_CATEGORIA_DESCRIPCION_LONGITUD_INVALIDA(
            "La longitud de la descripción de la categoría supera el máximo permitido de 255 caracteres."),

    // MetodoPago — nombre
    ERROR_USUARIO_METODO_PAGO_NOMBRE_NULO(
            "El nombre del método de pago es obligatorio. Por favor, ingréselo."),
    ERROR_TECNICO_METODO_PAGO_NOMBRE_NULO(
            "El nombre recibido para el método de pago es nulo."),

    ERROR_USUARIO_METODO_PAGO_NOMBRE_VACIO(
            "El nombre del método de pago no puede estar vacío. Por favor, ingréselo."),
    ERROR_TECNICO_METODO_PAGO_NOMBRE_VACIO(
            "El nombre recibido para el método de pago está vacío o contiene solo espacios."),

    ERROR_USUARIO_METODO_PAGO_NOMBRE_LONGITUD_INVALIDA(
            "El nombre del método de pago debe tener entre 3 y 100 caracteres."),
    ERROR_TECNICO_METODO_PAGO_NOMBRE_LONGITUD_INVALIDA(
            "La longitud del nombre del método de pago no se encuentra en el rango permitido [3, 100]."),

    // MetodoPago — descripcion
    ERROR_USUARIO_METODO_PAGO_DESCRIPCION_LONGITUD_INVALIDA(
            "La descripción del método de pago no puede superar los 255 caracteres."),
    ERROR_TECNICO_METODO_PAGO_DESCRIPCION_LONGITUD_INVALIDA(
            "La longitud de la descripción del método de pago supera el máximo permitido de 255 caracteres."),

    // Administrador — nombreUsuario
    ERROR_USUARIO_ADMINISTRADOR_NOMBRE_USUARIO_NULO(
            "El nombre de usuario del administrador es obligatorio. Por favor, ingréselo."),
    ERROR_TECNICO_ADMINISTRADOR_NOMBRE_USUARIO_NULO(
            "El nombreUsuario recibido para el administrador es nulo."),

    ERROR_USUARIO_ADMINISTRADOR_NOMBRE_USUARIO_VACIO(
            "El nombre de usuario del administrador no puede estar vacío."),
    ERROR_TECNICO_ADMINISTRADOR_NOMBRE_USUARIO_VACIO(
            "El nombreUsuario recibido para el administrador está vacío o contiene solo espacios."),

    ERROR_USUARIO_ADMINISTRADOR_NOMBRE_USUARIO_LONGITUD_INVALIDA(
            "El nombre de usuario del administrador debe tener entre 4 y 50 caracteres."),
    ERROR_TECNICO_ADMINISTRADOR_NOMBRE_USUARIO_LONGITUD_INVALIDA(
            "La longitud del nombreUsuario del administrador no se encuentra en el rango permitido [4, 50]."),

    // Administrador — correoElectronico
    ERROR_USUARIO_ADMINISTRADOR_CORREO_NULO(
            "El correo electrónico del administrador es obligatorio. Por favor, ingréselo."),
    ERROR_TECNICO_ADMINISTRADOR_CORREO_NULO(
            "El correoElectronico recibido para el administrador es nulo."),

    ERROR_USUARIO_ADMINISTRADOR_CORREO_VACIO(
            "El correo electrónico del administrador no puede estar vacío."),
    ERROR_TECNICO_ADMINISTRADOR_CORREO_VACIO(
            "El correoElectronico recibido para el administrador está vacío o contiene solo espacios."),

    ERROR_USUARIO_ADMINISTRADOR_CORREO_LONGITUD_INVALIDA(
            "El correo electrónico del administrador debe tener entre 6 y 150 caracteres."),
    ERROR_TECNICO_ADMINISTRADOR_CORREO_LONGITUD_INVALIDA(
            "La longitud del correoElectronico del administrador no se encuentra en el rango permitido [6, 150]."),

    // Administrador — contrasena
    ERROR_USUARIO_ADMINISTRADOR_CONTRASENA_NULA(
            "La contraseña del administrador es obligatoria. Por favor, ingrésela."),
    ERROR_TECNICO_ADMINISTRADOR_CONTRASENA_NULA(
            "La contrasena recibida para el administrador es nula."),

    ERROR_USUARIO_ADMINISTRADOR_CONTRASENA_VACIA(
            "La contraseña del administrador no puede estar vacía."),
    ERROR_TECNICO_ADMINISTRADOR_CONTRASENA_VACIA(
            "La contrasena recibida para el administrador está vacía o contiene solo espacios."),

    ERROR_USUARIO_ADMINISTRADOR_CONTRASENA_LONGITUD_INVALIDA(
            "La contraseña del administrador debe tener entre 8 y 255 caracteres."),
    ERROR_TECNICO_ADMINISTRADOR_CONTRASENA_LONGITUD_INVALIDA(
            "La longitud de la contrasena del administrador no se encuentra en el rango permitido [8, 255].");

    private final String contenido;

    MensajesEnum(final String contenido) {
        this.contenido = contenido;
    }

    public String getContenido() {
        return contenido;
    }
}
