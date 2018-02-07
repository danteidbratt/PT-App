package Models;

public class Note {
    
    private String comment;
    private IndividualSession individualSession;

    public Note() {
        individualSession = new IndividualSession();
    }
    
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public IndividualSession getIndividualSession() {
        return individualSession;
    }

    public void setIndividualSession(IndividualSession individualSession) {
        this.individualSession = individualSession;
    }
    
}