package com.example.swjtu.recylerviewtest.entity;

import java.io.Serializable;

/**
 * Created by tangpeng on 2017/3/8.
 */

public class CoursePractice implements Serializable {
    private String type;    //属于什么类型的测试
    private String name;
    private String groupId; //习题组编号
    private String deadLine;    //截至日期
    private int timeLimit;  //时限
    private int questionNum;    //题目数目
    private String practiceProfile;    //练习的说明信息

    public CoursePractice(String type, String name, String groupId, String deadLine, int timeLimit, int questionNum, String practiceProfile) {
        this.type = type;
        this.name = name;
        this.groupId = groupId;
        this.deadLine = deadLine;
        this.timeLimit = timeLimit;
        this.questionNum = questionNum;
        this.practiceProfile = practiceProfile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    public String getPracticeProfile() {
        return practiceProfile;
    }

    public void setPracticeProfile(String practiceProfile) {
        this.practiceProfile = practiceProfile;
    }
}
