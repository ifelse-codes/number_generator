package codes.ifelse.numbergenerator.service;

import codes.ifelse.numbergenerator.service.dto.RequestDto;
import codes.ifelse.numbergenerator.service.dto.Task;

import java.util.concurrent.ExecutionException;

public interface NumberGeneratorTaskService {
    Task startNumberGenerationTask(RequestDto request);
    Task getTaskStatus(String taskId);
    String getTaskGeneratedNumbers(String taskId);
}
