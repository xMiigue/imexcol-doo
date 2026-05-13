package co.edu.uco.imexcol.datos.dao.entidad;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.AccederDAO;
import co.edu.uco.imexcol.datos.dao.ActualizarDAO;
import co.edu.uco.imexcol.datos.dao.EliminarDAO;
import co.edu.uco.imexcol.datos.dao.RecuperarDAO;
import co.edu.uco.imexcol.entidad.CategoriaEntidad;

public interface CategoriaDAO
        extends AccederDAO<CategoriaEntidad>,
                RecuperarDAO<CategoriaEntidad, UUID>,
                ActualizarDAO<CategoriaEntidad>,
                EliminarDAO<UUID> {
}
