package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
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
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class EnvioNegocioImpl implements EnvioNegocio {

    private final FabricaDAO fabrica;
    private final EnvioEntidadAssembler ensamblador;

    public EnvioNegocioImpl(final FabricaDAO fabrica) {
        super();
        if (UtilObjeto.esNulo(fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido());
        }
        this.fabrica = fabrica;
        this.ensamblador = EnvioEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final EnvioDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new EnvioDominio());
        validarDatosComunes(dominioSeguro);
        validarCoherenciaFechas(dominioSeguro);
        dominioSeguro.setId(UUID.randomUUID());
        fabrica.obtenerEnvioDAO().acceder(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void actualizar(final EnvioDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new EnvioDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "envío");
        validarDatosComunes(dominioSeguro);
        validarCoherenciaFechas(dominioSeguro);
        fabrica.obtenerEnvioDAO().actualizar(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "envío");
        fabrica.obtenerEnvioDAO().eliminar(id);
    }

    @Override
    public List<EnvioDominio> consultar(final EnvioDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new EnvioDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = fabrica.obtenerEnvioDAO().consultarPorFiltro(entidadFiltro);
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
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_ENVIO_FECHA_ENTREGA_ANTERIOR_A_FECHA_ENVIO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_ENVIO_FECHA_ENTREGA_ANTERIOR_A_FECHA_ENVIO.getContenido()
                            .formatted(dominio.getFechaEntrega(), dominio.getFechaEnvio()));
        }
    }
}
