
package main;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;

import views.panelAdministracion;

public class main {
    
    public static void main(String[] args) {
      try {
            UIManager.setLookAndFeel(new FlatLightLaf());
                  UIManager.put( "Button.arc", 20 );

        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        panelAdministracion panel = new panelAdministracion();
        panel.setVisible(true);
        
     
    }
    
}
