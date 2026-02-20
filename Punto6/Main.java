package Punto6;

import MetodosAuxiliares.Entrada;
import MetodosAuxiliares.Salida;

public class Main {

    public static void main(String[] args) {
        Entrada entrada = new Entrada();
        Salida salida = new Salida();

        try {
            Integer votosJuan = solicitarVotos(entrada, salida, "Juan");
            if (votosJuan == null) {
                return;
            }

            Integer votosPedro = solicitarVotos(entrada, salida, "Pedro");
            if (votosPedro == null) {
                return;
            }

            Integer votosMaria = solicitarVotos(entrada, salida, "Maria");
            if (votosMaria == null) {
                return;
            }

            if (votosJuan < 0 || votosPedro < 0 || votosMaria < 0) {
                salida.mostrarMensaje("Los votos no pueden ser negativos.", "Resultado de la eleccion");
                return;
            }

            Candidato juan = new Candidato("Juan", votosJuan, true);
            Candidato pedro = new Candidato("Pedro", votosPedro, true);
            Candidato maria = new Candidato("Maria", votosMaria, true);

            int totalVotos = juan.getVotos() + pedro.getVotos() + maria.getVotos();

            if (totalVotos == 0) {
                salida.mostrarMensaje("No hay votos registrados.", "Resultado de la eleccion");
                return;
            }

            if (juan.getVotos() * 2 > totalVotos) {
                salida.mostrarMensaje("Gana en primera vuelta: Juan", "Resultado de la eleccion");
            } else if (pedro.getVotos() * 2 > totalVotos) {
                salida.mostrarMensaje("Gana en primera vuelta: Pedro", "Resultado de la eleccion");
            } else if (maria.getVotos() * 2 > totalVotos) {
                salida.mostrarMensaje("Gana en primera vuelta: Maria", "Resultado de la eleccion");
            } else if (juan.getVotos() == pedro.getVotos() && pedro.getVotos() == maria.getVotos()) {
                salida.mostrarMensaje("Eleccion anulada: empate triple.", "Resultado de la eleccion");
            } else if (juan.getVotos() > pedro.getVotos() && pedro.getVotos() == maria.getVotos()) {
                salida.mostrarMensaje("Eleccion anulada: empate doble por el segundo lugar.",
                        "Resultado de la eleccion");
            } else if (pedro.getVotos() > juan.getVotos() && juan.getVotos() == maria.getVotos()) {
                salida.mostrarMensaje("Eleccion anulada: empate doble por el segundo lugar.",
                        "Resultado de la eleccion");
            } else if (maria.getVotos() > juan.getVotos() && juan.getVotos() == pedro.getVotos()) {
                salida.mostrarMensaje("Eleccion anulada: empate doble por el segundo lugar.",
                        "Resultado de la eleccion");
            } else if (juan.getVotos() >= pedro.getVotos() && juan.getVotos() >= maria.getVotos()) {
                if (pedro.getVotos() >= maria.getVotos()) {
                    salida.mostrarMensaje("No hay ganador en primera vuelta.\nSegunda vuelta: Juan y Pedro",
                            "Resultado de la eleccion");
                    simularSegundaVuelta(entrada, salida, "Juan", "Pedro");
                } else {
                    salida.mostrarMensaje("No hay ganador en primera vuelta.\nSegunda vuelta: Juan y Maria",
                            "Resultado de la eleccion");
                    simularSegundaVuelta(entrada, salida, "Juan", "Maria");
                }
            } else if (pedro.getVotos() >= juan.getVotos() && pedro.getVotos() >= maria.getVotos()) {
                if (juan.getVotos() >= maria.getVotos()) {
                    salida.mostrarMensaje("No hay ganador en primera vuelta.\nSegunda vuelta: Pedro y Juan",
                            "Resultado de la eleccion");
                    simularSegundaVuelta(entrada, salida, "Pedro", "Juan");
                } else {
                    salida.mostrarMensaje("No hay ganador en primera vuelta.\nSegunda vuelta: Pedro y Maria",
                            "Resultado de la eleccion");
                    simularSegundaVuelta(entrada, salida, "Pedro", "Maria");
                }
            } else {
                if (juan.getVotos() >= pedro.getVotos()) {
                    salida.mostrarMensaje("No hay ganador en primera vuelta.\nSegunda vuelta: Maria y Juan",
                            "Resultado de la eleccion");
                    simularSegundaVuelta(entrada, salida, "Maria", "Juan");
                } else {
                    salida.mostrarMensaje("No hay ganador en primera vuelta.\nSegunda vuelta: Maria y Pedro",
                            "Resultado de la eleccion");
                    simularSegundaVuelta(entrada, salida, "Maria", "Pedro");
                }
            }
        } catch (NumberFormatException ex) {
            salida.mostrarMensaje("Debe ingresar solo numeros enteros validos.", "Resultado de la eleccion");
        }

    }

    private static Integer solicitarVotos(Entrada entrada, Salida salida, String candidato) {
        String valor = entrada.recibirInformacion("Ingrese votos de " + candidato + ":");

        if (valor == null) {
            salida.mostrarMensaje("Ingreso cancelado por el usuario. Proceso finalizado.", "Resultado de la eleccion");
            return null;
        }

        return Integer.parseInt(valor.trim());
    }

    private static void simularSegundaVuelta(Entrada entrada, Salida salida, String candidato1, String candidato2) {
        Integer votosCandidato1 = solicitarVotos(entrada, salida, candidato1 + " (segunda vuelta)");
        if (votosCandidato1 == null) {
            return;
        }

        Integer votosCandidato2 = solicitarVotos(entrada, salida, candidato2 + " (segunda vuelta)");
        if (votosCandidato2 == null) {
            return;
        }

        if (votosCandidato1 < 0 || votosCandidato2 < 0) {
            salida.mostrarMensaje("Los votos no pueden ser negativos.", "Resultado segunda vuelta");
            return;
        }

        if (votosCandidato1 == 0 && votosCandidato2 == 0) {
            salida.mostrarMensaje("No hay votos registrados en segunda vuelta.", "Resultado segunda vuelta");
            return;
        }

        if (votosCandidato1 > votosCandidato2) {
            salida.mostrarMensaje("Gana en segunda vuelta: " + candidato1, "Resultado segunda vuelta");
        } else if (votosCandidato2 > votosCandidato1) {
            salida.mostrarMensaje("Gana en segunda vuelta: " + candidato2, "Resultado segunda vuelta");
        } else {
            salida.mostrarMensaje("Segunda vuelta empatada. La eleccion debe repetirse.", "Resultado segunda vuelta");
        }
    }
}
