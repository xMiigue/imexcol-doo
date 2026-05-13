package co.edu.uco.imexcol.datos.dao.entidad;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.AccederDAO;
import co.edu.uco.imexcol.datos.dao.ActualizarDAO;
import co.edu.uco.imexcol.datos.dao.EliminarDAO;
import co.edu.uco.imexcol.datos.dao.RecuperarDAO;
import co.edu.uco.imexcol.entidad.ProductoEntidad;

public interface ProductoDAO
        extends AccederDAO<ProductoEntidad>,
                RecuperarDAO<ProductoEntidad, UUID>,
                ActualizarDAO<ProductoEntidad>,
                EliminarDAO<UUID> {
}
