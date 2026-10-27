package co.edu.uco.imexcol.transversal;

public enum MensajesEnum {

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

    ERROR_USUARIO_CATEGORIA_NOMBRE_DUPLICADO(
            "Ya existe una categoría registrada con ese nombre. Por favor utilice un nombre diferente."),
    ERROR_TECNICO_CATEGORIA_NOMBRE_DUPLICADO(
            "Ya existe otra categoría registrada con nombre=%s."),

    ERROR_USUARIO_ENVIO_FECHA_ENTREGA_ANTERIOR_A_FECHA_ENVIO(
            "La fecha de entrega no puede ser anterior a la fecha de envío."),
    ERROR_TECNICO_ENVIO_FECHA_ENTREGA_ANTERIOR_A_FECHA_ENVIO(
            "Inconsistencia detectada en Envio: fechaEntrega=%s anterior a fechaEnvio=%s."),

    ERROR_USUARIO_LINEA_PEDIDO_SUBTOTAL_INCONSISTENTE(
            "El subtotal de la línea del pedido no coincide con cantidad × precio unitario. Por favor verifique los valores."),
    ERROR_TECNICO_LINEA_PEDIDO_SUBTOTAL_INCONSISTENTE(
            "Inconsistencia detectada en LineaPedido: subtotal=%s, esperado=%s (cantidad=%s * precioUnitario=%s)."),

    ERROR_USUARIO_DIRECCION_DUPLICADA(
            "Ya existe una dirección registrada con la misma calle, ciudad y departamento para este cliente."),
    ERROR_TECNICO_DIRECCION_DUPLICADA(
            "Ya existe otra dirección del cliente=%s con calle=%s, ciudad=%s y departamento=%s."),

    ERROR_USUARIO_CATEGORIA_CON_PRODUCTOS(
            "No es posible eliminar la categoría porque tiene productos asociados."),
    ERROR_TECNICO_CATEGORIA_CON_PRODUCTOS(
            "La categoría id=%s tiene %s producto(s) asociado(s) y no puede eliminarse."),

    ERROR_USUARIO_PRODUCTO_CON_PEDIDOS(
            "No es posible eliminar el producto porque ya hace parte de pedidos registrados. Se recomienda desactivarlo."),
    ERROR_TECNICO_PRODUCTO_CON_PEDIDOS(
            "El producto id=%s aparece en %s línea(s) de pedido y no puede eliminarse."),

    ERROR_USUARIO_CLIENTE_CORREO_DUPLICADO(
            "Ya existe un cliente registrado con ese correo electrónico. Por favor utilice uno diferente."),
    ERROR_TECNICO_CLIENTE_CORREO_DUPLICADO(
            "Ya existe otro cliente registrado con correoElectronico=%s."),

    ERROR_USUARIO_ADMINISTRADOR_NOMBRE_USUARIO_DUPLICADO(
            "Ya existe un administrador con ese nombre de usuario. Por favor utilice uno diferente."),
    ERROR_TECNICO_ADMINISTRADOR_NOMBRE_USUARIO_DUPLICADO(
            "Ya existe otro administrador registrado con nombreUsuario=%s."),

    ERROR_USUARIO_ADMINISTRADOR_UNICO_NO_ELIMINABLE(
            "No es posible eliminar al único administrador registrado en el sistema."),
    ERROR_TECNICO_ADMINISTRADOR_UNICO_NO_ELIMINABLE(
            "Se intentó eliminar el administrador id=%s, que es el único registrado."),

    ERROR_USUARIO_METODO_PAGO_NOMBRE_DUPLICADO(
            "Ya existe un método de pago con ese nombre. Por favor utilice uno diferente."),
    ERROR_TECNICO_METODO_PAGO_NOMBRE_DUPLICADO(
            "Ya existe otro método de pago registrado con nombre=%s."),

    ERROR_USUARIO_CARRITO_COMPRAS_ACTIVO_DUPLICADO(
            "El cliente ya tiene un carrito de compras activo. Debe usar ese carrito antes de crear uno nuevo."),
    ERROR_TECNICO_CARRITO_COMPRAS_ACTIVO_DUPLICADO(
            "El cliente id=%s ya tiene otro carrito en estado %s."),

