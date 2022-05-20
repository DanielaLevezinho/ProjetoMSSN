package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import boids.Boid;
import boids.Eye;
import fractais.Rule;
import fractais.Tree;
import g4p_controls.GButton;
import g4p_controls.GEvent;
import g4p_controls.GLabel;
import physics.Body;
import physics.ParticleSystem;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.sound.SoundFile;
import setup.IProcessingApp;
import setup.PSControl;
import tools.SubPlot;

public class GameApp implements IProcessingApp {

	private double[] window = { -10, 10, -10, 10 };
	private float[] view = { 0, 0, 1, 1 };
	private SubPlot plt;
	public Player player;

	private Body tiroPlayer;
	public double thresholdX = -0.4f;
	public double thresholdY = -0.4f;

	public double thresholdPlayer = -0.9f;

	private boolean disparoPlayer;
	private List<Body> tirosPlayer;

	private NPC npcPatrol;
	private NPC npcAttack;
	private NPC npcParado;


	private List<Body> patrolTargets;

	public List<Boid> listPatrol;
	public List<Boid> listAttack;
	public List<Boid> listParado;

	public List<Body> tirosNPC;
	private Body tiroNPC;
	private boolean disparoNPC;

	private float resetShot;
	private float intervalBetweenShotPlayer;
	private float intervalBetweenShotNPC;

	private float nextShotPlayer;
	private float nextShotNPC;

	private boolean keyPress;
	private CheckGame checkGameState;

	private GLabel labelPoints;
	private GButton buttonPlayAgain;
	private PApplet p;

	private ArrayList<ParticleSystem> particleSystemsNPC;
	private List<Long> tempoParticulasNPC;
	private boolean checkDeadNPC;

	private float[] velParamsNPC = { PApplet.radians(360), PApplet.radians(360), 1.7f, 1.7f };
	private float[] lifetimeParamsNPC = { 0.5f, 0.5f };
	private float[] radiusParamsNPC = { 0.05f, 0.05f };

	private ArrayList<ParticleSystem> particleSystemsImpact;
	private List<Long> tempoParticulasImpact;

	private boolean checkImpactObject;
	private boolean checkImpactObjectPlayer;
	private boolean checkImpactObjectNPC;

	private float[] velParamsObjNPC = { PApplet.radians(90), PApplet.radians(-180), 1.7f, 1.7f };
	private float[] velParamsObjPlayer = { PApplet.radians(-90), PApplet.radians(180), 1.7f, 1.7f };
	private float[] lifetimeParamsObj = { 0.5f, 0.5f };
	private float[] radiusParamsObj = { 0.05f, 0.05f };

	private Obstacle objeto;

	private Tree tree;
	private boolean crescer = false;
	private ArrayList<Tree> trees;
	private int countLSystem;
	public List<Body> objectTargets;

	private int cooldownHit = 13;
	private int playerPoints = 0;

	private PImage spaceship, patrol, atack, statics;
	private float[] worldPosStatic;
	private float[] worldPosAttack;
	private float[] worldPosPatrol;
	private float[] worldPosPlayer;

	private SoundFile backgroundSound;
	private SoundFile tiroAffect;
	private SoundFile deadNPCAffect;
	private SoundFile deadPlayerAffect;

	private PImage backgroundImage;

	private boolean firstTime = true;

	private GUILife guiLife;
	private GLabel labelEndGame;

