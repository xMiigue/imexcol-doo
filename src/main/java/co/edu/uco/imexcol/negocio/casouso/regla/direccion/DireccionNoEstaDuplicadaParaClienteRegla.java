package co.edu.uco.imexcol.negocio.casouso.regla.direccion;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.entidad.ClienteEntidad;
import co.edu.uco.imexcol.entidad.DireccionEntidad;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.negocio.dominio.DireccionDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class DireccionNoEstaDuplicadaParaClienteRegla implements Regla {

    private static final DireccionNoEstaDuplicadaParaClienteRegla INSTANCIA =
            new DireccionNoEstaDuplicadaParaClienteRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_DIRECCION = 0;
    private static final int POSICION_FABRICA = 1;

    private DireccionNoEstaDuplicadaParaClienteRegla() {
        super();
    }

    public static DireccionNoEstaDuplicadaParaClienteRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var direccion = UtilObjeto.obtenerValorDefecto((DireccionDominio) datos[POSICION_DIRECCION],
                new DireccionDominio());
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        if (existeOtraDireccionIgualParaCliente(direccion, fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_DIRECCION_DUPLICADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_DIRECCION_DUPLICADA.getContenido()
                            .formatted(direccion.getCliente().getId(), direccion.getCalle(),
                                    direccion.getCiudad(), direccion.getDepartamento()));
        }
    }

    private static boolean existeOtraDireccionIgualParaCliente(final DireccionDominio direccion,
            final FabricaDAO fabrica) {
        final var filtro = new DireccionEntidad();
        filtro.setCliente(new ClienteEntidad(direccion.getCliente().getId()));
        filtro.setCalle(direccion.getCalle());
        filtro.setCiudad(direccion.getCiudad());
        filtro.setDepartamento(direccion.getDepartamento());
        return fabrica.obtenerDireccionDAO().consultarPorFiltro(filtro).stream()
                .anyMatch(existente -> !direccion.getId().equals(existente.getId()));
    }
}