    ERROR_USUARIO_CARRITO_COMPRAS_NO_PERTENECE_AL_CLIENTE(
            "El carrito de compras no pertenece al cliente indicado."),
    ERROR_TECNICO_CARRITO_COMPRAS_NO_PERTENECE_AL_CLIENTE(
            "El carrito id=%s pertenece al cliente id=%s y no al cliente id=%s."),

    ERROR_USUARIO_CARRITO_COMPRAS_YA_CONVERTIDO(
            "No es posible vaciar el carrito porque ya fue convertido en un pedido."),
    ERROR_TECNICO_CARRITO_COMPRAS_YA_CONVERTIDO(
            "El carrito id=%s está en estado %s y no puede vaciarse."),

    ERROR_USUARIO_CARRITO_COMPRAS_SIN_ITEMS_PARA_CONVERTIR(
            "El carrito solo puede marcarse como convertido si tiene al menos un producto."),
    ERROR_TECNICO_CARRITO_COMPRAS_SIN_ITEMS_PARA_CONVERTIR(
            "El carrito id=%s no tiene ítems y no puede pasar a estado %s."),

    ERROR_USUARIO_LINEA_PEDIDO_PRECIO_DIFERENTE_AL_PRODUCTO(
            "El precio de la línea del pedido debe ser igual al precio del producto al momento de la compra."),
    ERROR_TECNICO_LINEA_PEDIDO_PRECIO_DIFERENTE_AL_PRODUCTO(
            "El precioUnitario=%s de la línea no coincide con el precio=%s del producto id=%s."),

    ERROR_USUARIO_LINEA_PEDIDO_PRECIO_CONGELADO_MODIFICADO(
            "El precio congelado de una línea de pedido no puede modificarse."),
    ERROR_TECNICO_LINEA_PEDIDO_PRECIO_CONGELADO_MODIFICADO(
            "Se intentó cambiar el precioUnitario de la línea id=%s de %s a %s."),

    ERROR_USUARIO_LINEA_PEDIDO_PEDIDO_NO_ENTREGADO(
            "Solo se puede devolver una línea de un pedido que ya fue entregado."),
    ERROR_TECNICO_LINEA_PEDIDO_PEDIDO_NO_ENTREGADO(
            "La línea id=%s pertenece al pedido id=%s en estado %s; se requiere el estado %s."),

    ERROR_USUARIO_PEDIDO_NO_PAGADO(
            "El pedido aún no tiene un pago aprobado, por lo que no puede despacharse."),
    ERROR_TECNICO_PEDIDO_NO_PAGADO(
            "El pedido id=%s no tiene ningún pago en estado %s."),

    ERROR_USUARIO_PEDIDO_CON_ENVIO_ACTIVO(
            "El pedido ya tiene un envío activo. No es posible registrar otro."),
    ERROR_TECNICO_PEDIDO_CON_ENVIO_ACTIVO(
            "El pedido id=%s ya tiene el envío id=%s en estado %s."),

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

    ERROR_USUARIO_VARIABLE_ENTORNO_NO_DEFINIDA(
            "No fue posible establecer la conexión con la base de datos porque falta configuración del sistema. Por favor contacte al administrador del sistema."),
    ERROR_TECNICO_VARIABLE_ENTORNO_NO_DEFINIDA(
            "La variable de entorno requerida [%s] no está definida o está vacía y no tiene valor por defecto en application.properties."),

    ERROR_USUARIO_CONEXION_SQL_CERRAR(
            "No fue posible cerrar la conexión con la base de datos."),
    ERROR_TECNICO_CONEXION_SQL_CERRAR(
            "Se presentó una SQLException al ejecutar connection.close(). Revise el log para más detalles."),

    ERROR_USUARIO_CONEXION_SQL_VALIDACION_INESPERADA(
            "Se ha presentado un problema inesperado validando el estado de la conexión a la base de datos."),
    ERROR_TECNICO_CONEXION_SQL_VALIDACION_INESPERADA(
            "Se presentó un error inesperado validando el estado de la conexión SQL. Revise el log para más detalles."),

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

    ERROR_USUARIO_UUID_FORMATO_INVALIDO(
            "Se ha presentado un problema procesando un identificador de la información solicitada. Por favor intente de nuevo y si el problema persiste contacte al administrador del sistema."),
    ERROR_TECNICO_UUID_FORMATO_INVALIDO(
            "El texto [%s] no tiene el formato de un UUID válido y no pudo convertirse con UUID.fromString."),

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

