package awt;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.vecmath.Point2d;

import layout.ILayout;
import painter.ICompositePainter;
import diagram.DiagramBounder;
import diagram.element.ElementList;
import diagram.element.IDiagramElement;
import divide.IrregularDivider;
import divide.RegularDivider;

public class ListAWTPainter implements ICompositePainter<List<IDiagramElement>> {
	  
    private BasicAWTPainter subPainter;
    
    private DiagramBounder bounder;
    
    public ListAWTPainter(Graphics g) {
    	this(g, true);
    }
    
    public ListAWTPainter(Graphics g, boolean transformSubPainter) {
        subPainter = new BasicAWTPainter(g, transformSubPainter);
        bounder = new DiagramBounder(g);
    }

	@Override
	public void paint(List<IDiagramElement> diagramList, Rectangle2D canvas, RegularDivider divider) {
		List<Rectangle2D> canvases = divider.divide(canvas, diagramList.size());
		int i = 0;
		for (IDiagramElement diagram : diagramList) {
			subPainter.paint(diagram, canvases.get(i));
			i++;
		}
	}

	@Override
	public void paint(List<IDiagramElement> diagramList, Point2d center, ILayout layout) {
		// XXX FIXME
		ElementList compositeDiagram = new ElementList();
		for (IDiagramElement diagram : diagramList) {
			compositeDiagram.add(diagram);
		}
		
		List<Point2d> centers = layout.layout(compositeDiagram, center, bounder);
        int i = 0;
        for (IDiagramElement diagram : diagramList) {
            subPainter.paint(diagram, centers.get(i));
            i++;
        }
		
	}

	@Override
	public void paint(List<IDiagramElement> diagramList, Rectangle2D canvas, IrregularDivider divider) {
		List<Rectangle2D> canvases = divider.divide(canvas);
		int i = 0;
		for (IDiagramElement diagram : diagramList) {
			subPainter.paint(diagram, canvases.get(i));
			i++;
		}
	}

}
