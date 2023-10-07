package common;

import model.Worker;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author THAYCACAC
 */
public class Validate {

    // check id must be existed in DB.
    public boolean checkIdExist(ArrayList<Worker> lw, String id) {
        //check from first to last list id worker exist or not
        for (Worker worker : lw) {
            if (id.equalsIgnoreCase(worker.getId())) {
                return true;
            }
        }
        return false;
    }
    
    //check worker duplicate
    public boolean checkWorkerExist(ArrayList<Worker> lw, String name, int age, int salary, String workLocation) {
        //check from first to last list worker  worker exist or not
        for (Worker worker : lw) {
            if (name.equalsIgnoreCase(worker.getName())
                    && age == worker.getAge()
                    && salary == worker.getSalary()) {
                return false;
            }
        }
        return true;
    }

}