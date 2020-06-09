package codes.ifelse.numbergenerator.web.rest;

import codes.ifelse.numbergenerator.service.NumberGeneratorTaskService;
import codes.ifelse.numbergenerator.service.dto.RequestDto;
import codes.ifelse.numbergenerator.service.dto.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api")
public class NumberGeneratorTaskController {

    private final Logger log = LoggerFactory.getLogger(NumberGeneratorTaskController.class);

    private final NumberGeneratorTaskService service;

    public NumberGeneratorTaskController(NumberGeneratorTaskService service) {
        this.service = service;
    }

    @PostMapping("/generate")
    public ResponseEntity<Task> generateNumberFile(@RequestBody RequestDto request) {
        log.debug("REST request to generate number : {}", request);
        if (Objects.isNull(request.getGoal())) {
        }
        Task task = service.startNumberGenerationTask(request);
        return ResponseEntity.accepted().body(task);
    }

    @GetMapping("/tasks/{taskId}/status")
    public ResponseEntity<Task> getTaskStatus(@PathVariable String taskId) {
        log.debug("REST request to get task status : {}", taskId);
        Task task = service.getTaskStatus(taskId);
        return ResponseEntity.ok().body(task);
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<String> getTaskGeneratedNumbers(@PathVariable String taskId, @RequestParam String action) {
        log.debug("REST request to get task generated numbers status : {}", taskId);

        if (!action.equalsIgnoreCase("get_numlist")) {
            return ResponseEntity.badRequest().build();
        }
        String numbers = service.getTaskGeneratedNumbers(taskId);
        return ResponseEntity.ok().body(numbers);
    }
}
