package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.MetodoPagoEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.MetodoPagoNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.dominio.MetodoPagoDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class MetodoPagoNegocioImpl implements MetodoPagoNegocio {

    private final FabricaDAO fabrica;
    private final MetodoPagoEntidadAssembler ensamblador;

    public MetodoPagoNegocioImpl(final FabricaDAO fabrica) {
        super();
        if (UtilObjeto.esNulo(fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido());
        }
        this.fabrica = fabrica;
        this.ensamblador = MetodoPagoEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final MetodoPagoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new MetodoPagoDominio());
        validarDatosComunes(dominioSeguro);
        dominioSeguro.setId(UUID.randomUUID());
        fabrica.obtenerMetodoPagoDAO().acceder(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void actualizar(final MetodoPagoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new MetodoPagoDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "método de pago");
        validarDatosComunes(dominioSeguro);
        fabrica.obtenerMetodoPagoDAO().actualizar(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "método de pago");
        fabrica.obtenerMetodoPagoDAO().eliminar(id);
    }

    @Override
    public List<MetodoPagoDominio> consultar(final MetodoPagoDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new MetodoPagoDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = fabrica.obtenerMetodoPagoDAO().consultarPorFiltro(entidadFiltro);
        return ensamblador.ensamblarDominio(entidades);
    }

    private void validarDatosComunes(final MetodoPagoDominio dominio) {
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getNombre(), "nombre del método de pago");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getNombre(), "nombre del método de pago", 3, 100);
        if (!UtilTexto.estaVacia(dominio.getDescripcion())) {
            ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getDescripcion(),
                    "descripción del método de pago", 0, 255);
        }
    }
}