    ERROR_USUARIO_MAPPER_GENERICO(
            "Ocurrió un problema al procesar la información obtenida de la base de datos."),
    ERROR_TECNICO_MAPPER_GENERICO(
            "Se presentó un error técnico al mapear el ResultSet a la entidad. Verifique el esquema de la consulta y los nombres de las columnas."),

    ERROR_USUARIO_MAPPER_INESPERADO(
            "Ocurrió un problema INESPERADO al procesar la información obtenida de la base de datos."),
    ERROR_TECNICO_MAPPER_INESPERADO(
            "Se presentó un error inesperado al mapear el ResultSet. Revise el log para más detalles."),

    ERROR_USUARIO_FACTORY_NO_INICIALIZADA(
            "Se ha presentado un problema interno con el sistema. Por favor contacte al administrador del sistema."),
    ERROR_TECNICO_FACTORY_NO_INICIALIZADA(
            "La fábrica de acceso a datos (DAOFactory) no ha sido inicializada o devolvió null."),

    ERROR_USUARIO_NEGOCIO_OPERACION_FALLIDA(
            "No fue posible completar la operación solicitada. Por favor intente de nuevo y si el problema persiste contacte al administrador del sistema."),
    ERROR_TECNICO_NEGOCIO_OPERACION_FALLIDA(
            "Se presentó un error en la capa de negocio al ejecutar la operación. Revise el log para más detalles."),

    ERROR_USUARIO_NEGOCIO_OPERACION_INESPERADA(
            "Se ha presentado un problema INESPERADO al ejecutar la operación solicitada."),
    ERROR_TECNICO_NEGOCIO_OPERACION_INESPERADA(
            "Se presentó una excepción inesperada en la capa de negocio. Revise el log para más detalles."),

    ERROR_USUARIO_FACHADA_OPERACION_FALLIDA(
            "No fue posible completar la operación solicitada. Por favor intente de nuevo y si el problema persiste contacte al administrador del sistema."),
    ERROR_TECNICO_FACHADA_OPERACION_FALLIDA(
            "Se presentó un error en la capa de fachada al orquestar la operación. Revise el log para más detalles."),

    ERROR_USUARIO_FACHADA_OPERACION_INESPERADA(
            "Se ha presentado un problema INESPERADO al ejecutar la operación solicitada."),
    ERROR_TECNICO_FACHADA_OPERACION_INESPERADA(
            "Se presentó una excepción inesperada en la capa de fachada. Revise el log para más detalles."),

    ERROR_USUARIO_CONTROLADOR_OPERACION_FALLIDA(
            "No fue posible procesar la solicitud. Por favor intente de nuevo y si el problema persiste contacte al administrador del sistema."),
    ERROR_TECNICO_CONTROLADOR_OPERACION_FALLIDA(
            "Se presentó un error en la capa de controlador al procesar la solicitud. Revise el log para más detalles."),

    ERROR_USUARIO_CONTROLADOR_OPERACION_INESPERADA(
            "Se ha presentado un problema INESPERADO al procesar la solicitud."),
    ERROR_TECNICO_CONTROLADOR_OPERACION_INESPERADA(
            "Se presentó una excepción inesperada en la capa de controlador. Revise el log para más detalles."),

    ERROR_USUARIO_RECURSO_NO_ENCONTRADO(
            "El recurso solicitado no existe. Por favor verifique la dirección e intente de nuevo."),
    ERROR_TECNICO_RECURSO_NO_ENCONTRADO(
            "No existe un recurso que atienda la petición %s /%s."),

    ERROR_USUARIO_SOLICITUD_INVALIDA(
            "La solicitud enviada está vacía o no tiene un formato válido. Por favor verifique la información e intente de nuevo."),
    ERROR_TECNICO_SOLICITUD_INVALIDA(
            "No fue posible leer la petición HTTP: el cuerpo está vacío, el JSON está mal formado o un parámetro no tiene el tipo esperado."),

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

    ERROR_USUARIO_DATO_ESTADO_INVALIDO(
            "El valor [%s] no es válido para [%s]. Los valores permitidos son: %s."),
    ERROR_TECNICO_DATO_ESTADO_INVALIDO(
            "El estado [%s] del dato [%s] no se encuentra en el conjunto permitido [%s]."),

