import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.container.impl.bank.BankMode;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.MessageListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.api.utilities.Timer;

@ScriptManifest(author = "Xaklon", category = Category.MISC, description = "Mines and Banks at Varrock East", name = "Xak's Varrock East Miner", version = 1.1)
public class Main extends AbstractScript {

	public int State;
	public int rocksMined;
	GameObject bankBooth;
	GameObject chosenRock;
	public int rockID = 7484;
	Timer t;
	
	public boolean startScript = false;
	int tempSize;
	
	
	ArrayList<Integer> myID = new ArrayList<Integer>();
	int currId;
	

	public int getState() {
		return State;
	}
	public boolean getStartScript(){
		return startScript;
	}
	public void setStartScript(boolean startScript){
		this.startScript = startScript;
	}

	public void onStart() {
		log("pregui");
		VarGUI myGui = new VarGUI(this);
		myGui.setVisible(true);
		log("postgui");
		/*myID.add(7484);
		myID.add(7485);
		myID.add(7486);
		myID.add(7488);
		myID.add(7455);*/
		t = new Timer();
		getSkillTracker().start(Skill.MINING);

		State = 1;
	}

	@Override
	public int onLoop() {
		if(startScript){
		if (getState() == 1) {
			walkToMine();
		} else if (getState() == 2) {
			mine();
		} else if (getState() == 3) {
			walkToBank();
		} else if (getState() == 4) {
			bank();
		} else if (getState() == 1) {

		}
		}
		return 0;
		
	}
	

	public void walkToMine() {
		if(getInventory().isFull()){
			State = 3;
		}
		Tile mineTile = new Tile(3286, 3366);
		if (getWalking().getDestinationDistance() <= Calculations.random(3, 8)) {
			getWalking().walk(mineTile);
		}
		if (getLocalPlayer().distance(mineTile) <= 1) {

			sleep(Calculations.random(1100, 1500));
			State = 2;
		}

	}

	/*public int setChosenRock(){
		if(Calculations.random(1,3) == 1){
			rockID = 7484;
		}
		if(Calculations.random(1,3) == 2){
			rockID = 7485;
		}
		return rockID;
	}*/
	public void mine() {
		tempSize = getInventory().getEmptySlots();
		 currId = myID.get(Calculations.random(0,myID.size()));
		chosenRock = getGameObjects().closest(f -> f != null && f.getID() == currId);

		if (chosenRock != null) {
			getCamera().rotateToEntity(chosenRock);
			chosenRock.interact("Mine");
			
			sleepUntil(() -> getInventory().getEmptySlots() < tempSize || !chosenRock.exists(), 15000);
			rocksMined++;

		}
		if (getInventory().isFull()) {
			State = 3;
		}
	}

	public void walkToBank() {
		if (getWalking().getDestinationDistance() <= Calculations.random(3, 8)) {
			getWalking().walk(BankLocation.VARROCK_EAST.getCenter());
		}
		if (getLocalPlayer().distance(BankLocation.VARROCK_EAST.getCenter()) <= Calculations.random(3, 5)) {

			sleep(Calculations.random(1100, 1500));
			State = 4;
		}
	}

	public void bank() {
		bankBooth = getGameObjects().closest(7409);
		if (bankBooth != null) {
			
			bankBooth.interact("Bank");
			sleepUntil(() -> getBank().isOpen(), 15000);
			getBank().depositAllExcept(f -> f != null && f.getName().contains("pickaxe"));
			sleepUntil(() -> !getInventory().isFull(), 15000);
			getBank().close();
			sleepUntil(() -> !getBank().isOpen(), 15000);

		}
		if(getInventory().contains(f -> f != null && f.getName().contains("pickaxe")) || getInventory().isEmpty()){
			State = 1;
		}
		else{
			State = 4;
		}

	}

	public void onPaint(Graphics g) {
		if(startScript){
		Color myColor = new Color(0, 0, 0, 125);
		Color greenColor = new Color(0, 239, 80, 150);

		Font helvetica = new Font("Helvetica", Font.BOLD, 13);

		g.setColor(myColor);
		g.setFont(helvetica);
		g.fillRect(50, 40, 150, 180);

		g.setColor(greenColor);
		g.drawString("State is: " + State, 50, 50);
		g.drawString(t.formatTime(), 50, 80);
		g.drawString("XP/hr: " + getSkillTracker().getGainedExperiencePerHour(Skill.MINING), 50, 170);

		g.drawString("Rocks mined: " + rocksMined, 50, 110);
		g.drawString("Rocks/hr " + t.getHourlyRate(rocksMined), 50, 140);

		g.drawString("XP gained: " + getSkillTracker().getGainedExperience(Skill.MINING), 50, 200);
		}
	}

}
