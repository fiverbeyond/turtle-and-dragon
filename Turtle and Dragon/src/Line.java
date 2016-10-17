/**
 * Line - Helper class that represents a line drawn in a color.
 */

import org.newdawn.slick.*;

public class Line {

    double start_x;
    double start_y;
    double end_x;
    double end_y;

    Color color;

    public Line(double s_x, double s_y, double e_x, double e_y){
        start_x = s_x;
        start_y = s_y;
        end_x = e_x;
        end_y = e_y;
        color = new Color(255,255,255);
    }

    public Line(double s_x, double s_y, double e_x, double e_y, Color setColor){
        start_x = s_x;
        start_y = s_y;
        end_x = e_x;
        end_y = e_y;
        color = setColor;
    }

}
