package Models;

import java.util.ArrayList;
import java.util.List;

public class GroupSession extends Session{
    
    private int groupSessionID;
    private int capacity;
    private ExerciseType exerciseType;
    private List<Member> participants;

    public GroupSession() {
        this.exerciseType = new ExerciseType();
        this.participants = new ArrayList<>();
    }
    
    public int getGroupSessionID() {
        return groupSessionID;
    }

    public void setGroupSessionID(int groupSessionID) {
        this.groupSessionID = groupSessionID;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
    }

    public List<Member> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Member> participants) {
        this.participants = participants;
    }
    
}