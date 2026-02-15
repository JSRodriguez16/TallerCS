package MetodosAuxiliares;

import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Entrada {
    public String recibirInformacion(String mensaje){
        return JOptionPane.showInputDialog(null,mensaje);
    }
    
    public int mostrarMenu (String[] opciones, String mensaje,String titulo){
        JComboBox optionList = new JComboBox(opciones);
        optionList.setSelectedIndex(0);

        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.add(new JLabel(mensaje), BorderLayout.NORTH);
        panel.add(optionList, BorderLayout.CENTER);

        int seleccion = JOptionPane.showOptionDialog(
            null,
            panel,
            titulo,
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            new Object[] { "Aceptar", "Cancelar" },
            "Aceptar"
        );

        if (seleccion == JOptionPane.CLOSED_OPTION) {
            return -1;
        }
        if (seleccion != 0) {
            return 0;
        }
        return optionList.getSelectedIndex() + 1;
    }
}