	@Override
	public void setup(PApplet p) {
		this.p = p;
		backgroundImage = p.loadImage("../background.jpg");
		this.trees = new ArrayList<Tree>();
		grow(p, new PVector());

		this.guiLife = new GUILife();
		this.checkGameState = new CheckGame();
		this.checkGameState.setStart(true);
		if (firstTime) {
			setupGUI();
			try {
				backgroundSound = new SoundFile(p, "./Space Heroes .mp3");
				tiroAffect = new SoundFile(p, "./tiro.wav");
				deadNPCAffect = new SoundFile(p, "/deadNPC.mp3");
				deadPlayerAffect = new SoundFile(p, "./deadPlayer.wav");

				backgroundSound.loop();

			} catch (Exception e) {
				System.out.println("Loading sound...");
			}
		}
		playerPoints = 0;
		labelPoints.setText("Player Points: " + String.valueOf(playerPoints));
		this.tempoParticulasNPC = new ArrayList<Long>();
		this.tempoParticulasImpact = new ArrayList<Long>();
		this.plt = new SubPlot(window, view, p.width, p.height);
		this.player = new Player(new PVector(0, -8), 1, (float) 0.8, 20, p, plt);

		this.listPatrol = new ArrayList<Boid>();
		this.listAttack = new ArrayList<Boid>();
		this.listParado = new ArrayList<Boid>();

		this.patrolTargets = new ArrayList<Body>();
		this.tirosPlayer = new ArrayList<Body>();

		patrolTargets.add(new Body(new PVector(-9, 5), new PVector(), 1, 1, 100));
		patrolTargets.add(new Body(new PVector(9, 3), new PVector(), 1, 1, 100));
		patrolTargets.add(new Body(new PVector(9, 0), new PVector(), 1, 1, 100));

		this.objeto = new Obstacle(new PVector(-9, 0), 1, 0.5f, 0, p, plt);
		this.objeto.setEye(new Eye(this.objeto, patrolTargets));
		this.objeto.getEye().setTarget(patrolTargets.get(2));
		this.objeto.addBehavior(new Patrol(1, 9, 0));

//		this.objeto = new Obstacle(new PVector(-9, 0), 10, 2, 0, p, plt);
//		this.objeto.setEye(new Eye(objeto, patrolTargets));
//		this.objeto.getEye().setTarget(patrolTargets.get(2));
//		this.objeto.addBehavior(new Patrol(1, 9, 0));

		for (int i = 0; i < 4; i++) {
			int n = (int) p.random(5, 6);
			if (i % 2 == 0) {
				this.npcPatrol = new NPC(new PVector(-9 + i * -n, 5), 1, 0.5f, 0, p, plt);
				npcPatrol.setEye(new Eye(npcPatrol, patrolTargets));
				npcPatrol.getEye().setTarget(patrolTargets.get(0));
				npcPatrol.addBehavior(new Patrol(1, 9, 5));
				this.listPatrol.add(npcPatrol);

				this.npcAttack = new NPC(new PVector(9 - i * -n, 3), 1, 0.5f, 0, p, plt);
				npcAttack.setEye(new Eye(npcAttack, patrolTargets));
				npcAttack.getEye().setTarget(patrolTargets.get(1));
				npcAttack.addBehavior(new Patrol(1, -9, 3));
				this.listAttack.add(npcAttack);
			} else {
				this.npcPatrol = new NPC(new PVector(-9 + i * n, 5), 1, 0.5f, 0, p, plt);
				npcPatrol.setEye(new Eye(npcPatrol, patrolTargets));
				npcPatrol.getEye().setTarget(patrolTargets.get(0));
				npcPatrol.addBehavior(new Patrol(1, 9, 5));
				this.listPatrol.add(npcPatrol);

				this.npcAttack = new NPC(new PVector(9 - i * n, 3), 1, 0.5f, 0, p, plt);
				npcAttack.setEye(new Eye(npcAttack, patrolTargets));
				npcAttack.getEye().setTarget(patrolTargets.get(1));
				npcAttack.addBehavior(new Patrol(1, 9, 3));
				this.listAttack.add(npcAttack);

			}

		}

		for (int i = 0; i < 6; i++) {
			this.npcParado = new NPC(new PVector(8 - i * 3, 7), 1, 0.5f, 0, p, plt);
			this.listParado.add(npcParado);
		}

		this.tirosNPC = new ArrayList<Body>();
		this.resetShot = p.millis() / 1000f;

		this.intervalBetweenShotNPC = 3;
		this.intervalBetweenShotPlayer = 1;

		this.nextShotPlayer = resetShot + intervalBetweenShotPlayer;
		this.nextShotNPC = resetShot + intervalBetweenShotNPC;

		this.particleSystemsNPC = new ArrayList<ParticleSystem>();
		this.particleSystemsImpact = new ArrayList<ParticleSystem>();

	}

	public void grow(PApplet p, PVector pos) {
		Rule[] rules = new Rule[1];
		rules[0] = new Rule('F', "F+F--F+F");
		float rand = p.random(0.20f, 0.30f);
		this.tree = new Tree("F--F--F", rules, pos, .4f, PApplet.radians(60f), 3, rand, 2f, 0, p);
		this.trees.add(this.tree);
	}

	public void setupGUI() {
		patrol = p.loadImage("../azul.png");
		statics = p.loadImage("../verde.png");
		atack = p.loadImage("../vermelho.png");
		spaceship = p.loadImage("../spaceship.png");

		buttonPlayAgain = new GButton(p, 130, 10, 100, 20, "Play Again!");
		buttonPlayAgain.setLocalColorScheme(p.color(128, 128, 121));

		labelEndGame = new GLabel(p, 10, 9, 1500, 20, "Player " + String.valueOf(checkGameState.getPlayerWon()));
		labelEndGame.setLocalColorScheme(p.color(128, 128, 121));
		labelEndGame.setVisible(false);

		labelPoints = new GLabel(p, 10, 9, 1500, 20, "Player Points: " + String.valueOf(playerPoints));
		labelPoints.setLocalColorScheme(p.color(128, 128, 121));
	}

