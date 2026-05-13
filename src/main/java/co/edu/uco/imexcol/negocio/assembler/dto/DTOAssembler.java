package co.edu.uco.imexcol.negocio.assembler.dto;

import java.util.List;

public interface DTOAssembler<D, T> {

    D ensamblarDominio(T dto);

    T ensamblarDTO(D dominio);

    List<T> ensamblarDTO(List<D> listaDominios);

    List<D> ensamblarDominio(List<T> listaDTO);
}
