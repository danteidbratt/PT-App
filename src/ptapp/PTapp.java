
package ptapp;

import Models.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PTapp {
    
    Repository repository;
    Scanner scanner;
    Trainer myAccout;
    boolean done;

    public PTapp() {
        scanner = new Scanner(System.in);
        repository = new Repository();
        done = false;
    }
    
    private void start(){
        System.out.println("\nWhat would you like to do?"
                + "\n[1] Write a note"
                + "\n[2] Read notes"
                + "\n[3] Check member activity"
                + "\n[4] Log out");
        
        switch (scanner.nextLine()) {
            case "1":
                writeNote();
                break;
            case "2":
                readNote();
                break;
            case "3":
                checkMemberActivity();
                break;
            case "4":
                done = true;
                System.out.println("Good bye " + myAccout.getName());
                break;
            default:
                System.out.println("Invalid input...");
                break;
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    private void writeNote(){
        System.out.println("\nWhich member does you note concern?");
        int indID = repository.findIdForLatestIndividualSession(scanner.nextLine(), myAccout.getName());
        if(indID != 0) {
            System.out.println("\nWrite comment here:");
            if(repository.addNote(indID, scanner.nextLine()) == 1){
                System.out.println("Note added");
            }
            else {
                System.out.println("\nUnable to add note");
            }
        }
        else {
            System.out.println("\nNo Individual Sessions to comment");
        }
    }
    
    private void readNote(){
        System.out.println("\nWhich members notes would you like to read?");
        repository.getNotes(scanner.nextLine()).forEach(a -> System.out.println("\n" + a.getIndividualSession().getTimeScheduled().toString().replace('T', ' ') +
                " - " + a.getComment()));
    }
    
    private void checkMemberActivity(){
        System.out.println("\nWho members activity would you like to see?");
        Member temp = repository.getMember(scanner.nextLine());
        if(temp.getIndividualSessions().size() > 0){
            List<IndividualSession> tempIndSess = temp.getIndividualSessions().stream()
                    .filter(b -> b.isAttendance() && b.getTimeScheduled().isBefore(LocalDateTime.now())).collect(Collectors.toList());
            if(tempIndSess.size() > 0) {
                    tempIndSess.forEach(a -> System.out.println("\nTränare: " + a.getTrainer().getName() + 
                                                "\t\t-\tTillfälle: " + a.getTimeScheduled().toString().replace('T', ' ')));
            }
            else {
                System.out.println("\n" + temp.getName() + " hasn't attended any individual sessions");
            }
        }
        else {
            System.out.println("\n" + temp.getName() + " hasn't booked any individual sessions");
        }
    }
    
    private void login() {
        System.out.println("\nWhat is your name?");
        myAccout = repository.login(scanner.nextLine());
        if(myAccout != null) {
            while(!done){
                start();
            }
            done = false;
        }
        else {
            System.out.println("\nUnregistered Trainer");
        }
    }

    public static void main(String[] args) {
        PTapp ptapp = new PTapp();
        while(true){
            ptapp.login();
        }
    }
}