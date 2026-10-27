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
            "Ya existe una dirección registrada con la misma calle, ciudad, departamento y país para este cliente."),
    ERROR_TECNICO_DIRECCION_DUPLICADA(
            "Intento de inserción de dirección duplicada para cliente=%s (calle=%s, ciudad=%s, departamento=%s, pais=%s)."),

    ERROR_USUARIO_CATEGORIA_CON_PRODUCTOS(
            "No es posible eliminar la categoría porque tiene productos asociados."),
    ERROR_TECNICO_CATEGORIA_CON_PRODUCTOS(
            "La categoría id=%s tiene %s producto(s) asociado(s) y no puede eliminarse."),

    ERROR_USUARIO_PRODUCTO_CON_PEDIDOS(
            "No es posible eliminar el producto porque ya hace parte de pedidos registrados. Se recomienda desactivarlo."),
    ERROR_TECNICO_PRODUCTO_CON_PEDIDOS(
            "El producto id=%s aparece en %s línea(s) de pedido y no puede eliminarse."),

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

    ERROR_USUARIO_CASO_USO_DAO_PENDIENTE(
            "La operación solicitada no se encuentra disponible en este momento. Por favor intente más tarde."),
    ERROR_TECNICO_CASO_USO_DAO_PENDIENTE(
            "La operación [%s] del caso de uso [%s] está pendiente de integración con la capa de datos (DAO)."),

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
