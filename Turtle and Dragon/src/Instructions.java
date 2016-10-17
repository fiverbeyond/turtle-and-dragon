/**
 *Turtle Graphics Instruction Container
 *
 */

import org.newdawn.slick.*;

import java.util.ArrayList;

public class Instructions implements Runnable{


    public final int numDragonIterations = 13; // Used by dragon fractal

    /**
     * Overridden method, required by runnable object.
     * Creates a Turtle object and gives it the instructions it should follow to draw its image.
     */
    public void run(){

        // This is your turtle, Sam!
        // Give Sam instructions to make him draw!
        Turtle turtle = Turtle.getInstance();

        dragonFractal(turtle);

    }// end run


    /**
     * Draws the dragon fractal, over several iterations.
     * @param turtle the turtle who should draw the dragon fractal
     */
    public void dragonFractal(Turtle turtle){

        ArrayList<Point> points = new ArrayList<>();

        // Set starting point
        points.add(new Point(200, 250));
        points.add(new Point(800, 250));

        for (int i = 0; i < numDragonIterations; i++){
            turtle.clearLines();
            turtleIteration(turtle, points);
            pause(1);
        }

    } // end dragonFractal


    /**
     * Draws a single iteration of the Dragon Fractal.
     * Iterates over every point in the given collection,
     * adding a new point between each pair of points, according to the rules of the Dragon Fractal
     *
     * @param turtle the turtle currently drawing the fractal
     * @param points the current fractal
     */
    public void turtleIteration(Turtle turtle, ArrayList<Point> points){


        if (points.isEmpty()){
            System.err.println("Turtle iteration called on an empty array of points!");
            return;
        }

        turtle.stopDrawing();
        turtle.moveTo(points.get(0));
        turtle.startDrawing();

        boolean rightFirst = true;

        double pauseAmount = Math.max(0.01, 2.0 / points.size());

        for(int i = 0; i < points.size() - 1; i++){

            Point currentPoint = points.get(i);
            Point nextPoint = points.get(i+1);

            turtle.turnTowardPoint(nextPoint);
            double dist = turtle.distanceBetweenPoints(currentPoint, nextPoint);
            double legDist = dist / Math.sqrt(2);


            turtle.rotate( rightFirst ? 45 : -45);
            turtle.move(legDist);
            pause(pauseAmount);
            turtle.progressColor(1);
            points.add(i + 1, turtle.getLocation());

            i++;
            turtle.rotate(rightFirst ? -90 : 90);
            turtle.move(legDist);
            pause(pauseAmount);
            turtle.progressColor(1);
            turtle.rotate(rightFirst ? -45 : 45);

            rightFirst = ! rightFirst;

        }

    } // end turtleIteration


    /**
     * Helper function to pause for a given number of seconds.
     *
     * @param seconds number of seconds to pause
     */
    public static void pause(double seconds){
        try{
            Thread.sleep((int)(seconds * 1000));
        }
        catch (Exception e){
            System.out.println("TSK Caught exception: " + e);
        }
    }// end pause





    /* SOLUTIONS:

    CHECK MARK:
        sam.rotate(45);
        sam.move(50);
        sam.rotate(-90);
        sam.move(100);

    STAR:
        for(int i = 0; i < 5; i++){
            sam.rotate(144);
            sam.move(100);
        }


    STAR PROJECTION:
       for(int i = 0; i < 300; i++){
            sam.rotate(144.5);
            sam.move(300);
        }

    SPIRAL:
        double moveLength = 0.01;
        for(int i = 0; i < 1300; i++){
            sam.rotate(1);
            sam.move(moveLength);
            moveLength += 0.002;
            pause(0.01);
        }

   SQUARE SPIRAL:
   for(int i = 0; i < 100; i++){
            sam.rotate(90);
            sam.move(moveLength);
            moveLength += 4;
            pause(0.1);
        }


   DOTTED LINE SQUARE SPIRAL:
           double moveLength = 1;

        for(int i = 0; i < 60; i++){
            sam.rotate(90);


            for (int j = 0; j < moveLength / 2; j++){

                if (j % 2 == 0) {
                    sam.startDrawing();
                }
                else {
                    sam.stopDrawing();
                }
                sam.move(2);
            }

            moveLength += 6;
            pause(0.1);
        }


    COLOR-CHANGING SQUARE SPIRAL:
        int red = 0;
        int blue = 0;

        for(int i = 0; i < 100; i++){
            sam.rotate(90);
            sam.move(moveLength);
            if (red <= 255) {
                red += 3;

            }
            else if (blue <= 255){
                blue += 3;
            }

            sam.setColor(red, blue, 200);
            moveLength += 4;
            pause(0.1);
        }

    SNOWFLAKE:
        for(int i=0; i<6; i++) {
            for(int j=0; j<5; j++) {
                sam.move(10);
                sam.rotate(60);
            }
            sam.move(10);
        }

    REGULAR POLYGON:
        Scanner input = new Scanner(System.in);
        System.out.println("How many sides do you want?");
        int sides = input.nextInt();
        for(int i=0; i<sides; i++) {
            sam.move(50);
            sam.rotate(360/sides);
        }


*/



}
