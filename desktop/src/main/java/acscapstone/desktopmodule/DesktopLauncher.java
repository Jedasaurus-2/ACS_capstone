package acscapstone.desktopmodule;

import acscapstone.game.ACScapstone;
import com.jme3.system.AppSettings;

public class DesktopLauncher {

    public static void main(String[] args) {
        ACScapstone game = new ACScapstone();
        AppSettings appSettings = new AppSettings(true);
        appSettings.setTitle("Yo Big T");
        appSettings.setResizable(true);
        appSettings.setResolution(600, 600);
        
        game.setSettings(appSettings);
        game.start();
    }
}
