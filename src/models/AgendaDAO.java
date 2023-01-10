package models;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;
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
        JSONObject object = new JSONObject(getjson());
        JSONArray listArray = object.getJSONArray("Clientes");
        JSONObject datoSONObject;
        boolean flag = false;

        for (int i = 0; i < listArray.length(); i++) {
            datoSONObject = listArray.getJSONObject(i);

            if (datoSONObject.getInt("id") == agenda.getDocumento()) {
                flag = true;
            }

        }

        return flag;
    }

    public boolean registrarConsulta(Agenda agenda) {
        JSONObject object = new JSONObject(getjson());
        JSONArray listArray = object.getJSONArray("Agendas");
        JSONObject datoSONObject = new JSONObject();

        datoSONObject.put("id", agenda.getDocumento());
        datoSONObject.put("name", agenda.getNombre());
        datoSONObject.put("last_name", agenda.getApellido());
        datoSONObject.put("motivo", agenda.getMotivo());
        datoSONObject.put("fecha_de_consulta", agenda.getFecha_consulta());
        datoSONObject.put("hora_consulta", agenda.getHora_consulta());

        listArray.put(datoSONObject);//se agrega al array del objeto el dato

        try ( FileWriter file = new FileWriter("Clientes.json")) {

            file.write(object.toString());
            file.flush();
            return true;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "Error");
            return false;
        }

    }

    public boolean eliminarConsulta(Agenda agenda) {
        JSONObject object = new JSONObject(getjson());
        String fecha = "";
        String hora = "";
        JSONArray listArray = object.getJSONArray("Agendas");
        JSONObject datoSONObject = new JSONObject();
        int posicion_consulta = 0;

        for (int i = 0; i < listArray.length(); i++) {
            datoSONObject = listArray.getJSONObject(i);
            if (datoSONObject.getInt("id") == agenda.getDocumento()
                    && datoSONObject.getString("fecha_de_consulta").equals(agenda.getFecha_consulta())
                    && datoSONObject.getString("hora_consulta").equals(agenda.getHora_consulta())) {
                
                posicion_consulta = i; // Para saber en que posicion del array se encuentra el cliente.
                fecha = datoSONObject.getString("fecha_de_consulta");
                hora = datoSONObject.getString("hora_consulta");

            }
        }

        if (JOptionPane.showConfirmDialog(null, "Está seguro que desea eliminar la consulta para el " + fecha + " a las " + hora, " ATENCIÓN ",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            listArray.remove(posicion_consulta);

            try ( FileWriter file = new FileWriter("Clientes.json")) {

                file.write(object.toString());
                file.flush();

            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, "Error");

            }
            return true;

        } else {
            return false;
        }

    }

    public List listarConsultas() {
        List<Agenda> listaAgendas = new ArrayList();

        JSONObject object = new JSONObject(getjson());
        JSONArray listArray = object.getJSONArray("Agendas");
        JSONObject datoSONObject;

        for (int i = 0; i < listArray.length(); i++) {
            datoSONObject = listArray.getJSONObject(i);
            Agenda agenda = new Agenda();
            agenda.setNombre(datoSONObject.getString("name"));
            agenda.setApellido(datoSONObject.getString("last_name"));
            agenda.setDocumento(datoSONObject.getInt("id"));
            agenda.setMotivo(datoSONObject.getString("motivo"));
            agenda.setFecha_consulta(datoSONObject.getString("fecha_de_consulta"));
            agenda.setHora_consulta(datoSONObject.getString("hora_consulta"));
            listaAgendas.add(agenda);

        }

        return listaAgendas;
    }

    public boolean verificarConsulta(Agenda agenda) {
        JSONObject object = new JSONObject(getjson());
        JSONArray listArray = object.getJSONArray("Agendas");
        JSONObject datoSONObject;
        boolean flag = false;

        for (int i = 0; i < listArray.length(); i++) {
            datoSONObject = listArray.getJSONObject(i);
            if (datoSONObject.getString("fecha_de_consulta").equals(agenda.getFecha_consulta())
                    && datoSONObject.getString("hora_consulta").equals(agenda.getHora_consulta())) {
                flag = true;
            }

        }
        return flag;

    }

}
