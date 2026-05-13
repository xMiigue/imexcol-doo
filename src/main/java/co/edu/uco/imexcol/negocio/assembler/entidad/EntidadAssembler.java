package co.edu.uco.imexcol.negocio.assembler.entidad;

import java.util.List;

public interface EntidadAssembler<D, E> {

    E ensamblarEntidad(D dominio);

    D ensamblarDominio(E entidad);

    List<E> ensamblarEntidad(List<D> listaDominios);

    List<D> ensamblarDominio(List<E> listaEntidades);
}
