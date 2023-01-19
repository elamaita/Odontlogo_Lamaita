package models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AgendaDAO {

    public String getjson() {
        String json = "";
        try {
            //Parseo el JSON para obtener los datos a un STRING
            json = (String) new JSONParser().parse(new FileReader("Clientes.json")).toString();

        } catch (IOException | ParseException ex) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return json;
    }

    public boolean verificarCliente(Agenda agenda) {
        ObjectMapper objectMapper = new ObjectMapper();
        File clienteJSON = new File("Clientes.json");
        boolean flag = false;
        try {
            List<Clientes> listaClientes = objectMapper.readValue(clienteJSON, new TypeReference<List<Clientes>>() {
            });

            for (int i = 0; i < listaClientes.size(); i++) { //Recorro todos los clientes
                if (listaClientes.get(i).getId() == agenda.getId()) { // verifico que la cedula de la posicion del array en la que va sea igual a la que se declara
                    flag = true;
                }
            }
            return flag;
        } catch (IOException e) {
            return flag;
        }
    }

    public boolean registrarConsulta(Agenda agenda) {
        ObjectMapper objectMapper = new ObjectMapper();
        File agendaJSON = new File("Agendas.json");
        try {
            List<Agenda> listaAgendas = objectMapper.readValue(agendaJSON, new TypeReference<List<Agenda>>() {
            });
            listaAgendas.add(agenda);
            objectMapper.writeValue(agendaJSON, listaAgendas);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean eliminarConsulta(Agenda agenda) {
        ObjectMapper objectMapper = new ObjectMapper();
        File agendaJSON = new File("Agendas.json");

        try {
            String fecha = "";
            String hora = "";
            int posicion_consulta = 0;
            List<Agenda> listaAgendas = objectMapper.readValue(agendaJSON, new TypeReference<List<Agenda>>() {
            });
            for (int i = 0; i < listaAgendas.size(); i++) {
                if (listaAgendas.get(i).getId() == agenda.getId()
                        && listaAgendas.get(i).getFecha_consulta().equals(agenda.getFecha_consulta())
                        && listaAgendas.get(i).getHora_consulta().equals(agenda.getHora_consulta())) {

                    posicion_consulta = i; // Para saber en que posicion del array se encuentra el cliente.
                    fecha = listaAgendas.get(i).getFecha_consulta();
                    hora = listaAgendas.get(i).getHora_consulta();

                }
            }

            if (JOptionPane.showConfirmDialog(null, "Está seguro que desea eliminar la consulta para el " + fecha + " a las " + hora, " ATENCIÓN ",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                listaAgendas.remove(posicion_consulta);
                objectMapper.writeValue(agendaJSON, listaAgendas);

                return true;

            } else {
                return false;
            }

        } catch (IOException e) {
            return false;
        }

    }

    public List listarConsultas() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File agendaJSON = new File("Agendas.json");
            List<Agenda> listarAgenda = objectMapper.readValue(agendaJSON, new TypeReference<List<Agenda>>() {
            });
            return listarAgenda;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public boolean verificarConsulta(Agenda agenda) {
        ObjectMapper objectMapper = new ObjectMapper();
        File agendaJSON = new File("Agendas.json");
        boolean flag = false;
        try {
            List<Agenda> listaAgendas = objectMapper.readValue(agendaJSON, new TypeReference<List<Agenda>>() {
            });

            for (int i = 0; i < listaAgendas.size(); i++) { //Recorro todos los clientes
                if (listaAgendas.get(i).getFecha_consulta().equals(agenda.getFecha_consulta())
                        && listaAgendas.get(i).getHora_consulta().equals(agenda.getHora_consulta())) {
                    flag = true;
                }
            }
            return flag;
        } catch (IOException e) {
            return flag;
        }

    }

}
