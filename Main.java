import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String archivo = "C:\\Users\\danie\\Downloads\\cultivos.csv";

        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            br.readLine(); // Saltar encabezado

            int capacidad = 200000;
            int[] toneladas = new int[capacidad];

            String linea;
            int i = 0;

            while ((linea = br.readLine()) != null && i < capacidad) {
                String[] partes = linea.split(",");

                if (partes.length > 6) {
                    try {
                        toneladas[i] = Integer.parseInt(partes[6].trim());
                        i++;
                    } catch (NumberFormatException e) {
                        // Ignorar errores
                    }
                }
            }

            br.close();

            int[] datos = new int[i];
            System.arraycopy(toneladas, 0, datos, 0, i);

            System.out.println("Datos cargados: " + i);

            Scanner sc = new Scanner(System.in);

            System.out.println("\nSeleccione el algoritmo:");
            System.out.println("1. Insertion Sort");
            System.out.println("2. Counting Sort");
            System.out.println("3. Merge Sort");
            System.out.print("Opción: ");

            int opcion = sc.nextInt();

            long inicio = System.currentTimeMillis();

            switch (opcion) {
                case 1:
                    insertionSortDesc(datos);
                    break;
                case 2:
                    datos = countingSortDesc(datos);
                    break;
                case 3:
                    mergeSort(datos, 0, datos.length - 1);
                    break;
                default:
                    System.out.println("Opción inválida");
                    return;
            }

            long fin = System.currentTimeMillis();

            System.out.println("\nTiempo de ejecución: " + (fin - inicio) + " ms");

            System.out.println("\nTop 20 toneladas (mayor a menor):");
            for (int k = 0; k < 20 && k < datos.length; k++) {
                System.out.println(datos[k]);
            }

            sc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 🔹 INSERTION SORT DESC
    public static void insertionSortDesc(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int clave = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] < clave) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = clave;
        }
    }

    // 🔹 COUNTING SORT DESC
    public static int[] countingSortDesc(int[] arr) {

        int max = arr[0];
        for (int num : arr) {
            if (num > max) max = num;
        }

        int[] conteo = new int[max + 1];

        for (int num : arr) {
            conteo[num]++;
        }

        int[] resultado = new int[arr.length];
        int index = 0;

        for (int i = max; i >= 0; i--) {
            while (conteo[i] > 0) {
                resultado[index++] = i;
                conteo[i]--;
            }
        }

        return resultado;
    }

    // 🔹 MERGE SORT DESC
    public static void mergeSort(int[] arr, int izquierda, int derecha) {
        if (izquierda < derecha) {
            int medio = (izquierda + derecha) / 2;

            mergeSort(arr, izquierda, medio);
            mergeSort(arr, medio + 1, derecha);

            merge(arr, izquierda, medio, derecha);
        }
    }

    public static void merge(int[] arr, int izquierda, int medio, int derecha) {

        int n1 = medio - izquierda + 1;
        int n2 = derecha - medio;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; i++)
            L[i] = arr[izquierda + i];

        for (int j = 0; j < n2; j++)
            R[j] = arr[medio + 1 + j];

        int i = 0, j = 0, k = izquierda;

        while (i < n1 && j < n2) {
            if (L[i] >= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k++] = L[i++];
        }

        while (j < n2) {
            arr[k++] = R[j++];
        }
    }
}


