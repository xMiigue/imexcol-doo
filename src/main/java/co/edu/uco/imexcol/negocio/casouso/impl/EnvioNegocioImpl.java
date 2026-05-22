package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.EnvioDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.EnvioEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.EnvioNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.envio.ValidarEstadoEnvioEsValido;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarFechaNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.dominio.EnvioDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

/**
 * Casos de uso del objeto de dominio Envío.
 *
 * Políticas de negocio aplicadas (referencia documentación de políticas):
 * <ul>
 *   <li>ENV-POL-01: Validación de datos requeridos.</li>
 *   <li>ENV-POL-02: El pedido asociado debe existir.</li>
 *   <li>ENV-POL-03: Estado válido (PREPARANDO, EN_TRANSITO, ENTREGADO, DEVUELTO, EXTRAVIADO).</li>
 * </ul>
 */
public final class EnvioNegocioImpl implements EnvioNegocio {

    private final EnvioDAO dao;
    private final EnvioEntidadAssembler ensamblador;

    public EnvioNegocioImpl(final EnvioDAO dao) {
        super();
        if (UtilObjeto.esNulo(dao)) {
            throw ImexcolException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido(),
                    Lugar.NEGOCIO);
        }
        this.dao = dao;
        this.ensamblador = EnvioEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final EnvioDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new EnvioDominio());
        validarDatosComunes(dominioSeguro);
        validarCoherenciaFechas(dominioSeguro);
        dominioSeguro.setId(UUID.randomUUID());
        dao.acceder(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void actualizar(final EnvioDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new EnvioDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "envío");
        validarDatosComunes(dominioSeguro);
        validarCoherenciaFechas(dominioSeguro);
        dao.actualizar(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "envío");
        dao.eliminar(id);
    }

    @Override
    public List<EnvioDominio> consultar(final EnvioDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new EnvioDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = dao.consultarPorFiltro(entidadFiltro);
        return ensamblador.ensamblarDominio(entidades);
    }

    private void validarDatosComunes(final EnvioDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getPedido().getId(),
                "pedido asociado al envío");

        ValidarFechaNoEsValorPorDefecto.ejecutarValidacion(dominio.getFechaEnvio(),
                "fecha de envío");

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getTransportadora(),
                "transportadora del envío");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getTransportadora(),
                "transportadora del envío", 2, 100);

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getNumeroGuia(),
                "número de guía del envío");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getNumeroGuia(),
                "número de guía del envío", 5, 100);

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getEstado(), "estado del envío");
        ValidarEstadoEnvioEsValido.ejecutarValidacion(dominio.getEstado());
    }

    private void validarCoherenciaFechas(final EnvioDominio dominio) {
        if (!UtilFecha.esFechaPorDefecto(dominio.getFechaEntrega())
                && dominio.getFechaEntrega().isBefore(dominio.getFechaEnvio())) {
            throw ImexcolException.crear(
                    "La fecha de entrega no puede ser anterior a la fecha de envío.",
                    "Inconsistencia detectada en Envio: fechaEntrega=%s anterior a fechaEnvio=%s."
                            .formatted(dominio.getFechaEntrega(), dominio.getFechaEnvio()),
                    Lugar.NEGOCIO);
        }
    }
}
