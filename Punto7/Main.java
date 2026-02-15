import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import MetodosAuxiliares.Entrada;
import MetodosAuxiliares.Salida;

public class Main {
    static Random random = new Random();
    static List<Persona> personas = new ArrayList<>();
    static List<Accidente> accidentes = new ArrayList<>();

    public static void main(String[] args) {
        Entrada entrada = new Entrada();
        Salida salida = new Salida();
        for (int i = 0; i < 25; i++) {
            Persona persona = generarPersonaAleatoria(random, i);
            personas.add(persona);

            int cantidadAccidentes = 1 + random.nextInt(2);
            for (int j = 0; j < cantidadAccidentes; j++) {
                accidentes.add(generarAccidenteAleatorio(random, persona));
            }
        }

        String[] opciones = {
            "Ver registros de accidentes",
            "Ver el porcentaje de conductores menores de 25 años",
            "Ver el porcentaje de conductores del sexo femenino",
            "Ver el porcentaje de conductores masculinos con edades entre 12 y 30 años",
            "Ver el porcentaje de conductores cuyos carros estan registrados fuera de Medellin",
            "Registrar nuevo accidente",
            "Salir"
        };
        salida.mostrarMensaje(
            "Bienvenido al sistema de la oficina de seguros, a continuacion seleccione una opcion:",
            "Menu"
        );
        boolean continuar = true;
        while (continuar) {
            int opcion = entrada.mostrarMenu(opciones, "Seleccione una opcion", "Menu");
            switch (opcion) {
                case -1:
                    salida.mostrarMensaje("Operacion terminada", "Menu");
                    continuar = false;
                    break;
                case 0:
                    salida.mostrarMensaje("Operacion cancelada", "Menu");
                    break;
                case 1:
                    StringBuilder registros = new StringBuilder();
                    for (Persona persona : personas) {
                        registros.append("Persona: ")
                            .append(persona.getNombre())
                            .append(" (")
                            .append(persona.getCedula())
                            .append(")\n");
                        for (Accidente accidente : accidentes) {
                            if (accidente.getPersona() == persona) {
                                registros.append("  Accidente: ")
                                    .append(accidente.getDescripcion())
                                    .append(", fecha=")
                                    .append(accidente.getFechaAccidente())
                                    .append("\n");
                            }
                        }
                    }
                    salida.mostrarMensaje(registros.toString(), "Registros de accidentes");
                    break;
                case 2:
                    salida.mostrarMensaje(
                        "Porcentaje de conductores menores de 25 anos: " + (getPerMenores()) + "%",
                        "Resultado"
                    );
                    break;
                case 3:
                    salida.mostrarMensaje(
                        "Porcentaje de conductores del sexo femenino: " + (getPerFemenino()) + "%",
                        "Resultado"
                    );
                    break;
                case 4:
                    salida.mostrarMensaje(
                        "Porcentaje de conductores masculinos con edades entre 12 y 30 anos: "
                            + (getPerRango()) + "%",
                        "Resultado"
                    );
                    break;
                case 5:
                    salida.mostrarMensaje(
                        "Porcentaje de conductores cuyos carros estan registrados fuera de Medellin: "
                            + (getPerFuera()) + "%",
                        "Resultado"
                    );
                    break;
                case 6:
                    String nombre = leerTextoNoVacio(entrada, salida, "Ingrese el nombre de la persona:");
                    if (nombre == null) {
                        salida.mostrarMensaje("Operacion cancelada", "Registro");
                        break;
                    }
                    Integer cedula = leerEntero(entrada, salida, "Ingrese la cedula de la persona:");
                    if (cedula == null) {
                        salida.mostrarMensaje("Operacion cancelada", "Registro");
                        break;
                    }
                    Date fechaNac = leerFecha(
                        entrada,
                        salida,
                        "Ingrese la fecha de nacimiento de la persona (YYYY-MM-DD):"
                    );
                    if (fechaNac == null) {
                        salida.mostrarMensaje("Operacion cancelada", "Registro");
                        break;
                    }
                    Integer sexo = leerEntero(entrada, salida, "Ingrese el sexo de la persona (0 masculino, 1 femenino):");
                    if (sexo == null) {
                        salida.mostrarMensaje("Operacion cancelada", "Registro");
                        break;
                    }
                    Integer registroCarro = leerEntero(entrada, salida, "Ingrese el registro del carro de la persona:");
                    if (registroCarro == null) {
                        salida.mostrarMensaje("Operacion cancelada", "Registro");
                        break;
                    }
                    Persona nuevaPersona = new Persona(nombre, cedula, fechaNac, sexo, registroCarro);
                    personas.add(nuevaPersona);
                    String descripcion = leerTextoNoVacio(entrada, salida, "Ingrese la descripcion del accidente:");
                    if (descripcion == null) {
                        salida.mostrarMensaje("Operacion cancelada", "Registro");
                        break;
                    }
                    Accidente nuevoAccidente = new Accidente(nuevaPersona, new Date(System.currentTimeMillis()),
                            descripcion);
                    accidentes.add(nuevoAccidente);
                    salida.mostrarMensaje(
                        "Accidente registrado exitosamente para " + nuevaPersona.getNombre(),
                        "Registro"
                    );
                    break;
                case 7:
                    salida.mostrarMensaje(
                        "Gracias por usar el sistema de la oficina de seguros",
                        "Salida"
                    );
                    continuar = false;
                    break;
                default:
                    break;
            }
        }
    }

