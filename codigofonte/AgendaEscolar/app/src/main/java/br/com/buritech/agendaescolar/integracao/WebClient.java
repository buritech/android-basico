package br.com.buritech.agendaescolar.integracao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Classe usada para acesso ao WebService
 */
public class WebClient {

    private final String urlWebService;

    public WebClient(String urlWebService) {
        this.urlWebService = urlWebService;
    }

    /**
     * Converte um InputStream para uma String
     * @param is Objeto de entrada
     * @return String com os dados de entrada
     */
    private String getStringFromInputStream(InputStream is) {
        BufferedReader reader = null;
        StringBuilder texto = new StringBuilder();
        String line;
        try {
            reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null) {
                texto.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return texto.toString();
    }

    public String post(String json){
        //Definicoes de comunicacao
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(this.urlWebService);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            //Coloca a String JSON no conteudo a ser enviado
            httpURLConnection.setRequestMethod("POST");
            //Informa que o conteudo da requisicao eh JSON e
            httpURLConnection.setRequestProperty("Content-Type",
                    "application/json; charset=utf-8");
            //Solicita que a resposta tambem seja em JSON
            httpURLConnection.setRequestProperty("Accept",
                    "application/json; charset=utf-8");
            //Coloca a String JSON no conteudo a ser enviado
            OutputStream out = httpURLConnection.getOutputStream();
            //Envio do JSON para o server
            out.write(json.getBytes());
            //Análise da resposta do servidor web
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = httpURLConnection.getInputStream();
                return getStringFromInputStream(is);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
