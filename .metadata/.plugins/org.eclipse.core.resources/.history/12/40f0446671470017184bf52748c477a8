package ee.itcollege.tetris.parts;

import java.util.ArrayList;

import ee.itcollege.tetris.lib.CollisionDetector;
import javafx.scene.shape.Shape;

@SuppressWarnings("serial")
public class Figure extends ArrayList<Block> {
	

	public void move(int deltaX, int deltaY) {
		
		for (Block block : this) {
			block.setX(block.getX() + deltaX * Block.SIZE);
		}
		
	}
	
}





