package codes.ifelse.numbergenerator.service.impl;

import codes.ifelse.numbergenerator.service.FileService;
import codes.ifelse.numbergenerator.service.dto.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

@Component
public class FileServiceImpl implements FileService {
    private final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    @Async
    public CompletableFuture<Task> writeNumberToFile(Task task) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("tmp/" + task.getTaskId() + "_output.txt", true));
            BigInteger goal = new BigInteger(task.getGoal());
            BigInteger steps = new BigInteger(task.getStep());
            while (goal.compareTo(BigInteger.ZERO) >= 0) {
                writer.append(goal + "\n");
                goal = goal.subtract(steps);
            }
        } catch (IOException ex) {
            log.error("error : {}", ex.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return CompletableFuture.completedFuture(task);
    }

    @Override
    public Task getFileWritingStatus(String taskId) {
        Task task = new Task(taskId);
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader("tmp/" + taskId + "_output.txt"));
            String last = null, line;
            while ((line = input.readLine()) != null) {
                last = line;
            }
            if (!last.equals("0")) {
                task.setStatus(Task.STATUS.IN_PROGRESS);
            } else {
                task.setStatus(Task.STATUS.COMPLETE);
            }
        } catch (FileNotFoundException e) {
            task.setStatus(Task.STATUS.ERROR);
        } catch (IOException e) {
            task.setStatus(Task.STATUS.ERROR);
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return task;
    }

    @Override
    public String getTaskGeneratedNumbers(String taskId) {
        StringBuilder numbers = new StringBuilder("");
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader("tmp/" + taskId + "_output.txt"));
            String line;
            while ((line = input.readLine()) != null) {
                numbers.append(line + ",");
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return numbers.toString();
    }
}
