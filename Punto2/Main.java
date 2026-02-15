package Punto2;

import java.util.ArrayList;
import java.util.Iterator;
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
        List<Pedido> pedidos = null;

        try {
            pedidos = generarPedidosAleatorios(25, random);
        } catch (Exception ex) {
            Errores.manejarErrores(() -> {
                throw ex;
            }, salida);
            return;
        }

        String[] opciones = {
                "Ver todos los pedidos registrados",
                "Agregar pedido",
                "Eliminar pedido por ID",
        };

        boolean continuar = true;
        while (continuar) {
            int opcion = entrada.mostrarMenu(opciones, "Seleccione una opcion", "Menu");
            switch (opcion) {
                case 0:
                    continuar = false;
                    break;
                case -1:
                    continuar = false;
                    break;
                case 1:
                    try {
                        verTodosPedidos(pedidos, salida);
                    } catch (Exception ex) {
                        Errores.manejarErrores(() -> {
                            throw ex;
                        }, salida);
                    }
                    break;
                case 2:
                    try {
                        agregarPedido(pedidos, entrada, salida);
                    } catch (Exception ex) {
                        Errores.manejarErrores(() -> {
                            throw ex;
                        }, salida);
                    }
                    break;
                case 3:
                    try {
                        eliminarPedido(pedidos, entrada, salida);
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

    private static List<Pedido> generarPedidosAleatorios(int cantidad, Random random) {
        List<Pedido> pedidos = new ArrayList<>();
        String[] nombres = {
                "Juan Perez", "Maria Garcia", "Carlos Lopez", "Ana Martinez", "Luis Rodriguez",
                "Carmen Fernandez", "Jose Gonzalez", "Laura Sanchez", "Miguel Torres", "Isabel Ramirez",
                "Pedro Flores", "Sofia Diaz", "Diego Morales", "Elena Castro", "Jorge Ruiz"
        };

        for (int i = 0; i < cantidad; i++) {
            String nombre = nombres[random.nextInt(nombres.length)];
            int id = 1000 + i;
            int tipoFumigacion = 1 + random.nextInt(4);
            int numeroHectareas = 50 + random.nextInt(1500);

            pedidos.add(new Pedido(nombre, id, tipoFumigacion, numeroHectareas));
        }
        return pedidos;
    }

    private static void verTodosPedidos(List<Pedido> pedidos, Salida salida) {
        if (pedidos.isEmpty()) {
            salida.mostrarMensaje("No hay pedidos registrados", "Pedidos");
            return;
        }

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Total de pedidos: ").append(pedidos.size()).append("\n\n");

        for (Pedido pedido : pedidos) {
            mensaje.append("ID: ").append(pedido.getId()).append("\n");
            mensaje.append("Granjero: ").append(pedido.getNombreGranjero()).append("\n");
            mensaje.append("Tipo de fumigacion: ").append(obtenerTipoFumigacion(pedido.getTipoFumigacion()))
                    .append("\n");
            mensaje.append("Numero de hectareas: ").append(pedido.getNumeroHectareas()).append("\n");
            mensaje.append("Total: $").append(pedido.calculoTotal()).append("\n");
            mensaje.append("----------------------------------------\n");
        }

        JTextArea textArea = new JTextArea(mensaje.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 400));

        JOptionPane.showMessageDialog(null, scrollPane, "Pedidos Registrados", JOptionPane.PLAIN_MESSAGE);
    }

    private static String obtenerTipoFumigacion(int tipo) {
        switch (tipo) {
            case 1:
                return "Tipo 1 ($10/hectarea)";
            case 2:
                return "Tipo 2 ($15/hectarea)";
            case 3:
                return "Tipo 3 ($20/hectarea)";
            case 4:
                return "Tipo 4 ($30/hectarea)";
            default:
                return "Tipo desconocido";
        }
    }

    private static void agregarPedido(List<Pedido> pedidos, Entrada entrada, Salida salida) {
        String nombre = entrada.recibirInformacion("Ingrese el nombre del granjero:");
        if (nombre == null || nombre.trim().isEmpty()) {
            salida.mostrarMensaje("Nombre invalido", "Error");
            return;
        }

        String idStr = entrada.recibirInformacion("Ingrese el ID del pedido:");
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

        // Verificar si el ID ya existe
        for (Pedido p : pedidos) {
            if (p.getId() == id) {
                salida.mostrarMensaje("Ya existe un pedido con ese ID", "Error");
                return;
            }
        }

        String tipoStr = entrada.recibirInformacion(
                "Ingrese el tipo de fumigacion (1-4):\n1: $10/hectarea\n2: $15/hectarea\n3: $20/hectarea\n4: $30/hectarea");
        if (tipoStr == null || tipoStr.trim().isEmpty()) {
            salida.mostrarMensaje("Tipo de fumigacion invalido", "Error");
            return;
        }

        int tipo;
        try {
            tipo = Integer.parseInt(tipoStr);
            if (tipo < 1 || tipo > 4) {
                salida.mostrarMensaje("El tipo de fumigacion debe ser entre 1 y 4", "Error");
                return;
            }
        } catch (NumberFormatException e) {
            salida.mostrarMensaje("El tipo de fumigacion debe ser un numero entero", "Error");
            return;
        }

        String hectareasStr = entrada.recibirInformacion("Ingrese el numero de hectareas:");
        if (hectareasStr == null || hectareasStr.trim().isEmpty()) {
            salida.mostrarMensaje("Numero de hectareas invalido", "Error");
            return;
        }

        int hectareas;
        try {
            hectareas = Integer.parseInt(hectareasStr);
            if (hectareas <= 0) {
                salida.mostrarMensaje("El numero de hectareas debe ser mayor a 0", "Error");
                return;
            }
        } catch (NumberFormatException e) {
            salida.mostrarMensaje("El numero de hectareas debe ser un numero entero", "Error");
            return;
        }

        Pedido nuevoPedido = new Pedido(nombre, id, tipo, hectareas);
        pedidos.add(nuevoPedido);

        salida.mostrarMensaje(
                "Pedido agregado exitosamente\n\n" +
                        "ID: " + id + "\n" +
                        "Granjero: " + nombre + "\n" +
                        "Tipo: " + obtenerTipoFumigacion(tipo) + "\n" +
                        "Hectareas: " + hectareas + "\n" +
                        "Total: $" + nuevoPedido.calculoTotal(),
                "Pedido Agregado");
    }

    private static void eliminarPedido(List<Pedido> pedidos, Entrada entrada, Salida salida) {
        if (pedidos.isEmpty()) {
            salida.mostrarMensaje("No hay pedidos registrados para eliminar", "Pedidos");
            return;
        }

        String idStr = entrada.recibirInformacion("Ingrese el ID del pedido a eliminar:");
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

        Iterator<Pedido> iterator = pedidos.iterator();
        boolean encontrado = false;
        while (iterator.hasNext()) {
            Pedido pedido = iterator.next();
            if (pedido.getId() == id) {
                iterator.remove();
                encontrado = true;
                salida.mostrarMensaje("Pedido con ID " + id + " eliminado exitosamente", "Pedido Eliminado");
                break;
            }
        }

        if (!encontrado) {
            salida.mostrarMensaje("No se encontro ningun pedido con el ID " + id, "Error");
        }
    }
}
