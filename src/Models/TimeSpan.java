package Models;

import java.time.LocalDateTime;


public class TimeSpan {
    
    private final LocalDateTime start;
    private final LocalDateTime finish;
    private final int minutes;

    public TimeSpan(LocalDateTime start, int minutes) {
        this.start = start;
        this.minutes = minutes;
        this.finish = start.plusMinutes(minutes);
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getFinish() {
        return finish;
    }

    public int getMinutes() {
        return minutes;
    }
    
}