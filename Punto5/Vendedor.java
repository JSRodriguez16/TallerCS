package Punto5;

import java.util.ArrayList;

public class Vendedor {
    int id;
    String nombre;
    ArrayList<Venta> ventas;

    public Vendedor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.ventas = new ArrayList<>();
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

    public ArrayList<Venta> getVentas() {
        return this.ventas;
    }

    public void setVentas(ArrayList<Venta> ventas) {
        this.ventas = ventas;
    }

    public Boolean agregarVenta(Venta venta) {
        return this.ventas.add(venta);
    }

    public Boolean eliminarVenta(Venta venta) {
        return this.ventas.remove(venta);
    }

    public int calcularComision(int idVenta) {
        for (Venta venta : this.ventas) {
            if (venta.getId() == idVenta) {
                if (venta.calcularValorVenta(venta) <= 100000) {
                    return (int) (venta.calcularValorVenta(venta) * 0.1);
                } else {
                    return (int) (venta.calcularValorVenta(venta) * 0.07);
                }
            }
        }
        return 0;
    }

    public int calcularTotalVentas() {
        int total = 0;
        for (Venta venta : this.ventas) {
            total += venta.calcularValorVenta(venta)+calcularComision(venta.getId());
        }
        return total;
    }
}
