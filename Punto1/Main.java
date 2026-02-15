package Punto1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import MetodosAuxiliares.Entrada;
import MetodosAuxiliares.Errores;
import MetodosAuxiliares.Salida;

public class Main {

    public static void main(String[] args) {
        Entrada entrada = new Entrada();
        Salida salida = new Salida();
        Random random = new Random();
        List<Estudiante> estudiantes = null;

        try {
            estudiantes = generarEstudiantesAleatorios(25, random);
        } catch (Exception ex) {
            Errores.manejarErrores(() -> {
                throw ex;
            }, salida);
            return;
        }

        String[] opciones = {
                "Promedio de edad de los estudiantes de Ingenieria",
                "Porcentaje de hombres que estudian Ingenieria",
                "Porcentaje de mujeres menores a 20 años de la Universidad",
                "Promedio de edad de las mujeres de Ingenieria",
                "Porcentaje de hombres mayores a 22 años que estudian Derecho en la noche",
                "Agregar estudiante",
                "Eliminar estudiante por codigo",
        };

        boolean continuar = true;
        while (continuar) {
            int opcion = entrada.mostrarMenu(opciones, "Seleccione una opcion", "Menu");
            if (opcion == -1) {
                continuar = false;
                break;
            }
            switch (opcion) {
                case 1:
                    try {
                        salida.mostrarMensaje(ejecutarConsulta(estudiantes, 1), "Resultado");
                    } catch (Exception ex) {
                        Errores.manejarErrores(() -> {
                            throw ex;
                        }, salida);
                    }
                    break;
                case 2:
                    try {
                        salida.mostrarMensaje(ejecutarConsulta(estudiantes, 2), "Resultado");
                    } catch (Exception ex) {
                        Errores.manejarErrores(() -> {
                            throw ex;
                        }, salida);
                    }
                    break;
                case 3:
                    try {
                        salida.mostrarMensaje(ejecutarConsulta(estudiantes, 3), "Resultado");
                    } catch (Exception ex) {
                        Errores.manejarErrores(() -> {
                            throw ex;
                        }, salida);
                    }
                    break;
                case 4:
                    try {
                        salida.mostrarMensaje(ejecutarConsulta(estudiantes, 4), "Resultado");
                    } catch (Exception ex) {
                        Errores.manejarErrores(() -> {
                            throw ex;
                        }, salida);
                    }
                    break;
                case 5:
                    try {
                        salida.mostrarMensaje(ejecutarConsulta(estudiantes, 5), "Resultado");
                    } catch (Exception ex) {
                        Errores.manejarErrores(() -> {
                            throw ex;
                        }, salida);
                    }
                    break;
                case 6:
                    try {
                        agregarEstudiante(estudiantes, entrada, salida);
                    } catch (Exception ex) {
                        Errores.manejarErrores(() -> {
                            throw ex;
                        }, salida);
                    }
                    break;
                case 7:
                    try {
                        eliminarEstudiante(estudiantes, entrada, salida);
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

    private static List<Estudiante> generarEstudiantesAleatorios(int cantidad, Random random) {
        List<Estudiante> estudiantes = new ArrayList<>();
        String[] nombres = {
                "Ana", "Luis", "Maria", "Juan", "Camila", "Andres", "Lucia", "Felipe",
                "Valentina", "Sergio", "Daniela", "Nicolas", "Paula", "Jorge", "Sara"
        };

        for (int i = 0; i < cantidad; i++) {
            String nombre = nombres[random.nextInt(nombres.length)] + " " + (i + 1);
            int codigo = 1000 + i;
            int edad = 16 + random.nextInt(15);
            int sexo = 1 + random.nextInt(2);
            int carrera = 1 + random.nextInt(3);
            int jornada = 1 + random.nextInt(2);

            estudiantes.add(new Estudiante(nombre, codigo, edad, sexo, carrera, jornada));
        }
        return estudiantes;
    }

    private static String ejecutarConsulta(List<Estudiante> estudiantes, int tipo) {
        switch (tipo) {
            case 1:
                return "Promedio de edad de los estudiantes de Ingenieria: " +
                        formatearNumero(promedioEdad(estudiantes, 1, null, null));
            case 2:
                return "Porcentaje de hombres que estudian Ingenieria: " +
                        formatearNumero(porcentaje(estudiantes, est -> est.getSexo() == 1 && est.getCarrera() == 1))
                        + "%";
            case 3:
                return "Porcentaje de mujeres menores a 20 años de la Universidad: " +
                        formatearNumero(porcentaje(estudiantes, est -> est.getSexo() == 2 && est.getEdad() < 20)) + "%";
            case 4:
                return "Promedio de edad de las mujeres de Ingenieria: " +
                        formatearNumero(promedioEdad(estudiantes, 1, 2, null));
            case 5:
                return "Porcentaje de hombres mayores a 22 años que estudian Derecho en la noche: " +
                        formatearNumero(porcentaje(estudiantes, est -> est.getSexo() == 1 && est.getEdad() > 22
                                && est.getCarrera() == 3 && est.getJornada() == 2))
                        + "%";
            default:
                return "Consulta no valida";
        }
    }

    private static double promedioEdad(
            List<Estudiante> estudiantes,
            Integer carrera,
            Integer sexo,
            Integer jornada) {
        int suma = 0;
        int contador = 0;
        for (Estudiante estudiante : estudiantes) {
            if (carrera != null && estudiante.getCarrera() != carrera) {
                continue;
            }
            if (sexo != null && estudiante.getSexo() != sexo) {
                continue;
            }
            if (jornada != null && estudiante.getJornada() != jornada) {
                continue;
            }
            suma += estudiante.getEdad();
            contador++;
        }
        return contador == 0 ? 0.0 : (double) suma / contador;
    }

    private interface EstudianteFiltro {
        boolean acepta(Estudiante estudiante);
    }

    private static double porcentaje(List<Estudiante> estudiantes, EstudianteFiltro filtro) {
        if (estudiantes.isEmpty()) {
            return 0.0;
        }
        int contador = 0;
        for (Estudiante estudiante : estudiantes) {
            if (filtro.acepta(estudiante)) {
                contador++;
            }
        }
        return (contador * 100.0) / estudiantes.size();
    }

    private static String formatearNumero(double valor) {
        return String.format("%.2f", valor);
    }

    private static void agregarEstudiante(List<Estudiante> estudiantes, Entrada entrada, Salida salida) {
        String nombre = entrada.recibirInformacion("Ingrese el nombre del estudiante:");
        if (nombre == null || nombre.trim().isEmpty()) {
            salida.mostrarMensaje("Operacion cancelada", "Registro");
            return;
        }

        Integer codigo = leerEntero(entrada, salida, "Ingrese el codigo del estudiante:");
        if (codigo == null) {
            salida.mostrarMensaje("Operacion cancelada", "Registro");
            return;
        }

        for (Estudiante est : estudiantes) {
            if (est.getCodigo() == codigo) {
                salida.mostrarMensaje("Ya existe un estudiante con ese codigo", "Registro");
                return;
            }
        }

        Integer edad = leerEntero(entrada, salida, "Ingrese la edad del estudiante:");
        if (edad == null) {
            salida.mostrarMensaje("Operacion cancelada", "Registro");
            return;
        }

        Integer sexo = leerEntero(entrada, salida, "Ingrese el sexo (1 Masculino, 2 Femenino):");
        if (sexo == null || sexo < 1 || sexo > 2) {
            salida.mostrarMensaje("Operacion cancelada", "Registro");
            return;
        }

        Integer carrera = leerEntero(entrada, salida, "Ingrese la carrera (1 Ingenieria, 2 Contaduria, 3 Derecho):");
        if (carrera == null || carrera < 1 || carrera > 3) {
            salida.mostrarMensaje("Operacion cancelada", "Registro");
            return;
        }

        Integer jornada = leerEntero(entrada, salida, "Ingrese la jornada (1 Diurna, 2 Nocturna):");
        if (jornada == null || jornada < 1 || jornada > 2) {
            salida.mostrarMensaje("Operacion cancelada", "Registro");
            return;
        }

        estudiantes.add(new Estudiante(nombre.trim(), codigo, edad, sexo, carrera, jornada));
        salida.mostrarMensaje("Estudiante agregado correctamente", "Registro");
    }

    private static void eliminarEstudiante(List<Estudiante> estudiantes, Entrada entrada, Salida salida) {
        Integer codigo = leerEntero(entrada, salida, "Ingrese el codigo del estudiante a eliminar:");
        if (codigo == null) {
            salida.mostrarMensaje("Operacion cancelada", "Registro");
            return;
        }

        boolean eliminado = false;
        for (Iterator<Estudiante> iterator = estudiantes.iterator(); iterator.hasNext();) {
            Estudiante estudiante = iterator.next();
            if (estudiante.getCodigo() == codigo) {
                iterator.remove();
                eliminado = true;
                break;
            }
        }

        if (eliminado) {
            salida.mostrarMensaje("Estudiante eliminado correctamente", "Registro");
        } else {
            salida.mostrarMensaje("No se encontro un estudiante con ese codigo", "Registro");
        }
    }

    private static Integer leerEntero(Entrada entrada, Salida salida, String mensaje) {
        while (true) {
            String texto = entrada.recibirInformacion(mensaje);
            if (texto == null) {
                return null;
            }
            texto = texto.trim();
            if (texto.isEmpty()) {
                salida.mostrarMensaje("Entrada vacia", "Error");
                continue;
            }
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException ex) {
                salida.mostrarMensaje("Ingrese un numero valido", "Error");
            }
        }
    }
}
