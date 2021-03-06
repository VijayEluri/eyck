package divide;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class GridDivider extends AbstractDivider implements RegularDivider {
    
    private int rows;
    
    private int cols;
    
    private int findLargestFactor(int n) {
        for (int i = (int)Math.sqrt(n); i >= 2; i--) {
            if (n % i == 0) {
                return i;
            }
        }
        // n is prime
        return 1;
    }
    
    // XXX commented out because the constructor is meant to be independent of the part count
//    public GridDivider(int rows, int cols) {
//        this.rows = rows;
//        this.cols = cols;
//    }
    
    public int getRowCount() {
        return rows;
    }
    
    public int getColCount() {
        return cols;
    }

    @Override
    public List<Rectangle2D> divide(Rectangle2D canvas, int parts) {
    	rows = findLargestFactor(parts);
        cols = parts / rows;
        
        double colWidth = canvas.getWidth() / cols;
        double rowHeight = canvas.getHeight() / rows;
        
        
        List<Rectangle2D> subCanvases = new ArrayList<Rectangle2D>();
        double y = canvas.getMinY();
        for (int row = 0; row < rows; row++) {
            double x = canvas.getMinX();
            for (int col = 0; col < cols; col++) {
                subCanvases.add(new Rectangle2D.Double(x, y, colWidth, rowHeight));
                x += colWidth;
            }
            y += rowHeight;
        }
        return subCanvases;
    }

}
