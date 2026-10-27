package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.DireccionEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.DireccionNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.dominio.DireccionDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class DireccionNegocioImpl implements DireccionNegocio {

    private final FabricaDAO fabrica;
    private final DireccionEntidadAssembler ensamblador;

    public DireccionNegocioImpl(final FabricaDAO fabrica) {
        super();
        if (UtilObjeto.esNulo(fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido());
        }
        this.fabrica = fabrica;
        this.ensamblador = DireccionEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final DireccionDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new DireccionDominio());
        validarDatosComunes(dominioSeguro);
        validarNoExisteDireccionDuplicada(dominioSeguro);
        dominioSeguro.setId(UUID.randomUUID());
        fabrica.obtenerDireccionDAO().acceder(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void actualizar(final DireccionDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new DireccionDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "dirección");
        validarDatosComunes(dominioSeguro);
        fabrica.obtenerDireccionDAO().actualizar(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "dirección");
        fabrica.obtenerDireccionDAO().eliminar(id);
    }

    @Override
    public List<DireccionDominio> consultar(final DireccionDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new DireccionDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = fabrica.obtenerDireccionDAO().consultarPorFiltro(entidadFiltro);
        return ensamblador.ensamblarDominio(entidades);
    }

    private void validarDatosComunes(final DireccionDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getCliente().getId(),
                "cliente asociado a la dirección");

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getCalle(), "calle de la dirección");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getCalle(), "calle de la dirección", 5, 200);

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getCiudad(), "ciudad de la dirección");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getCiudad(), "ciudad de la dirección", 2, 100);

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getDepartamento(),
                "departamento de la dirección");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getDepartamento(),
                "departamento de la dirección", 2, 100);

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getPais(), "país de la dirección");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getPais(), "país de la dirección", 2, 100);

        if (!UtilTexto.estaVacia(dominio.getCodigoPostal())) {
            ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getCodigoPostal(),
                    "código postal de la dirección", 3, 10);
        }
    }

    private void validarNoExisteDireccionDuplicada(final DireccionDominio dominio) {
        final var filtro = ensamblador.ensamblarEntidad(new DireccionDominio());
        filtro.getCliente().setId(dominio.getCliente().getId());
        filtro.setCalle(dominio.getCalle());
        filtro.setCiudad(dominio.getCiudad());
        filtro.setDepartamento(dominio.getDepartamento());
        filtro.setPais(dominio.getPais());

        final var existentes = fabrica.obtenerDireccionDAO().consultarPorFiltro(filtro);
        if (!UtilObjeto.esNulo(existentes) && !existentes.isEmpty()) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_DIRECCION_DUPLICADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_DIRECCION_DUPLICADA.getContenido()
                            .formatted(dominio.getCliente().getId(), dominio.getCalle(),
                                    dominio.getCiudad(), dominio.getDepartamento(), dominio.getPais()));
        }
    }
}
