package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Clientes;
import models.ClientesDAO;
import views.panelAdministracion;

public class ClientesController implements ActionListener, MouseListener {

    private Clientes cliente;
    private ClientesDAO cliente_dao;
    private panelAdministracion views;
    DefaultTableModel model = new DefaultTableModel();

    public ClientesController(Clientes paciente, ClientesDAO paciente_dao, panelAdministracion views) {
        this.cliente = paciente;
        this.cliente_dao = paciente_dao;
        this.views = views;

        //Agregar ActionListener a los botones
        this.views.btn_añadir_cliente.addActionListener(this);
        this.views.btn_eliminar_cliente.addActionListener(this);
        this.views.btn_cancelar_cliente.addActionListener(this);

        //Agregar acciones del mouse listener
        this.views.jTabbedPane1.addMouseListener(this);
        this.views.table_clientes.addMouseListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_añadir_cliente) {
            if (views.txt_apellido_cliente.getText().equals("")
                    || views.txt_nombre_cliente.getText().equals("")
                    || views.txt_documento_cliente.getText().equals("")
                    || views.txt_telefono_cliente.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");
                cliente.setId(Integer.parseInt(views.txt_documento_cliente.getText()));
                cliente.setName(views.txt_nombre_cliente.getText());
                cliente.setLast_name(views.txt_apellido_cliente.getText());
                cliente.setAddress(views.txt_direccion_cliente.getText());
                cliente.setTelefono(views.txt_telefono_cliente.getText());
                cliente.setEmail(views.txt_email_cliente.getText());
                cliente.setDate(ff.format(views.date_cliente.getCalendar().getTime()));

                if (cliente_dao.verificarCliente(cliente)) {
                    JOptionPane.showMessageDialog(null, "¡Ese cliente ya existe!.");

                } else {
                    if (cliente_dao.registrarCliente(cliente)) {
                        clearFields();
                        JOptionPane.showMessageDialog(null, "Cliente añadido exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al añadir el cliente.");

                    }

                }
            }

        } else if (e.getSource() == views.btn_eliminar_cliente) {
            if (views.txt_eliminar_cliente_documento.getText().equals("")
                    || views.txt_eliminar_cliente_apellido.getText().equals("")
                    || views.txt_eliminar_cliente_nombre.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Debes de seleccionar un cliente primero.");
            } else {
                cliente.setId(Integer.parseInt(views.txt_eliminar_cliente_documento.getText()));
                if (cliente_dao.eliminarCliente(cliente)) {
                    cleanTable();
                    listarClientes();
                    clearFields();
                    JOptionPane.showMessageDialog(null, "Cliente elminado con exito.");
                } else {
                    JOptionPane.showMessageDialog(null, "El cliente no ha sido borrado.");

                }
            }

        } else if (e.getSource() == views.btn_cancelar_cliente) {
            clearFields();

        }

    }

    public void listarClientes() {
        List<Clientes> list = cliente_dao.listarClientes();
        model = (DefaultTableModel) views.table_clientes.getModel();
        Object[] row = new Object[7]; //aqui definimos la cantidad de columnas, en este caso hay 6
        for (int i = 0; i < list.size(); i++) {   //se usa el list.size para saber cuantas filas tiene la tabla
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getName();
            row[2] = list.get(i).getLast_name();
            row[3] = list.get(i).getTelefono();
            row[4] = list.get(i).getAddress();
            row[5] = list.get(i).getEmail();
            row[6] = list.get(i).getDate();
            model.addRow(row);

        }
    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

    public void clearFields() {
        views.txt_apellido_cliente.setText("");
        views.txt_nombre_cliente.setText("");
        views.txt_direccion_cliente.setText("");
        views.txt_documento_cliente.setText("");
        views.txt_telefono_cliente.setText("");
        views.txt_email_cliente.setText("");
        views.date_cliente.setDate(null);
        views.txt_eliminar_cliente_documento.setText("");
        views.txt_eliminar_cliente_nombre.setText("");
        views.txt_eliminar_cliente_apellido.setText("");

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.jTabbedPane1) {
            cleanTable();
            listarClientes();
        } else if (e.getSource() == views.table_clientes) {
            int row = views.table_clientes.rowAtPoint(e.getPoint());    //saber en que fila se hizo click con el mouse
            views.txt_eliminar_cliente_documento.setText(views.table_clientes.getValueAt(row, 0).toString());
            views.txt_eliminar_cliente_nombre.setText(views.table_clientes.getValueAt(row, 1).toString());
            views.txt_eliminar_cliente_apellido.setText(views.table_clientes.getValueAt(row, 2).toString());

            //deshabilitar cajas de texto
            views.txt_eliminar_cliente_documento.setEditable(false);
            views.txt_eliminar_cliente_nombre.setEditable(false);
            views.txt_eliminar_cliente_apellido.setEditable(false);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
