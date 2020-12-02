import java.util.Scanner;
import static jdk.vm.ci.hotspot.HotSpotJVMCIRuntime.runtime;

public class Dijkstra{
    
    public static int[][] crearMatriz(){
        System.out.print("Ingrese la cantidad de nodos: ");
        Scanner lect = new Scanner(System.in);
        int tam = lect.nextInt();
        int MatAdy[][] = new int[tam][tam];
        System.out.println();
        for (int i = 0; i < tam; i++) {
            System.out.println("Ingresando datos sobre el nodo "+i+"...");
            while (true) {                
                System.out.print("Ingrese el número del nodo adyacente al nodo "+i+". De no haber ninguno por agregar, ingrese '-': ");
                String rpta = lect.next();
                if(!rpta.equals("-")){
                    if(Integer.valueOf(rpta)>=tam || Integer.valueOf(rpta)<0){
                        System.out.println(" *** Número de nodo no válido *** ");
                    } else{
                        int ady = Integer.valueOf(rpta);
                        System.out.print("Ingrese el peso entre ambos nodos: ");
                        int peso = lect.nextInt();
                        MatAdy[i][ady] = peso;
                    }
                } else break;
            }
            System.out.println();
        }
        imprimirMat(MatAdy);
        return MatAdy;
    }
    
