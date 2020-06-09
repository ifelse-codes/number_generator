package codes.ifelse.numbergenerator.service.impl;

import codes.ifelse.numbergenerator.service.FileService;
import codes.ifelse.numbergenerator.service.NumberGeneratorTaskService;
import codes.ifelse.numbergenerator.service.dto.RequestDto;
import codes.ifelse.numbergenerator.service.dto.Task;
import codes.ifelse.numbergenerator.web.rest.NumberGeneratorTaskController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NumberGeneratorTaskServiceImpl implements NumberGeneratorTaskService {
    private final Logger log = LoggerFactory.getLogger(NumberGeneratorTaskController.class);
    private final FileService fileService;

    public NumberGeneratorTaskServiceImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public Task startNumberGenerationTask(RequestDto request) {
        Task task = new Task(generateTaskId().toString());
        task.setGoal(request.getGoal());
        task.setStep(request.getStep());
        task.setStatus(Task.STATUS.IN_PROGRESS);

        fileService.writeNumberToFile(task);

        return task;
    }


    private UUID generateTaskId() {
        return UUID.randomUUID();
    }

    @Override
    public Task getTaskStatus(String taskId) {
        return fileService.getFileWritingStatus(taskId);
    }

    @Override
    public String getTaskGeneratedNumbers(String taskId) {
        return fileService.getTaskGeneratedNumbers(taskId);
    }
}
