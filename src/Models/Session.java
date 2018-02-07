package Models;

import java.time.*;

public abstract class Session {
    
    private int sessionID;
//    private TimeSpan timeSpan;
    private LocalDateTime timeScheduled;
    private Trainer trainer;
    private Hall hall;

    public Session() {
        this.trainer = new Trainer();
        this.hall = new Hall();
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

//    public LocalDateTime getTimeScheduled() {
//        return timeScheduled;
//    }
//
//    public void setTimeScheduled(LocalDateTime timeScheduled) {
//        this.timeScheduled = timeScheduled;
//    }
//
//    public Models.Duration getDuration() {
//        return duration;
//    }
//
//    public void setDuration(Models.Duration duration) {
//        this.duration = duration;
//    }
    
    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public LocalDateTime getTimeScheduled() {
        return timeScheduled;
    }

    public void setTimeScheduled(LocalDateTime timeScheduled) {
        this.timeScheduled = timeScheduled;
    }
    
}