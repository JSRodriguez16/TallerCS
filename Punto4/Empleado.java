package Punto4;

public class Empleado {
    int codigo;
    String nombre;
    float salarioHora;
    int horasTrabajadas;
    int numeroHijos;

    public Empleado(int codigo, String nombre, float salarioHora, int horasTrabajadas) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.salarioHora = salarioHora;
        this.horasTrabajadas = horasTrabajadas;
        this.numeroHijos = 0;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getSalarioHora() {
        return this.salarioHora;
    }

    public void setSalarioHora(float salarioHora) {
        this.salarioHora = salarioHora;
    }

    public int getHorasTrabajadas() {
        return this.horasTrabajadas;
    }

    public void setHorasTrabajadas(int horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    public float calcularDevengado() {
        return this.salarioHora * this.horasTrabajadas;
    }

    public void setNumeroHijos(int numeroHijos) {
        this.numeroHijos = numeroHijos;
    }

    public int getNumeroHijos() {
        return this.numeroHijos;
    }

    public float calcularRetencion() {
        float devengado = this.calcularDevengado();
        if (devengado < 428000) {
            if (numeroHijos > 12) {
                return 0;
            } else if (6 < numeroHijos && numeroHijos <= 12) {
                return devengado * ((12 - (numeroHijos / 2f)) / 100f);
            } else {
                return devengado * 0.04f;
            }
        } else {
            if (numeroHijos < 5) {
                return devengado * 0.05f;
            } else if (5 <= numeroHijos && numeroHijos < 10) {
                return devengado * ((10f / numeroHijos) / 100f);
            } else {
                return 0;
            }
        }
    }

    public float calcularSubsidio(){
        return this.numeroHijos * 17200;
    }

    public float calcularSalarioNeto() {
        return this.calcularDevengado() - this.calcularRetencion() + this.calcularSubsidio();
    }
}