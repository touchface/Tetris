package top.touchface.tetris.model;

import android.content.Context;
import java.util.ArrayList;
import top.touchface.tetris.model.entry.Score;
import top.touchface.tetris.utils.DBHelper;

public class ScoreModel {

    private int score=0;//一局游戏的分数
    private DBHelper dbHelper;
    public ScoreModel(Context context){

        dbHelper=new DBHelper(context);
    }
    public void setScore(int score){
        this.score=score;
    }
    /**
     * 将游戏分数清空
     * **/
    public void initScore(){
        this.score=0;
    }
    /**
     * 在游戏中用于添加游戏积分
     * @param l_num 消除的方块的行数
     * **/
    public void addScore(int l_num){
        score+=l_num*100;
    }
    /**
     * 获取当前的游戏分数
     * **/
    public int getScore(){
        return score;
    }
    /**
     * 获取本地的游戏记录
     * **/
    public ArrayList<Integer> getLocalScores(){
        return null;
    }
    /**
     * 获取本地的游戏记录
     * **/
    public int getMaxScore(){
        Score score=dbHelper.getMaxScore();
        int num=0;
        if(score!=null){
            num=score.getS_score().intValue();
        }
        return num;
    }
    /**
     * 获取本地的游戏记录
     * **/
    public boolean saveScore(String username){
        if(getMaxScore()>score){
            return false;
        }
        Score scoreMsg=new Score();
        scoreMsg.setS_score(score);
        scoreMsg.setS_name(username);
        long i=dbHelper.insertScore(scoreMsg);
        if(i>0){
            return true;
        }else{
            return false;
        }
    }

}
