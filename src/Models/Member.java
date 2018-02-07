package Models;

import java.util.List;

public class Member {

    private int ID;
    private String name;
    private List<GroupSession> groupSessions;
    private List<IndividualSession> individualSessions;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GroupSession> getGroupSessions() {
        return groupSessions;
    }

    public void setGroupSessions(List<GroupSession> groupSessions) {
        this.groupSessions = groupSessions;
    }

    public List<IndividualSession> getIndividualSessions() {
        return individualSessions;
    }

    public void setIndividualSessions(List<IndividualSession> individualSessions) {
        this.individualSessions = individualSessions;
    }
    
}