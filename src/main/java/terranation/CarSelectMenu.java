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
        

    }
    
}
