/**
 * To run the Turtle Graphics project, run this class instead of Instructions.java!
 *
 * Launcher - Helper class that provides graphics and starts the program
 * moving.
 *
 */

import org.newdawn.slick.*;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Turtle extends BasicGame
{


    Color[] colorProgression = {Color.blue, Color.gray, Color.cyan, Color.white};

    // These variables must be set in init.
    static Image backGround;
    static Image turtle;
    Lock lineLock = new ReentrantLock();

    // Set the game window dimensions.
    static int window_height = 1000;
    static int window_length = 1000;

    float turtle_x = 200;
    float turtle_y = 200;

    int goalColorIndex = 0;
    Color currentColor = new Color(255,255,255);
    Color goalColor = colorProgression[goalColorIndex];

    ArrayList<Line> lines = new ArrayList<Line>();

    boolean isDrawing = true;

    static Turtle instance;


    /**
     * Clears all lines currently drawn on the screen.
    */
    public void clearLines(){
        lineLock.lock();
        lines.clear();
        lineLock.unlock();
    }// end clearLines

    /**
     * Turns the turtle object clockwise (right) by the given angle.
     *
     * @param angle the angle in degrees that the turtle should be turned
     */
    public void rotate(double angle){
        turtle.rotate((float) angle);
    }

    /**
     * Moves the turtle straight by the given number of pixels.
     *
     * @param distance - the number of pixels the turtle should move forward.
     */
    public void move(double distance){

        double rangle = Math.toRadians(turtle.getRotation());

        float x_addition = (float) (Math.cos(rangle) * distance);
        float y_addition = (float) (Math.sin(rangle) * distance);

        if(isDrawing){

            lineLock.lock();
            lines.add(new Line(turtle_x, turtle_y, (turtle_x + x_addition), (turtle_y + y_addition), new Color(currentColor)));
            lineLock.unlock();
        }

        turtle_x += x_addition;
        turtle_y += y_addition;
    }



    /**
     *
     *  Tells the turtle to "put down the pen" and start drawing as it moves.
     */
    public void startDrawing(){
        isDrawing = true;
    }



    /**
     * Tells the turtle to "pick up the pen" and move without drawing a line.
     */
    public void stopDrawing(){
        isDrawing = false;
    }

    // setColor: Sets the color of the line that the turtle will draw.

    /**
     * Sets the current color of the turtle's drawing line,
     * based on RGB values.
     *
     * @param red  red value (0 - 255)
     * @param green green value (0 - 255)
     * @param blue blue value (0 - 255)
     */
    public void setColor(int red, int green, int blue){
        currentColor = new Color(red, green, blue);
    }

    /**
     * Sets the color of the turtle's drawing line.
     * @param toSet the color for the turtle to draw
     */
    public void setColor(Color toSet){
        currentColor = new Color(toSet);
    }


    /**
     * prints the turtle's current position and rotation to the console.
     * Used for debugging and educational purposes.
     */
    public void shout(){

        System.out.println("Rotation: " + turtle.getRotation());
        System.out.println("X: " + turtle_x + " Y: " + turtle_y + "\n");
    }

    /**
     * Returns the point where the turtle currently resides.
     *
     * @return a point object representing the current location of the turtle on screen
     */
    public Point getLocation(){
        return new Point(turtle_x, turtle_y);
    }


    /**
     * Teleports the turtle to a given coordinate without drawing.
     * @param x x coordinate to teleport to
     * @param y y coordinate to teleport to
     */
    public void moveTo(float x, float y){

        if(isDrawing){
            lineLock.lock();
            lines.add(new Line(turtle_x, turtle_y, x, y, new Color(currentColor)));
            lineLock.unlock();
        }
        turtle_x = x;
        turtle_y = y;

    }

    /**
     * Teleports the turtle to a given coordinate without drawing.
     * @param point the point to teleport to.
     */
    public void moveTo(Point point){
        moveTo( (float) point.x, (float) point.y);
    }


    /**
     *
     * Calculates the distance between two points.
     *
     * @param x1 x coordinate of the first point
     * @param y1 y coordinate of the first point
     * @param x2 x coordinate of the second point
     * @param y2 y coordinate of the second point
     * @return the distance, in pixels, between the two points
     */
    public double distanceBetweenPoints(float x1, float y1, float x2, float y2){
        double distance = Math.sqrt( Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
        return distance;
    }

    /**
     * Calculates the distance between two points
     *
     * @param p1 the first point
     * @param p2 the second point
     * @return the distance, in pixels, between the two points
     */
    public double distanceBetweenPoints(Point p1, Point p2){
        return distanceBetweenPoints((float) p1.x, (float) p1.y, (float) p2.x, (float) p2.y);
    }


    /**
     * Rotates the turtle until it is facing the given target point
     *
     * @param x x coordinate of the target point
     * @param y y coordinate of the target point
     */
    public void turnTowardPoint(float x, float y){
        float x_diff = x - turtle_x;
        float y_diff = y - turtle_y;

        float angle = (float) Math.toDegrees(Math.atan2(y_diff, x_diff));

        turtle.setRotation(angle);

    }

    /**
     * Rotates the turtle until it is facing the given target point
     * @param p Target point to turn towards
     */
    public void turnTowardPoint(Point p){
        turnTowardPoint((float) p.x, (float) p.y);
    }


    /**
     * Returns the amount of rotation (from the right-facing 0 degree) direction
     * that the turtle is currently facing
     * A rotation of 0.0 indicates the turtle is pointing directly to the right.
     *
     * @return the number of degrees clockwise (from right-facing) that the turtle is facing
     */
    public float getRotation(){
        return turtle.getRotation();
    }


    /**
     * Sets the rotation to the given value.
     * A rotation of 0.0 indicates the turtle is pointing directly to the right.
     * @param angle
     */
    public void setRotation(float angle){
        turtle.setRotation(angle);
    }



    /**
     * Prints coordinates to the console when the mouse is clicked.
     *
     * @param button unused in this overridden method
     * @param x x coordinate of the mouse
     * @param y y coordinate of the mouse
     * @param clickCount unused in this overridden method
     */
    @Override
    public void mouseClicked(int button, int x, int y, int clickCount){
        System.out.println("Mouse clicked at: x = " + x + ", y = " + y);
    }


    // update: Required to be there in a graphical program, but blank here

    /**
     * Slick2D overidden function. Runs once per frame, before rendering.
     *
     * @param gc
     * @param delta
     * @throws SlickException
     */
    @Override
    public void update(GameContainer gc, int delta) throws SlickException {

    }

    /**
     * Basic constructor.
     * @param gamename title to be shown on the Slick2D window.
     */
    public Turtle(String gamename)
    {
        super(gamename);
    }

    /**
     * Singleton handle.
     * @return the instance of this singleton class.
     */
    public static Turtle getInstance(){
        return instance;
    }

    /**
     * Changes the color to the next color in the colorProgression sequence
     */
    public void progressFullColor(){
        currentColor = goalColor;
        goalColorIndex = (goalColorIndex == colorProgression.length - 1) ? 0 : goalColorIndex + 1;
        goalColor = colorProgression[goalColorIndex];
    }

    /**
     * Changes the color "towards" the next color in the colorProgression sequence
     * by the given amount.
     *
     * The progression variable represents the difference in RGB values between
     * the start color and end color.
     *
     * For example, if the current color is black (0, 0, 0),
     * and the next color in the progression is bright red (200, 100, 0),
     * and this function is called with the value 900,
     * the resulting color will be (60, 30, 0).
     *
     * @param progression the delta of RGB colors to progress
     */
    public void progressColor(int progression){
        //Find which color in our array is closest to Current Color
        // Find out which RBG

        int redDiff = goalColor.getRed() - currentColor.getRed();
        int blueDiff = goalColor.getBlue() - currentColor.getBlue();
        int greenDiff = goalColor.getGreen() - currentColor.getGreen();

        //System.out.println("Color diff: " + redDiff + " " + greenDiff + " " + blueDiff + " ");

        int redDelta = 0;
        int blueDelta = 0;
        int greenDelta = 0;

        if ((Math.abs(redDiff) >= Math.abs(blueDiff)) && (Math.abs(redDiff) >= Math.abs(greenDiff))){
            //System.out.println("Red progressed");
            redDelta = (redDiff < 0) ? -1 * progression : progression;
        }
        else if ((Math.abs(blueDiff) >= Math.abs(redDiff)) && (Math.abs(blueDiff) >= Math.abs(greenDiff))){
            //System.out.println("Blue progressed");
            blueDelta = (blueDiff < 0) ? -1 * progression : progression;
        }

        else if ((Math.abs(greenDiff) >= Math.abs(blueDiff)) && (Math.abs(greenDiff) >= Math.abs(redDiff))){
            //System.out.println("Green progressed");
            greenDelta = (greenDiff < 0) ? -1 * progression : progression;
        }

        currentColor = new Color(currentColor.getRed() + redDelta,
                                 currentColor.getGreen() + greenDelta,
                                 currentColor.getBlue() + blueDelta);

        if (Math.abs(redDiff) <= progression && Math.abs(blueDiff) <= progression && Math.abs(greenDiff) <= progression){

            goalColorIndex = (goalColorIndex == colorProgression.length - 1) ? 0 : goalColorIndex + 1;
            goalColor = colorProgression[goalColorIndex];
           // System.out.println("New goal color selected: " + goalColor.getRed() + " "
           //                     + goalColor.getGreen() + " " + goalColor.getBlue());
        }

    }

    /**
     * Main function.
     * @param args
     */
    public static void main(String[] args)
    {
        System.out.println("It's turtle time!");

        try
        {

            AppGameContainer appgc;
            instance = new Turtle("Go, turtle, go!");
            appgc = new AppGameContainer(instance);
            appgc.setDisplayMode(window_length, window_height, false);
            appgc.setShowFPS(false);
            appgc.start();
        }
        catch (SlickException ex)
        {
            Logger.getLogger(Instructions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    // init: Starts the graphical program by loading images
    @Override
    public void init(GameContainer gc) throws SlickException {
        backGround = new Image("assets/Farm.png");
        turtle = new Image("assets/Turtle.png");

        Thread pulse = new Thread(new Instructions());
        pulse.start();
    }

    /**
     * Overridden Slick2D method. Renders the current scene onto the window.
     * @param gc
     * @param g
     * @throws SlickException
     */
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        // backGround.draw(0,0);
        // Subtract 50 from each coordinate to draw the turtle
        // such that the line is coming from the center of the turtle.
        turtle.draw(turtle_x - 15,turtle_y - 15);
        lineLock.lock();

        for (Line line : lines){
            g.setColor(line.color);
            g.drawLine((float) line.start_x, (float)line.start_y, (float)line.end_x, (float)line.end_y);
        }
        lineLock.unlock();

    }


}