import java.util.ArrayList;
import java.util.List;

public class TetrisBlocks {

	public static void main(String[] args) {
		
		List<int[][]> blocks = new ArrayList<int[][]>();
		
		while(blocks.size() < 1) {
			try {
				int[][] randomBlock = generateRandomTetrisBlock();
				if(!blocks.contains(randomBlock)) {
					blocks.add(randomBlock);
					printBlock(randomBlock);
				}	
			} catch(Exception e) {
				
			}
		}
	}
	
	public static int[][] generateRandomTetrisBlock() {
		int counter = 0;
		int x = 2;
		int y = 3;
		
		List<int[]> block = new ArrayList<int[]>();
		
		block.add(new int[]{x, y});
		while(counter < 3){
			double randomDir = Math.random();
			
			if(randomDir >= 0 && randomDir < 0.25){
				x += 1;
			} else if(randomDir >= 0.25 && randomDir < 0.5) {
				x -= 1;
			} else if(randomDir >= 0.5 && randomDir < 0.75){
				y += 1;
			} else {
				y -= 1;
			}
			
			int[] piece = new int[]{ x, y };
			
			boolean found = false;
			for(int[] existingPiece : block) {
				if(existingPiece[0] == piece[0] && existingPiece[1] == piece[1])
					found = true;
			}
			if(!found) {
				
				block.add(piece);
				counter += 1;
			}
		}
		
		int[][] t = new int[][] {
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }
		};
		
		for(int[] coords : block) {
			t[coords[0]][coords[1]] = 1;
		}
		
		return t;	
	}
	
	public static void printBlock(int[][] block) {
		for(int x = 0; x < 9; x++) {
			for(int y = 0; y < 9; y++) {
				if(block[x][y] > 0)
					System.out.print(block[x][y]);
				else
					System.out.print(" ");
			}
			System.out.println("");
		}
	}

}
