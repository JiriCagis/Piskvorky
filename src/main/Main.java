package main;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import main.view.MainWindow;
import test.TestAll;

public class Main {
    public static void main(String[] args){
        //JUnit tests [inportant: only for develop]
        //Result result = JUnitCore.runClasses(TestAll.class);
        //for(Failure failure: result.getFailures()){
        //    System.out.println(failure.toString());
        //}
        //System.out.println("Test was successful : " + result.wasSuccessful());
        
        //Start application
        new MainWindow().setVisible(true);
    }
}
