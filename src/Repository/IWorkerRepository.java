package Repository;

import java.util.ArrayList;
import model.History;
import model.Worker;

public interface IWorkerRepository {

    void addWorker(ArrayList<Worker> lw);

    void changeSalary(ArrayList<Worker> lw, ArrayList<History> lh, String status);

    void printListHistory(ArrayList<History> lh);
}
