package com.techgeeknext.controller;

import com.techgeeknext.model.Todo;
import com.techgeeknext.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200") //open for specific port
@RestController
public class TodosController {

    @Autowired
    TodoRepository todoRepository;

    /**
     * Get all the todos
     *
     * @return ResponseEntity
     */
    @GetMapping("/todos")
    public ResponseEntity<List<Todo>> gettodos() {
        try {
            return new ResponseEntity<>(todoRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get the todos by id
     *
     * @param id
     * @return ResponseEntity
     */
    @GetMapping("/todos/{id}")
    public ResponseEntity<Todo> gettodosById(@PathVariable("id") long id) {
        try {
            //check if todos exist in database
            Todo empObj = getEmpRec(id);

            if (empObj != null) {
                return new ResponseEntity<>(empObj, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Create new todos
     *
     * @param todos
     * @return ResponseEntity
     */
    @PostMapping("/todos")
    public ResponseEntity<Todo> newtodos(@RequestBody Todo todos) {
        Todo newtodos = todoRepository
                .save(todos.builder()
                        .title(todos.getTitle())
                        .body(todos.getBody())
                        .build());
        return new ResponseEntity<>(newtodos, HttpStatus.OK);
    }

    /**
     * Update todos record by using it's id
     *
     * @param id
     * @param todos
     * @return
     */
    @PutMapping("/todos/{id}")
    public ResponseEntity<Todo> updatetodos(@PathVariable("id") long id, @RequestBody Todo todos) {

        //check if todos exist in database
        Todo empObj = getEmpRec(id);

        if (empObj != null) {
            empObj.setTitle(todos.getTitle());
            empObj.setBody(todos.getBody());
            return new ResponseEntity<>(todoRepository.save(empObj), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Delete todos by Id
     *
     * @param id
     * @return ResponseEntity
     */
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<HttpStatus> deletetodosById(@PathVariable("id") long id) {
        try {
            //check if todos exist in database
            Todo emp = getEmpRec(id);

            if (emp != null) {
                todoRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Delete all todos
     *
     * @return ResponseEntity
     */
    @DeleteMapping("/todos")
    public ResponseEntity<HttpStatus> deleteAllTodos() {
        try {
            todoRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Method to get the todos record by id
     *
     * @param id
     * @return todos
     */
    private Todo getEmpRec(long id) {
        Optional<Todo> empObj = todoRepository.findById(id);

        if (empObj.isPresent()) {
            return empObj.get();
        }
        return null;
    }

}
