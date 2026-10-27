package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.EnvioEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.EnvioNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.envio.ValidarEnvioExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.envio.ValidarEstadoEnvioEsValido;
import co.edu.uco.imexcol.negocio.casouso.validador.envio.ValidarFechaEntregaNoEsAnteriorAFechaEnvio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarFechaNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.casouso.validador.pedido.ValidarPedidoEstaPagado;
import co.edu.uco.imexcol.negocio.casouso.validador.pedido.ValidarPedidoExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.pedido.ValidarPedidoNoTieneEnvioActivo;
import co.edu.uco.imexcol.negocio.dominio.EnvioDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class EnvioNegocioImpl implements EnvioNegocio {

    private static final String DATO_PEDIDO = "pedido asociado al envío";
    private static final String DATO_FECHA_ENVIO = "fecha de envío";
    private static final String DATO_TRANSPORTADORA = "transportadora del envío";
    private static final String DATO_NUMERO_GUIA = "número de guía del envío";
    private static final String DATO_ESTADO = "estado del envío";
    private static final int LONGITUD_MINIMA_TRANSPORTADORA = 2;
    private static final int LONGITUD_MAXIMA_TRANSPORTADORA = 100;
    private static final int LONGITUD_MINIMA_NUMERO_GUIA = 5;
    private static final int LONGITUD_MAXIMA_NUMERO_GUIA = 100;

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
        asignarNuevoIdentificador(dominioSeguro);
        asegurarPedidoExista(dominioSeguro);
        asegurarPedidoEstePagado(dominioSeguro);
        asegurarPedidoNoTengaOtroEnvioActivo(dominioSeguro);
        guardar(dominioSeguro);
    }

    @Override
    public void actualizar(final EnvioDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new EnvioDominio());
        validarDatosComunes(dominioSeguro);
        validarCoherenciaFechas(dominioSeguro);
        asegurarEnvioExista(dominioSeguro.getId());
        asegurarPedidoExista(dominioSeguro);
        modificar(dominioSeguro);
    }

    @Override
    public void eliminar(final UUID id) {
        asegurarEnvioExista(id);
        borrarEventosRastreo(id);
        borrar(id);
    }

    @Override
    public List<EnvioDominio> consultar(final EnvioDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new EnvioDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = fabrica.obtenerEnvioDAO().consultarPorFiltro(entidadFiltro);
        return ensamblador.ensamblarDominio(entidades);
    }

    private void validarDatosComunes(final EnvioDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getPedido().getId(), DATO_PEDIDO);
        ValidarFechaNoEsValorPorDefecto.ejecutarValidacion(dominio.getFechaEnvio(), DATO_FECHA_ENVIO);
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getTransportadora(), DATO_TRANSPORTADORA);
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getTransportadora(), DATO_TRANSPORTADORA,
                LONGITUD_MINIMA_TRANSPORTADORA, LONGITUD_MAXIMA_TRANSPORTADORA);
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getNumeroGuia(), DATO_NUMERO_GUIA);
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getNumeroGuia(), DATO_NUMERO_GUIA,
                LONGITUD_MINIMA_NUMERO_GUIA, LONGITUD_MAXIMA_NUMERO_GUIA);
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getEstado(), DATO_ESTADO);
        ValidarEstadoEnvioEsValido.ejecutarValidacion(dominio.getEstado());
    }

    private void validarCoherenciaFechas(final EnvioDominio dominio) {
        ValidarFechaEntregaNoEsAnteriorAFechaEnvio.ejecutarValidacion(dominio.getFechaEntrega(),
                dominio.getFechaEnvio());
    }

    private void asegurarPedidoExista(final EnvioDominio dominio) {
        ValidarPedidoExistePorId.ejecutarValidacion(dominio.getPedido().getId(), fabrica);
    }

    private void asegurarPedidoEstePagado(final EnvioDominio dominio) {
        ValidarPedidoEstaPagado.ejecutarValidacion(dominio.getPedido().getId(), fabrica);
    }

    private void asegurarPedidoNoTengaOtroEnvioActivo(final EnvioDominio dominio) {
        ValidarPedidoNoTieneEnvioActivo.ejecutarValidacion(dominio.getPedido().getId(), dominio.getId(), fabrica);
    }

    private void asegurarEnvioExista(final UUID id) {
        ValidarEnvioExistePorId.ejecutarValidacion(id, fabrica);
    }

    private void asignarNuevoIdentificador(final EnvioDominio dominio) {
        dominio.setId(UtilUUID.generarNuevoUUID());
    }

    private void guardar(final EnvioDominio dominio) {
        fabrica.obtenerEnvioDAO().crear(ensamblador.ensamblarEntidad(dominio));
    }

    private void modificar(final EnvioDominio dominio) {
        fabrica.obtenerEnvioDAO().actualizar(ensamblador.ensamblarEntidad(dominio));
    }

    private void borrarEventosRastreo(final UUID id) {
        fabrica.obtenerEventoRastreoDAO().eliminarPorEnvio(id);
    }

    private void borrar(final UUID id) {
        fabrica.obtenerEnvioDAO().eliminar(id);
    }
}
