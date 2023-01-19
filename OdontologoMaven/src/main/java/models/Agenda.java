package models;

import lombok.Data;

@Data
public class Agenda {

    private int id;
    private String name;
    private String last_name;
    private String motivo;
    private String fecha_consulta;
    private String hora_consulta;

}
