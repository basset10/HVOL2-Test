import static com.osreboot.ridhvl2.HvlStatics.hvlDraw;
import static com.osreboot.ridhvl2.HvlStatics.hvlFont;
import static com.osreboot.ridhvl2.HvlStatics.hvlLoad;
import static com.osreboot.ridhvl2.HvlStatics.hvlQuadc;
import static com.osreboot.ridhvl2.HvlStatics.hvlTexture;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.hvol2.base.HvlGameInfo;
import com.osreboot.hvol2.base.anarchy.HvlAgentClientAnarchy;
import com.osreboot.hvol2.base.anarchy.HvlAgentServerAnarchy;
import com.osreboot.hvol2.direct.HvlDirect;
import com.osreboot.ridhvl2.HvlCoord;
import com.osreboot.ridhvl2.painter.HvlQuad;
import com.osreboot.ridhvl2.template.HvlChronology;
import com.osreboot.ridhvl2.template.HvlDisplay;
import com.osreboot.ridhvl2.template.HvlDisplayWindowed;
import com.osreboot.ridhvl2.template.HvlTemplateI;

public class MainClient extends HvlTemplateI{

	//Holds information representing the current game and version, must match on client and server.
	public static final HvlGameInfo GAME_INFO = new HvlGameInfo("hvol_test", "1.0");
	public static final String USERNAME = "bassket10";
	
	
	private static Random rng = new Random();

	public static Random getRngValue() {
		return rng;
	}

	// Returns random int between min and max, inclusive. Source: ridhvl1 (os_reboot)
	public static int randomIntBetween(int min, int max) {
		max++;
		if (max > min)
			return min + rng.nextInt(max - min);
		if (max < min)
			return max + rng.nextInt(min - max);
		return min;
	}
	

	public static void main(String[] args) {
		HvlChronology.registerChronology(HvlDirect.class);
		new MainClient();
	}

	public HvlCoord playerPos;

	public MainClient() {
		super(new HvlDisplayWindowed(144, 500, 500, "HVOL2 Test Client", true));
	}

	@Override
	public void initialize() {
		hvlLoad("INOF.hvlft");  
		HvlDirect.initialize(32, new HvlAgentClientAnarchy(GAME_INFO, "localhost", 25565, USERNAME));
		HvlDirect.connect();
		HvlDirect.writeTCP("myusername.messagetosend", "it's ur boi");
		playerPos = new HvlCoord(100, 100);
	}

	@Override
	public void update(float delta) {
		HvlDirect.update(delta);
		hvlFont(0).drawc("Yo I'm the Client", Display.getWidth()/2, 200, Color.white, 0.17f);
		hvlFont(0).drawc(HvlDirect.getStatus()+"", Display.getWidth()/2, 100, Color.white, 0.13f);

		hvlDraw(hvlQuadc(playerPos.x, playerPos.y, 20f, 20f),Color.white);

		if(Keyboard.isKeyDown(Keyboard.KEY_W)) playerPos.y -= delta * 100;
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) playerPos.x -= delta * 100;
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) playerPos.y += delta * 100;
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) playerPos.x += delta * 100;

		//TCP = slower, more reliable, better for crucial information
		//UDP = faster, less reliable, can arrive out of order. Better for playerPos

		HvlDirect.writeUDP("client.currentposition."+USERNAME, playerPos);

		ArrayList<String> connectedUsers = new ArrayList<String>();
		
		if(HvlDirect.getKeys().contains("connectedusers"))
		connectedUsers.addAll(HvlDirect.getValue("connectedusers"));

		for(String key : HvlDirect.getKeys()) {
			if(key.startsWith("client.otherposition")) {
				HvlCoord otherPlayerLocation = HvlDirect.getValue(key);
				String otherName = key.replace("client.otherposition.", "");
				if(!otherName.equals(USERNAME) && connectedUsers.contains(otherName)) {			
					hvlDraw(hvlQuadc(otherPlayerLocation.x, otherPlayerLocation.y, 20, 20),Color.white);
					hvlFont(0).drawc(otherName, otherPlayerLocation.x, otherPlayerLocation.y-15, Color.white, 0.1f);
				}		
			}		
		}		
	}		
}
