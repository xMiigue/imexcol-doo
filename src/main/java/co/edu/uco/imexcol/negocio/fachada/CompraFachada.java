package co.edu.uco.imexcol.negocio.fachada;

import co.edu.uco.imexcol.dto.ResultadoCompraDTO;
import co.edu.uco.imexcol.dto.SolicitudCompraDTO;

public interface CompraFachada {

    ResultadoCompraDTO realizarCompra(SolicitudCompraDTO solicitud);
}
