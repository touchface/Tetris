package top.touchface.tetris.model.entry;

public class Score {

    private Integer s_id;
    private String s_name;
    private Integer s_score;
    public Score(){

    }

    public Integer getS_id() {
        return s_id;
    }

    public void setS_id(Integer s_id) {
        this.s_id = s_id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public Integer getS_score() {
        return s_score;
    }

    public void setS_score(Integer s_score) {
        this.s_score = s_score;
    }
}
