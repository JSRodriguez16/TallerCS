package Punto4;

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
        List<Empleado> empleados = null;

        try {
            empleados = generarEmpleadosAleatorios(25, random);
        } catch (Exception ex) {
            Errores.manejarErrores(() -> {
                throw ex;
            }, salida);
            return;
        }

        String[] opciones = {
                "Ver todos los empleados registrados",
                "Agregar empleado",
                "Eliminar empleado por codigo",
        };

        boolean continuar = true;
        while (continuar) {
            int opcion = entrada.mostrarMenu(opciones, "Seleccione una opcion", "Menu");
            switch (opcion) {
                case 0:
                case -1:
                    continuar = false;
                    break;
                case 1:
                    try {
                        verTodosEmpleados(empleados);
                    } catch (Exception ex) {
                        Errores.manejarErrores(() -> {
                            throw ex;
                        }, salida);
                    }
                    break;
                case 2:
                    try {
                        agregarEmpleado(empleados, entrada, salida);
                    } catch (Exception ex) {
                        Errores.manejarErrores(() -> {
                            throw ex;
                        }, salida);
                    }
                    break;
                case 3:
                    try {
                        eliminarEmpleado(empleados, entrada, salida);
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

    private static List<Empleado> generarEmpleadosAleatorios(int cantidad, Random random) {
        List<Empleado> empleados = new ArrayList<>();
        String[] nombres = {
                "Juan Perez", "Maria Garcia", "Carlos Lopez", "Ana Martinez", "Luis Rodriguez",
                "Carmen Fernandez", "Jose Gonzalez", "Laura Sanchez", "Miguel Torres", "Isabel Ramirez",
                "Pedro Flores", "Sofia Diaz", "Diego Morales", "Elena Castro", "Jorge Ruiz",
                "Patricia Herrera", "Roberto Jimenez", "Alejandra Mendez", "Fernando Ortiz", "Claudia Vargas"
        };

        for (int i = 0; i < cantidad; i++) {
            int codigo = 2000 + i;
            String nombre = nombres[random.nextInt(nombres.length)] + " " + (i + 1);
            float salarioHora = 5000 + random.nextInt(10000);
            int horasTrabajadas = 120 + random.nextInt(201);
            int numeroHijos = random.nextInt(16);

            Empleado empleado = new Empleado(codigo, nombre, salarioHora, horasTrabajadas);
            empleado.setNumeroHijos(numeroHijos);
            empleados.add(empleado);
        }
        return empleados;
    }

    private static void verTodosEmpleados(List<Empleado> empleados) {
        if (empleados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay empleados registrados", "Empleados",
                    JOptionPane.PLAIN_MESSAGE);
            return;
        }

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Total de empleados: ").append(empleados.size()).append("\n\n");

        for (Empleado empleado : empleados) {
            mensaje.append("Codigo: ").append(empleado.getCodigo()).append("\n");
            mensaje.append("Nombre: ").append(empleado.getNombre()).append("\n");
            mensaje.append("Salario Hora: $").append(String.format("%.2f", empleado.getSalarioHora()))
                    .append("\n");
            mensaje.append("Horas Trabajadas: ").append(empleado.getHorasTrabajadas()).append("\n");
            mensaje.append("Numero de Hijos: ").append(empleado.getNumeroHijos()).append("\n");
            mensaje.append("Devengado: $").append(String.format("%.2f", empleado.calcularDevengado()))
                    .append("\n");
            mensaje.append("Retencion: $").append(String.format("%.2f", empleado.calcularRetencion()))
                    .append("\n");
            mensaje.append("Subsidio: $").append(String.format("%.2f", empleado.calcularSubsidio()))
                    .append("\n");
            mensaje.append("Salario Neto: $").append(String.format("%.2f", empleado.calcularSalarioNeto()))
                    .append("\n");
            mensaje.append("----------------------------------------\n");
        }

        JTextArea textArea = new JTextArea(mensaje.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(520, 420));

        JOptionPane.showMessageDialog(null, scrollPane, "Empleados Registrados", JOptionPane.PLAIN_MESSAGE);
    }

    private static void agregarEmpleado(List<Empleado> empleados, Entrada entrada, Salida salida) {
        String codigoStr = entrada.recibirInformacion("Ingrese el codigo del empleado:");
        if (codigoStr == null || codigoStr.trim().isEmpty()) {
            salida.mostrarMensaje("Codigo invalido", "Error");
            return;
        }

        int codigo;
        try {
            codigo = Integer.parseInt(codigoStr);
        } catch (NumberFormatException e) {
            salida.mostrarMensaje("El codigo debe ser un numero entero", "Error");
            return;
        }

        for (Empleado e : empleados) {
            if (e.getCodigo() == codigo) {
                salida.mostrarMensaje("Ya existe un empleado con ese codigo", "Error");
                return;
            }
        }

        String nombre = entrada.recibirInformacion("Ingrese el nombre del empleado:");
        if (nombre == null || nombre.trim().isEmpty()) {
            salida.mostrarMensaje("Nombre invalido", "Error");
            return;
        }

        String salarioHoraStr = entrada.recibirInformacion("Ingrese el salario por hora:");
        if (salarioHoraStr == null || salarioHoraStr.trim().isEmpty()) {
            salida.mostrarMensaje("Salario por hora invalido", "Error");
            return;
        }

        float salarioHora;
        try {
            salarioHora = Float.parseFloat(salarioHoraStr);
            if (salarioHora <= 0) {
                salida.mostrarMensaje("El salario por hora debe ser mayor a 0", "Error");
                return;
            }
        } catch (NumberFormatException e) {
            salida.mostrarMensaje("El salario por hora debe ser un numero", "Error");
            return;
        }

        String horasStr = entrada.recibirInformacion("Ingrese las horas trabajadas:");
        if (horasStr == null || horasStr.trim().isEmpty()) {
            salida.mostrarMensaje("Horas trabajadas invalidas", "Error");
            return;
        }

        int horas;
        try {
            horas = Integer.parseInt(horasStr);
            if (horas <= 0) {
                salida.mostrarMensaje("Las horas trabajadas deben ser mayor a 0", "Error");
                return;
            }
        } catch (NumberFormatException e) {
            salida.mostrarMensaje("Las horas trabajadas deben ser un numero entero", "Error");
            return;
        }

        String hijosStr = entrada.recibirInformacion("Ingrese el numero de hijos:");
        if (hijosStr == null || hijosStr.trim().isEmpty()) {
            salida.mostrarMensaje("Numero de hijos invalido", "Error");
            return;
        }

        int numeroHijos;
        try {
            numeroHijos = Integer.parseInt(hijosStr);
            if (numeroHijos < 0) {
                salida.mostrarMensaje("El numero de hijos no puede ser negativo", "Error");
                return;
            }
        } catch (NumberFormatException e) {
            salida.mostrarMensaje("El numero de hijos debe ser un numero entero", "Error");
            return;
        }

        Empleado nuevoEmpleado = new Empleado(codigo, nombre, salarioHora, horas);
        nuevoEmpleado.setNumeroHijos(numeroHijos);
        empleados.add(nuevoEmpleado);

        salida.mostrarMensaje(
                "Empleado agregado exitosamente\n\n" +
                        "Codigo: " + codigo + "\n" +
                        "Nombre: " + nombre + "\n" +
                        "Salario Hora: $" + String.format("%.2f", salarioHora) + "\n" +
                        "Horas Trabajadas: " + horas + "\n" +
                        "Numero de Hijos: " + numeroHijos + "\n" +
                        "Devengado: $" + String.format("%.2f", nuevoEmpleado.calcularDevengado()) + "\n" +
                        "Retencion: $" + String.format("%.2f", nuevoEmpleado.calcularRetencion()) + "\n" +
                        "Subsidio: $" + String.format("%.2f", nuevoEmpleado.calcularSubsidio()) + "\n" +
                        "Salario Neto: $" + String.format("%.2f", nuevoEmpleado.calcularSalarioNeto()),
                "Empleado Agregado");
    }

    private static void eliminarEmpleado(List<Empleado> empleados, Entrada entrada, Salida salida) {
        if (empleados.isEmpty()) {
            salida.mostrarMensaje("No hay empleados registrados para eliminar", "Empleados");
            return;
        }

        String codigoStr = entrada.recibirInformacion("Ingrese el codigo del empleado a eliminar:");
        if (codigoStr == null || codigoStr.trim().isEmpty()) {
            salida.mostrarMensaje("Codigo invalido", "Error");
            return;
        }

        int codigo;
        try {
            codigo = Integer.parseInt(codigoStr);
        } catch (NumberFormatException e) {
            salida.mostrarMensaje("El codigo debe ser un numero entero", "Error");
            return;
        }

        Iterator<Empleado> iterator = empleados.iterator();
        boolean encontrado = false;
        while (iterator.hasNext()) {
            Empleado empleado = iterator.next();
            if (empleado.getCodigo() == codigo) {
                iterator.remove();
                encontrado = true;
                salida.mostrarMensaje("Empleado con codigo " + codigo + " eliminado exitosamente",
                        "Empleado Eliminado");
                break;
            }
        }

        if (!encontrado) {
            salida.mostrarMensaje("No se encontro ningun empleado con el codigo " + codigo, "Error");
        }
    }

}
