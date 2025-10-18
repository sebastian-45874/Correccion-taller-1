public class Compra {
    private final String cedula;
    private final String pelicula;
    private final int entradas;

    public Compra(String cedula, String pelicula, int entradas) {
        this.cedula = cedula;
        this.pelicula = pelicula;
        this.entradas = entradas;
    }

    public String getCedula() {
        return cedula;
    }

    public String getPelicula() {
        return pelicula;
    }

    public int getEntradas() {
        return entradas;
    }

    @Override
    public String toString() {
        return "Pelicula:" +pelicula + " | Cedula: " +cedula +" | Entradas: " + entradas + " | Total: $ "+(entradas*5);
    }
}