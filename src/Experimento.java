
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Experimento{
    
    public static int distMin(int distancias[], Boolean recorrido[], int tam){
        int min = Integer.MAX_VALUE;
        int posDelMinimo = -1;   
        for (int v = 0; v < tam; v++) 
            if (recorrido[v] == false && distancias[v] <= min) { 
                min = distancias[v]; 
                posDelMinimo = v; 
            }   
        return posDelMinimo; 
    }
    public static long algoritmo(int matAdy[][], int src, int tam) 
    { 
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        int distancias[] = new int[tam];         
  
        Boolean recorridos[] = new Boolean[tam]; 
        for (int i = 0; i < tam; i++) { 
            distancias[i] = Integer.MAX_VALUE; 
            recorridos[i] = false;
        } 
  
        distancias[src] = 0;   
        for (int count = 0; count < tam; count++) { 
            int u = distMin(distancias, recorridos, matAdy.length);             // Marcar el nodo escogido como Recorrido
            recorridos[u] = true; 
            for (int v = 0; v < tam; v++){                
                if (!recorridos[v] && matAdy[u][v] != 0 && distancias[u] != Integer.MAX_VALUE && distancias[u] + matAdy[u][v] < distancias[v]) {
                    distancias[v] = distancias[u] + matAdy[u][v]; 
                }
            } 
        }
        long memory = runtime.totalMemory() - runtime.freeMemory();
        return memory;
    }
    
    public static int[][] crearMatrizAleatoria (int tam){
        int[][] mat = new int[tam][tam];
        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam; j++) {
                if(i!=j){
                    mat[i][j] = (int) (Math.random() * (10 - 2 + 1) + 2);
                }
            }
        }
        return mat;
    }
    
    public static void main(String[] args) throws IOException 
    { 
        File file = new File("datos.txt");
        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);
           
        for (int i = 5; i < 201; i++) {
            pw.print(i+",");
            long startTime = System.nanoTime();
            int MatAdy[][] = crearMatrizAleatoria(i);
            long suma = 0;
            for (int j = 0; j < i; j++) {
                suma += algoritmo(MatAdy, 1, MatAdy.length);
            }
            
            long stopTime = System.nanoTime();
            long elapsedTime = stopTime - startTime;
            pw.print(elapsedTime);
            pw.println(","+suma);
        }
        pw.close();
    } 
}