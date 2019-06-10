import ChainChomp.Animation;
import acm.breadboards.AbstractBreadboard;
import acm.graphics.GCanvas;
import acm.graphics.GImage;
import com.sun.net.httpserver.Filter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ChainChompChancetoShine extends AbstractBreadboard implements KeyListener {
    private GImage couch;
    private static final String COUCH="src/ChainChomp/couch.png";
    private ChainChomp.Animation lava;
    private GImage ChainChomp;
    private static final String CHAINBOI = "src/ChainChomp/ChainChomp.png";
    private GImage livingRoom;
    private static final String LIVINGROOM = "src/ChainChomp/EmptyLivingRoom1.png";


    private int dx = 0;
    private int dy = 0;

    private static final double GRAVITY = 9.8;
    private static final double TERMINAL_VELOCITY = 350;



    public void fall()
    {

    }



    public void betterMove() {
        if (dx == -1) {
         ChainChomp.move(-20,0);
        }
        else if (dx == 1) {
            ChainChomp.move(20,0);
        }
        else if (dy == -1) {
            ChainChomp.move(0,20);
        }
        else if (dy == 1) {
            ChainChomp.move(0,-20);
        }
        //this.updateElements();

    }

    public void run() {

        livingRoom = new GImage(LIVINGROOM);
        livingRoom.scale(1.3,1.3);
        this.add(livingRoom);


        //The Floor is Lava
        lava = new ChainChomp.Animation("src/ChainChomp/LavaGif", Animation.LOOPS);
        lava.setLocation(400,700);
        this.add(lava);
        this.setSize(800,800);


        couch = new GImage(COUCH);
        couch.scale(.5,.5);
        couch.setLocation(0, 550);
        this.add(couch);


        ChainChomp = new GImage(CHAINBOI);
        ChainChomp.scale(.3,.3);
        ChainChomp.setLocation(25, 530);
        this.add(ChainChomp);

        if(ChainChomp.getBounds().intersects(lava.getBounds())) {
            System.out.print("Ryan is a jerk.");
        }






        this.getGCanvas().addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {}

                    @Override
                    public void keyPressed(KeyEvent e) {
                        int key = e.getKeyCode();
                        dx = 0;
                        dy = 0;
                        if (key == KeyEvent.VK_LEFT) {
                            dx = -1;
                        }
                        else if (key == KeyEvent.VK_RIGHT) {
                            dx = 1;
                        }
                        else if (key == KeyEvent.VK_UP) {
                            dy = 1;
                        }
                        else if (key == KeyEvent.VK_DOWN) {
                            dy = -1;
                        }
                        betterMove();

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {}
                }


        );














/*
        switch (keyMovement) {
            case "dog":
                myPet = new Dog(topTextbox);
                myPet.appendText("What would you like to name your dog?");
                //myPet = new Dog();
                this.add(myPet);
                break;
            case "parrot":
                myPet = new Parrot(topTextbox);
                myPet.appendText("What would you like to name your parrot?");
                this.add(myPet);
                break;
            case "Stitch":
                myPet = new Stitch(topTextbox);
                myPet.appendText("What would you like to name your Stitch?");
                this.add(myPet);
                break;
            default:
                myPet = new Snail(topTextbox);
                myPet.appendText("What would you like to name your snail?");
                this.add(myPet);
                break;
        }

*/





    }

}
