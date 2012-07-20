package windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FrameDragger implements MouseListener, MouseMotionListener {

	
    private JFrame frameToDrag = null;
    private JDialog dialogToDrag = null;

    private Point lastDragPosition;

    private boolean isDragged = false;
    
       
    public FrameDragger(JFrame frameToDrag) {
        this.frameToDrag = frameToDrag;
    }
    public FrameDragger(JDialog dialogToDrag) {
        this.dialogToDrag = dialogToDrag;
    }

    public void mouseDragged(MouseEvent e) {
        Point currentDragPosition = e.getLocationOnScreen();
        int deltaX = currentDragPosition.x - lastDragPosition.x;
        int deltaY = currentDragPosition.y - lastDragPosition.y;
        if (deltaX != 0 || deltaY != 0) {
        	if(frameToDrag != null){
        		int x = frameToDrag.getLocation().x + deltaX;
            	int y = frameToDrag.getLocation().y + deltaY;
        	    frameToDrag.setLocation(x, y);
        	}
        	if(dialogToDrag != null){
        		int x = dialogToDrag.getLocation().x + deltaX;
            	int y = dialogToDrag.getLocation().y + deltaY;
            	dialogToDrag.setLocation(x, y);
        	}
            lastDragPosition = currentDragPosition;
            isDragged = true;
        }
    }

    
    public boolean isDragged(){
    	return isDragged;
    }
    
    public void mousePressed(MouseEvent e) {
        lastDragPosition = e.getLocationOnScreen();
    }

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
		isDragged = false;
	}

	public void mouseReleased(MouseEvent arg0) {
		
	}

	public void mouseMoved(MouseEvent arg0) {
		isDragged = false;
	}

 
}