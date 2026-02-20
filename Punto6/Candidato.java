package Punto6;

public class Candidato {
    String nombre;
    int votos;
    boolean estado;

    public Candidato(String nombre, int votos, boolean estado) {
        this.nombre = nombre;
        this.votos = votos;
        this.estado = estado;
    }


    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVotos() {
        return this.votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }

    public boolean isEstado() {
        return this.estado;
    }

    public boolean getEstado() {
        return this.estado;
    }

    public void descalificar() {
        this.estado = false;
    }
    
}
