package Punto8;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import MetodosAuxiliares.Entrada;
import MetodosAuxiliares.Salida;

public class Main {

    public static void main(String[] args) {
        Entrada entrada = new Entrada();
        Salida salida = new Salida();
        Random random = new Random();

        List<Persona> personas = generarPersonasAleatorias(100, random);

        String[] opciones = {
                "Mujeres de cabello rubio y ojos azules, que miden entre 1.65 metros y 1.75 metros y que pesen menos de 120 libras",
                "Hombres de ojos castaños de más de 1.70 metros de altura y que pesen entre 180 y 220 libras",
                "Agregar persona",
                "Eliminar persona por Id",
                "Ver todas las personas"
        };

        boolean continuar = true;
        while (continuar) {
            int opcion = entrada.mostrarMenu(opciones, "Seleccione una opcion", "Menu");
            switch (opcion) {
                case -1:
                case 0:
                    continuar = false;
                    break;
                case 1:
                    mostrarFiltroA(personas);
                    break;
                case 2:
                    mostrarFiltroB(personas);
                    break;
                case 3:
                    agregarPersona(personas, entrada, salida);
                    break;
                case 4:
                    eliminarPersonaPorId(personas, entrada, salida);
                    break;
                case 5:
                    mostrarTodasLasPersonas(personas);
                    break;
                default:
                    salida.mostrarMensaje("Opcion no valida", "Menu");
                    break;
            }
        }
    }

    private static List<Persona> generarPersonasAleatorias(int cantidad, Random random) {
        String[] nombresFemeninos = {
                "Ana", "Maria", "Sofia", "Laura", "Camila", "Paula", "Valentina", "Daniela", "Juliana"
        };
        String[] nombresMasculinos = {
                "Luis", "Carlos", "Juan", "Diego", "Andres", "Miguel", "Sebastian", "Nicolas", "Mateo"
        };
        String[] apellidos = {
                "Gomez", "Perez", "Rodriguez", "Lopez", "Martinez", "Hernandez", "Diaz", "Torres", "Ramirez",
                "Suarez", "Morales", "Castro"
        };

        List<Persona> personas = new ArrayList<>();

        for (int i = 1; i <= cantidad; i++) {
            String sexo = random.nextBoolean() ? "F" : "M";
            String[] nombresPorSexo = sexo.equals("F") ? nombresFemeninos : nombresMasculinos;
            String nombre = nombresPorSexo[random.nextInt(nombresPorSexo.length)] + " "
                    + apellidos[random.nextInt(apellidos.length)];

            int edad = 18 + random.nextInt(48);
            float altura = 1.50f + random.nextFloat() * 0.50f;
            float peso = 95f + random.nextFloat() * 145f;
            int colorOjos = 1 + random.nextInt(3);
            int colorCabello = 1 + random.nextInt(3);

            personas.add(new Persona(i, nombre, sexo, edad, altura, peso, colorOjos, colorCabello));
        }

        return personas;
    }

    private static void mostrarFiltroA(List<Persona> personas) {
        List<String> nombres = filtrarPersonas(personas, p -> 
            p.getSexo().equalsIgnoreCase("F") && p.getColorCabello() == 2 && p.getColorOjos() == 1 &&
            p.getAltura() >= 1.65f && p.getAltura() <= 1.75f && p.getPeso() < 120f);
        mostrarResultado("Mujeres rubias, ojos azules, entre 1.65 y 1.75 m, y menos de 120 lb", nombres);
    }

    private static void mostrarFiltroB(List<Persona> personas) {
        List<String> nombres = filtrarPersonas(personas, p ->
            p.getSexo().equalsIgnoreCase("M") && p.getColorOjos() == 2 &&
            p.getAltura() > 1.70f && p.getPeso() >= 180f && p.getPeso() <= 220f);
        mostrarResultado("Hombres de ojos castaños, mas de 1.70 m, y entre 180 y 220 lb", nombres);
    }

    private static List<String> filtrarPersonas(List<Persona> personas,
            java.util.function.Predicate<Persona> criterio) {
        List<String> nombres = new ArrayList<>();
        for (Persona persona : personas) {
            if (criterio.test(persona)) {
                nombres.add(persona.getNombre());
            }
        }
        return nombres;
    }

    private static void agregarPersona(List<Persona> personas, Entrada entrada, Salida salida) {
        // Leer nombre
        String textoNombre = entrada.recibirInformacion("Ingrese el nombre:");
        if (textoNombre == null || textoNombre.trim().isEmpty()) return;
        String nombre = textoNombre.trim();

        // Leer y validar sexo
        String textoSexo = entrada.recibirInformacion("Ingrese sexo (F/M):");
        if (textoSexo == null || textoSexo.trim().isEmpty()) return;
        String sexo = textoSexo.toUpperCase().trim();
        if (!sexo.equals("F") && !sexo.equals("M")) {
            salida.mostrarMensaje("El sexo debe ser F o M.", "Error Punto 8");
            return;
        }

        Integer edad = leerConValidacion(entrada, salida, "Ingrese la edad:", 0, 120);
        if (edad == null) return;

        Float altura = leerDecimalConValidacion(entrada, salida,
                "Ingrese la altura en metros (ej: 1.72):", 1.0f, 2.5f);
        if (altura == null) return;

        Float peso = leerDecimalConValidacion(entrada, salida, "Ingrese el peso en libras:", 50f, 500f);
        if (peso == null) return;

        Integer colorOjos = leerConValidacion(entrada, salida,
                "Color de ojos (1 azules, 2 castaños, 3 demas):", 1, 3);
        if (colorOjos == null) return;

        Integer colorCabello = leerConValidacion(entrada, salida,
                "Color de cabello (1 castaño, 2 rubio, 3 demas):", 1, 3);
        if (colorCabello == null) return;

        // Calcular siguiente ID directamente
        int nuevoId = 1;
        for (Persona p : personas) {
            if (p.getId() >= nuevoId) {
                nuevoId = p.getId() + 1;
            }
        }

        personas.add(new Persona(nuevoId, nombre, sexo, edad, altura, peso, colorOjos, colorCabello));
        salida.mostrarMensaje("Persona agregada con ID " + nuevoId, "Punto 8");
    }

