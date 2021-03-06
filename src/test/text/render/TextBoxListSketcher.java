package test.text.render;

import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.vecmath.Point2d;

import painter.IBounder;

import diagram.element.TextElement;
import sketcher.AbstractSketcher;
import test.text.model.Text;
import test.text.model.TextBounder;

public class TextBoxListSketcher 
       extends AbstractSketcher<List<Text>, TextElement> {
    
    private double scale;
    
    private IBounder<List<Text>> modelBounder;
    
    public TextBoxListSketcher() {
        modelBounder = new TextBounder();
    }

    @Override
    public TextElement sketch(List<Text> textBoxes) {
        scale = 1.0;
        Point2d center = getCenter(textBoxes);
        
        System.out.println("rough scale = " + scale + " model center = " + center);
        
        TextElement diagram = new TextElement();
        Point2d cc = new Point2d(0, 0);
        for (Text textBox : textBoxes) {
            double x = transform(textBox.center.x, center.x, scale, cc.x);
            double y = transform(textBox.center.y, center.y, scale, cc.y);
            Point2d p = new Point2d(x, y);
            TextElement element = new TextElement(textBox.text, p);
            diagram.add(element);
        }
        return diagram;
    }

    @Override
    public Rectangle2D getModelBounds(List<Text> model) {
        return modelBounder.getBounds(model);
    }

}
