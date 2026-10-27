package co.edu.uco.imexcol.negocio.casouso;

import co.edu.uco.imexcol.negocio.dominio.ResultadoCompraDominio;
import co.edu.uco.imexcol.negocio.dominio.SolicitudCompraDominio;

public interface CompraNegocio {

    ResultadoCompraDominio realizarCompra(SolicitudCompraDominio solicitud);
}
