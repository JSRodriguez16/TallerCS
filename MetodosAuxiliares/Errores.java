package MetodosAuxiliares;

public class Errores {
    public static void manejarErrores(Runnable operacion, Salida salida){
        try {
            operacion.run();
        } catch (Exception e) {
            salida.mostrarMensaje("Hubo un error al intentar ejecutar la operacion, intentelo de nuevo", "Error");
            manejarErrores(operacion, salida);
        }

    }
}
