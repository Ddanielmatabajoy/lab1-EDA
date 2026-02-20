import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Cultivo {
    String nombre;
    int toneladas;

    public Cultivo(String nombre, int toneladas) {
        this.nombre = nombre;
        this.toneladas = toneladas;
    }
}

public class Main {

    public static void main(String[] args) {

        String archivo = "C:\\Users\\danie\\Downloads\\cultivos.csv";

        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            br.readLine(); 

            int capacidad = 100000;
            Cultivo[] datos = new Cultivo[capacidad];

            String linea;
            int i = 0;

            while ((linea = br.readLine()) != null && i < capacidad) {
                String[] partes = linea.split(",");

                if (partes.length > 6) {
                    try {
                        String nombreCultivo = partes[3].trim();
                        int toneladas = Integer.parseInt(partes[6].trim());

                        datos[i] = new Cultivo(nombreCultivo, toneladas);
                        i++;
                    } catch (NumberFormatException e) {
                        
                    }
                }
            }

            br.close();

            
            Cultivo[] original = new Cultivo[i];
            System.arraycopy(datos, 0, original, 0, i);

            System.out.println("Datos cargados: " + i);

            //  INsertion Sort
            Cultivo[] copia1 = copiarArreglo(original);
            long inicio1 = System.currentTimeMillis();
            insertionSort(copia1);
            long fin1 = System.currentTimeMillis();

            //  Counting Sort
            Cultivo[] copia2 = copiarArreglo(original);
            long inicio2 = System.currentTimeMillis();
            copia2 = countingSort(copia2);
            long fin2 = System.currentTimeMillis();

            //  Merge Sort
            Cultivo[] copia3 = copiarArreglo(original);
            long inicio3 = System.currentTimeMillis();
            mergeSort(copia3, 0, copia3.length - 1);
            long fin3 = System.currentTimeMillis();

            //Mostrar tiempos
            System.out.println("\nTIEMPOS DE EJECUCIÓN");
            System.out.println("Insertion Sort: " + (fin1 - inicio1) + " ms");
            System.out.println("Counting Sort:  " + (fin2 - inicio2) + " ms");
            System.out.println("Merge Sort:     " + (fin3 - inicio3) + " ms");

            // Mostrar primeros 20 resultados
            System.out.println("\n=== PRIMEROS 20 RESULTADOS (Merge Sort) ===");
            for (int j = 0; j < 20 && j < copia3.length; j++) {
                System.out.println(copia3[j].nombre + " - " + copia3[j].toneladas);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Copiar arreglo
    public static Cultivo[] copiarArreglo(Cultivo[] original) {
        Cultivo[] copia = new Cultivo[original.length];
        System.arraycopy(original, 0, copia, 0, original.length);
        return copia;
    }

    // INSERTION SORT 
    public static void insertionSort(Cultivo[] arr) {
        for (int i = 1; i < arr.length; i++) {
            Cultivo clave = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j].toneladas > clave.toneladas) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = clave;
        }
    }

    //  COUNTING SORT (ASC - ESTABLE Y CORRECTO)
    public static Cultivo[] countingSort(Cultivo[] arr) {

        int max = arr[0].toneladas;
        for (Cultivo c : arr) {
            if (c.toneladas > max)
                max = c.toneladas;
        }

        int[] conteo = new int[max + 1];

        // Contar frecuencias
        for (Cultivo c : arr) {
            conteo[c.toneladas]++;
        }

        // Acumulativo
        for (int i = 1; i <= max; i++) {
            conteo[i] += conteo[i - 1];
        }

        Cultivo[] resultado = new Cultivo[arr.length];

        // Construcción estable (desde atrás)
        for (int i = arr.length - 1; i >= 0; i--) {
            resultado[conteo[arr[i].toneladas] - 1] = arr[i];
            conteo[arr[i].toneladas]--;
        }

        return resultado;
    }

    // MERGE SORT (ASC)
    public static void mergeSort(Cultivo[] arr, int izquierda, int derecha) {
        if (izquierda < derecha) {
            int medio = (izquierda + derecha) / 2;

            mergeSort(arr, izquierda, medio);
            mergeSort(arr, medio + 1, derecha);

            merge(arr, izquierda, medio, derecha);
        }
    }

    public static void merge(Cultivo[] arr, int izquierda, int medio, int derecha) {

        int n1 = medio - izquierda + 1;
        int n2 = derecha - medio;

        Cultivo[] L = new Cultivo[n1];
        Cultivo[] R = new Cultivo[n2];

        for (int i = 0; i < n1; i++)
            L[i] = arr[izquierda + i];

        for (int j = 0; j < n2; j++)
            R[j] = arr[medio + 1 + j];

        int i = 0, j = 0, k = izquierda;

        while (i < n1 && j < n2) {
            if (L[i].toneladas <= R[j].toneladas) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }

        while (i < n1)
            arr[k++] = L[i++];

        while (j < n2)
            arr[k++] = R[j++];
    }
}
