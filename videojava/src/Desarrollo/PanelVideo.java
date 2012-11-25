/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Desarrollo;

import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.swing.JPanel;
import javax.swing.JProgressBar;



/**
 *
 * @author Brayhan
 */
public class PanelVideo extends JPanel {
    
 
    
    private Player mediaPlayer;
    private Component video;

    // La url es el archivo a reproducir
    //Las dimensiones  del contenedor que tendra el reproductor
    public PanelVideo(URL url, Dimension d) {
        try {

            //Se forzara el uso de componentes de render ligero
            Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, true);

            //Se crea un dato source de una representacion url de un archivo
            mediaPlayer = Manager.createRealizedPlayer(url);

            //Ahora se declarara un componente adecuado para el uso en una GUI
            //Borramos Component video y dejamos a video
            video = mediaPlayer.getVisualComponent();

            System.out.println("video = " + video);
            
            //Añadimos al componente las dimensiones del contenedor
            video.setSize(d);

            //Borramos
//            video.setVisible(true);

            //Finalmente se añade al JPanel
            add(video);

            //No se añadiran los controles del video aun
            //Por el momento el video se reproducira automaticamente se crea el objeto
            //Esto es lo necesario para reproducir un video
            //Borramos 
//            mediaPlayer.start();

            //Ahora nos vamos para la interfaz Desarrollo


            //control de excepciones
        } catch (IOException ex) {
            Logger.getLogger(PanelVideo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoPlayerException ex) {
            Logger.getLogger(PanelVideo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CannotRealizeException ex) {
            Logger.getLogger(PanelVideo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    //Esto es para lo del volumen
    public float getVolumen() {
        return mediaPlayer.getGainControl().getLevel();
        
    }

    //Colocamos un valor float correspondiente al volumen
    public void setVolumen(float volumen) {
        mediaPlayer.getGainControl().setLevel(volumen);
    }

    //Esto es para lo del mute
    public void MUTE() {
        
        if (mediaPlayer.getGainControl().getMute()) {
            
            mediaPlayer.getGainControl().setMute(false);
        } else {
            
            mediaPlayer.getGainControl().setMute(true);
        }
    }

    //Esto es para lo del Stop
    public void STOP() {
        mediaPlayer.stop();
        video.hide();
    }

    //Esto es para lo del Play
    public void PLAY() {
        
        mediaPlayer.start();
        video.show();
    }
    //Esto es para lo de la barra de progreso
    private TimerTask task;
    private int timeReproduccion = 0;
    private Timer tiempo;
    private int speed = 1000;//Esto es a la velocidad que va el video

    //Estos son los metodos para la animacion
    public void comenzarAnima(final JProgressBar proceso) {

        //Creamos una instancia de la clase Timer

        tiempo = new Timer();
        task = new TimerTask() {
            public void run() {
                //Aca se utiliza una formula para obtener el porcentaje de la reproduccion del video
                // (Reproduccion actual * 100) / total de la reproduccion)
                timeReproduccion = (int) Math.round((mediaPlayer.getMediaTime().getSeconds() * 100) / mediaPlayer.getDuration().getSeconds());

                //Esto es para que uno sepa que el video ha alcanzado el final de la reproduccion
                if (mediaPlayer.getMediaTime().getSeconds() == mediaPlayer.getDuration().getSeconds()) {
                    timeReproduccion = 100;
                    STOP();
                    pararAnima(proceso);
                    
                } else {
                    //Se actualiza el proceso
                    proceso.setValue(timeReproduccion);
                    
                    System.out.println("El tiempo total del video es:  " + mediaPlayer.getDuration().getSeconds());
                    //Segundos del video en reproducion
                    System.out.println("Timpo em segundos:  " + mediaPlayer.getMediaTime().getSeconds());
                    
                    System.out.println("Tiempo de reproduccion:  " + timeReproduccion);
                     
                    
                }
                
            }
        };
        
        tiempo.schedule(task, 0, speed);
    }
    
    public void pararAnima(final JProgressBar proceso) {
        
        tiempo.cancel();
        task.cancel();
        
    }
}
