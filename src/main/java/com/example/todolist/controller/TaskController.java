//package com.example.todolist.controller;
//
//import com.example.todolist.model.Task;
//import com.example.todolist.repository.TaskRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/task")
//public class TaskController {
//
//    @Autowired
//    private TaskRepository taskRepository;
//
//    @GetMapping
//    public List<Task> getAllTasks(){
//        return taskRepository.findAll();
//    }
//
//    /*Listagem de ID*/
//    @GetMapping("/id")
//    public ResponseEntity<Task> getTaskById(@RequestParam Long id) {
//        Optional<Task> task = taskRepository.findById(id);
//        return task.map (ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @PostMapping
//    public ResponseEntity<Task> createdTask(@RequestBody Task task) {
//        Task savedTask = taskRepository.save(task);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetail){
//        Optional<Task> task = taskRepository.findById(id);
//        if(task.isPresent()) {
//            Task existingTask = task.get();
//            existingTask.setTitle(taskDetail.getTitle());
//            existingTask.setDescription(taskDetail.getDescription());
//            existingTask.setCompleted(taskDetail.isCompleted());
//
//            Task updateTask = taskRepository.save(existingTask);
//            return ResponseEntity.ok(updateTask);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Task> deleteTask(@PathVariable long id) {
//        Optional<Task> task =  taskRepository.findById(id);
//        if (task.isPresent()) {
//            taskRepository.delete(task.get());
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//}
