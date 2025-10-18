import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class Ventana {
    private JPanel principal;
    private JTabbedPane tabbedPane1;
    private JPanel COMPRA;
    private JPanel DETALLE;
    private JPanel HISTORIAL;
    private JComboBox<String> cboPeliculas;
    private JTextField txtCedula;
    private JSpinner spiEntradas;
    private JButton btnComprar;
    private JTextArea txtDetalle;
    private JLabel lblNaruto;
    private JLabel lblAntman;
    private JLabel lblPiratas;
    private JButton btnConsultar;
    private GestorCine gestor = new GestorCine();

    public Ventana() {
        if(cboPeliculas.getItemCount()== 0){
            cboPeliculas.setModel(new DefaultComboBoxModel<>(
                    new String[]{GestorCine.PIRATAS, GestorCine.NARUTO,  GestorCine.ANTMAN}
            ));
        }

        spiEntradas.setModel(new SpinnerNumberModel(1,1,5,1));
        txtDetalle.setEditable(false);
        lblPiratas.setText("Piratas: $0");
        lblNaruto.setText("Naruto: $0");
        lblAntman.setText("Antman: $0");

        btnComprar.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { botonComprar(); }
        });
        btnConsultar.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { botonConsultar(); }
        });
    }

    private void botonComprar() {
        try {
            String pelicula = cboPeliculas.getSelectedItem().toString();
            String cedula   = txtCedula.getText();

            try { spiEntradas.commitEdit(); } catch (java.text.ParseException ignored) {}
            int entradas = ((Number) spiEntradas.getValue()).intValue();

            if (entradas > 5) { JOptionPane.showMessageDialog(null,"No se pueden comprar más de 5 entradas por persona."); return; }
            if (entradas < 1) { JOptionPane.showMessageDialog(null,"Debe comprar al menos 1 entrada."); return; }

            Compra c = gestor.registrarCompra(pelicula, cedula, entradas);


            txtDetalle.append(c.toString() + System.lineSeparator());


            txtCedula.setText("");
            spiEntradas.setValue(1);
            txtCedula.requestFocus();

            JOptionPane.showMessageDialog(null, "Compra registrada.");
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void botonConsultar() {
    lblPiratas.setText("Piratas: $" + gestor.totalPorPelicula(GestorCine.PIRATAS));
    lblNaruto.setText("Naruto: $" + gestor.totalPorPelicula(GestorCine.NARUTO));
    lblAntman.setText("Antman: $" + gestor.totalPorPelicula(GestorCine.ANTMAN));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ventana");
        frame.setContentPane(new Ventana().principal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}




