package top.touchface.tetris;
/**
 * @author Jason
 * 地图类，用于保存堆积的方块
 * **/
public class GMap {
    public Cell cells[][];
    public GMap(int map_x, int map_y){
        cells=new Cell[map_x][map_y];
    }
}
