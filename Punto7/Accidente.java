import java.sql.Date;

public class Accidente {
    Persona persona;
    Date fechaAccidente;
    String descripcion;

    public Accidente(Persona persona, Date fechaAccidente, String descripcion) {
        this.persona = persona;
        this.fechaAccidente = fechaAccidente;
        this.descripcion = descripcion;
    }


    public Persona getPersona() {
        return this.persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Date getFechaAccidente() {
        return this.fechaAccidente;
    }

    public void setFechaAccidente(Date fechaAccidente) {
        this.fechaAccidente = fechaAccidente;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
