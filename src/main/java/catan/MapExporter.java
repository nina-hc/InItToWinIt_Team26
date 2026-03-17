package catan;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MapExporter {

	public static void writeBaseMap(String file){

		/*coordinates for tiles 0 to 18*/
		int[][] tileCoordinates =  {
				{0, 0, 0},
				{1, -1, 0},
				{0, -1, 1},
				{-1, 0, 1},
				{-1, 1, 0},
				{0, 1, -1},
				{1, 0, -1},
				{2, -2, 0},
				{1, -2, 1},
				{0, -2, 2},
				{-1, -1, 2},
				{-2, 0, 2},
				{-2, 1, 1},
				{-2, 2, 0},
				{-1, 2, -1},
				{0, 2, -2},
				{1, 1, -2},
				{2, 0, -2},
				{2, -1, -1},
		};

		List<Map<String,Object>> tiles = new ArrayList<>();

		//Get the board tiles
		Board board = new Board();
		//loop through adding the tiles
		for(int i = 0; i < 19; i++){
			Tile tile = board.getTile(i);
			Map<String,Object> mapTiles = new LinkedHashMap<>();

			mapTiles.put("q",tileCoordinates[i][0]);
			mapTiles.put("s",tileCoordinates[i][1]);
			mapTiles.put("r",tileCoordinates[i][2]);
			mapTiles.put("resource",resourceVisualizerConversion(tile.getResourceType()));

		}




	}

	public static String resourceVisualizerConversion(ResourceType type){
		switch(type){
			case LUMBER -> {
				return "WOOD";
			}
			case WOOL ->  {
				return "SHEEP";
			}
			case GRAIN ->   {
				return "WHEAT";
			}
			case ORE ->   {
				return "ORE";
			}
			case BRICK ->   {
				return "BRICK";
			}
			case DESERT -> {
				return null;
			}
			default -> {
				throw new IllegalArgumentException("Invalid tile type");
			}
		}
	}

	//colours need to be here

}
