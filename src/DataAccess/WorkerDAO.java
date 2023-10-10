package DataAccess;

import common.Library;
import common.Validate;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import model.History;
import model.Worker;

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

    public void addWorker(ArrayList<Worker> lw) {
        while (true) {
            String id = l.inputString("Enter code: ");
            while (v.checkIdExist(lw, id)) {
                System.err.println("Code(id) must be existed in DB.");
                id = l.inputString("Enter code: ");
            }
            String name = l.inputString("Enter name: ");
            int age = l.getInt("Enter age: ", 18, 50);
            int salary = inputSalary();
            String workLocation = l.inputString("Enter work location: ");
            if (!v.checkWorkerExist(lw, name, age, salary, workLocation)) {
                System.err.println("Duplicate.");
            } else {
                lw.add(new Worker(id, name, age, salary, workLocation));
                System.err.println("Add success.");
                return;
            }
        }
    }

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

            if (status.equalsIgnoreCase("DOWN")) {

                while (true) {
                    if (salaryUpdate >= salaryCurrent) {
                        System.err.println("Must be smaller than current salary.");
                        salaryUpdate = inputSalary();
                    } else {
                        salaryCurrent -= salaryUpdate;
                        break;
                    }
                }
            } else {
                salaryCurrent += salaryUpdate;
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
        while (salary <= 0) {
            System.out.println("Salary must be greater than 0");
            salary = l.getIntNoLimit("Enter salary: ");
        }
        return salary;
    }

    public void printListHistory(ArrayList<History> lh) {
        if (lh.isEmpty()) {
            System.err.println("List empty.");
            return;
        }
        System.out.printf("%-10s%-12s%-10s%-10s%-10s%-20s\n", "Code", "Name", "Age",
                "Salary", "Status", "Date");
        Collections.sort(lh);

        for (History history : lh) {
            printHistory(history);
        }
    }

    public Worker getWorkerByCode(ArrayList<Worker> lw, String id) {
        for (Worker worker : lw) {
            if (id.equalsIgnoreCase(worker.getId())) {
                return worker;
            }
        }
        return null;
    }

    public String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());

    }

    public void printHistory(History history) {
        System.out.printf("%-10s%-12s%-10d%-10d%-10s%-20s\n", history.getId(),
                history.getName(), history.getAge(), history.getSalary(),
                history.getStatus(), history.getDate());
    }

}
