package Punto5;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import MetodosAuxiliares.Entrada;
import MetodosAuxiliares.Errores;
import MetodosAuxiliares.Salida;

public class Main {

    public static void main(String[] args) {
        Entrada entrada = new Entrada();
        Salida salida = new Salida();
        Random random = new Random();
        List<Producto> productos = null;
        List<Vendedor> vendedores = null;

        try {
            productos = generarProductos(5);
            vendedores = generarVendedores(10);
            asignarVentas(vendedores, productos, random);
        } catch (Exception ex) {
            Errores.manejarErrores(() -> {
                throw ex;
            }, salida);
            return;
        }

        String[] opciones = {
                "Ver todos los vendedores con sus ventas y comisiones",
                "Ver detalle de un vendedor especifico",
                "Ver resumen general",
                "Agregar vendedor",
                "Eliminar vendedor",
                "Agregar venta a un vendedor"
        };

        boolean continuar = true;
        while (continuar) {
            int opcion = entrada.mostrarMenu(opciones, "Seleccione una opcion", "Menu de Vendedores");
            switch (opcion) {
                case 0:
                case -1:
                    continuar = false;
                    break;
                case 1:
                    try {
                        verTodosVendedores(vendedores);
                    } catch (Exception ex) {
                        Errores.manejarErrores(() -> {
                            throw ex;
                        }, salida);
                    }
                    break;
                case 2:
                    try {
                        verDetalleVendedor(vendedores, entrada, salida);
                    } catch (Exception ex) {
                        Errores.manejarErrores(() -> {
                            throw ex;
                        }, salida);
                    }
                    break;
                case 3:
                    try {
                        verResumenGeneral(vendedores);
                    } catch (Exception ex) {
                        Errores.manejarErrores(() -> {
                            throw ex;
                        }, salida);
                    }
                    break;
                case 4:
                    try {
                        agregarVendedor(vendedores, entrada, salida);
                    } catch (Exception ex) {
                        Errores.manejarErrores(() -> {
                            throw ex;
                        }, salida);
                    }
                    break;
                case 5:
                    try {
                        eliminarVendedor(vendedores, entrada, salida);
                    } catch (Exception ex) {
                        Errores.manejarErrores(() -> {
                            throw ex;
                        }, salida);
                    }
                    break;
                case 6:
                    try {
                        agregarVentaAVendedor(vendedores, productos, entrada, salida);
                    } catch (Exception ex) {
                        Errores.manejarErrores(() -> {
                            throw ex;
                        }, salida);
                    }
                    break;
                default:
                    salida.mostrarMensaje("Opcion no valida", "Menu");
                    break;
            }
        }
    }

    private static List<Producto> generarProductos(int cantidad) {
        List<Producto> productos = new ArrayList<>();
        String[] nombres = {
                "Laptop Dell Inspiron", "Mouse Logitech MX Master", "Teclado Mecanico",
                "Monitor Samsung 24\"", "Auriculares Sony WH-1000XM4", "Webcam Logitech C920",
                "Disco Duro Externo 1TB", "Memoria RAM 16GB", "SSD 500GB", "Impresora HP LaserJet"
        };
        String[] descripciones = {
                "Alta calidad", "Producto premium", "Excelente rendimiento",
                "Ideal para profesionales", "Tecnologia de punta"
        };

        Random random = new Random();
        for (int i = 0; i < cantidad; i++) {
            int id = 1000 + i;
            String nombre = nombres[random.nextInt(nombres.length)];
            String descripcion = descripciones[random.nextInt(descripciones.length)];
            int precio = (random.nextInt(40) + 1) * 10000;
            productos.add(new Producto(id, precio, nombre, descripcion));
        }

        return productos;
    }

