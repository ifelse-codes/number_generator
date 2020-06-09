package codes.ifelse.numbergenerator.service;

import codes.ifelse.numbergenerator.service.dto.RequestDto;
import codes.ifelse.numbergenerator.service.dto.Task;

import java.util.concurrent.CompletableFuture;

public interface FileService {
    CompletableFuture writeNumberToFile(Task task);
    Task getFileWritingStatus(String taskId);
    String getTaskGeneratedNumbers(String taskId);
}
