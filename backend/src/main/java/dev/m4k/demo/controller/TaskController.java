package dev.m4k.demo.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import dev.m4k.demo.config.CurrentUser;
import dev.m4k.demo.dto.LocalUser;
import dev.m4k.demo.dto.TaskRequest;
import dev.m4k.demo.model.Task;
import dev.m4k.demo.model.User;
import dev.m4k.demo.repo.TaskRepository;
import dev.m4k.demo.repo.UserRepository;
import dev.m4k.demo.service.UserService;
import dev.m4k.demo.util.GeneralUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.swing.text.html.Option;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TaskController {

  @Autowired
  UserRepository userRepository;

  @Autowired
  TaskRepository taskRepository;

  @GetMapping("/tasks")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> getAllTasks(@CurrentUser LocalUser localUser, @RequestParam(required=false) String s) {
    try {
      List<Task> tasks = new ArrayList<>();
      if (s == null) {
        tasks = localUser.getUser().getTasks();
      } else {
        tasks = localUser.getUser().getTasks().stream().filter(t -> t.getDescription().toLowerCase().contains(s.toLowerCase())).collect(Collectors.toList());
      }

      if (tasks.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(tasks, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Transactional
  @PostMapping("/tasks")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> createTask(@CurrentUser LocalUser localUser, @RequestBody TaskRequest task) {
    try {
      Task _task = taskRepository.save(new Task(task.getDescription()));
      User user = localUser.getUser();
      List<Task> tasks = user.getTasks();
      tasks.add(_task);
      user.setTasks(tasks);
      userRepository.save(user);
      return new ResponseEntity<>(_task, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Transactional
  @PutMapping("/tasks")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> updateTask(@CurrentUser LocalUser localUser, @RequestBody TaskRequest task) {
    Optional<Task> _task = localUser.getUser().getTasks().stream().filter(t -> t.getId() == task.getId()).findFirst();

    if (_task.isPresent()) {
      Task updatedTask = _task.get();
      updatedTask.setDescription(task.getDescription());
      updatedTask.setDone(task.isDone());
      updatedTask.setModifiedDate(Calendar.getInstance().getTime());
      return new ResponseEntity<>(taskRepository.save(updatedTask), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Transactional
  @DeleteMapping("/tasks")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> deleteTask(@CurrentUser LocalUser localUser, @RequestBody TaskRequest task) {
    try {
      User user = localUser.getUser();
      List<Task> tasks = user.getTasks();
      Task _task = tasks.stream().filter(t -> t.getId() == task.getId()).collect(Collectors.toList()).get(0);
      tasks.remove(_task);
      user.setTasks(tasks);
      userRepository.save(user);
      taskRepository.deleteById(_task.getId());
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