    public static void imprimirMat(int[][] mat){
        System.out.println("Matriz generada: ");
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                System.out.print(mat[i][j]+" ");
            }
            System.out.println("");
        }
    }
    
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
    
    public static void imprimir(int distancias[], String[] camino, int tam) 
    { 
        System.out.println("Nro Router \t Distancia desde origen \t Camino"); 
        for (int i = 0; i < tam; i++) 
            System.out.println(i + " \t\t " + distancias[i]+ " \t\t\t\t " +camino[i]); 
    } 
    public static void algoritmo(int matAdy[][], int src, int tam) 
    { 
        // Se inicia a contar el tiempo de ejecución para las comparaciones: 
        long startTime = System.nanoTime();
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        // Arreglo que guardará la distancia menor del nodo origen hacia los demás nodos
        int distancias[] = new int[tam];         
  
        // Arreglo que almacenará el valor de Verdadero cuando se haya hallado el menor camino desde el origen.
        Boolean recorridos[] = new Boolean[tam]; 
        String[] camino = new String[matAdy.length];
        // Inicializar las distancias en infinito y la lista de nodos recorridos en Falso
        for (int i = 0; i < tam; i++) { 
            distancias[i] = Integer.MAX_VALUE; 
            recorridos[i] = false;
        } 
  
        // La distancia hacia el nodo origen es 0
        distancias[src] = 0; 
        
        //El único nodo a mostrar en el camino del origen es el nodo "src"
        camino[src] = String.valueOf(src);
        
        // Para cada vértice:
        for (int count = 0; count < tam; count++) { 
            // Se busca el nodo con menor distancia al origen de la lista de nodos que aún no han sido recorridos.
            // (En la primera iteración, siempre saldrá el nodo origen, pues es 0 y los demás infinito)
            int u = distMin(distancias, recorridos, matAdy.length); 
            // Marcar el nodo escogido como Recorrido
            recorridos[u] = true; 
            // Actualiza la distancia entre el nodo origen y los vértices adyacente
            for (int v = 0; v < tam; v++){                
                /*Condiciones:
                1. Que el vértice a actualizar no haya sido recorrido anteriormente.
                2. Que haya un camino hacia dicho vértice (valor en la matriz de adyacencia diferente a 0).
                3. Que la distancia desde el nodo origen hasta el nodo u no sea infinito.
                4. CONDICIÓN PRINCIPAL: que la distancia desde origen hasta v, pasando por los demás nodos, sea menor 
                   a la distancia hallada anteriormente.*/
                if (!recorridos[v] && matAdy[u][v] != 0 && distancias[u] != Integer.MAX_VALUE && distancias[u] + matAdy[u][v] < distancias[v]) {
                    distancias[v] = distancias[u] + matAdy[u][v]; 
                    camino[v] = "";
                    camino[v] += camino[u] + " -> "+ String.valueOf(v);
                }
            } 
        } 
        imprimir(distancias,camino, matAdy.length); 
        long stopTime = System.nanoTime();
        long elapsedTime = stopTime - startTime;
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory is bytes: " + memory);
        System.out.println("Tiempo de ejecución: "+elapsedTime);
        preguntar(matAdy);
    }
    
    public static void preguntar(int[][] matriz){
        System.out.println("Escriba el número de alguna de las opciones mostradas para continuar: ");
        System.out.println("1. Consultar los caminos y pesos con otro nodo origen y misma topología. ");
        System.out.println("2. Ingresar nueva topología.");
        System.out.println("3. Cerrar programa.");
        Scanner lector = new Scanner(System.in);
        int src;
        int resp = lector.nextInt();
        switch(resp){
            case 1:
                System.out.print("Ingrese número del nodo origen: ");
                src = lector.nextInt();
                while(src<0 || src>=matriz.length){
                    System.out.print("Nodo inválido. Pruebe otra vez: ");
                    src = lector.nextInt();
                }
                algoritmo(matriz, src, matriz.length);
                break;
            case 2: 
                int MatAdy[][] = crearMatriz();
                System.out.println();
                System.out.print("¿Nodo origen? ");
                src = lector.nextInt();
                System.out.println();
                algoritmo(MatAdy, src, MatAdy.length); 
                break;
            case 3:
                System.out.println("Finalizando programa...");
                break;
        }
    }
    
    public static void main(String[] args) 
    { 
        /*int MatAdy[][] = new int[][] { { 0, 2, 4}, 
                                       { 2, 3, 1}, 
                                       { 4, 3, 2,}};
        /*int MatAdy[][] = new int[][] { { 0, 8, 10, 0, 0 }, 
                                       { 8, 0, 3, 8, 0 }, 
                                       { 10, 3, 0, 5, 3 }, 
                                       { 0, 8, 5, 0, 6 }, 
                                       { 0, 0, 3, 6, 0 }};*/
        /*int MatAdy[][] = new int[][] { { 0, 8, 10, 0, 0, 4, 0, 0 }, 
                                       { 8, 0, 3, 8, 0, 3, 6, 0 }, 
                                       { 10, 3, 0, 5, 3, 0, 0, 0 }, 
                                       { 0, 8, 5, 0, 6, 0, 8, 5 }, 
                                       { 0, 0, 3, 6, 0, 0, 0, 3 }, 
                                       { 4, 3, 0, 0, 0, 0, 9, 0 }, 
                                       { 0, 6, 0, 8, 0, 9, 0, 9 }, 
                                       { 0, 0, 0, 5, 3, 0, 9, 0 }}; */
        /*int MatAdy[][] = new int[][] { { 0, 2, 0, 0, 10, 0, 0, 0, 0, 0, 15 }, 
                                       { 2, 0, 8, 9, 0, 0, 0, 0, 0, 0, 0 }, 
                                       { 0, 8, 0, 0, 0, 0, 0, 0, 4, 0, 2 }, 
                                       { 0, 9, 0, 7, 0, 3, 0, 0, 0, 0, 0 }, 
                                       { 10, 0, 0, 7, 0, 5, 2, 0, 0, 0, 0 }, 
                                       { 0, 0, 0, 0, 5, 0, 9, 0, 0, 0, 0 }, 
                                       { 0, 0, 0, 0, 2, 9, 0, 8, 0, 0, 0 }, 
                                       { 0, 0, 0, 3, 0, 0, 8, 0, 5, 0, 0 }, 
                                       { 0, 0, 4, 0, 0, 0, 0, 5, 0, 10, 0 },
                                       { 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 5 },
                                       { 15, 0, 2, 0, 0, 0, 0, 0, 10, 5, 0 }};*/
        int MatAdy[][] = crearMatriz();
        System.out.println();
        System.out.print("¿Nodo origen? ");
        Scanner lector = new Scanner(System.in);
        int src = lector.nextInt();
        System.out.println();
        algoritmo(MatAdy, src, MatAdy.length);        
    } 
}