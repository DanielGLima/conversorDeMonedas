package MAIN;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class ConversorMonedas {

    private static final String API_KEY = "d1ce7d8c783fc4d03be54730";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("*************************************");
            System.out.println("**** 1) Dolar --> Peso Mexicano  ****");
            System.out.println("**** 2) Peso Mexicano --> Dolar  ****");
            System.out.println("**** 3) Dolar --> Peso argentino ****");
            System.out.println("**** 4) Peso Argentino --> Dolar ****");
            System.out.println("**** 5) Dolar --> Real Brasileño  ****");
            System.out.println("**** 6) Real Brasileño --> Dolar  ****");
            System.out.println("**** 7) Salir  ****");
            System.out.println("*************************************");
            System.out.print("Seleccione la opcion que desee: ");
            opcion = scanner.nextInt();

            if (opcion >= 1 && opcion <= 6) {
                System.out.print("Ingrese el valor que desea convertir: ");
                double valor = scanner.nextDouble();
                double resultado = convertirMoneda(opcion, valor);
                System.out.println("El valor convertido es: " + resultado);
            } else if (opcion == 7) {
                System.out.println("Saliendo...");
            } else {
                System.out.println("Opción inválida, intente de nuevo.");
            }
        } while (opcion != 7);

        scanner.close();
    }

    public static double convertirMoneda(int opcion, double valor) {
        double tasaConversion = obtenerTasaConversion(opcion);
        return valor * tasaConversion;
    }

    public static double obtenerTasaConversion(int opcion) {
        String base = "USD";
        String target = "";

        switch (opcion) {
            case 1: target = "MXN"; break;
            case 2: base = "MXN"; target = "USD"; break;
            case 3: target = "ARS"; break;
            case 4: base = "ARS"; target = "USD"; break;
            case 5: target = "BRL"; break;
            case 6: base = "BRL"; target = "USD"; break;
            default: return 1.0;
        }

        try {
            String urlStr = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/pair/" + base + "/" + target;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            JSONObject json = new JSONObject(content.toString());
            return json.getDouble("conversion_rate");
        } catch (Exception e) {
            e.printStackTrace();
            return 1.0;
        }
    }
}
