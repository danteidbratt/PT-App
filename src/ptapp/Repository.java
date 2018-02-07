package ptapp;

import Models.*;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Repository {

    LogInfo logInfo;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Repository() {
        logInfo = new LogInfo();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public Trainer login(String trainerName) {
        String query = "select * from trainer where name like ?";
        try (Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
                PreparedStatement stmt = con.prepareStatement(query);) {
            stmt.setString(1, trainerName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Trainer trainer = new Trainer();
                trainer.setID(rs.getInt("ID"));
                trainer.setName(rs.getString("name"));
                return trainer;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int findIdForLatestIndividualSession(String memberName, String trainerName) {
        int individualSessionID = 0;
        LocalDateTime latest = LocalDateTime.MIN;
        String query1 = "select individualSession.ID as 'indID', session.scheduled as 'scheduled' "
                + "from member "
                + "inner join individualSession on member.ID = individualSession.memberID "
                + "inner join session on individualSession.sessionID = session.ID "
                + "inner join trainer on session.trainerID = trainer.ID "
                + "where member.name like ? and trainer.name = ?";
        String query2 = "select count(ID) as 'count' from note where individualSessionID = ?";
        try (Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
                PreparedStatement stmt1 = con.prepareStatement(query1);
                PreparedStatement stmt2 = con.prepareStatement(query2);) {
            stmt1.setString(1, memberName);
            stmt1.setString(2, trainerName);
            ResultSet rs1 = stmt1.executeQuery();
            while (rs1.next()) {
                LocalDateTime temp = LocalDateTime.parse(rs1.getString("scheduled").substring(0, 19), formatter);
                if (temp.isAfter(latest)) {
                    latest = temp;
                    individualSessionID = rs1.getInt("indID");
                }
            }
            stmt2.setInt(1, individualSessionID);
            ResultSet rs2 = stmt2.executeQuery();
            while (rs2.next()) {
                if (rs2.getInt("count") > 0) {
                    individualSessionID = 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return individualSessionID;
    }

    public int addNote(int individualSessionID, String comment) {
        int rowsAffected = -1;
        String query = "insert into note(individualsessionID, comment) values (?, ?)";
        try (Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
                PreparedStatement stmt = con.prepareStatement(query);) {
            stmt.setInt(1, individualSessionID);
            stmt.setString(2, comment);
            rowsAffected = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public List<Note> getNotes(String memberName) {
        List<Note> notes = new ArrayList<>();
        String query = "select session.scheduled as 'scheduled', note.comment as 'comment' "
                + "from member "
                + "inner join individualSession on member.ID = individualSession.memberID "
                + "inner join note on individualSession.ID = note.IndividualSessionID "
                + "inner join session on individualSession.sessionID = session.ID "
                + "where member.name like ?";
        try (Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
                PreparedStatement stmt = con.prepareStatement(query);) {
            stmt.setString(1, memberName);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Note temp = new Note();
                temp.setComment(rs.getString("comment"));
                temp.getIndividualSession().setTimeScheduled(LocalDateTime.parse(rs.getString("scheduled").substring(0, 19), formatter));
                notes.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }
    
    public Member getMember(String memberName) {
        Member member = new Member();
        String query = "select * from member where name like ?";
        try (Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
                PreparedStatement stmt = con.prepareStatement(query);) {
            stmt.setString(1, memberName);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                member.setID(rs.getInt("ID"));
                member.setName(rs.getString("name"));
                member.setIndividualSessions(getIndividualSessionsInMember(member.getID()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return member;
    }
    
    public List<IndividualSession> getIndividualSessionsInMember(int memberID) {
        List<IndividualSession> individualSessions = new ArrayList<>();
        String query = "select session.scheduled as 'scheduled', trainer.name as 'trainerName', individualSession.attendance as 'attendance' "
                + "from individualSession "
                + "inner join session on individualSession.sessionID = session.ID "
                + "inner join trainer on session.trainerID = trainer.ID "
                + "where individualSession.memberID = ?";
        try (Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
             PreparedStatement stmt = con.prepareStatement(query);
        ){
            stmt.setInt(1, memberID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                IndividualSession temp = new IndividualSession();
                temp.setAttendance(rs.getBoolean("attendance"));
                temp.setTimeScheduled(LocalDateTime.parse(rs.getString("scheduled").substring(0, 19), formatter));
                temp.getTrainer().setName(rs.getString("trainerName"));
                individualSessions.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return individualSessions;
    }

}
