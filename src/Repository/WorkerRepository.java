package Repository;

import DataAccess.WorkerDAO;
import java.util.ArrayList;
import model.History;
import model.Worker;

public class WorkerRepository implements IWorkerRepository {

    @Override
    public void addWorker(ArrayList<Worker> lw) {
        WorkerDAO.Instance().addWorker(lw);
    }

    @Override
    public void changeSalary(ArrayList<Worker> lw, ArrayList<History> lh, String status) {
        WorkerDAO.Instance().changeSalary(lw, lh, status);
    }

    @Override
    public void printListHistory(ArrayList<History> lh) {
        WorkerDAO.Instance().printListHistory(lh);
    }
}
