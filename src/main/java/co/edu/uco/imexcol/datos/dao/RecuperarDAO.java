package co.edu.uco.imexcol.datos.dao;

import java.util.List;

public interface RecuperarDAO<E, ID> {

    List<E> consultarTodos();

    List<E> consultarPorFiltro(E filtro);

    E consultarPorId(ID id);
}
