package org.project.models;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

import static org.project.constants.Constants.*;

@Data

public class TimeTable {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int groupId;
    private int teacherId;
    private int id;
    private static int currentId = 0;

    public TimeTable(LocalDateTime startTime, LocalDateTime endTime, int groupId, int teacherId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.groupId = groupId;
        this.teacherId = teacherId;
        this.id = ++currentId;
    }
    public TimeTable(){
    }

    public static void setCurrentId(int id){
        currentId = id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("startTime", startTime.format(FORMATTER))
                .append("endTime", endTime.format(FORMATTER))
                .append("groupId", groupId)
                .append("teacherId", teacherId)
                .append("id", id)
                .toString();
    }
    public void setStartTime(String startTime) {
        this.startTime = LocalDateTime.parse(startTime, FORMATTER);
    }

    public void setEndTime(String startTime) {
        this.endTime = LocalDateTime.parse(startTime,FORMATTER);
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime startTime) {
        this.endTime = startTime;
    }
}
