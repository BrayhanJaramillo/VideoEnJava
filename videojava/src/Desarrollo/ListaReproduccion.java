/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Desarrollo;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 *
 * @author Brayhan
 */
public class ListaReproduccion {

    private Vector urlCompleto;
    private Vector name;
    private static FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo de video", "mpg", "mpeg");

    public ListaReproduccion() {
        //se crea dos vectores para almacenar la "url completa" y el "nombre" del archivo de video
        // los vectores tienen una capacidad inicial de 10 y un incremento de 2
        urlCompleto = new Vector(10, 2);
        name = new Vector(10, 2);
    }

    /* AÃ±ade a los vectores la url y el nombre de un archivo     
     */
    public void setVideo(URL u, String s) {
        urlCompleto.addElement(u);
        name.addElement(s);
    }

    /* Dado el nombre de un archivo de video, este es buscado en el vector y si
     es encontrado y removido de "ambos vectores" */
    public void removervideo(String s) {
        name.removeElement(s);
        Enumeration n = name.elements();
        while (n.hasMoreElements()) {
            String elemento = (String) n.nextElement();
            if (elemento.equals(s)) {
                urlCompleto.remove(elemento);
                break;
            }
        }
    }

    /* Dado un string con el nombre de un video, se busca su posicion en el vector
     * y luego se retorna la posicion de la URL completa
     */
    public URL getURL(String s) {
        Enumeration n = name.elements();
        URL turl = null;
        int d = 0;
        while (n.hasMoreElements()) {
            String elemento = (String) n.nextElement();
            if (elemento.equals(s)) {
                turl = (URL) urlCompleto.elementAt(d);
                break;
            }
            d++;
        }
        return turl;
    }

    /* devuelve la url dado la posicion del mismo */
    public URL getLastURL(int d) {
        URL turl = (URL) urlCompleto.elementAt(d);
        return turl;
    }

    /* Metod que muestra una ventana de dialgo para aÃ±adir "archivos" al playlist
     * hace uso del metodo anterior "setvideo"
     */
    public void Dialog(DefaultListModel modelo) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                //se asigna a mediaURL el archivo de video seleccionado
                URL url = fileChooser.getSelectedFile().toURL();
                String namefile = fileChooser.getSelectedFile().getName();
                //coloca video 
                setVideo(url, namefile);
                //aÃ±ade a playlist
                modelo.addElement(namefile);
            } catch (IOException ex) {
                Logger.getLogger(ListaReproduccion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