    private static List<Vendedor> generarVendedores(int cantidad) {
        List<Vendedor> vendedores = new ArrayList<>();
        String[] nombres = {
                "Juan Perez", "Maria Garcia", "Carlos Lopez", "Ana Martinez", "Luis Rodriguez",
                "Carmen Fernandez", "Jose Gonzalez", "Laura Sanchez", "Miguel Torres", "Isabel Ramirez",
                "Pedro Flores", "Sofia Diaz", "Diego Morales", "Elena Castro", "Jorge Ruiz"
        };

        for (int i = 0; i < cantidad; i++) {
            int id = 5000 + i;
            String nombre = nombres[i % nombres.length];
            vendedores.add(new Vendedor(id, nombre));
        }

        return vendedores;
    }

    private static void asignarVentas(List<Vendedor> vendedores, List<Producto> productos, Random random) {
        int ventaId = 1;
        long fechaBase = System.currentTimeMillis();

        for (Vendedor vendedor : vendedores) {

            int cantidadVentas = 2 + random.nextInt(3);

            for (int i = 0; i < cantidadVentas; i++) {

                long offset = random.nextInt(30) * 24 * 60 * 60 * 1000L;
                Date fechaVenta = new Date(fechaBase - offset);

                int cantidadProductos = 1 + random.nextInt(3);
                ArrayList<Producto> productosVenta = new ArrayList<>();

                for (int j = 0; j < cantidadProductos; j++) {
                    productosVenta.add(productos.get(random.nextInt(productos.size())));
                }

                Venta venta = new Venta(ventaId++, fechaVenta, productosVenta);
                vendedor.agregarVenta(venta);
            }
        }
    }

    private static void verTodosVendedores(List<Vendedor> vendedores) {
        if (vendedores.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay vendedores registrados", "Vendedores",
                    JOptionPane.PLAIN_MESSAGE);
            return;
        }

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Total de vendedores: ").append(vendedores.size()).append("\n\n");

        for (Vendedor vendedor : vendedores) {
            mensaje.append("---------------------------------------------\n");
            mensaje.append("ID: ").append(vendedor.getId()).append("\n");
            mensaje.append("Nombre: ").append(vendedor.getNombre()).append("\n");
            mensaje.append("Cantidad de ventas: ").append(vendedor.getVentas().size()).append("\n");
            mensaje.append("---------------------------------------------\n\n");

            if (vendedor.getVentas().isEmpty()) {
                mensaje.append("Sin ventas registradas\n");
            } else {
                int totalComisiones = 0;
                int ventaNum = 1;

                for (Venta venta : vendedor.getVentas()) {
                    int valorVenta = venta.calcularValorVenta(venta);
                    int comision = vendedor.calcularComision(venta.getId());
                    totalComisiones += comision;

                    mensaje.append("Venta #").append(ventaNum++).append(" (ID: ").append(venta.getId())
                            .append(")\n");
                    mensaje.append("Fecha: ").append(venta.getFecha()).append("\n");
                    mensaje.append("Productos:\n");

                    for (Producto producto : venta.getProductos()) {
                        mensaje.append("  - ").append(producto.getNombre())
                                .append(" - $").append(String.format("%,d", producto.getPrecio())).append("\n");
                    }

                    mensaje.append("Valor de la venta: $").append(String.format("%,d", valorVenta))
                            .append("\n");
                    mensaje.append("Comision (")
                            .append(valorVenta <= 100000 ? "10%" : "7%")
                            .append("): $").append(String.format("%,d", comision)).append("\n");
                    mensaje.append("\n");
                }

                mensaje.append("TOTAL VENTAS: $")
                        .append(String.format("%,d", vendedor.calcularTotalVentas())).append("\n");
                mensaje.append("TOTAL COMISIONES: $")
                        .append(String.format("%,d", totalComisiones)).append("\n");
            }

            mensaje.append("\n---------------------------------------------\n\n");
        }

        JTextArea textArea = new JTextArea(mensaje.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(false);
        textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        textArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(600, 500));

