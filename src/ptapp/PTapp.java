package ptapp;

import Models.*;
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
                readNotes();
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
        int indID = 0;
        List<IndividualSession> attendedNoCommentWithMe = repository.getIndividualSessionsInMember(repository.getMember(scanner.nextLine()).getID()).stream()
                .filter(a -> a.isAttendance() && a.getNote() == null && a.getTrainer().getName().equalsIgnoreCase(myAccout.getName()))
                .collect(Collectors.toList());
        if(attendedNoCommentWithMe.size() != 0) {
            IndividualSession earliest = attendedNoCommentWithMe.get(0);
            for (IndividualSession i : attendedNoCommentWithMe) {
                if(i.getTimeScheduled().isBefore(earliest.getTimeScheduled())){
                    earliest = i;
                }
            }
            indID = earliest.getIndividualSessionID();
            if(indID != 0) {
                System.out.println("\nWrite comment here:");
                if(repository.addNote(indID, scanner.nextLine()) == 1){
                    System.out.println("Note added");
                }
                else {
                    System.out.println("\nUnable to add note");
                }
            }
        }
        else {
            System.out.println("\nNo Individual Sessions to comment");
        }
    }
    
    private void readNotes(){
        System.out.println("\nWhich members notes would you like to read?");
        String input = scanner.nextLine();
        List<Note> temp = repository.getNotes(input);
        if(temp.size() > 0) {
        temp.forEach(a -> System.out.println("\n" + a.getIndividualSession().getTimeScheduled().toString().replace('T', ' ') +
                " - " + a.getComment()));
        }
        else {
            System.out.println("\n" + input + " doesn't have any notes");
        }
    }
    
    private void checkMemberActivity(){
        System.out.println("\nWho members activity would you like to see?");
        Member temp = repository.getMember(scanner.nextLine());
        if(temp.getIndividualSessions().size() > 0){
            List<IndividualSession> tempIndSess = temp.getIndividualSessions().stream()
                    .filter(b -> b.isAttendance()).collect(Collectors.toList());
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
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("X")) {
            System.exit(0);
        }
        myAccout = repository.login(input);
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