	public void killNPC(Body tiroPlayer) {
		for (int i = 0; i < listPatrol.size(); i++) {
			if (tiroPlayer.getPos().x > (listPatrol.get(i).getPos().x + thresholdX)
					&& tiroPlayer.getPos().y > (listPatrol.get(i).getPos().y + thresholdY)
					&& tiroPlayer.getPos().x < (listPatrol.get(i).getPos().x - thresholdX)
					&& tiroPlayer.getPos().y < (listPatrol.get(i).getPos().y - thresholdY)) {
				PSControl psc = new PSControl(velParamsNPC, lifetimeParamsNPC, radiusParamsNPC, 500,
						p.color(0, 0, 255));
				ParticleSystem particleSystem = new ParticleSystem(listPatrol.get(i).getPos(), new PVector(0, 0), 0.5f,
						3, psc);
				tempoParticulasNPC.add(System.currentTimeMillis());
				particleSystemsNPC.add(particleSystem);
				listPatrol.remove(i);
				tirosPlayer.remove(tiroPlayer);
				playerPoints = playerPoints + npcPatrol.getPatrolPoints();
				labelPoints.setText("Player Points: " + String.valueOf(playerPoints));
				checkGameState.gameOver(p, this, player);
				deadNPCAffect.play();
				checkDeadNPC = true;
			}
		}

		for (int i = 0; i < listAttack.size(); i++) {
			if (tiroPlayer.getPos().x > (listAttack.get(i).getPos().x + thresholdX)
					&& tiroPlayer.getPos().y > (listAttack.get(i).getPos().y + thresholdY)
					&& tiroPlayer.getPos().x < (listAttack.get(i).getPos().x - thresholdX)
					&& tiroPlayer.getPos().y < (listAttack.get(i).getPos().y - thresholdY)) {
				PSControl psc = new PSControl(velParamsNPC, lifetimeParamsNPC, radiusParamsNPC, 500,
						p.color(255, 0, 0));
				ParticleSystem particleSystem = new ParticleSystem(listAttack.get(i).getPos(), new PVector(0, 0), 0.5f,
						3, psc);
				tempoParticulasNPC.add(System.currentTimeMillis());
				particleSystemsNPC.add(particleSystem);
				listAttack.remove(i);
				tirosPlayer.remove(tiroPlayer);
				playerPoints = playerPoints + npcAttack.getAtackerPoints();
				labelPoints.setText("Player Points: " + String.valueOf(playerPoints));
				checkGameState.gameOver(p, this, player);
				deadNPCAffect.play();
				checkDeadNPC = true;
			}
		}

		for (int i = 0; i < listParado.size(); i++) {
			if (tiroPlayer.getPos().x > (listParado.get(i).getPos().x + thresholdX)
					&& tiroPlayer.getPos().y > (listParado.get(i).getPos().y + thresholdY)
					&& tiroPlayer.getPos().x < (listParado.get(i).getPos().x - thresholdX)
					&& tiroPlayer.getPos().y < (listParado.get(i).getPos().y - thresholdY)) {
				PSControl psc = new PSControl(velParamsNPC, lifetimeParamsNPC, radiusParamsNPC, 500,
						p.color(0, 255, 0));
				ParticleSystem particleSystem = new ParticleSystem(listParado.get(i).getPos(), new PVector(0, 0), 0.5f,
						10, psc);
				tempoParticulasNPC.add(System.currentTimeMillis());
				particleSystemsNPC.add(particleSystem);
				listParado.remove(i);
				tirosPlayer.remove(tiroPlayer);
				playerPoints = playerPoints + npcParado.getStaticPoints();
				labelPoints.setText("Player Points: " + String.valueOf(playerPoints));
				checkGameState.gameOver(p, this, player);
				deadNPCAffect.play();
				checkDeadNPC = true;
			}
		}
	}

