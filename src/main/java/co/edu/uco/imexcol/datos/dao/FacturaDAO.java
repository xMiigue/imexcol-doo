package co.edu.uco.imexcol.datos.dao;

import java.util.UUID;

import co.edu.uco.imexcol.entidad.FacturaEntidad;

public interface FacturaDAO
        extends AccederDAO<FacturaEntidad>,
                RecuperarDAO<FacturaEntidad, UUID> {
}
