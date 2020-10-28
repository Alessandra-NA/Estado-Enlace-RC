
import java.util.*; 

public class Dijkstra{
    int nroVertices;

    public Dijkstra(int nroVertices) {
        this.nroVertices = nroVertices;
    }
    
    public int distMin(int distancias[], Boolean recorrido[]){
        int min = Integer.MAX_VALUE;
        int posDelMinimo = -1;   
        for (int v = 0; v < this.nroVertices; v++) 
            if (recorrido[v] == false && distancias[v] <= min) { 
                min = distancias[v]; 
                posDelMinimo = v; 
            }   
        return posDelMinimo; 
    }
    
    public void imprimir(int distancias[], ArrayList camino) 
    { 
        System.out.println("Nro Router \t Distancia desde origen \t Camino"); 
        for (int i = 0; i < this.nroVertices; i++) 
            System.out.println(i + " \t\t " + distancias[i]+ " \t\t\t\t " +camino.get(i)); 
    } 
    public void algoritmo(int matAdy[][], int src) 
    { 
        // Arreglo que guardará la distancia menor del nodo origen hacia los demás nodos
        int distancias[] = new int[this.nroVertices];         
  
        // Arreglo que almacenará el valor de Verdadero cuando se haya hallado el menor camino desde el origen.
        Boolean recorridos[] = new Boolean[this.nroVertices]; 
        
        ArrayList caminos = new ArrayList();
        // Inicializar las distancias en infinito y la lista de nodos recorridos en Falso
        // Inicializar el Arraylist "Caminos" con n espacios, siendo n la cantidad de vértices
        for (int i = 0; i < this.nroVertices; i++) { 
            distancias[i] = Integer.MAX_VALUE; 
            recorridos[i] = false; 
            caminos.add(null);
        } 
  
        // La distancia hacia el nodo origen es 0
        distancias[src] = 0; 
        
        //El único nodo a mostrar en el camino del origen es el nodo "src"
        ArrayList temp = new ArrayList();
        temp.add(src);
        caminos.set(src, temp);
        
        // Para cada vértice:
        for (int count = 0; count < this.nroVertices; count++) { 
            // Se busca el nodo con menor distancia al origen de la lista de nodos que aún no han sido recorridos.
            // (En la primera iteración, siempre saldrá el nodo origen, pues es 0 y los demás infinito)
            int u = distMin(distancias, recorridos); 
            // Marcar el nodo escogido como Recorrido
            recorridos[u] = true; 
            // Actualiza la distancia entre el nodo origen y los vértices adyacente
            for (int v = 0; v < this.nroVertices; v++){                
                /*Condiciones:
                1. Que el vértice a actualizar no haya sido recorrido anteriormente.
                2. Que haya un camino hacia dicho vértice (valor en la matriz de adyacencia diferente a 0).
                3. Que la distancia desde el nodo origen hasta el nodo u no sea infinito.
                4. CONDICIÓN PRINCIPAL: que la distancia desde origen hasta v, pasando por los demás nodos, sea menor 
                   a la distancia hallada anteriormente.*/
                if (!recorridos[v] && matAdy[u][v] != 0 && distancias[u] != Integer.MAX_VALUE && distancias[u] + matAdy[u][v] < distancias[v]) {
                    distancias[v] = distancias[u] + matAdy[u][v]; 
                    // Agregar al camino del nodo V el camino del nodo anterior + el nodo V
                    temp = new ArrayList();
                    for (int i = 0; i < ((ArrayList)caminos.get(u)).size(); i++) {
                        temp.add(((ArrayList)caminos.get(u)).get(i));
                    }
                    temp.add(v);
                    caminos.set(v, temp);                    
                }
            } 
        } 
        imprimir(distancias,caminos); 
    }


    public static void main(String[] args) 
    { 
        
        int MatAdy[][] = new int[][] { { 0, 4, 0, 0, 0, 0, 0, 8, 0 }, 
                                       { 4, 0, 8, 0, 0, 0, 0, 11, 0 }, 
                                       { 0, 8, 0, 7, 0, 4, 0, 0, 2 }, 
                                       { 0, 0, 7, 0, 9, 14, 0, 0, 0 }, 
                                       { 0, 0, 0, 9, 0, 10, 0, 0, 0 }, 
                                       { 0, 0, 4, 14, 10, 0, 2, 0, 0 }, 
                                       { 0, 0, 0, 0, 0, 2, 0, 1, 6 }, 
                                       { 8, 11, 0, 0, 0, 0, 1, 0, 7 }, 
                                       { 0, 0, 2, 0, 0, 0, 6, 7, 0 } };
        Dijkstra t = new Dijkstra(MatAdy.length); 
        t.algoritmo(MatAdy, 5); 
    } 
}
