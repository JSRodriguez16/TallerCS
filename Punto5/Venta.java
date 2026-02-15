package Punto5;

import java.sql.Date;
import java.util.ArrayList;

public class Venta {
    int id;
    Date fecha;
    ArrayList<Producto> productos;

    public Venta(int id, Date fecha, ArrayList<Producto> productos) {
        this.id = id;
        this.fecha = fecha;
        this.productos = productos;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ArrayList<Producto> getProductos() {
        return this.productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public Boolean agregarProducto(Producto producto) {
        return this.productos.add(producto);
    }

    public Boolean eliminarProducto(Producto producto) {
        return this.productos.remove(producto);
    }

    public int calcularValorVenta(Venta venta) {
        int total = 0;
        for (Producto producto : venta.getProductos()) {
            total += producto.getPrecio();
        }
        return total;
    }
}
