package layout;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point2d;

import diagram.element.IDiagramElement;

import painter.IBounder;

public class LinearLayout extends AbstractLayout {

    @Override
    public List<Point2d> layout(
            IDiagramElement diagram, Point2d center, IBounder<IDiagramElement> bounder) {
        List<Point2d> points = new ArrayList<Point2d>();
        
        double totalDimension = 0;
        List<Rectangle2D> boundsList = new ArrayList<Rectangle2D>();
        for (IDiagramElement element : diagram.getChildren()) {
            Rectangle2D bounds = bounder.getBounds(element);
            boundsList.add(bounds);
            
            // for now, just add up the widths
            totalDimension += bounds.getWidth();
        }
        
        // a guard to catch cases of empty trees
        if (boundsList.size() == 0) return points;
        
        Rectangle2D prevBounds = boundsList.get(0);
        double prevX = (center.x - (totalDimension / 2)) + (prevBounds.getWidth() / 2);
        Point2d current = new Point2d(prevX, center.y);
        points.add(current);
        
        for (int i = 1; i < boundsList.size(); i++) {
            Rectangle2D currBounds = boundsList.get(i);
            double dx = (prevBounds.getWidth() / 2) + (currBounds.getWidth() / 2);
            Point2d p = new Point2d(prevX + dx, current.y);
            points.add(p);
            prevX = p.x;
            prevBounds = currBounds;
        }
        
        return points;
    }

}