	public void killPlayer() {
		double[] threshold = plt.getWorldCoord(20, 100);
		double thresholdx = threshold[0] + 10;
		double[] thresholdpos = plt.getWorldCoord(40, 100);
		double thresholdxpos = thresholdpos[0] + 10;

		for (int i = 0; i < tirosNPC.size(); i++) {
			if (tirosNPC.get(i).getPos().x > (player.getPos().x - thresholdx) && tirosNPC.get(i).getPos().y > -10f
					&& tirosNPC.get(i).getPos().x < (player.getPos().x + thresholdxpos)
					&& tirosNPC.get(i).getPos().y < (player.getPos().y)) {
				cooldownHit -= 1;

				if (cooldownHit <= 0) {
					player.setHP(player.getHP() - 1);
					System.out.println("hp " + player.getHP());
					cooldownHit = 13;
					PSControl psc = new PSControl(velParamsObjNPC, lifetimeParamsObj, radiusParamsObj, 200,
							p.color(255, 0, 0));
					ParticleSystem particleSystemImpact = new ParticleSystem(tirosNPC.get(i).getPos(),
							new PVector(0, 0), 0.5f, 3, psc);
					tempoParticulasImpact.add(System.currentTimeMillis());
					particleSystemsImpact.add(particleSystemImpact);
					tirosNPC.remove(i);
					checkImpactObject = true;

				}
			}
			checkGameState.gameOver(p, this, player);
		}

	}

	public void impactPlayerObject(Body tiroPlayer) {
		if (checkImpactObjectPlayer) {
			if (tiroPlayer.getPos().x > (objeto.getPos().x + thresholdY)
					&& tiroPlayer.getPos().y > (objeto.getPos().y + thresholdY)
					&& tiroPlayer.getPos().x < (objeto.getPos().x + 2)
					&& tiroPlayer.getPos().y < (objeto.getPos().y - thresholdY)) {
				PSControl psc = new PSControl(velParamsObjPlayer, lifetimeParamsObj, radiusParamsObj, 200,
						p.color(255, 0, 0));
				ParticleSystem particleSystemImpact = new ParticleSystem(tiroPlayer.getPos(), new PVector(0, 0), 0.5f,
						3, psc);
				tempoParticulasImpact.add(System.currentTimeMillis());
				particleSystemsImpact.add(particleSystemImpact);
				tirosPlayer.remove(tiroPlayer);
				checkImpactObject = true;
				checkImpactObjectPlayer = false;
			}
		}
	}

	public void impactNPCObject() {
		if (checkImpactObjectNPC) {
			for (int i = 0; i < tirosNPC.size(); i++) {
				if (tirosNPC.get(i).getPos().x > (objeto.getPos().x + thresholdY)
						&& tirosNPC.get(i).getPos().y > (objeto.getPos().y + thresholdY)
						&& tirosNPC.get(i).getPos().x < (objeto.getPos().x + 2)
						&& tirosNPC.get(i).getPos().y < (objeto.getPos().y - thresholdY)) {

					PSControl psc = new PSControl(velParamsObjNPC, lifetimeParamsObj, radiusParamsObj, 200,
							p.color(255, 0, 0));
					ParticleSystem particleSystemImpact = new ParticleSystem(tirosNPC.get(i).getPos(),
							new PVector(0, 0), 0.5f, 3, psc);
					tempoParticulasImpact.add(System.currentTimeMillis());
					particleSystemsImpact.add(particleSystemImpact);
					tirosNPC.remove(i);
					checkImpactObject = true;
					checkImpactObjectNPC = false;
				}
			}
		}

	}

