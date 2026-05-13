package co.edu.uco.imexcol.transversal;

public enum MensajesEnum {

    // ============================================================
    // Validaciones de dominios — Categoria
    // ============================================================
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

    ERROR_USUARIO_CATEGORIA_DESCRIPCION_LONGITUD_INVALIDA(
            "La descripción de la categoría no puede superar los 255 caracteres."),
    ERROR_TECNICO_CATEGORIA_DESCRIPCION_LONGITUD_INVALIDA(
            "La longitud de la descripción de la categoría supera el máximo permitido de 255 caracteres."),

    // ============================================================
    // Validaciones de dominios — MetodoPago
    // ============================================================
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

    ERROR_USUARIO_METODO_PAGO_DESCRIPCION_LONGITUD_INVALIDA(
            "La descripción del método de pago no puede superar los 255 caracteres."),
    ERROR_TECNICO_METODO_PAGO_DESCRIPCION_LONGITUD_INVALIDA(
            "La longitud de la descripción del método de pago supera el máximo permitido de 255 caracteres."),

    // ============================================================
    // Validaciones de dominios — Administrador
    // ============================================================
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
            "La longitud de la contrasena del administrador no se encuentra en el rango permitido [8, 255]."),

    // ============================================================
    // Capa de datos — Conexión SQL
    // ============================================================
    ERROR_USUARIO_CONEXION_SQL_NULA(
            "Se ha presentado un problema con la conexión a la base de datos. Por favor intente de nuevo y si el problema persiste contacte al administrador del sistema."),
    ERROR_TECNICO_CONEXION_SQL_NULA(
            "La conexión recibida para llevar a cabo la operación contra la base de datos es nula."),

    ERROR_USUARIO_CONEXION_SQL_CERRADA(
            "Se ha presentado un problema con la conexión a la base de datos. Por favor intente de nuevo y si el problema persiste contacte al administrador del sistema."),
    ERROR_TECNICO_CONEXION_SQL_CERRADA(
            "La conexión requerida para llevar a cabo la operación contra la base de datos está cerrada."),

    ERROR_USUARIO_CONEXION_SQL_ABRIR(
            "No fue posible establecer la conexión con la base de datos. Por favor intente de nuevo más tarde."),
    ERROR_TECNICO_CONEXION_SQL_ABRIR(
            "Se presentó una SQLException al intentar establecer la conexión con la base de datos. Revise el log para más detalles."),

    ERROR_USUARIO_CONEXION_SQL_CERRAR(
            "No fue posible cerrar la conexión con la base de datos."),
    ERROR_TECNICO_CONEXION_SQL_CERRAR(
            "Se presentó una SQLException al ejecutar connection.close(). Revise el log para más detalles."),

    ERROR_USUARIO_CONEXION_SQL_VALIDACION_INESPERADA(
            "Se ha presentado un problema inesperado validando el estado de la conexión a la base de datos."),
    ERROR_TECNICO_CONEXION_SQL_VALIDACION_INESPERADA(
            "Se presentó un error inesperado validando el estado de la conexión SQL. Revise el log para más detalles."),

    // ============================================================
    // Capa de datos — Transacción
    // ============================================================
    ERROR_USUARIO_TRANSACCION_NO_INICIADA(
            "Se ha presentado un problema al ejecutar la operación. Por favor intente de nuevo y si el problema persiste contacte al administrador del sistema."),
    ERROR_TECNICO_TRANSACCION_NO_INICIADA(
            "La transacción requerida para la operación contra la base de datos no ha sido iniciada (autoCommit=true)."),

    ERROR_USUARIO_TRANSACCION_YA_INICIADA(
            "Se ha presentado un problema al ejecutar la operación. Por favor intente de nuevo y si el problema persiste contacte al administrador del sistema."),
    ERROR_TECNICO_TRANSACCION_YA_INICIADA(
            "La transacción ya se encontraba iniciada antes de la operación (autoCommit=false). No puede iniciarse nuevamente."),

    ERROR_USUARIO_TRANSACCION_INICIAR(
            "No fue posible iniciar la transacción. Por favor intente de nuevo y si el problema persiste contacte al administrador del sistema."),
    ERROR_TECNICO_TRANSACCION_INICIAR(
            "Se presentó una SQLException al ejecutar connection.setAutoCommit(false). Revise el log para más detalles."),

    ERROR_USUARIO_TRANSACCION_CONFIRMAR(
            "No fue posible confirmar la transacción. Por favor intente de nuevo y si el problema persiste contacte al administrador del sistema."),
    ERROR_TECNICO_TRANSACCION_CONFIRMAR(
            "Se presentó una SQLException al ejecutar connection.commit(). Revise el log para más detalles."),

    ERROR_USUARIO_TRANSACCION_REVERTIR(
            "No fue posible deshacer la transacción. Por favor intente de nuevo y si el problema persiste contacte al administrador del sistema."),
    ERROR_TECNICO_TRANSACCION_REVERTIR(
            "Se presentó una SQLException al ejecutar connection.rollback(). Revise el log para más detalles."),

    ERROR_USUARIO_TRANSACCION_VALIDACION_INESPERADA(
            "Se ha presentado un problema inesperado validando el estado de la transacción."),
    ERROR_TECNICO_TRANSACCION_VALIDACION_INESPERADA(
            "Se presentó un error inesperado validando el estado de la transacción SQL. Revise el log para más detalles."),

    // ============================================================
    // Capa de datos — Operaciones DAO genéricas (CRUD)
    // ============================================================
    ERROR_USUARIO_DAO_CREAR_GENERICO(
            "Se ha presentado un problema al registrar la información. Por favor intente de nuevo y si el problema persiste contacte al administrador del sistema."),
    ERROR_TECNICO_DAO_CREAR_GENERICO(
            "Se produjo un error SQL ejecutando la operación INSERT en la base de datos."),

    ERROR_USUARIO_DAO_CONSULTAR_GENERICO(
            "Se ha presentado un problema al consultar la información. Por favor intente de nuevo y si el problema persiste contacte al administrador del sistema."),
    ERROR_TECNICO_DAO_CONSULTAR_GENERICO(
            "Se produjo un error SQL ejecutando la operación SELECT en la base de datos."),

    ERROR_USUARIO_DAO_MODIFICAR_GENERICO(
            "Se ha presentado un problema al modificar la información. Por favor intente de nuevo y si el problema persiste contacte al administrador del sistema."),
    ERROR_TECNICO_DAO_MODIFICAR_GENERICO(
            "Se produjo un error SQL ejecutando la operación UPDATE en la base de datos."),

    ERROR_USUARIO_DAO_ELIMINAR_GENERICO(
            "Se ha presentado un problema al eliminar la información. Por favor intente de nuevo y si el problema persiste contacte al administrador del sistema."),
    ERROR_TECNICO_DAO_ELIMINAR_GENERICO(
            "Se produjo un error SQL ejecutando la operación DELETE en la base de datos."),

    ERROR_USUARIO_DAO_OPERACION_INESPERADA(
            "Se ha presentado un problema INESPERADO ejecutando la operación contra la base de datos."),
    ERROR_TECNICO_DAO_OPERACION_INESPERADA(
            "Se produjo una excepción inesperada ejecutando la operación contra la base de datos. Revise el log para más detalles."),

    // ============================================================
    // Capa de datos — Mapper
    // ============================================================
    ERROR_USUARIO_MAPPER_GENERICO(
            "Ocurrió un problema al procesar la información obtenida de la base de datos."),
    ERROR_TECNICO_MAPPER_GENERICO(
            "Se presentó un error técnico al mapear el ResultSet a la entidad. Verifique el esquema de la consulta y los nombres de las columnas."),

    ERROR_USUARIO_MAPPER_INESPERADO(
            "Ocurrió un problema INESPERADO al procesar la información obtenida de la base de datos."),
    ERROR_TECNICO_MAPPER_INESPERADO(
            "Se presentó un error inesperado al mapear el ResultSet. Revise el log para más detalles."),

    // ============================================================
    // Capa de datos — DAOFactory
    // ============================================================
    ERROR_USUARIO_FACTORY_NO_INICIALIZADA(
            "Se ha presentado un problema interno con el sistema. Por favor contacte al administrador del sistema."),
    ERROR_TECNICO_FACTORY_NO_INICIALIZADA(
            "La fábrica de acceso a datos (DAOFactory) no ha sido inicializada o devolvió null."),

    // ============================================================
    // Capa de negocio
    // ============================================================
    ERROR_USUARIO_NEGOCIO_OPERACION_FALLIDA(
            "No fue posible completar la operación solicitada. Por favor intente de nuevo y si el problema persiste contacte al administrador del sistema."),
    ERROR_TECNICO_NEGOCIO_OPERACION_FALLIDA(
            "Se presentó un error en la capa de negocio al ejecutar la operación. Revise el log para más detalles."),

    ERROR_USUARIO_NEGOCIO_OPERACION_INESPERADA(
            "Se ha presentado un problema INESPERADO al ejecutar la operación solicitada."),
    ERROR_TECNICO_NEGOCIO_OPERACION_INESPERADA(
            "Se presentó una excepción inesperada en la capa de negocio. Revise el log para más detalles."),

    // ============================================================
    // Capa de fachada
    // ============================================================
    ERROR_USUARIO_FACHADA_OPERACION_FALLIDA(
            "No fue posible completar la operación solicitada. Por favor intente de nuevo y si el problema persiste contacte al administrador del sistema."),
    ERROR_TECNICO_FACHADA_OPERACION_FALLIDA(
            "Se presentó un error en la capa de fachada al orquestar la operación. Revise el log para más detalles."),

    ERROR_USUARIO_FACHADA_OPERACION_INESPERADA(
            "Se ha presentado un problema INESPERADO al ejecutar la operación solicitada."),
    ERROR_TECNICO_FACHADA_OPERACION_INESPERADA(
            "Se presentó una excepción inesperada en la capa de fachada. Revise el log para más detalles."),

    // ============================================================
    // Capa de controlador
    // ============================================================
    ERROR_USUARIO_CONTROLADOR_OPERACION_FALLIDA(
            "No fue posible procesar la solicitud. Por favor intente de nuevo y si el problema persiste contacte al administrador del sistema."),
    ERROR_TECNICO_CONTROLADOR_OPERACION_FALLIDA(
            "Se presentó un error en la capa de controlador al procesar la solicitud. Revise el log para más detalles."),

    ERROR_USUARIO_CONTROLADOR_OPERACION_INESPERADA(
            "Se ha presentado un problema INESPERADO al procesar la solicitud."),
    ERROR_TECNICO_CONTROLADOR_OPERACION_INESPERADA(
            "Se presentó una excepción inesperada en la capa de controlador. Revise el log para más detalles."),

    // ============================================================
    // Reglas y validadores genéricos — Mensajes parametrizados
    // (usan %s para el nombre del dato y otros valores)
    // ============================================================
    ERROR_USUARIO_DATO_TEXTO_REQUERIDO(
            "El dato [%s] es obligatorio. Por favor, ingréselo."),
    ERROR_TECNICO_DATO_TEXTO_REQUERIDO(
            "El dato [%s] llegó nulo o vacío al ejecutar la regla TextoEsObligatorioRegla."),

    ERROR_USUARIO_DATO_TEXTO_LONGITUD_INVALIDA(
            "El dato [%s] debe tener entre %s y %s caracteres."),
    ERROR_TECNICO_DATO_TEXTO_LONGITUD_INVALIDA(
            "La longitud del dato [%s] no se encuentra en el rango permitido [%s, %s]."),

    ERROR_USUARIO_DATO_ID_VALOR_DEFECTO(
            "El identificador del [%s] no puede ser el valor por defecto. Por favor verifique la información."),
    ERROR_TECNICO_DATO_ID_VALOR_DEFECTO(
            "El UUID recibido para [%s] es el valor por defecto. Verifique el origen del id enviado."),

    ERROR_USUARIO_DATO_NUMERO_NEGATIVO(
            "El dato [%s] debe ser un número positivo o cero."),
    ERROR_TECNICO_DATO_NUMERO_NEGATIVO(
            "El dato numérico [%s] llegó con valor negativo a la regla NumeroEsPositivoRegla."),

    ERROR_USUARIO_DATO_NUMERO_FUERA_DE_RANGO(
            "El dato [%s] debe estar entre %s y %s."),
    ERROR_TECNICO_DATO_NUMERO_FUERA_DE_RANGO(
            "El dato numérico [%s] está fuera del rango permitido [%s, %s] en la regla NumeroEnRangoRegla."),

    ERROR_USUARIO_DATO_FECHA_REQUERIDA(
            "La fecha [%s] es obligatoria."),
    ERROR_TECNICO_DATO_FECHA_REQUERIDA(
            "La fecha [%s] llegó como valor por defecto a la regla FechaNoEsValorPorDefectoRegla."),

    ERROR_USUARIO_REGLA_DATOS_NULOS(
            "Se ha presentado un problema validando los datos de entrada para una regla de negocio."),
    ERROR_TECNICO_REGLA_DATOS_NULOS(
            "El arreglo de parámetros pasado a una regla es nulo. Verifique la invocación de la regla."),

    ERROR_USUARIO_REGLA_PARAMETROS_INSUFICIENTES(
            "Se ha presentado un problema validando los parámetros requeridos para una regla de negocio."),
    ERROR_TECNICO_REGLA_PARAMETROS_INSUFICIENTES(
            "Una regla recibió un número de parámetros insuficiente. Verifique la invocación de la regla."),

    // ============================================================
    // Casos de uso pendientes de integración con capa de datos
    // ============================================================
    ERROR_USUARIO_CASO_USO_DAO_PENDIENTE(
            "La operación solicitada no se encuentra disponible en este momento. Por favor intente más tarde."),
    ERROR_TECNICO_CASO_USO_DAO_PENDIENTE(
            "La operación [%s] del caso de uso [%s] está pendiente de integración con la capa de datos (DAO). TODO: cablear DAOFactory."),

    // ============================================================
    // Genéricos
    // ============================================================
    ERROR_USUARIO_INESPERADO(
            "Se ha presentado un problema INESPERADO tratando de llevar a cabo la operación deseada. Por favor intente de nuevo y si el problema persiste contacte al administrador del sistema."),
    ERROR_TECNICO_INESPERADO(
            "Se presentó una excepción inesperada. Revise el log para más detalles.");

    private final String contenido;

    MensajesEnum(final String contenido) {
        this.contenido = contenido;
    }

    public String getContenido() {
        return contenido;
    }
}
