package terranation;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

public class HudManager {
    private BitmapText hudText;

    public HudManager(AssetManager assetManager, Node guiNode) {
        BitmapFont  guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        hudText= new BitmapText(guiFont);
        hudText.setSize(guiFont.getCharSet().getRenderedSize() * 1.5f);
        hudText.setColor(ColorRGBA.White);
        hudText.setLocalTranslation(50f,50f,0f);
        hudText.setText("Speed: 0 Mph | Gear: 1 | RPM: 0");

        guiNode.attachChild(hudText);
    }
    public void update(float speed, int gear, float rpm) {
        int currentSpeed = (int) Math.abs(speed);
        String gearDisplay = (gear == 0) ? "R" : String.valueOf(gear);
        hudText.setText("Speed: " + currentSpeed + " Mph | Gear: " + gearDisplay + " | RPM: " + (int) rpm);
    }
}
