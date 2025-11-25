import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ventana {
    private JPanel principal;
    private JTabbedPane tabbedPane1;
    private JList lstMostrar;
    private JSpinner spnID;
    private JTextField txtNombre;
    private JTextField txtCategoria;
    private JTextField txtCantidad;
    private JButton EDITARPRODUCTOButton;
    private JList lstDatosAlmacenados;
    private JButton btnOrdenarProducto;
    private JButton btnOrdenarID;
    private JButton BUSCARPRODUCTOCONMAYORSTOCKButton;
    private JButton btnDatosAlmacenados;
    private JComboBox CboProducto;

    InventarioProductos inventario = new InventarioProductos();

    public void llenarListaMostrar() {
        DefaultListModel dlm = new DefaultListModel();
        for (Producto p : inventario.todos()) {
            dlm.addElement(p.toString());
        }
        lstMostrar.setModel(dlm);
    }

    public Ventana() {
        llenarListaMostrar();

        EDITARPRODUCTOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(spnID.getValue().toString());
                Producto encontrado = inventario.buscarLinealPorId(id);
                if (encontrado != null) {
                    String nombre = txtNombre.getText();
                    String categoria = txtCategoria.getText();
                    int cantidad = Integer.parseInt(txtCantidad.getText());
                    encontrado.setNombre(nombre);
                    encontrado.setCategoria(categoria);
                    encontrado.setCantidad(cantidad);
                    JOptionPane.showMessageDialog(null, "Producto actualizado");
                    llenarListaMostrar();
                } else {
                    JOptionPane.showMessageDialog(null, "No existe un producto con ese ID");
                }
            }
        });

        btnDatosAlmacenados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultListModel dlm = new DefaultListModel();
                for (Producto p : inventario.todos()) {
                    dlm.addElement(p.toString());
                }
                lstDatosAlmacenados.setModel(dlm);
            }
        });

        btnOrdenarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String categoria = CboProducto.getSelectedItem().toString();
                DefaultListModel dlm = new DefaultListModel();
                for (Producto p : inventario.ordenarPorCantidadCategoria(categoria)) {
                    dlm.addElement(p.toString());
                }
                lstDatosAlmacenados.setModel(dlm);
            }
        });

        btnOrdenarID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventario.ordenarPorId();
                DefaultListModel dlm = new DefaultListModel();
                for (Producto p : inventario.todos()) {
                    dlm.addElement(p.toString());
                }
                lstDatosAlmacenados.setModel(dlm);
            }
        });

        BUSCARPRODUCTOCONMAYORSTOCKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Producto mayor = inventario.buscarMayorCantidad();
                if (mayor != null) {
                    DefaultListModel dlm = new DefaultListModel();
                    dlm.addElement(mayor.toString());
                    lstDatosAlmacenados.setModel(dlm);
                    JOptionPane.showMessageDialog(null, "Producto con mayor stock encontrado");
                } else {
                    JOptionPane.showMessageDialog(null, "No hay productos en el inventario");
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setContentPane(new Ventana().principal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}