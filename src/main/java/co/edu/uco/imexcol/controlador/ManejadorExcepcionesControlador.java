package co.edu.uco.imexcol.controlador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import co.edu.uco.imexcol.controlador.dto.Respuesta;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolAutenticacionException;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolControladorException;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolDatosException;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolFachadaException;

@RestControllerAdvice
public final class ManejadorExcepcionesControlador {

    private static final Logger REGISTRO = LoggerFactory.getLogger(ManejadorExcepcionesControlador.class);

    @ExceptionHandler(ImexcolAutenticacionException.class)
    public ResponseEntity<Respuesta<Object>> manejarAutenticacionFallida(
            final ImexcolAutenticacionException excepcion) {
        REGISTRO.warn(excepcion.getMensajeTecnico());
        return responderFallida(excepcion.getMensajeUsuario(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ ImexcolDatosException.class, ImexcolFachadaException.class })
    public ResponseEntity<Respuesta<Object>> manejarErrorTecnico(final ImexcolException excepcion) {
        REGISTRO.error(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
        return responderFallida(excepcion.getMensajeUsuario(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ImexcolException.class)
    public ResponseEntity<Respuesta<Object>> manejarExcepcionImexcol(final ImexcolException excepcion) {
        REGISTRO.warn(excepcion.getMensajeTecnico());
        return responderFallida(excepcion.getMensajeUsuario(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Respuesta<Object>> manejarRecursoNoEncontrado(final NoResourceFoundException excepcion) {
        registrarAdvertencia(MensajesEnum.ERROR_TECNICO_RECURSO_NO_ENCONTRADO,
                excepcion.getHttpMethod(), excepcion.getResourcePath());
        return responderFallida(MensajesEnum.ERROR_USUARIO_RECURSO_NO_ENCONTRADO.getContenido(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Respuesta<Object>> manejarSolicitudInvalida(final Exception excepcion) {
        registrarAdvertencia(MensajesEnum.ERROR_TECNICO_SOLICITUD_INVALIDA, excepcion.getMessage());
        return responderFallida(MensajesEnum.ERROR_USUARIO_SOLICITUD_INVALIDA.getContenido(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Respuesta<Object>> manejarMetodoNoSoportado(
            final HttpRequestMethodNotSupportedException excepcion) {
        registrarAdvertencia(MensajesEnum.ERROR_TECNICO_METODO_NO_SOPORTADO,
                excepcion.getMethod(), excepcion.getSupportedHttpMethods());
        return responderFallida(MensajesEnum.ERROR_USUARIO_METODO_NO_SOPORTADO.getContenido(),
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Respuesta<Object>> manejarTipoContenidoNoSoportado(
            final HttpMediaTypeNotSupportedException excepcion) {
        registrarAdvertencia(MensajesEnum.ERROR_TECNICO_TIPO_CONTENIDO_NO_SOPORTADO,
                excepcion.getContentType(), excepcion.getSupportedMediaTypes());
        return responderFallida(MensajesEnum.ERROR_USUARIO_TIPO_CONTENIDO_NO_SOPORTADO.getContenido(),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Respuesta<Object>> manejarExcepcionInesperada(final Exception excepcion) {
        final var excepcionControlador = ImexcolControladorException.crear(excepcion,
                MensajesEnum.ERROR_USUARIO_CONTROLADOR_OPERACION_INESPERADA.getContenido(),
                MensajesEnum.ERROR_TECNICO_CONTROLADOR_OPERACION_INESPERADA.getContenido());
        REGISTRO.error(excepcionControlador.getMensajeTecnico(), excepcionControlador.getExcepcionRaiz());
        return responderFallida(excepcionControlador.getMensajeUsuario(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static void registrarAdvertencia(final MensajesEnum plantilla, final Object... argumentos) {
        if (REGISTRO.isWarnEnabled()) {
            REGISTRO.warn(plantilla.getContenido().formatted(argumentos));
        }
    }

    private static ResponseEntity<Respuesta<Object>> responderFallida(final String mensaje,
            final HttpStatus estado) {
        final var respuesta = Respuesta.<Object>crearFallida();
        respuesta.agregarMensaje(mensaje);
        return new ResponseEntity<>(respuesta, estado);
    }
}
