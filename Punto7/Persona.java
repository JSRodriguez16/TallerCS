import java.sql.Date;
import java.util.concurrent.TimeUnit;

public class Persona{
    String nombre;
    int cedula;
    Date fechaNac;
    int sexo;
    int registroCarro;

    public Persona(String nombre, int cedula, Date fechaNac, int sexo, int registroCarro) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.fechaNac = fechaNac;
        this.sexo = sexo;
        this.registroCarro = registroCarro;
    }


    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCedula() {
        return this.cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public Date getFechaNac() {
        return this.fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public int getSexo() {
        return this.sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public int getRegistroCarro() {
        return this.registroCarro;
    }

    public void setRegistroCarro(int registroCarro) {
        this.registroCarro = registroCarro;
    }

    public int getEdad(){
        Date actualDate = new Date(System.currentTimeMillis());
        long diferenciaMillis = actualDate.getTime() - fechaNac.getTime();
        return (new Date(diferenciaMillis)).getYear();
    }
}
