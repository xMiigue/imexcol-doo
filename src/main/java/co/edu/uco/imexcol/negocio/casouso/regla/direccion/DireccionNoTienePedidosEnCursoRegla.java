package co.edu.uco.imexcol.negocio.casouso.regla.direccion;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.entidad.DireccionEntidad;
import co.edu.uco.imexcol.entidad.PedidoEntidad;
import co.edu.uco.imexcol.negocio.casouso.regla.Regla;
import co.edu.uco.imexcol.negocio.casouso.regla.UtilRegla;
import co.edu.uco.imexcol.negocio.dominio.enums.EstadoPedidoEnum;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class DireccionNoTienePedidosEnCursoRegla implements Regla {

    private static final DireccionNoTienePedidosEnCursoRegla INSTANCIA = new DireccionNoTienePedidosEnCursoRegla();
    private static final int CANTIDAD_PARAMETROS_REQUERIDOS = 2;
    private static final int POSICION_ID_DIRECCION = 0;
    private static final int POSICION_FABRICA = 1;
    private static final long SIN_PEDIDOS_EN_CURSO = 0L;

    private DireccionNoTienePedidosEnCursoRegla() {
        super();
    }

    public static DireccionNoTienePedidosEnCursoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void ejecutar(final Object... datos) {
        UtilRegla.asegurarParametrosSuficientes(datos, CANTIDAD_PARAMETROS_REQUERIDOS);
        final var idDireccion = UtilUUID.obtenerValorDefecto((UUID) datos[POSICION_ID_DIRECCION]);
        final var fabrica = (FabricaDAO) datos[POSICION_FABRICA];

        final var enCurso = contarPedidosEnCurso(consultarPedidosDeLaDireccion(idDireccion, fabrica));
        if (enCurso > SIN_PEDIDOS_EN_CURSO) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_DIRECCION_CON_PEDIDOS_EN_CURSO.getContenido(),
                    MensajesEnum.ERROR_TECNICO_DIRECCION_CON_PEDIDOS_EN_CURSO.getContenido()
                            .formatted(idDireccion, enCurso));
        }
    }

    private static List<PedidoEntidad> consultarPedidosDeLaDireccion(final UUID idDireccion, final FabricaDAO fabrica) {
        final var filtro = new PedidoEntidad();
        filtro.setDireccion(new DireccionEntidad(idDireccion));
        return fabrica.obtenerPedidoDAO().consultarPorFiltro(filtro);
    }

    private static long contarPedidosEnCurso(final List<PedidoEntidad> pedidos) {
        return pedidos.stream()
                .filter(pedido -> EstadoPedidoEnum.representaPedidoEnCurso(pedido.getEstado()))
                .count();
    }
}