    ERROR_USUARIO_DATO_NUMERO_NO_MAYOR_A_CERO(
            "El dato [%s] debe ser un número mayor a cero."),
    ERROR_TECNICO_DATO_NUMERO_NO_MAYOR_A_CERO(
            "El dato numérico [%s] llegó con un valor menor o igual a cero a la regla NumeroEsMayorACeroRegla."),

    EXITO_ADMINISTRADOR_CONSULTA(
            "Administradores consultados satisfactoriamente."),

    ERROR_USUARIO_ADMINISTRADOR_NO_ENCONTRADO(
            "No se encontró el administrador con el identificador solicitado."),
    ERROR_TECNICO_ADMINISTRADOR_NO_ENCONTRADO(
            "No existe un registro de administrador con id=%s."),

    EXITO_ADMINISTRADOR_CONSULTA_POR_ID(
            "Administrador consultado satisfactoriamente."),

    EXITO_ADMINISTRADOR_REGISTRO(
            "Administrador registrado satisfactoriamente."),

    EXITO_ADMINISTRADOR_ACTUALIZACION(
            "Administrador actualizado satisfactoriamente."),

    EXITO_ADMINISTRADOR_ELIMINACION(
            "Administrador eliminado satisfactoriamente."),

    ERROR_USUARIO_AUTENTICACION_CLIENTE_CREDENCIALES_OBLIGATORIAS(
            "Correo electrónico y contraseña son obligatorios."),

    ERROR_USUARIO_AUTENTICACION_CLIENTE_CREDENCIALES_INVALIDAS(
            "Correo o contraseña inválidos."),

    EXITO_AUTENTICACION(
            "Autenticación exitosa."),

    ERROR_USUARIO_AUTENTICACION_ADMINISTRADOR_CREDENCIALES_OBLIGATORIAS(
            "Nombre de usuario y contraseña son obligatorios."),

    ERROR_USUARIO_AUTENTICACION_ADMINISTRADOR_CREDENCIALES_INVALIDAS(
            "Usuario o contraseña inválidos."),

    EXITO_AUTENTICACION_REGISTRO_CLIENTE(
            "Cliente registrado satisfactoriamente. Ya puede iniciar sesión."),

    EXITO_CARRITO_COMPRAS_CONSULTA(
            "Carritos de compras consultados satisfactoriamente."),

    ERROR_USUARIO_CARRITO_COMPRAS_NO_ENCONTRADO(
            "No se encontró el carrito de compras con el identificador solicitado."),
    ERROR_TECNICO_CARRITO_COMPRAS_NO_ENCONTRADO(
            "No existe un registro de carrito de compras con id=%s."),

    EXITO_CARRITO_COMPRAS_CONSULTA_POR_ID(
            "Carrito de compras consultado satisfactoriamente."),

    EXITO_CARRITO_COMPRAS_REGISTRO(
            "Carrito de compras registrado satisfactoriamente."),

    EXITO_CARRITO_COMPRAS_ACTUALIZACION(
            "Carrito de compras actualizado satisfactoriamente."),

    EXITO_CARRITO_COMPRAS_ELIMINACION(
            "Carrito de compras eliminado satisfactoriamente."),

    EXITO_CATEGORIA_CONSULTA(
            "Categorías consultadas satisfactoriamente."),

    ERROR_USUARIO_CATEGORIA_NO_ENCONTRADO(
            "No se encontró la categoría con el identificador solicitado."),
    ERROR_TECNICO_CATEGORIA_NO_ENCONTRADO(
            "No existe un registro de categoría con id=%s."),

    EXITO_CATEGORIA_CONSULTA_POR_ID(
            "Categoría consultada satisfactoriamente."),

    EXITO_CATEGORIA_REGISTRO(
            "Categoría registrada satisfactoriamente."),

    EXITO_CATEGORIA_ACTUALIZACION(
            "Categoría actualizada satisfactoriamente."),

    EXITO_CATEGORIA_ELIMINACION(
            "Categoría eliminada satisfactoriamente."),

    EXITO_CLIENTE_CONSULTA(
            "Clientes consultados satisfactoriamente."),

    ERROR_USUARIO_CLIENTE_NO_ENCONTRADO(
            "No se encontró el cliente con el identificador solicitado."),
    ERROR_TECNICO_CLIENTE_NO_ENCONTRADO(
            "No existe un registro de cliente con id=%s."),

    EXITO_CLIENTE_CONSULTA_POR_ID(
            "Cliente consultado satisfactoriamente."),

