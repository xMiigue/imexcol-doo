package co.edu.uco.imexcol.negocio.dominio;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilBooleano;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class AdministradorDominio {

    private UUID id;
    private String nombreUsuario;
    private String correoElectronico;
    private String contrasena;
    private boolean estado;

    private AdministradorDominio(final Builder builder) {
        super();
        setId(builder.id);
        setNombreUsuario(builder.nombreUsuario);
        setCorreoElectronico(builder.correoElectronico);
        setContrasena(builder.contrasena);
        setEstado(builder.estado);
    }

    public UUID getId() {
        return id;
    }

    private void setId(final UUID id) {
        this.id = UtilUUID.obtenerValorDefecto(id);
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    private void setNombreUsuario(final String nombreUsuario) {
        this.nombreUsuario = UtilTexto.aplicarTrim(nombreUsuario);
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    private void setCorreoElectronico(final String correoElectronico) {
        this.correoElectronico = UtilTexto.aplicarTrim(correoElectronico);
    }

    public String getContrasena() {
        return contrasena;
    }

    private void setContrasena(final String contrasena) {
        this.contrasena = UtilTexto.aplicarTrim(contrasena);
    }

    public boolean isEstado() {
        return estado;
    }

    private void setEstado(final Boolean estado) {
        this.estado = UtilBooleano.obtenerValorDefecto(estado);
    }

    public static final class Builder {

        private UUID id;
        private String nombreUsuario;
        private String correoElectronico;
        private String contrasena;
        private Boolean estado;

        public Builder id(final UUID id) {
            this.id = id;
            return this;
        }

        public Builder nombreUsuario(final String nombreUsuario) {
            this.nombreUsuario = nombreUsuario;
            return this;
        }

        public Builder correoElectronico(final String correoElectronico) {
            this.correoElectronico = correoElectronico;
            return this;
        }

        public Builder contrasena(final String contrasena) {
            this.contrasena = contrasena;
            return this;
        }

        public Builder estado(final Boolean estado) {
            this.estado = estado;
            return this;
        }

        public AdministradorDominio build() {
            return new AdministradorDominio(this);
        }
    }
}
