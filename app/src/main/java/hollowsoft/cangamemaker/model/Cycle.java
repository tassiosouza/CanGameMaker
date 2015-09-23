package hollowsoft.cangamemaker.model;

import com.orm.SugarRecord;

import java.util.List;

public class Cycle extends SugarRecord<Task> {

    private String name;
    private Long idTag;
    private Tag tag;
    private float score;
    private int played;

    private String listTask;

    private List<Task> taskList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Long getTagId() {
        return idTag;
    }

    public void setTagId(Long idTag) {
        this.idTag = idTag;
    }

    public String getListTask() {
        return listTask;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public void setListTask(String listTask) {
        this.listTask = listTask;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
}
