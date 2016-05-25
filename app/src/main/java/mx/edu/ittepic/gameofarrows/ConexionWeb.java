package mx.edu.ittepic.gameofarrows;

import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by miguel on 25/05/16.
 */

public class ConexionWeb extends AsyncTask<URL,String,String> {
    List<String[]> variables;
    Login login;

    public ConexionWeb(Login a){
        variables = new ArrayList<String[]>();
        login=a;
    }
    public void agregarVariables(String id,String dato){
        String[] temp = {id,dato};
        variables.add(temp);
    }

    @Override
    protected String doInBackground(URL... params) {
        HttpURLConnection conexion=null;
        String datosPost="";
        String respuesta="";

        try{
            for(int i =0;i<variables.size();i++){
                datosPost+= URLEncoder.encode(variables.get(i)[0],"UTF-8")+"="+URLEncoder.encode(variables.get(i)[1],"UTF-8")+" ";
            }
            datosPost=datosPost.trim();//Quita espacios antes y despues de la cadena
            datosPost=datosPost.replaceAll(" ","&");
            //datosPost=datosPost.substring(0,datosPost.length()-2);

            conexion = (HttpURLConnection) params[0].openConnection();

            conexion.setDoOutput(true);
            conexion.setFixedLengthStreamingMode(datosPost.length());
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream out = new BufferedOutputStream(conexion.getOutputStream());
            out.write(datosPost.getBytes());
            out.flush();
            out.close();

            if(conexion.getResponseCode()==200){//Si recibio el host recibio los datos enviados y proceso respuesta

                InputStreamReader in = new InputStreamReader(conexion.getInputStream(),"UTF-8");
                BufferedReader lector = new BufferedReader(in);

                String linea = "";
                do{
                    linea=lector.readLine();
                    if(linea!=null){
                        respuesta+=linea;
                    }
                }while(linea!=null);
            }else{
                respuesta="Error_404";
            }

        }catch(UnknownHostException uhe){
            return "Error_404_1";
//            publishProgress("Error:"+uhe.getMessage());
        }catch(IOException ioe){
            return "Error_404_2";
            //publishProgress("Error:"+ioe.getMessage());
        }finally {
            if(conexion!=null){
                conexion.disconnect();
            }

        }

        return respuesta;
    }
    protected void onPostExecute(String s){
        super.onPostExecute(s);
        login.mostrarResultado(s);
    }
}
