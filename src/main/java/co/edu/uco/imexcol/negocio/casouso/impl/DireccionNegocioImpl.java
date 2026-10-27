package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.DireccionEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.DireccionNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.cliente.ValidarClienteExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.direccion.ValidarDireccionExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.direccion.ValidarDireccionNoEstaDuplicadaParaCliente;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.dominio.DireccionDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class DireccionNegocioImpl implements DireccionNegocio {

    private static final String DATO_CLIENTE = "cliente asociado a la dirección";
    private static final String DATO_CALLE = "calle de la dirección";
    private static final String DATO_CIUDAD = "ciudad de la dirección";
    private static final String DATO_DEPARTAMENTO = "departamento de la dirección";
    private static final String DATO_PAIS = "país de la dirección";
    private static final String DATO_CODIGO_POSTAL = "código postal de la dirección";
    private static final int LONGITUD_MINIMA_CALLE = 5;
    private static final int LONGITUD_MAXIMA_CALLE = 200;
    private static final int LONGITUD_MINIMA_CIUDAD = 2;
    private static final int LONGITUD_MAXIMA_CIUDAD = 100;
    private static final int LONGITUD_MINIMA_DEPARTAMENTO = 2;
    private static final int LONGITUD_MAXIMA_DEPARTAMENTO = 100;
    private static final int LONGITUD_MINIMA_PAIS = 2;
    private static final int LONGITUD_MAXIMA_PAIS = 100;
    private static final int LONGITUD_MINIMA_CODIGO_POSTAL = 3;
    private static final int LONGITUD_MAXIMA_CODIGO_POSTAL = 10;

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
        asegurarClienteExista(dominioSeguro);
        asignarNuevoIdentificador(dominioSeguro);
        asegurarDireccionNoEsteDuplicada(dominioSeguro);
        guardar(dominioSeguro);
    }

    @Override
    public void actualizar(final DireccionDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new DireccionDominio());
        validarDatosComunes(dominioSeguro);
        asegurarDireccionExista(dominioSeguro.getId());
        asegurarClienteExista(dominioSeguro);
        asegurarDireccionNoEsteDuplicada(dominioSeguro);
        modificar(dominioSeguro);
    }

    @Override
    public void eliminar(final UUID id) {
        asegurarDireccionExista(id);
        borrar(id);
    }

    @Override
    public List<DireccionDominio> consultar(final DireccionDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new DireccionDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = fabrica.obtenerDireccionDAO().consultarPorFiltro(entidadFiltro);
        return ensamblador.ensamblarDominio(entidades);
    }

    private void validarDatosComunes(final DireccionDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getCliente().getId(), DATO_CLIENTE);
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getCalle(), DATO_CALLE);
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getCalle(), DATO_CALLE,
                LONGITUD_MINIMA_CALLE, LONGITUD_MAXIMA_CALLE);
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getCiudad(), DATO_CIUDAD);
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getCiudad(), DATO_CIUDAD,
                LONGITUD_MINIMA_CIUDAD, LONGITUD_MAXIMA_CIUDAD);
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getDepartamento(), DATO_DEPARTAMENTO);
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getDepartamento(), DATO_DEPARTAMENTO,
                LONGITUD_MINIMA_DEPARTAMENTO, LONGITUD_MAXIMA_DEPARTAMENTO);
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getPais(), DATO_PAIS);
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getPais(), DATO_PAIS,
                LONGITUD_MINIMA_PAIS, LONGITUD_MAXIMA_PAIS);
        validarCodigoPostalOpcional(dominio.getCodigoPostal());
    }

    private void validarCodigoPostalOpcional(final String codigoPostal) {
        if (!UtilTexto.estaVacia(codigoPostal)) {
            ValidarLongitudTextoEsValida.ejecutarValidacion(codigoPostal, DATO_CODIGO_POSTAL,
                    LONGITUD_MINIMA_CODIGO_POSTAL, LONGITUD_MAXIMA_CODIGO_POSTAL);
        }
    }

    private void asegurarClienteExista(final DireccionDominio dominio) {
        ValidarClienteExistePorId.ejecutarValidacion(dominio.getCliente().getId(), fabrica);
    }

    private void asignarNuevoIdentificador(final DireccionDominio dominio) {
        dominio.setId(UtilUUID.generarNuevoUUID());
    }

    private void asegurarDireccionNoEsteDuplicada(final DireccionDominio dominio) {
        ValidarDireccionNoEstaDuplicadaParaCliente.ejecutarValidacion(dominio, fabrica);
    }

    private void asegurarDireccionExista(final UUID id) {
        ValidarDireccionExistePorId.ejecutarValidacion(id, fabrica);
    }

    private void guardar(final DireccionDominio dominio) {
        fabrica.obtenerDireccionDAO().crear(ensamblador.ensamblarEntidad(dominio));
    }

    private void modificar(final DireccionDominio dominio) {
        fabrica.obtenerDireccionDAO().actualizar(ensamblador.ensamblarEntidad(dominio));
    }

    private void borrar(final UUID id) {
        fabrica.obtenerDireccionDAO().eliminar(id);
    }
}