    private static Integer leerEntero(Entrada entrada, Salida salida, String mensaje) {
        while (true) {
            String texto = entrada.recibirInformacion(mensaje);
            if (texto == null) {
                return null;
            }
            if (texto.trim().isEmpty()) {
                salida.mostrarMensaje("Entrada vacia", "Error");
                continue;
            }
            try {
                return Integer.parseInt(texto.trim());
            } catch (NumberFormatException e) {
                salida.mostrarMensaje("Ingrese un numero valido", "Error");
            }
        }
    }

    private static String leerTextoNoVacio(Entrada entrada, Salida salida, String mensaje) {
        while (true) {
            String texto = entrada.recibirInformacion(mensaje);
            if (texto == null) {
                return null;
            }
            if (texto.trim().isEmpty()) {
                salida.mostrarMensaje("Entrada vacia", "Error");
                continue;
            }
            return texto.trim();
        }
    }

    private static Date leerFecha(Entrada entrada, Salida salida, String mensaje) {
        while (true) {
            String texto = entrada.recibirInformacion(mensaje);
            if (texto == null) {
                return null;
            }
            if (texto.trim().isEmpty()) {
                salida.mostrarMensaje("Entrada vacia", "Error");
                continue;
            }
            try {
                return Date.valueOf(texto.trim());
            } catch (IllegalArgumentException e) {
                salida.mostrarMensaje("Formato de fecha invalido (YYYY-MM-DD)", "Error");
            }
        }
    }

    private static Persona generarPersonaAleatoria(Random random, int index) {
        String[] nombres = { "Ana", "Luis", "Maria", "Carlos", "Sofia", "Juan", "Laura", "Diego", "Camila", "Andres" };
        String[] apellidos = { "Gomez", "Perez", "Rodriguez", "Lopez", "Martinez", "Hernandez", "Diaz", "Torres" };

        String nombre = nombres[random.nextInt(nombres.length)] + " " + apellidos[random.nextInt(apellidos.length)];
        int cedula = 10000000 + random.nextInt(90000000);
        Date fechaNac = randomDate(random, dateFromYMD(1960, 1, 1), dateFromYMD(2005, 12, 31));
        int sexo = random.nextInt(2);
        int registroCarro = 1000 + random.nextInt(9000);

        return new Persona(nombre, cedula + index, fechaNac, sexo, registroCarro);
    }

    private static Accidente generarAccidenteAleatorio(Random random, Persona persona) {
        String[] descripciones = {
                "Exceso de velocidad",
                "Estacionamiento prohibido",
                "Paso en rojo",
                "Conduccion temeraria",
                "No uso del cinturon"
        };

        Date fecha = randomDate(random, dateFromYMD(2020, 1, 1), new Date(System.currentTimeMillis()));
        String descripcion = descripciones[random.nextInt(descripciones.length)];

        return new Accidente(persona, fecha, descripcion);
    }

    private static Date randomDate(Random random, Date start, Date end) {
        long startMillis = start.getTime();
        long endMillis = end.getTime();
        long randomMillis = startMillis + (long) (random.nextDouble() * (endMillis - startMillis));
        return new Date(randomMillis);
    }

    private static Date dateFromYMD(int year, int month, int day) {
        return Date.valueOf(String.format("%04d-%02d-%02d", year, month, day));
    }

    private static int getPerMenores() {
        return (int) ((personas.stream().filter(p -> p.getEdad() < 25).count() * 100.0) / personas.size());
    }

    private static int getPerFemenino() {
        return (int) ((personas.stream().filter(p -> p.getSexo() == 1).count() * 100.0) / personas.size());
    }

    private static int getPerRango() {
        return (int) ((personas.stream().filter(p -> p.getSexo() == 0 && p.getEdad() >= 12 && p.getEdad() <= 30).count()
                * 100.0) / personas.size());
    }

    private static int getPerFuera() {
        return (int) ((personas.stream().filter(p -> p.getRegistroCarro() < 5000).count() * 100.0) / personas.size());
    }

}