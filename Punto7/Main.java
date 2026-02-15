import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static Random random = new Random();
    static List<Persona> personas = new ArrayList<>();
    static List<Accidente> accidentes = new ArrayList<>();

    public static void main(String[] args) {
        for (int i = 0; i < 25; i++) {
            Persona persona = generarPersonaAleatoria(random, i);
            personas.add(persona);

            int cantidadAccidentes = 1 + random.nextInt(2);
            for (int j = 0; j < cantidadAccidentes; j++) {
                accidentes.add(generarAccidenteAleatorio(random, persona));
            }
        }

        System.out.println("Bienvenido al sistema de la oficina de seguros, a continuacion seleccione una opcion: ");
        System.out.print(
            "1) Ver registros de accidentes\n2) Ver el porcentaje de conductores menores de 25 años\n3) Ver el porcentaje de conductores del sexo femenino\n4) Ver el porcentaje de conductores masculinos con edades entre 12 y 30 años\n5) Ver el porcentaje de conductores cuyos carros estan registrados fuera de Medellin\n6) Registrar nuevo accidente\n7) Salir\nOpcion: ");
        Scanner sc = new Scanner(System.in);
        Boolean condition = true;
        int opcion = 0;
        while (condition) {
            try {
                opcion = sc.nextInt();
                condition = false;
            } catch (Exception e) {
                System.out.println("Ingrese una opcion valida");
            }
        }
        condition = true;
        while (condition) {
            switch (opcion) {
                case 1:
                    for (Persona persona : personas) {
                        System.out.println("Persona: " + persona.getNombre() + " (" + persona.getCedula() + ")");
                        for (Accidente accidente : accidentes) {
                            if (accidente.getPersona() == persona) {
                                System.out.println("  Accidente: " + accidente.getDescripcion() + ", fecha="
                                        + accidente.getFechaAccidente());
                            }
                        }
                    }
                    break;
                case 2:
                    System.out.println("Porcentaje de conductores menores de 25 años: " + (getPerMenores()) + "%");
                    break;
                case 3:
                    System.out.println("Porcentaje de conductores del sexo femenino: " + (getPerFemenino()) + "%");
                    break;
                case 4:
                    System.out.println("Porcentaje de conductores masculinos con edades entre 12 y 30 años: "
                        + (getPerRango()) + "%");
                    break;
                case 5:
                    System.out.println("Porcentaje de conductores cuyos carros estan registrados fuera de Medellin: "
                        + (getPerFuera()) + "%");
                    break;
                case 6:
                    System.out.println("Ingrese el nombre de la persona: ");
                    String nombre = sc.next();
                    System.out.println("Ingrese la cedula de la persona: ");
                    int cedula = sc.nextInt();
                    System.out.println("Ingrese la fecha de nacimiento de la persona (YYYY-MM-DD): ");
                    Date fechaNac = Date.valueOf(sc.next());
                    System.out.println("Ingrese el sexo de la persona (0 para masculino, 1 para femenino): ");
                    int sexo = sc.nextInt();
                    System.out.println("Ingrese el registro del carro de la persona: ");
                    int registroCarro = sc.nextInt();
                    Persona nuevaPersona = new Persona(nombre, cedula, fechaNac, sexo, registroCarro);
                    personas.add(nuevaPersona);
                    System.out.println("Ingrese la descripcion del accidente: ");
                    String descripcion = sc.next();
                    Accidente nuevoAccidente = new Accidente(nuevaPersona, new Date(System.currentTimeMillis()),
                            descripcion);
                    accidentes.add(nuevoAccidente);
                    System.out.println("Accidente registrado exitosamente para " + nuevaPersona.getNombre());
                    break;
                case 7:
                    System.out.println("Gracias por usar el sistema de la oficina de seguros");
                    condition = false;
                    break;
                default:
                    break;
            }
            if (opcion == 7) {
                condition = false;
            } else {
                System.out.print(
                        "1) Ver registros de accidentes\n2) Ver el porcentaje de conductores menores de 25 años\n3) Ver el porcentaje de conductores del sexo femenino\n4) Ver el porcentaje de conductores masculinos con edades entre 12 y 30 años\n5) Ver el porcentaje de conductores cuyos carros estan registrados fuera de Medellin\n6) Registrar nuevo accidente\n7) Salir\nOpcion: ");
                condition = true;
                while (condition) {
                    try {
                        opcion = sc.nextInt();
                        condition = false;
                    } catch (Exception e) {
                        System.out.println("Ingrese una opcion valida");
                    }
                }
                condition = true;
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