    private static void eliminarPersonaPorId(List<Persona> personas, Entrada entrada, Salida salida) {
        if (personas.isEmpty()) {
            salida.mostrarMensaje("No hay personas registradas para eliminar.", "Punto 8");
            return;
        }

        String textoId = entrada.recibirInformacion("Ingrese el ID de la persona a eliminar:");
        if (textoId == null || textoId.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(textoId.trim());
            Iterator<Persona> iterator = personas.iterator();
            while (iterator.hasNext()) {
                Persona persona = iterator.next();
                if (persona.getId() == id) {
                    iterator.remove();
                    salida.mostrarMensaje("Persona con ID " + id + " eliminada correctamente.", "Punto 8");
                    return;
                }
            }
            salida.mostrarMensaje("No se encontro una persona con ID " + id, "Punto 8");
        } catch (NumberFormatException e) {
            salida.mostrarMensaje("Debe ingresar un numero entero valido.", "Error Punto 8");
        }
    }

    private static Integer leerConValidacion(Entrada entrada, Salida salida, String mensaje, int min, int max) {
        String texto = entrada.recibirInformacion(mensaje);
        if (texto == null || texto.trim().isEmpty()) return null;
        try {
            int valor = Integer.parseInt(texto.trim());
            if (valor < min || valor > max) {
                salida.mostrarMensaje("El valor debe estar entre " + min + " y " + max + ".", "Error Punto 8");
                return null;
            }
            return valor;
        } catch (NumberFormatException e) {
            salida.mostrarMensaje("Debe ingresar un numero entero valido.", "Error Punto 8");
            return null;
        }
    }

    private static Float leerDecimalConValidacion(Entrada entrada, Salida salida, String mensaje, float min, float max) {
        String texto = entrada.recibirInformacion(mensaje);
        if (texto == null || texto.trim().isEmpty()) return null;
        try {
            float valor = Float.parseFloat(texto.replace(',', '.').trim());
            if (valor < min || valor > max) {
                salida.mostrarMensaje("El valor debe estar entre " + min + " y " + max + ".", "Error Punto 8");
                return null;
            }
            return valor;
        } catch (NumberFormatException e) {
            salida.mostrarMensaje("Debe ingresar un numero decimal valido.", "Error Punto 8");
            return null;
        }
    }

    private static void mostrarTodasLasPersonas(List<Persona> personas) {
        if (personas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay personas registradas.", "Todas las Personas",
                    JOptionPane.PLAIN_MESSAGE);
            return;
        }

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("ID\t Nombre\t\t\t Sexo\t Edad\t Altura\t Peso\t Ojos\t\t Cabello\n");
        mensaje.append(
                "-----------------------------------------------------------------------------------------------------------\n");

        for (Persona p : personas) {
            String nombreOjos = p.getColorOjos() == 1 ? "Azules" : p.getColorOjos() == 2 ? "Castaños" : p.getColorOjos() == 3 ? "Otros" : "Desconocido";
            String nombreCabello = p.getColorCabello() == 1 ? "Castaño" : p.getColorCabello() == 2 ? "Rubio" : p.getColorCabello() == 3 ? "Otros" : "Desconocido";
            mensaje.append(p.getId()).append("\t ")
                   .append(String.format("%-20s", p.getNombre())).append("\t ")
                   .append(p.getSexo()).append("\t ")
                   .append(p.getEdad()).append("\t ")
                   .append(String.format("%.2f", p.getAltura())).append("\t ")
                   .append(String.format("%.2f", p.getPeso())).append("\t ")
                   .append(String.format("%-10s", nombreOjos)).append("\t ")
                   .append(nombreCabello).append("\n");
        }

        mostrarEnVentana(mensaje.toString(), "Todas las Personas", 900, 400);
    }

    private static void mostrarResultado(String titulo, List<String> nombres) {
        StringBuilder mensaje = new StringBuilder(titulo).append("\n\n");
        if (nombres.isEmpty()) {
            mensaje.append("No se encontraron personas que cumplan la condicion.");
        } else {
            for (String nombre : nombres) {
                mensaje.append("- ").append(nombre).append("\n");
            }
        }
        mostrarEnVentana(mensaje.toString(), "Resultado", 520, 360);
    }

    private static void mostrarEnVentana(String contenido, String titulo, int ancho, int alto) {
        JTextArea textArea = new JTextArea(contenido);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(ancho, alto));

        JOptionPane.showMessageDialog(null, scrollPane, titulo, JOptionPane.PLAIN_MESSAGE);
    }
}