    EXITO_CLIENTE_REGISTRO(
            "Cliente registrado satisfactoriamente."),

    EXITO_CLIENTE_ACTUALIZACION(
            "Cliente actualizado satisfactoriamente."),

    EXITO_CLIENTE_ELIMINACION(
            "Cliente eliminado satisfactoriamente."),

    EXITO_DIRECCION_CONSULTA(
            "Direcciones consultadas satisfactoriamente."),

    ERROR_USUARIO_DIRECCION_NO_ENCONTRADO(
            "No se encontró la dirección con el identificador solicitado."),
    ERROR_TECNICO_DIRECCION_NO_ENCONTRADO(
            "No existe un registro de dirección con id=%s."),

    EXITO_DIRECCION_CONSULTA_POR_ID(
            "Dirección consultada satisfactoriamente."),

    EXITO_DIRECCION_REGISTRO(
            "Dirección registrada satisfactoriamente."),

    EXITO_DIRECCION_ACTUALIZACION(
            "Dirección actualizada satisfactoriamente."),

    EXITO_DIRECCION_ELIMINACION(
            "Dirección eliminada satisfactoriamente."),

    EXITO_ENVIO_CONSULTA(
            "Envíos consultados satisfactoriamente."),

    ERROR_USUARIO_ENVIO_NO_ENCONTRADO(
            "No se encontró el envío con el identificador solicitado."),
    ERROR_TECNICO_ENVIO_NO_ENCONTRADO(
            "No existe un registro de envío con id=%s."),

    EXITO_ENVIO_CONSULTA_POR_ID(
            "Envío consultado satisfactoriamente."),

    EXITO_ENVIO_REGISTRO(
            "Envío registrado satisfactoriamente."),

    EXITO_ENVIO_ACTUALIZACION(
            "Envío actualizado satisfactoriamente."),

    EXITO_ENVIO_ELIMINACION(
            "Envío eliminado satisfactoriamente."),

    EXITO_ITEM_CARRITO_CONSULTA(
            "Ítems del carrito consultados satisfactoriamente."),

    ERROR_USUARIO_ITEM_CARRITO_NO_ENCONTRADO(
            "No se encontró el ítem del carrito con el identificador solicitado."),
    ERROR_TECNICO_ITEM_CARRITO_NO_ENCONTRADO(
            "No existe un registro de ítem del carrito con id=%s."),

    EXITO_ITEM_CARRITO_CONSULTA_POR_ID(
            "Ítem del carrito consultado satisfactoriamente."),

    EXITO_ITEM_CARRITO_REGISTRO(
            "Ítem del carrito registrado satisfactoriamente."),

    EXITO_ITEM_CARRITO_ACTUALIZACION(
            "Ítem del carrito actualizado satisfactoriamente."),

    EXITO_ITEM_CARRITO_ELIMINACION(
            "Ítem del carrito eliminado satisfactoriamente."),

    EXITO_LINEA_PEDIDO_CONSULTA(
            "Líneas de pedido consultadas satisfactoriamente."),

    ERROR_USUARIO_LINEA_PEDIDO_NO_ENCONTRADO(
            "No se encontró la línea de pedido con el identificador solicitado."),
    ERROR_TECNICO_LINEA_PEDIDO_NO_ENCONTRADO(
            "No existe un registro de línea de pedido con id=%s."),

    EXITO_LINEA_PEDIDO_CONSULTA_POR_ID(
            "Línea de pedido consultada satisfactoriamente."),

    EXITO_LINEA_PEDIDO_REGISTRO(
            "Línea de pedido registrada satisfactoriamente."),

    EXITO_LINEA_PEDIDO_ACTUALIZACION(
            "Línea de pedido actualizada satisfactoriamente."),

    EXITO_LINEA_PEDIDO_ELIMINACION(
            "Línea de pedido eliminada satisfactoriamente."),

    EXITO_METODO_PAGO_CONSULTA(
            "Métodos de pago consultados satisfactoriamente."),

    ERROR_USUARIO_METODO_PAGO_NO_ENCONTRADO(
            "No se encontró el método de pago con el identificador solicitado."),
    ERROR_TECNICO_METODO_PAGO_NO_ENCONTRADO(
            "No existe un registro de método de pago con id=%s."),

    EXITO_METODO_PAGO_CONSULTA_POR_ID(
            "Método de pago consultado satisfactoriamente."),

