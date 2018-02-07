package Models;

public class IndividualSession extends Session{
    
    private int individualSessionID;
    private Note note;
    private Member member;
    private boolean attendance;

    public int getIndividualSessionID() {
        return individualSessionID;
    }

    public void setIndividualSessionID(int individualSessionID) {
        this.individualSessionID = individualSessionID;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
    
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public boolean isAttendance() {
        return attendance;
    }

    public void setAttendance(boolean attendance) {
        this.attendance = attendance;
    }
    
}