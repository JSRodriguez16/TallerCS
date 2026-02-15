package MetodosAuxiliares;

import javax.swing.JOptionPane;

public class Salida {
    public void mostrarMensaje(String mensaje,String titulo){
        JOptionPane.showMessageDialog(null, mensaje,titulo,JOptionPane.PLAIN_MESSAGE);
    }
}
