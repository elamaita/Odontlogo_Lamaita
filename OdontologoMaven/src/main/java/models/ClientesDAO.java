package models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;

public class ClientesDAO {

    public boolean verificarCliente(Clientes cliente) {
        ObjectMapper objectMapper = new ObjectMapper();
        File clienteJSON = new File("Clientes.json");
        boolean flag = false;
        try {
            List<Clientes> listaClientes = objectMapper.readValue(clienteJSON, new TypeReference<List<Clientes>>() {
            });

            for (int i = 0; i < listaClientes.size(); i++) { //Recorro todos los clientes
                if (listaClientes.get(i).getId() == cliente.getId()) { // verifico que la cedula de la posicion del array en la que va sea igual a la que se declara
                    flag = true;
                }
            }
            return flag;
        } catch (IOException e) {
            return flag;
        }
    }

    public boolean registrarCliente(Clientes cliente) {
        ObjectMapper objectMapper = new ObjectMapper();
        File clienteJSON = new File("Clientes.json");
        try {
            List<Clientes> listaClientes = objectMapper.readValue(clienteJSON, new TypeReference<List<Clientes>>() {
            });
            listaClientes.add(cliente);
            objectMapper.writeValue(clienteJSON, listaClientes);
            return true;
        } catch (IOException e) {
            return false;

        }

    }

    public boolean eliminarCliente(Clientes cliente) {
        ObjectMapper objectMapper = new ObjectMapper();
        File clienteJSON = new File("Clientes.json");
        try {
            List<Clientes> listaClientes = objectMapper.readValue(clienteJSON, new TypeReference<List<Clientes>>() {});
            String nombre_eliminar = "";
            String apellido_eliminar = "";
            int posicion_cliente = 0;
            for (int i = 0; i < listaClientes.size(); i++) {
                if (listaClientes.get(i).getId() == cliente.getId()) {
                    posicion_cliente = i; // Para saber en que posicion del array se encuentra el cliente.
                    nombre_eliminar = listaClientes.get(i).getName();
                    apellido_eliminar = listaClientes.get(i).getLast_name();
                }
            }

            if (JOptionPane.showConfirmDialog(null, "Está seguro que desea eliminar el cliente " + nombre_eliminar + " " + apellido_eliminar, " ATENCIÓN ",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                listaClientes.remove(posicion_cliente);
                objectMapper.writeValue(clienteJSON, listaClientes);

                return true;

            } else {
                return false;
            }
        } catch (IOException e) {
            return false;

        }

    }

    public List listarClientes() {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File clienteJSON = new File("Clientes.json");
            List<Clientes> listaClientes = objectMapper.readValue(clienteJSON, new TypeReference<List<Clientes>>() {
            });
            return listaClientes;
        } catch (IOException ex) {
            return null;
        }

    }

}