	public void restartGame(GButton button, GEvent event) {
		if (button == buttonPlayAgain && event == GEvent.CLICKED) {
			firstTime = false;
			this.setup(p);
			checkGameState.setStart(true);
			countLSystem = 0;
			cooldownHit = 10;
		}
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.background(backgroundImage);
		if (checkGameState.getGameOver() && !checkGameState.getStart()) {
			labelPoints.moveTo(365, 230);
			if (checkGameState.getPlayerWon()) {
				labelEndGame.setText("Player Won!");
			} else {
				labelEndGame.setText("Player Lost!");

			}
			labelEndGame.moveTo(365, 250);
			labelEndGame.setVisible(true);
			buttonPlayAgain.moveTo(360, 280);

		}

		if (checkGameState.getStart()) {
			buttonPlayAgain.setVisible(true);
			labelEndGame.setVisible(false);
			labelPoints.moveTo(10, 9);
			buttonPlayAgain.moveTo(130, 10);
			buttonPlayAgain.addEventHandler(this, "restartGame");

			resetShot += dt;

			this.guiLife.life(player, p);

			if (!crescer) {
				crescer = true;
				countLSystem++;
				grow(p, new PVector(p.random(-9, 9), p.random(-9, 9)));
			}

			for (int i = 0; i < trees.size(); i++) {
				if (!trees.get(i).getDead()) {
					trees.get(i).grow(dt);
					trees.get(i).display(p, plt);
					if (countLSystem < 30) {
						this.crescer = false;
					}
				} else {
					trees.removeAll(trees);
					countLSystem = 0;
					this.crescer = false;
				}

			}

//			float[] p1 = plt.getPixelCoord(player.getPos().x, player.getPos().y);
//			p.rect((float) (p1[0] - 20), p1[1], 60, 100);
			worldPosPlayer = plt.getPixelCoord(player.getPos().x - 0.5f, player.getPos().y + 0.7f);
			p.image(spaceship, worldPosPlayer[0], worldPosPlayer[1]);

			this.objeto.applyBehavior(0, dt);
			this.objeto.display(p, plt);

			for (int i = 0; i < listPatrol.size(); i++) {
				listPatrol.get(i).applyBehavior(0, dt);
				worldPosPatrol = plt.getPixelCoord(listPatrol.get(i).getPos().x - 0.5f,
						listPatrol.get(i).getPos().y + 0.7f);
				p.image(patrol, worldPosPatrol[0], worldPosPatrol[1]);
			}

			for (int i = 0; i < listAttack.size(); i++) {
				listAttack.get(i).applyBehavior(0, dt);
				worldPosAttack = plt.getPixelCoord(listAttack.get(i).getPos().x - 0.5f,
						listAttack.get(i).getPos().y + 0.7f);
				p.image(atack, worldPosAttack[0], worldPosAttack[1]);
			}

			for (int i = 0; i < listParado.size(); i++) {
				worldPosStatic = plt.getPixelCoord(listParado.get(i).getPos().x - 0.5f,
						listParado.get(i).getPos().y + 0.7f);
				p.image(statics, worldPosStatic[0], worldPosStatic[1]);
			}

			if (resetShot > nextShotPlayer) {
				keyPress = true;
				nextShotPlayer = resetShot + intervalBetweenShotPlayer;
			}

			if (disparoPlayer) {
				for (int i = 0; i < tirosPlayer.size(); i++) {
					tirosPlayer.get(i).move(dt);
					tirosPlayer.get(i).display(p, plt);
					killNPC(tirosPlayer.get(i));
					impactPlayerObject(tiroPlayer);
				}
			}

			if (checkDeadNPC) {
				for (int i = 0; i < particleSystemsNPC.size(); i++) {
					particleSystemsNPC.get(i).move(dt);
					particleSystemsNPC.get(i).display(p, plt);

//				}
//				for (int i = 0; i < tempoParticulasNPC.size(); i++) {
					if (System.currentTimeMillis() - tempoParticulasNPC.get(i) > 400f) {
						particleSystemsNPC.get(i).getPSControl().setFlow(0);
					}
				}
			}

			if (checkImpactObject) {
				for (int i = 0; i < particleSystemsImpact.size(); i++) {
					particleSystemsImpact.get(i).move(dt);
					particleSystemsImpact.get(i).display(p, plt);

//				}
//				for (int i = 0; i < tempoParticulasImpact.size(); i++) {
					if (System.currentTimeMillis() - tempoParticulasImpact.get(i) > 100f) {
						particleSystemsImpact.get(i).getPSControl().setFlow(0);
					}
				}
			}

			if (resetShot > nextShotNPC && listAttack.size() > 0) {
				int index1 = (int) p.random(0, listAttack.size());
				for (int i = 0; i < index1; i++) {
					tiroNPC = ((NPC) listAttack.get(i)).fire();
					tirosNPC.add(tiroNPC);
					disparoNPC = true;
					checkImpactObjectNPC = true;
					nextShotNPC = resetShot + intervalBetweenShotNPC;
				}
				if (index1 == 0) {
					disparoNPC = false;
				}
			}

			if (disparoNPC) {
				for (int i = 0; i < tirosNPC.size(); i++) {
					tirosNPC.get(i).move(dt);
					tirosNPC.get(i).display(p, plt);
					impactNPCObject();

				}
				killPlayer();
			}

			if (player.getHP() == 0) {
				deadPlayerAffect.play();
			}

		}

	}

	@Override
	public void mousePressed(PApplet p) {

	}

	@Override
	public void keyPressed(PApplet p) {
		if (p.key == 'a' && player.getPos().x > -9) {
			player.setPos(new PVector(-1, 0).add(player.getPos()));
		}
		if (p.key == 'd' && player.getPos().x < 9) {
			player.setPos(new PVector(1, 0).add(player.getPos()));
		}
		if (p.key == 'w' && keyPress) {
			tiroPlayer = player.fire();
			tirosPlayer.add(tiroPlayer);
			tiroAffect.play();
			disparoPlayer = true;
			checkImpactObjectPlayer = true;
			keyPress = false;
		}

	}

}