    EXITO_METODO_PAGO_REGISTRO(
            "Método de pago registrado satisfactoriamente."),

    EXITO_METODO_PAGO_ACTUALIZACION(
            "Método de pago actualizado satisfactoriamente."),

    EXITO_METODO_PAGO_ELIMINACION(
            "Método de pago eliminado satisfactoriamente."),

    EXITO_PAGO_CONSULTA(
            "Pagos consultados satisfactoriamente."),

    ERROR_USUARIO_PAGO_NO_ENCONTRADO(
            "No se encontró el pago con el identificador solicitado."),
    ERROR_TECNICO_PAGO_NO_ENCONTRADO(
            "No existe un registro de pago con id=%s."),

    EXITO_PAGO_CONSULTA_POR_ID(
            "Pago consultado satisfactoriamente."),

    EXITO_PAGO_REGISTRO(
            "Pago registrado satisfactoriamente."),

    EXITO_PAGO_ACTUALIZACION(
            "Pago actualizado satisfactoriamente."),

    EXITO_PAGO_ELIMINACION(
            "Pago eliminado satisfactoriamente."),

    EXITO_PEDIDO_CONSULTA(
            "Pedidos consultados satisfactoriamente."),

    ERROR_USUARIO_PEDIDO_NO_ENCONTRADO(
            "No se encontró el pedido con el identificador solicitado."),
    ERROR_TECNICO_PEDIDO_NO_ENCONTRADO(
            "No existe un registro de pedido con id=%s."),

    EXITO_PEDIDO_CONSULTA_POR_ID(
            "Pedido consultado satisfactoriamente."),

    EXITO_PEDIDO_REGISTRO(
            "Pedido registrado satisfactoriamente."),

    EXITO_PEDIDO_ACTUALIZACION(
            "Pedido actualizado satisfactoriamente."),

    EXITO_PEDIDO_ELIMINACION(
            "Pedido eliminado satisfactoriamente."),

    EXITO_PRODUCTO_CONSULTA(
            "Productos consultados satisfactoriamente."),

    ERROR_USUARIO_PRODUCTO_NO_ENCONTRADO(
            "No se encontró el producto con el identificador solicitado."),
    ERROR_TECNICO_PRODUCTO_NO_ENCONTRADO(
            "No existe un registro de producto con id=%s."),

    EXITO_PRODUCTO_CONSULTA_POR_ID(
            "Producto consultado satisfactoriamente."),

    EXITO_PRODUCTO_REGISTRO(
            "Producto registrado satisfactoriamente."),

    EXITO_PRODUCTO_ACTUALIZACION(
            "Producto actualizado satisfactoriamente."),

    EXITO_PRODUCTO_ELIMINACION(
            "Producto eliminado satisfactoriamente."),

    EXITO_FACTURA_CONSULTA(
            "Facturas consultadas satisfactoriamente."),

    EXITO_FACTURA_CONSULTA_POR_ID(
            "Factura consultada satisfactoriamente."),

    EXITO_FACTURA_CONSULTA_POR_PEDIDO(
            "Factura del pedido consultada satisfactoriamente."),

    EXITO_FACTURA_REGISTRO(
            "Factura generada satisfactoriamente."),

    ERROR_USUARIO_FACTURA_NO_ENCONTRADA(
            "No se encontró la factura con el identificador solicitado."),
    ERROR_TECNICO_FACTURA_NO_ENCONTRADA(
            "No existe un registro de factura con id=%s."),

    ERROR_USUARIO_FACTURA_TOTAL_INCONSISTENTE(
            "La sumatoria del subtotal más los impuestos no coincide con el total facturado."),
    ERROR_TECNICO_FACTURA_TOTAL_INCONSISTENTE(
            "Inconsistencia detectada en Factura: totalFacturado=%s, subtotal=%s, totalImpuestos=%s, suma esperada=%s."),

    ERROR_USUARIO_FACTURA_NUMERO_DUPLICADO(
            "Ya existe una factura con el número generado. Por favor intente de nuevo."),
    ERROR_TECNICO_FACTURA_NUMERO_DUPLICADO(
            "Ya existe una factura registrada con numeroFactura=%s."),

    ERROR_USUARIO_PAGO_NO_APROBADO(
            "El pago asociado no existe o aún no ha sido aprobado, por lo que no puede facturarse."),
    ERROR_TECNICO_PAGO_NO_APROBADO(
            "El pago id=%s está en estado [%s]; se requiere el estado %s."),

