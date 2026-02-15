package Punto1;

public class Estudiante {
    private String nombre;
    private int codigo;
    private int edad;
    private int sexo;
    private int carrera;
    private int jornada;


    public Estudiante(String nombre, int codigo, int edad, int sexo, int carrera, int jornada) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.edad = edad;
        this.sexo = sexo;
        this.carrera = carrera;
        this.jornada = jornada;
    }


    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getEdad() {
        return this.edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getSexo() {
        return this.sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public int getCarrera() {
        return this.carrera;
    }

    public void setCarrera(int carrera) {
        this.carrera = carrera;
    }

    public int getJornada() {
        return this.jornada;
    }

    public void setJornada(int jornada) {
        this.jornada = jornada;
    }
    
}
