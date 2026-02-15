package Punto2;

public class Pedido {
    String nombreGranjero;
    int id;
    int tipoFumigacion;
    int numeroHectareas;

    public Pedido(String nombreGranjero, int id, int tipoFumigacion, int numeroHectareas) {
        this.nombreGranjero = nombreGranjero;
        this.id = id;
        this.tipoFumigacion = tipoFumigacion;
        this.numeroHectareas = numeroHectareas;
    }

    public String getNombreGranjero() {
        return this.nombreGranjero;
    }

    public void setNombreGranjero(String nombreGranjero) {
        this.nombreGranjero = nombreGranjero;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipoFumigacion() {
        return this.tipoFumigacion;
    }

    public void setTipoFumigacion(int tipoFumigacion) {
        this.tipoFumigacion = tipoFumigacion;
    }

    public int getNumeroHectareas() {
        return this.numeroHectareas;
    }

    public void setNumeroHectareas(int numeroHectareas) {
        this.numeroHectareas = numeroHectareas;
    }    

    public int calculoTotal(){
        int total = 0;
        switch (this.tipoFumigacion) {
            case 1:
                total = this.numeroHectareas * 10;
                break;
            case 2:
                total = this.numeroHectareas * 15;
                break;
            case 3:
                total = this.numeroHectareas * 20;
                break;
            case 4:
                total = this.numeroHectareas * 30;
                break;
            default:
                System.out.println("Tipo de fumigacion no valido");
                break;
        }
        if (total>3000 && this.numeroHectareas<=1000) {
            total = (int) (total * 0.90);
        }
        if (this.numeroHectareas>1000) {
                total = (int) (total * 0.95);
        }
        return total;
    }
}
