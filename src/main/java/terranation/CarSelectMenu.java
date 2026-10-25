package terranation;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

public class CarSelectMenu {
    private final Node guiNode;
    private final CarAttributes[] options = CarAttributes.allCars();
    private int selected =0;


    private BitmapText titleText;
    private BitmapText listText;
    private BitmapText statsText;


    public CarSelectMenu(AssetManager assestManager, Node guiNode, int screenWidth, int screenHeight) {
        this.guiNode = guiNode;
        BitmapFont guiFont = assestManager.loadFont("Interface/Fonts/Default.fnt");

        titleText = new BitmapText(guiFont);
        


        listText = new BitmapText(guiFont);


        statsText = new BitmapText(guiFont);

        guiNode.attachChild(titleText);
        guiNode.attachChild(listText);
        guiNode.attachChild(statsText);
        
        refresh();
    }



    public void moveSelection(int direction) {

    }

    public CarAttributes getSelectedCar() {
        //TODO: return the currently selected car attributes
    }

    public void hide() {
        // TODO: detaches all three texts from the guiNode
    }

    public void refresh() {
        //TODO: build one string for listed TEXT with a for loop over captions

        //TODO2: build the stats STring for options[selected] using the getters
    }
}