    ERROR_USUARIO_PAGO_CON_FACTURA(
            "El pago ya tiene una factura emitida."),
    ERROR_TECNICO_PAGO_CON_FACTURA(
            "El pago id=%s ya está asociado a la factura id=%s."),

    ERROR_USUARIO_PEDIDO_CON_FACTURA(
            "El pedido ya tiene una factura emitida."),
    ERROR_TECNICO_PEDIDO_CON_FACTURA(
            "El pedido id=%s ya está asociado a la factura id=%s."),

    ERROR_USUARIO_PEDIDO_SIN_FACTURA(
            "El pedido aún no tiene una factura emitida."),
    ERROR_TECNICO_PEDIDO_SIN_FACTURA(
            "No existe una factura asociada a los pagos del pedido id=%s."),

    ERROR_USUARIO_PEDIDO_SIN_PAGO_APROBADO_PARA_FACTURAR(
            "El pedido aún no tiene un pago aprobado, por lo que no puede facturarse."),
    ERROR_TECNICO_PEDIDO_SIN_PAGO_APROBADO_PARA_FACTURAR(
            "El pedido id=%s no tiene ningún pago en estado %s."),

    ERROR_USUARIO_PEDIDO_NO_PERTENECE_AL_CLIENTE(
            "Acceso denegado: el pedido no pertenece al cliente indicado."),
    ERROR_TECNICO_PEDIDO_NO_PERTENECE_AL_CLIENTE(
            "El pedido id=%s pertenece al cliente id=%s y no al cliente id=%s."),

    EXITO_RESENA_CONSULTA(
            "Reseñas consultadas satisfactoriamente."),

    EXITO_RESENA_CONSULTA_POR_ID(
            "Reseña consultada satisfactoriamente."),

    EXITO_RESENA_REGISTRO(
            "Reseña publicada satisfactoriamente."),

    EXITO_RESENA_ELIMINACION(
            "Reseña eliminada satisfactoriamente."),

    ERROR_USUARIO_RESENA_NO_ENCONTRADA(
            "La reseña solicitada no se encuentra registrada."),
    ERROR_TECNICO_RESENA_NO_ENCONTRADA(
            "No existe un registro de reseña con id=%s."),

    ERROR_USUARIO_RESENA_CALIFICACION_FUERA_DE_RANGO(
            "La calificación debe estar entre %s y %s."),
    ERROR_TECNICO_RESENA_CALIFICACION_FUERA_DE_RANGO(
            "La calificación=%s está fuera del rango permitido [%s, %s]."),

    ERROR_USUARIO_RESENA_COMPRA_NO_VERIFICADA(
            "No puede calificar un producto que no ha comprado."),
    ERROR_TECNICO_RESENA_COMPRA_NO_VERIFICADA(
            "El cliente id=%s no tiene pedidos con compra exitosa que incluyan el producto id=%s."),

    ERROR_USUARIO_RESENA_DUPLICADA(
            "Ya publicó una reseña para este producto."),
    ERROR_TECNICO_RESENA_DUPLICADA(
            "El cliente id=%s ya tiene la reseña id=%s para el producto id=%s."),

    ERROR_USUARIO_RESENA_NO_PERTENECE_AL_CLIENTE(
            "Acceso denegado: solo puede eliminar sus propias reseñas."),
    ERROR_TECNICO_RESENA_NO_PERTENECE_AL_CLIENTE(
            "La reseña id=%s pertenece al cliente id=%s y no al cliente id=%s."),

    EXITO_IMAGEN_PRODUCTO_CONSULTA(
            "Imágenes del producto consultadas satisfactoriamente."),

    EXITO_IMAGEN_PRODUCTO_REGISTRO(
            "Imagen agregada al producto satisfactoriamente."),

    EXITO_IMAGEN_PRODUCTO_ACTUALIZACION(
            "Imagen del producto modificada satisfactoriamente."),

    EXITO_IMAGEN_PRODUCTO_ELIMINACION(
            "Imagen del producto eliminada satisfactoriamente."),

    ERROR_USUARIO_IMAGEN_PRODUCTO_NO_ENCONTRADA(
            "La imagen solicitada no se encuentra registrada."),
    ERROR_TECNICO_IMAGEN_PRODUCTO_NO_ENCONTRADA(
            "No existe un registro de imagen de producto con id=%s."),

