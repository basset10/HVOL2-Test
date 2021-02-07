import static com.osreboot.ridhvl2.HvlStatics.hvlDraw;
import static com.osreboot.ridhvl2.HvlStatics.hvlFont;
import static com.osreboot.ridhvl2.HvlStatics.hvlLoad;
import static com.osreboot.ridhvl2.HvlStatics.hvlQuadc;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.hvol2.base.HvlGameInfo;
import com.osreboot.hvol2.base.anarchy.HvlAgentServerAnarchy;
import com.osreboot.hvol2.base.anarchy.HvlIdentityAnarchy;
import com.osreboot.hvol2.base.route.HvlRouteRelayFree;
import com.osreboot.hvol2.direct.HvlDirect;
import com.osreboot.ridhvl2.HvlCoord;
import com.osreboot.ridhvl2.template.HvlChronology;
import com.osreboot.ridhvl2.template.HvlDisplayWindowed;
import com.osreboot.ridhvl2.template.HvlTemplateI;

public class MainServer extends HvlTemplateI{

	//Holds information representing the current game and version, must match on client and server.
	public static final HvlGameInfo GAME_INFO = new HvlGameInfo("hvol_test", "1.0");
	
	//public static ArrayList<String> connectedUsers;

	public static void main(String[] args) {
		HvlChronology.registerChronology(HvlDirect.class);
		new MainServer();
	}

	public MainServer() {
		super(new HvlDisplayWindowed(144, 500, 500, "HVOL2 Test Server", true));
	}

	@Override
	public void initialize() {
		hvlLoad("INOF.hvlft");
		HvlDirect.initialize(32, new HvlAgentServerAnarchy(GAME_INFO, 25565));
		HvlDirect.getRouter().add(new HvlRouteRelayFree<>("client.currentposition","client.otherposition"));
		HvlDirect.connect();

		HvlDirect.bindOnMessageReceived((m, i) -> {
			return m;
		});

		HvlDirect.bindOnRemoteConnection(i -> {
			System.out.println("Connection - " + i);
		});

		HvlDirect.bindOnRemoteDisconnection(i ->  {
			System.out.println("Disconnection - " + i);
		});

	}
	
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

	@Override
	public void update(float delta) {
			
		HvlDirect.update(delta);
		hvlFont(0).drawc("Yo I'm the Server", Display.getWidth()/2, 200, Color.white, 0.17f);

		hvlFont(0).drawc(HvlDirect.getConnections().size()+"", Display.getWidth()/2, 100, Color.white, 0.12f);

		for(HvlIdentityAnarchy i : HvlDirect.<HvlIdentityAnarchy>getConnections()) {
			
			HvlDirect.writeTCP(i, "connectedusers", new ArrayList<String>(HvlDirect.<HvlIdentityAnarchy>getConnections().stream().map(i2 -> ((HvlIdentityAnarchy)i2).getName()).collect(Collectors.toList())));
			String s = HvlDirect.getValue(i, "myusername.messagetosend") + "";	
			hvlFont(0).drawc(s, randomIntBetween(0,500), randomIntBetween(0,500), Color.white, 0.17f);


			if(HvlDirect.getKeys(i).contains("client.currentposition."+i.getName())) {
				HvlCoord playerPos = HvlDirect.getValue(i, "client.currentposition."+i.getName());
				hvlFont(0).drawc(i.getName(), playerPos.x, playerPos.y-15, Color.white, 0.1f);
				hvlDraw(hvlQuadc(playerPos.x, playerPos.y, 20f, 20f),Color.white);
			}


		}

		//HvlIdentityAnarchy identityOfClient = new 
		//String s = HvlDirect.getValue(
	}

}
