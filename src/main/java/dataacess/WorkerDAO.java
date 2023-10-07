/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataacess;

import common.Library;
import model.History;
import common.Validate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import model.Worker;

/**
 *
 * @author Administrator
 */
public class WorkerDAO {
    private static WorkerDAO instance = null;
    Library l;
    Validate v;

    public WorkerDAO() {
        l = new Library();
        v = new Validate();
    }
    
    public static WorkerDAO Instance() {
        if (instance == null) {
            synchronized (WorkerDAO.class) {
                if (instance == null) {
                    instance = new WorkerDAO();
                }
            }
        }
        return instance;
    }
    
    //allow user add worker
    public void addWorker(ArrayList<Worker> lw) {
        while(true) {
            String id = l.inputString("Enter code: ");
            while (v.checkIdExist(lw, id)) {          
                System.err.println("Code(id) must be existed in DB.");
                id = l.inputString("Enter code: ");
            }
            String name = l.inputString("Enter name: ");
            int age = l.getInt("Enter age: ", 18, 50);
            int salary = inputSalary();
            String workLocation = l.inputString("Enter work location: ");
            if (!v.checkWorkerExist(lw,name, age, salary, workLocation)) {
                System.err.println("Duplicate.");
            } else {
                lw.add(new Worker(id, name, age, salary, workLocation));
                System.err.println("Add success.");
                return;
            }
        }     
    }

    //allow user increase salary for user
    public void changeSalary(ArrayList<Worker> lw, ArrayList<History> lh, String status) {
        if (lw.isEmpty()) {
            System.err.println("List empty.");
            return;
        }
        String id = l.inputString("Enter code: ");
        Worker worker = getWorkerByCode(lw, id);
        if (worker == null) {
            System.err.println("Not exist worker.");
        } else {
            int salaryCurrent = worker.getSalary();
            int salaryUpdate = inputSalary();
            //check user want to update salary
            if(status.equalsIgnoreCase("DOWN")) {
                //loop until user input salary update > salary current
                while (true) {     
                    if(salaryUpdate >= salaryCurrent) {
                        System.err.println("Must be smaller than current salary.");
                        salaryUpdate = inputSalary();
                    } else {
                        salaryCurrent-=salaryUpdate;
                        break;
                    }
                }
            }
            else {
                salaryCurrent+=salaryUpdate;
            }
            lh.add(new History(status, getCurrentDate(), worker.getId(),
                        worker.getName(), worker.getAge(), salaryCurrent,
                        worker.getWorkLocation()));
            worker.setSalary(salaryCurrent);
            System.err.println("Update success");
        }
    }
    
    public int inputSalary() {
        int salary = l.getIntNoLimit("Enter salary: ");
        while(salary <= 0) {
            System.out.println("Salary must be greater than 0");
            salary = l.getIntNoLimit("Enter salary: ");
        }
        return salary;
    }

    //allow user print history
    public void printListHistory(ArrayList<History> lh) {
        if (lh.isEmpty()) {
            System.err.println("List empty.");
            return;
        }
        System.out.printf("%-10s%-12s%-10s%-10s%-10s%-20s\n", "Code", "Name", "Age",
                "Salary", "Status", "Date");
        Collections.sort(lh);
        //print history from first to last list
        for (History history : lh) {
            printHistory(history);
        }
    }

    //get worker by code
    public Worker getWorkerByCode(ArrayList<Worker> lw, String id) {
        for (Worker worker : lw) {
            if (id.equalsIgnoreCase(worker.getId())) {
                return worker;
            }
        }
        return null;
    }

    //get current date 
    public String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }

    //print history
    public void printHistory(History history) {
        System.out.printf("%-10s%-12s%-10d%-10d%-10s%-20s\n", history.getId(),
                history.getName(), history.getAge(), history.getSalary(),
                history.getStatus(), history.getDate());
    }
}
