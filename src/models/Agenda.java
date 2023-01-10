package models;

public class Agenda {

    private int documento;
    private String nombre;
    private String apellido;
    private String motivo;
    private String fecha_consulta;
    private String hora_consulta;

    public Agenda() {
    }

    public Agenda(int documento, String nombre, String apellido, String motivo, String fecha_consulta, String hora_consulta) {
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.motivo = motivo;
        this.fecha_consulta = fecha_consulta;
        this.hora_consulta = hora_consulta;
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getFecha_consulta() {
        return fecha_consulta;
    }

    public void setFecha_consulta(String fecha_consulta) {
        this.fecha_consulta = fecha_consulta;
    }

    public String getHora_consulta() {
        return hora_consulta;
    }

    public void setHora_consulta(String hora_consulta) {
        this.hora_consulta = hora_consulta;
    }

}