        JOptionPane.showMessageDialog(null, scrollPane, "Todos los Vendedores", JOptionPane.PLAIN_MESSAGE);
    }

    private static void verDetalleVendedor(List<Vendedor> vendedores, Entrada entrada, Salida salida) {
        if (vendedores.isEmpty()) {
            salida.mostrarMensaje("No hay vendedores registrados", "Vendedores");
            return;
        }

        String idStr = entrada.recibirInformacion("Ingrese el ID del vendedor:");
        if (idStr == null || idStr.trim().isEmpty()) {
            salida.mostrarMensaje("ID invalido", "Error");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            salida.mostrarMensaje("El ID debe ser un numero entero", "Error");
            return;
        }

        Vendedor vendedorEncontrado = null;
        for (Vendedor v : vendedores) {
            if (v.getId() == id) {
                vendedorEncontrado = v;
                break;
            }
        }

        if (vendedorEncontrado == null) {
            salida.mostrarMensaje("No se encontro ningun vendedor con el ID " + id, "Error");
            return;
        }

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("===== DETALLE DEL VENDEDOR =====\n\n");
        mensaje.append("ID: ").append(vendedorEncontrado.getId()).append("\n");
        mensaje.append("Nombre: ").append(vendedorEncontrado.getNombre()).append("\n");
        mensaje.append("Cantidad de ventas: ").append(vendedorEncontrado.getVentas().size()).append("\n\n");

        if (vendedorEncontrado.getVentas().isEmpty()) {
            mensaje.append("Sin ventas registradas\n");
        } else {
            int totalComisiones = 0;
            int ventaNum = 1;

            for (Venta venta : vendedorEncontrado.getVentas()) {
                int valorVenta = venta.calcularValorVenta(venta);
                int comision = vendedorEncontrado.calcularComision(venta.getId());
                totalComisiones += comision;

                mensaje.append("--- Venta #").append(ventaNum++).append(" ---\n");
                mensaje.append("ID Venta: ").append(venta.getId()).append("\n");
                mensaje.append("Fecha: ").append(venta.getFecha()).append("\n");
                mensaje.append("Productos:\n");

                for (Producto producto : venta.getProductos()) {
                    mensaje.append("  - ").append(producto.getNombre())
                            .append(" - $").append(String.format("%,d", producto.getPrecio())).append("\n");
                }

                mensaje.append("Valor de la venta: $").append(String.format("%,d", valorVenta)).append("\n");
                mensaje.append("Comision (").append(valorVenta <= 100000 ? "10%" : "7%")
                        .append("): $").append(String.format("%,d", comision)).append("\n\n");
            }

            mensaje.append("================================\n");
            mensaje.append("TOTAL VENTAS: $")
                    .append(String.format("%,d", vendedorEncontrado.calcularTotalVentas())).append("\n");
            mensaje.append("TOTAL COMISIONES: $")
                    .append(String.format("%,d", totalComisiones)).append("\n");
        }

        JTextArea textArea = new JTextArea(mensaje.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(false);
        textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        textArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 400));

        JOptionPane.showMessageDialog(null, scrollPane, "Detalle del Vendedor", JOptionPane.PLAIN_MESSAGE);
    }

    private static void verResumenGeneral(List<Vendedor> vendedores) {
        if (vendedores.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay vendedores registrados", "Vendedores",
                    JOptionPane.PLAIN_MESSAGE);
            return;
        }

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("=======================================\n");
        mensaje.append("       RESUMEN GENERAL DE VENTAS       \n");
        mensaje.append("=======================================\n\n");

        int totalVendedores = vendedores.size();
        int totalVentas = 0;
        int totalGeneral = 0;
        int totalComisionesGeneral = 0;

        for (Vendedor vendedor : vendedores) {
            totalVentas += vendedor.getVentas().size();
            totalGeneral += vendedor.calcularTotalVentas();

            for (Venta venta : vendedor.getVentas()) {
                totalComisionesGeneral += vendedor.calcularComision(venta.getId());
            }
        }

        mensaje.append("Vendedores registrados: ").append(totalVendedores).append("\n");
        mensaje.append("Total de ventas realizadas: ").append(totalVentas).append("\n");
        mensaje.append("Promedio de ventas por vendedor: ")
                .append(String.format("%.2f", (double) totalVentas / totalVendedores)).append("\n\n");

        mensaje.append("---------------------------------------\n");
        mensaje.append("Monto total de ventas: $")
                .append(String.format("%,d", totalGeneral)).append("\n");
        mensaje.append("Total de comisiones pagadas: $")
                .append(String.format("%,d", totalComisionesGeneral)).append("\n");
        mensaje.append("Promedio de comision por vendedor: $")
                .append(String.format("%,d", totalComisionesGeneral / totalVendedores)).append("\n");
        mensaje.append("=======================================\n");

        JTextArea textArea = new JTextArea(mensaje.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        textArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(450, 300));

        JOptionPane.showMessageDialog(null, scrollPane, "Resumen General", JOptionPane.PLAIN_MESSAGE);
    }

    private static void agregarVendedor(List<Vendedor> vendedores, Entrada entrada, Salida salida) {
        String idStr = entrada.recibirInformacion("Ingrese el ID del vendedor:");
        if (idStr == null || idStr.trim().isEmpty()) {
            salida.mostrarMensaje("ID invalido", "Error");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            salida.mostrarMensaje("El ID debe ser un numero entero", "Error");
            return;
        }

        for (Vendedor v : vendedores) {
            if (v.getId() == id) {
                salida.mostrarMensaje("Ya existe un vendedor con ese ID", "Error");
                return;
            }
        }

        String nombre = entrada.recibirInformacion("Ingrese el nombre del vendedor:");
        if (nombre == null || nombre.trim().isEmpty()) {
            salida.mostrarMensaje("Nombre invalido", "Error");
            return;
        }

        Vendedor nuevoVendedor = new Vendedor(id, nombre);
        vendedores.add(nuevoVendedor);

        salida.mostrarMensaje(
                "Vendedor agregado exitosamente\n\n" +
                        "ID: " + id + "\n" +
                        "Nombre: " + nombre + "\n" +
                        "Ventas: 0",
                "Vendedor Agregado");
    }

    private static void eliminarVendedor(List<Vendedor> vendedores, Entrada entrada, Salida salida) {
        if (vendedores.isEmpty()) {
            salida.mostrarMensaje("No hay vendedores registrados para eliminar", "Vendedores");
            return;
        }

        String idStr = entrada.recibirInformacion("Ingrese el ID del vendedor a eliminar:");
        if (idStr == null || idStr.trim().isEmpty()) {
            salida.mostrarMensaje("ID invalido", "Error");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            salida.mostrarMensaje("El ID debe ser un numero entero", "Error");
            return;
        }

        Vendedor vendedorAEliminar = null;
        for (Vendedor v : vendedores) {
            if (v.getId() == id) {
                vendedorAEliminar = v;
                break;
            }
        }

        if (vendedorAEliminar == null) {
            salida.mostrarMensaje("No se encontro ningun vendedor con el ID " + id, "Error");
            return;
        }

        vendedores.remove(vendedorAEliminar);
        salida.mostrarMensaje("Vendedor " + vendedorAEliminar.getNombre() + " (ID: " + id
                + ") eliminado exitosamente", "Vendedor Eliminado");
    }

    private static void agregarVentaAVendedor(List<Vendedor> vendedores, List<Producto> productos,
            Entrada entrada, Salida salida) {
        if (vendedores.isEmpty()) {
            salida.mostrarMensaje("No hay vendedores registrados", "Error");
            return;
        }

        if (productos.isEmpty()) {
            salida.mostrarMensaje("No hay productos disponibles", "Error");
            return;
        }

        String idVendedorStr = entrada.recibirInformacion("Ingrese el ID del vendedor:");
        if (idVendedorStr == null || idVendedorStr.trim().isEmpty()) {
            salida.mostrarMensaje("ID invalido", "Error");
            return;
        }

        int idVendedor;
        try {
            idVendedor = Integer.parseInt(idVendedorStr);
        } catch (NumberFormatException e) {
            salida.mostrarMensaje("El ID debe ser un numero entero", "Error");
            return;
        }

        Vendedor vendedor = null;
        for (Vendedor v : vendedores) {
            if (v.getId() == idVendedor) {
                vendedor = v;
                break;
            }
        }

        if (vendedor == null) {
            salida.mostrarMensaje("No se encontro ningun vendedor con el ID " + idVendedor, "Error");
            return;
        }

        int maxIdVenta = 0;
        for (Vendedor v : vendedores) {
            for (Venta venta : v.getVentas()) {
                if (venta.getId() > maxIdVenta) {
                    maxIdVenta = venta.getId();
                }
            }
        }
        int nuevoIdVenta = maxIdVenta + 1;

        ArrayList<Producto> productosVenta = new ArrayList<>();
        boolean agregarMasProductos = true;

        while (agregarMasProductos) {
            StringBuilder opcionesProductos = new StringBuilder();
            opcionesProductos.append("Productos disponibles:\n\n");
            for (int i = 0; i < productos.size(); i++) {
                Producto p = productos.get(i);
                opcionesProductos.append((i + 1)).append(". ").append(p.getNombre())
                        .append(" - $").append(String.format("%,d", p.getPrecio())).append("\n");
            }
            opcionesProductos.append("\nIngrese el numero del producto:");

            String productoNumStr = entrada.recibirInformacion(opcionesProductos.toString());
            if (productoNumStr == null || productoNumStr.trim().isEmpty()) {
                salida.mostrarMensaje("Numero de producto invalido", "Error");
                continue;
            }

            int productoNum;
            try {
                productoNum = Integer.parseInt(productoNumStr);
                if (productoNum < 1 || productoNum > productos.size()) {
                    salida.mostrarMensaje("Numero de producto fuera de rango", "Error");
                    continue;
                }
            } catch (NumberFormatException e) {
                salida.mostrarMensaje("El numero debe ser un entero", "Error");
                continue;
            }

            productosVenta.add(productos.get(productoNum - 1));

            String[] opcionesAgregar = { "Si", "No" };
            int respuesta = entrada.mostrarMenu(opcionesAgregar, "¿Desea agregar otro producto?",
                    "Agregar Producto");
            agregarMasProductos = (respuesta == 1);
        }

        if (productosVenta.isEmpty()) {
            salida.mostrarMensaje("Debe agregar al menos un producto", "Error");
            return;
        }

        Date fechaVenta = new Date(System.currentTimeMillis());
        Venta nuevaVenta = new Venta(nuevoIdVenta, fechaVenta, productosVenta);
        vendedor.agregarVenta(nuevaVenta);

        int valorVenta = nuevaVenta.calcularValorVenta(nuevaVenta);
        int comision = vendedor.calcularComision(nuevaVenta.getId());

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Venta agregada exitosamente\n\n");
        mensaje.append("Vendedor: ").append(vendedor.getNombre()).append("\n");
        mensaje.append("ID Venta: ").append(nuevoIdVenta).append("\n");
        mensaje.append("Fecha: ").append(fechaVenta).append("\n\n");
        mensaje.append("Productos:\n");
        for (Producto p : productosVenta) {
            mensaje.append("  - ").append(p.getNombre())
                    .append(" - $").append(String.format("%,d", p.getPrecio())).append("\n");
        }
        mensaje.append("\nValor de la venta: $").append(String.format("%,d", valorVenta)).append("\n");
        mensaje.append("Comision (").append(valorVenta <= 100000 ? "10%" : "7%")
                .append("): $").append(String.format("%,d", comision));

        salida.mostrarMensaje(mensaje.toString(), "Venta Agregada");
    }
}
