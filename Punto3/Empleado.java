package Punto3;

public class Empleado {
    int codigo;
    String nombre;
    float tarifaHoras;
    int sexo;
    int horasTrabajadas;


    public Empleado(int codigo, String nombre, float tarifaHoras, int sexo, int horasTrabajadas) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tarifaHoras = tarifaHoras;
        this.sexo = sexo;
        this.horasTrabajadas = horasTrabajadas;
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

    public float getTarifaHoras() {
        return this.tarifaHoras;
    }

    public void setTarifaHoras(float tarifaHoras) {
        this.tarifaHoras = tarifaHoras;
    }

    public int getSexo() {
        return this.sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public int getHorasTrabajadas() {
        return this.horasTrabajadas;
    }

    public void setHorasTrabajadas(int horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    public float calcularSalarioBruto() {
        float salario = 0;
        int horasSalario = this.horasTrabajadas;
        if (horasSalario <= 240) {
            salario = horasSalario * this.tarifaHoras;
        } else {
            salario = 240 * this.tarifaHoras;
            horasSalario = horasSalario - 240;            
            if (horasSalario <=60) {
                salario += horasSalario * (this.tarifaHoras * 2.5);
            } else {
                salario += 60 * (this.tarifaHoras * 2.5);
                horasSalario = horasSalario - 60;
                salario += horasSalario * (this.tarifaHoras * 1.7);
            }
        }
        return salario;
    }

    public float calcularRetencion() {
        float salarioBruto = this.calcularSalarioBruto();
        if (salarioBruto<900000) {
            return 0;
        }else if (salarioBruto<1200000) {
            return salarioBruto * 0.05f;
        }else if (salarioBruto<2000000) {
            return salarioBruto * 0.1f;
        }else {
            return salarioBruto * 0.2f;
        }
    }

    public float calcularSalarioNeto() {
        float salarioBruto = this.calcularSalarioBruto();
        float retencion = this.calcularRetencion();
        return salarioBruto - retencion;
    }

}