    ERROR_USUARIO_IMAGEN_PRODUCTO_EXTENSION_NO_PERMITIDA(
            "El formato del archivo no es permitido. Use una de estas extensiones: %s."),
    ERROR_TECNICO_IMAGEN_PRODUCTO_EXTENSION_NO_PERMITIDA(
            "La foto [%s] no termina en una extensión permitida [%s]."),

    ERROR_USUARIO_IMAGEN_PRODUCTO_ORDEN_FUERA_DE_RANGO(
            "El orden de visualización debe estar entre %s y %s."),
    ERROR_TECNICO_IMAGEN_PRODUCTO_ORDEN_FUERA_DE_RANGO(
            "ordenVisualizacion=%s está fuera del rango permitido [%s, %s]."),

    ERROR_USUARIO_IMAGEN_PRODUCTO_ORDEN_OCUPADO(
            "El orden de visualización ya está ocupado por otra imagen del producto."),
    ERROR_TECNICO_IMAGEN_PRODUCTO_ORDEN_OCUPADO(
            "El producto id=%s ya tiene la imagen id=%s con ordenVisualizacion=%s."),

    ERROR_USUARIO_IMAGEN_PRODUCTO_FOTO_DUPLICADA(
            "Ya existe una imagen registrada con esa ruta."),
    ERROR_TECNICO_IMAGEN_PRODUCTO_FOTO_DUPLICADA(
            "La foto [%s] ya está registrada en la imagen id=%s."),

    ERROR_USUARIO_IMAGEN_PRODUCTO_NO_PERTENECE_AL_PRODUCTO(
            "La imagen no pertenece al producto indicado."),
    ERROR_TECNICO_IMAGEN_PRODUCTO_NO_PERTENECE_AL_PRODUCTO(
            "La imagen id=%s pertenece al producto id=%s y no al producto id=%s."),

    ERROR_USUARIO_PRODUCTO_DEBE_CONSERVAR_IMAGEN(
            "No se puede eliminar la imagen porque el producto debe conservar al menos una."),
    ERROR_TECNICO_PRODUCTO_DEBE_CONSERVAR_IMAGEN(
            "El producto id=%s tiene %s imagen(es); el mínimo que debe conservar es %s."),

    EXITO_EVENTO_RASTREO_CONSULTA(
            "Historial de rastreo consultado satisfactoriamente."),

    EXITO_EVENTO_RASTREO_REGISTRO(
            "Novedad de rastreo registrada satisfactoriamente."),

    ERROR_USUARIO_EVENTO_RASTREO_ENVIO_NO_ACTIVO(
            "Solo se pueden registrar novedades de rastreo en envíos que estén en preparación o en tránsito."),
    ERROR_TECNICO_EVENTO_RASTREO_ENVIO_NO_ACTIVO(
            "El envío id=%s está en estado [%s], que no es un estado activo."),

    ERROR_USUARIO_EVENTO_RASTREO_FECHA_ANTERIOR_AL_DESPACHO(
            "La fecha del evento no puede ser anterior a la fecha de despacho del envío."),
    ERROR_TECNICO_EVENTO_RASTREO_FECHA_ANTERIOR_AL_DESPACHO(
            "Inconsistencia cronológica: fechaHora=%s es anterior a fechaEnvio=%s del envío id=%s."),

    ERROR_USUARIO_EVENTO_RASTREO_DUPLICADO(
            "Ya existe un evento con el mismo estado y la misma fecha para este envío."),
    ERROR_TECNICO_EVENTO_RASTREO_DUPLICADO(
            "El envío id=%s ya tiene el evento id=%s con estadoLogistico=%s y fechaHora=%s."),

    ERROR_USUARIO_EVENTO_RASTREO_OBSERVACION_REQUERIDA(
            "La observación es obligatoria cuando el estado logístico es %s."),
    ERROR_TECNICO_EVENTO_RASTREO_OBSERVACION_REQUERIDA(
            "Se recibió un evento con estadoLogistico=%s sin observación."),

    ERROR_USUARIO_ENVIO_NO_PERTENECE_AL_CLIENTE(
            "Acceso denegado: el envío no pertenece al cliente indicado."),
    ERROR_TECNICO_ENVIO_NO_PERTENECE_AL_CLIENTE(
            "El envío id=%s pertenece al pedido id=%s del cliente id=%s y no al cliente id=%s."),

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
