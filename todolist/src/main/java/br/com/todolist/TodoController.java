package br.com.todolist;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin("*")

public class TodoController {
    private final TodoRepository TodoRepo;

    public TodoController(TodoRepository todoRepo) {
        TodoRepo = todoRepo;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<Todo> getAll() {
        return this.TodoRepo.findAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Todo create(@RequestBody Todo tarefas) {
        return this.TodoRepo.save(tarefas);
    }

    @DeleteMapping("/{tarefaId}")
    public ResponseEntity<Void> delete(@PathVariable Integer tarefaId) {
        Optional<Todo> todo = this.TodoRepo.findById(tarefaId);
        if (todo.isPresent()) {
            this.TodoRepo.deleteById(tarefaId);
            return ResponseEntity.noContent().build();

        } else {
            return ResponseEntity.notFound().build();

        }

    }

    @PutMapping("/{todoId}/start_task")
    public ResponseEntity<Todo> startTask(@PathVariable Integer todoId) {
        Todo todoDatabase = this.TodoRepo.findById(todoId).get();
        if (todoDatabase != null) {
            todoDatabase.setStatus(StatusEnum.IN_PROGRESS);
            this.TodoRepo.save(todoDatabase);
            return ResponseEntity.ok(todoDatabase);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{todoId}/end_task")
    public ResponseEntity<Todo> endTask(@PathVariable Integer todoId) {
        Todo todoDatabase = this.TodoRepo.findById(todoId).get();
        if (todoDatabase != null) {
            todoDatabase.setStatus(StatusEnum.FINISHED);
            this.TodoRepo.save(todoDatabase);
            return ResponseEntity.ok(todoDatabase);

        } else {
            return ResponseEntity.notFound().build();
        }

    }
    @PutMapping("/{todoId}")
    public ResponseEntity<Todo> update(@PathVariable Integer todoId, @RequestBody Todo todo) {
        Todo todoDatabase = this.TodoRepo.findById(todoId).get();
        if (todoDatabase != null) {
            todoDatabase.setDescription(todo.getDescription());
            this.TodoRepo.save(todoDatabase);
            return ResponseEntity.ok(todoDatabase);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}