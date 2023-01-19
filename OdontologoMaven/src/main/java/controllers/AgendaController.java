package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Agenda;
import models.AgendaDAO;
import views.panelAdministracion;

public class AgendaController implements ActionListener, MouseListener {
    
    private Agenda agenda;
    private AgendaDAO agenda_dao;
    private panelAdministracion view;
    
    DefaultTableModel model = new DefaultTableModel();
    
    public AgendaController(Agenda agenda, AgendaDAO agenda_dao, panelAdministracion view) {
        this.agenda = agenda;
        this.agenda_dao = agenda_dao;
        this.view = view;

        //Boton de añadir
        view.btn_añadir_paciente.addActionListener(this);
        view.btn_cancelar_agenda.addActionListener(this);
        view.btn_eliminar_consulta.addActionListener(this);
        view.btn_cancelar_consulta.addActionListener(this);
        
        view.jTabbedPane1.addMouseListener(this);
        view.consultas_table.addMouseListener(this);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.btn_añadir_paciente) {
            if (view.txt_apellido_paciente.getText().equals("")
                    || view.txt_nombre_paciente.getText().equals("")
                    || view.txt_documento_paciente.getText().equals("")
                    || view.txtMotivo.getText().equals("")
                    || view.dateConsulta.getCalendar() == null) {
                JOptionPane.showMessageDialog(null, "Todos los datos deben de estar completos.");
            } else {
                SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");
                agenda.setId(Integer.parseInt(view.txt_documento_paciente.getText()));
                agenda.setName(view.txt_nombre_paciente.getText());
                agenda.setLast_name(view.txt_apellido_paciente.getText());
                agenda.setMotivo(view.txtMotivo.getText());
                agenda.setFecha_consulta(ff.format(view.dateConsulta.getCalendar().getTime()));
                agenda.setHora_consulta(view.cmb_hora_consulta.getSelectedItem().toString());
                
                if (agenda_dao.verificarCliente(agenda)) {
                    
                    if (agenda_dao.verificarConsulta(agenda) != true) {
                        if (agenda_dao.registrarConsulta(agenda)) {
                            cleanFields();
                            JOptionPane.showMessageDialog(null, "Consulta agendada con éxito.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al reservar la consulta con el cliente.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Ya se encuentra reservada esa consulta.");
                        
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Este cliente no existe.");
                }
            }
            
        } else if (e.getSource() == view.btn_cancelar_agenda) {
            cleanFields();
        } else if (e.getSource() == view.btn_eliminar_consulta) {
            if (view.txt_consulta_cancelar_documento.getText().equals("")
                    || view.txt_consulta_cancelar_fecha.getText().equals("")
                    || view.txt_consulta_cancelar_hora.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Debes de seleccionar una consulta primero.");
                
            } else {
                agenda.setId(Integer.parseInt(view.txt_consulta_cancelar_documento.getText()));
                agenda.setFecha_consulta(view.txt_consulta_cancelar_fecha.getText());
                agenda.setHora_consulta(view.txt_consulta_cancelar_hora.getText());
                if (agenda_dao.eliminarConsulta(agenda)) {
                    cleanTable();
                    listarConsultas();
                    cleanFields();
                    JOptionPane.showMessageDialog(null, "Consulta cancelada con exito.");
                } else {
                    JOptionPane.showMessageDialog(null, "La consulta no ha sido cancelada.");
                    
                }
            }
            
        } else if (e.getSource() == view.btn_cancelar_consulta) {
            cleanFields();
        }
        
    }
    
    public void listarConsultas() {
        List<Agenda> list = agenda_dao.listarConsultas();
        model = (DefaultTableModel) view.consultas_table.getModel();
        Object[] row = new Object[6]; //aqui definimos la cantidad de columnas, en este caso hay 6
        for (int i = 0; i < list.size(); i++) {   //se usa el list.size para saber cuantas filas tiene la tabla
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getName();
            row[2] = list.get(i).getLast_name();
            row[3] = list.get(i).getFecha_consulta();
            row[4] = list.get(i).getHora_consulta();
            row[5] = list.get(i).getMotivo();
            model.addRow(row);
            
        }
        
    }
    
    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }
    
    public void cleanFields() {
        view.txtMotivo.setText("");
        view.txt_apellido_paciente.setText("");
        view.txt_nombre_paciente.setText("");
        view.txt_documento_paciente.setText("");
        view.cmb_hora_consulta.setSelectedIndex(0);
        view.dateConsulta.setCalendar(null);
        view.txt_consulta_cancelar_documento.setText("");
        view.txt_consulta_cancelar_nombre.setText("");
        view.txt_consulta_cancelar_apellido.setText("");
        view.txt_consulta_cancelar_fecha.setText("");
        view.txt_consulta_cancelar_hora.setText("");
        
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == view.jTabbedPane1) {
            cleanTable();
            listarConsultas();
        } else if (e.getSource() == view.consultas_table) {
            int row = view.consultas_table.rowAtPoint(e.getPoint());    //saber en que fila se hizo click con el mouse
            view.txt_consulta_cancelar_documento.setText(view.consultas_table.getValueAt(row, 0).toString());
            view.txt_consulta_cancelar_nombre.setText(view.consultas_table.getValueAt(row, 1).toString());
            view.txt_consulta_cancelar_apellido.setText(view.consultas_table.getValueAt(row, 2).toString());
            view.txt_consulta_cancelar_fecha.setText(view.consultas_table.getValueAt(row, 3).toString());
            view.txt_consulta_cancelar_hora.setText(view.consultas_table.getValueAt(row, 4).toString());
            
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
