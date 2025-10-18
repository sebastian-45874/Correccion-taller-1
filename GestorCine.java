import java.util.*;

public class GestorCine{

    public static final String PIRATAS = "Piratas";
    public static final String NARUTO = "Naruto";
    public static final String ANTMAN = "Antman";

    private static final int CAPACIDAD = 17;
    private static final int PRECIO = 5;

    private final Map<String, Queue<Compra>> colas = new HashMap<>();
    private final Map<String, Integer> capacidad = new HashMap<>();
    private final Map<String, Integer> vendidos = new HashMap<>();
    private final Set<String> cedulasUsadas = new HashSet<>();
    private int total = 0;

    public GestorCine(){
        colas.put(PIRATAS, new LinkedList<>());
        colas.put(NARUTO, new LinkedList<>());
        colas.put(ANTMAN, new LinkedList<>());
        capacidad.put(PIRATAS, CAPACIDAD);
        capacidad.put(NARUTO, CAPACIDAD);
        capacidad.put(ANTMAN, CAPACIDAD);
        vendidos.put(PIRATAS, 0);
        vendidos.put(NARUTO, 0);
        vendidos.put(ANTMAN, 0);
    }

    private String canon(String p){
        if(p == null) return null;
        if(p.equalsIgnoreCase("Piratas")) return PIRATAS;
        if(p.equalsIgnoreCase("Naruto")) return NARUTO;
        if(p.equalsIgnoreCase("Antman")) return ANTMAN;
        return p;
    }

    private void validar(String p){
        if(!colas.containsKey(p)) throw new RuntimeException("Película no encontrada: "+p);
    }

    public Compra registrarCompra(String pelicula, String cedula, int entradas){
        String p = canon(pelicula);
        validar(p);
        if(cedula == null || cedula.isEmpty())throw new RuntimeException("Ingrese la cédula ");
        if( entradas < 1) throw new RuntimeException("Debe comprar por lo menos una entrada ");
        if(entradas > 5) throw new RuntimeException("No se puede comprar más de 5 entradas por persona ");
        if(cedulasUsadas.contains(cedula))throw new RuntimeException("Esa cédula ya realizo una compra. Ingrese otra cédula");
        int libres = capacidad.get(p);
        if(entradas > libres)throw new RuntimeException("No hay capacidad suficiente en" + p + ". asientos libres: " +libres);
        Compra c = new Compra(cedula, p, entradas);
        colas.get(p).add(c);
        capacidad.put(p, libres - entradas);
        vendidos.put(p, vendidos.get(p) + entradas);
        total += entradas * PRECIO;
        cedulasUsadas.add(cedula);
        return c;
    }

    public int getVendidos(String pelicula){
        String p = canon(pelicula);
        validar(p);
        return vendidos.get(p);
    }

    public int getCapacidadRestantes(String pelicula){
        String p = canon(pelicula);
        validar(p);
        return capacidad.get(p);
    }

    public int getTotal(){
        return total;
    }

    public int totalPorPelicula(String pelicula){
        String p = canon(pelicula);
        validar(p);
        return vendidos.get(p) * PRECIO;
    }

    public String historial(){
        StringBuilder sb = new StringBuilder();
        for(String p : new String[]{PIRATAS, NARUTO, ANTMAN}){
            for(Compra c : colas.get(p)) sb.append(c.toString()).append("\n");
        }
        return sb.toString();
    }
}