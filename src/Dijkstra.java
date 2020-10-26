
import java.util.*; 
import java.lang.*; 
import java.io.*; 

public class Dijkstra{
    int nroVertices;

    public Dijkstra(int nroVertices) {
        this.nroVertices = nroVertices;
    }
    
    public int distMin(int distancias[], Boolean recorrido[]){
        // Initialize min value 
        int min = Integer.MAX_VALUE;
        int posDelMinimo = -1; 
  
        for (int v = 0; v < this.nroVertices; v++) 
            if (recorrido[v] == false && distancias[v] <= min) { 
                min = distancias[v]; 
                posDelMinimo = v; 
            } 
  
        return posDelMinimo; 
    }
    
    public void imprimir(int distancias[]) 
    { 
        System.out.println("Nro Router \t Distancia desde nodo origen"); 
        for (int i = 0; i < this.nroVertices; i++) 
            System.out.println(i + " \t\t " + distancias[i]); 
    } 
    public void algoritmo(int matAdy[][], int src) 
    { 
        // Arreglo que guardará la distancia menor del nodo origen hacia los demás nodos
        int distancias[] = new int[this.nroVertices];         
  
        // Arreglo que almacenará el valor de Verdadero cuando se haya hallado el menor camino desde el origen.
        Boolean recorridos[] = new Boolean[this.nroVertices]; 
  
        // Inicializar las distancias en infinito y la lista de nodos recorridos en Falso
        for (int i = 0; i < this.nroVertices; i++) { 
            distancias[i] = Integer.MAX_VALUE; 
            recorridos[i] = false; 
        } 
  
        // La distancia hacia el nodo origen es 0
        distancias[src] = 0; 
  
        // Para cada vértice:
        for (int count = 0; count < this.nroVertices - 1; count++) { 
            // Se busca el nodo con menor distancia al origen de la lista de nodos que aún no han sido recorridos.
            // En la primera iteración, siempre saldrá el nodo origen, pues es 0 y los demás infinito.
            int u = distMin(distancias, recorridos); 
  
            // Marcar el nodo escogido como Recorrido
            recorridos[u] = true; 
  
            // Actualiza la distancia entre el nodo origen y los vértices adyacente
            for (int v = 0; v < this.nroVertices; v++) 
  
                /*Condiciones:
                1. Que el vértice a actualizar no haya sido recorrido anteriormente.
                2. Que haya un camino hacia dicho vértice (valor en la matriz de adyacencia diferente a 0).
                3. Que la distancia desde el nodo origen hasta el nodo u no sea infinito.
                4. CONDICIÓN PRINCIPAL: que la distancia desde origen hasta v, pasando por los demás nodos, sea menor 
                   a la distancia hallada anteriormente.*/
                if (!recorridos[v] && matAdy[u][v] != 0 && distancias[u] != Integer.MAX_VALUE && distancias[u] + matAdy[u][v] < distancias[v]) 
                    distancias[v] = distancias[u] + matAdy[u][v]; 
            
            
        } 
        imprimir(distancias); 
    }


    public static void main(String[] args) 
    { 
        
        int MatAdy[][] = /*new int[][] { { 0, 4, 0, 0, 0, 0, 0, 8, 0 }, 
                                      { 4, 0, 8, 0, 0, 0, 0, 11, 0 }, 
                                      { 0, 8, 0, 7, 0, 4, 0, 0, 2 }, 
                                      { 0, 0, 7, 0, 9, 14, 0, 0, 0 }, 
                                      { 0, 0, 0, 9, 0, 10, 0, 0, 0 }, 
                                      { 0, 0, 4, 14, 10, 0, 2, 0, 0 }, 
                                      { 0, 0, 0, 0, 0, 2, 0, 1, 6 }, 
                                      { 8, 11, 0, 0, 0, 0, 1, 0, 7 }, 
                                      { 0, 0, 2, 0, 0, 0, 6, 7, 0 } }*/
                        new int[][] { { 0, 6, 4, 0},
                                    { 0, 0, 10, 8},
                                    { 0, 1, 0, 1},
                                    { 0, 0, 0, 0}}; 
        Dijkstra t = new Dijkstra(4); 
        t.algoritmo(MatAdy, 0); 
    } 
}
