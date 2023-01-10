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

public class ClientesDAO {

    public String getjson() {
        String json = "";
        try {
            //Parseo el JSON para obtener el json en un string
            json = (String) new JSONParser().parse(new FileReader("Clientes.json")).toString();

        } catch (IOException | ParseException ex) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return json;
    }

    public boolean verificarCliente(Clientes cliente) {
        JSONObject object = new JSONObject(getjson());
        JSONArray listArray = object.getJSONArray("Clientes"); //Obtengo el array de clientes del JSON
        JSONObject datoSONObject;
        boolean flag = false;
        for (int i = 0; i < listArray.length(); i++) { //Recorro todos los clientes
            datoSONObject = listArray.getJSONObject(i);
            if (datoSONObject.getInt("id") == cliente.getDocumento()) { // verifico que la cedula de la posicion del array en la que va sea igual a la que se declara
                flag = true;
            }
        }
        return flag;
    }

    public boolean registrarCliente(Clientes cliente) {
        JSONObject object = new JSONObject(getjson());
        JSONArray listArray = object.getJSONArray("Clientes"); //Obtengo el array de clientes del JSON
        JSONObject datoSONObject = new JSONObject();

        //Ingreso los datos a un nuevo objeto
        datoSONObject.put("id", cliente.getDocumento());
        datoSONObject.put("name", cliente.getNombre());
        datoSONObject.put("last_name", cliente.getApellido());
        datoSONObject.put("telefono", cliente.getTelefono());
        datoSONObject.put("address", cliente.getDireccion());
        datoSONObject.put("email", cliente.getEmail());
        datoSONObject.put("date", cliente.getFecha_nacimiento());

        
        listArray.put(datoSONObject); // Paso los datos del objeto al array de clientes

        try ( FileWriter file = new FileWriter("Clientes.json")) {

            file.write(object.toString()); //Sobreescribo el archivo con la informacion actualizda
            file.flush();
            return true;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "Error");
            return false;
        }

    }

    public boolean eliminarCliente(Clientes cliente) {
        JSONObject object = new JSONObject(getjson());
        String nombre_eliminar = "";
        String apelldio_eliminar ="";
        JSONArray listArray = object.getJSONArray("Clientes");
        JSONObject datoSONObject = new JSONObject();
        int posicion_cliente = 0;

        for (int i = 0; i < listArray.length(); i++) {
            datoSONObject = listArray.getJSONObject(i);
            if (datoSONObject.getInt("id") == cliente.getDocumento()) {
                posicion_cliente = i; // Para saber en que posicion del array se encuentra el cliente.
                nombre_eliminar = datoSONObject.getString("name");
                apelldio_eliminar = datoSONObject.getString("last_name");
            }
        }

        if (JOptionPane.showConfirmDialog(null, "Está seguro que desea eliminar el cliente " + nombre_eliminar + " " + apelldio_eliminar , " ATENCIÓN ",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            listArray.remove(posicion_cliente);

            try ( FileWriter file = new FileWriter("Clientes.json")) {

                file.write(object.toString());
                file.flush();
                
                
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, "Error");
          
            }
            return true;

        }else{
            return false;
        }
        

    }

    public List listarClientes() {
        List<Clientes> listarClientes = new ArrayList();

        JSONObject object = new JSONObject(getjson());
        JSONArray listArray = object.getJSONArray("Clientes");
        JSONObject datoSONObject;

        for (int i = 0; i < listArray.length(); i++) {
            datoSONObject = listArray.getJSONObject(i);
            Clientes cliente = new Clientes();
            cliente.setNombre(datoSONObject.getString("name"));
            cliente.setApellido(datoSONObject.getString("last_name"));
            cliente.setDocumento(datoSONObject.getInt("id"));
            cliente.setDireccion(datoSONObject.getString("address"));
            cliente.setTelefono(datoSONObject.getString("telefono"));
            cliente.setFecha_nacimiento(datoSONObject.getString("date"));
            cliente.setEmail(datoSONObject.getString("email"));

            listarClientes.add(cliente);

        }

        return listarClientes;
    }

}
