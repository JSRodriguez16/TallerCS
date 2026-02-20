package Punto8;

public class Persona {
    int id;
    String nombre;
    String sexo; // "M" para masculino, "F" para femenino
    int edad;
    float altura; // en metros
    float peso; // en libras
    int colorOjos; // (1 para azules, 2 para castaños y 3 para los demás)
    int colorCabello; // (1 para castaño, 2 para rubio y 3 para los demás)


    public Persona(int id, String nombre, String sexo, int edad, float altura, float peso, int colorOjos, int colorCabello) {
        this.id = id;
        this.nombre = nombre;
        this.sexo = sexo;
        this.edad = edad;
        this.altura = altura;
        this.peso = peso;
        this.colorOjos = colorOjos;
        this.colorCabello = colorCabello;
    }

    public Persona(String nombre, String sexo, int edad, float altura, float peso, int colorOjos, int colorCabello) {
        this(0, nombre, sexo, edad, altura, peso, colorOjos, colorCabello);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSexo() {
        return this.sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getEdad() {
        return this.edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public float getAltura() {
        return this.altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public float getPeso() {
        return this.peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public int getColorOjos() {
        return this.colorOjos;
    }

    public void setColorOjos(int colorOjos) {
        this.colorOjos = colorOjos;
    }

    public int getColorCabello() {
        return this.colorCabello;
    }

    public void setColorCabello(int colorCabello) {
        this.colorCabello = colorCabello;
    }
}
