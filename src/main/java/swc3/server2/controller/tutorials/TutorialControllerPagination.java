package swc3.server2.controller.tutorials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swc3.server2.exception.ResourceNotFoundException;
import swc3.server2.model.Tutorial;
import swc3.server2.repository.TutorialRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api3")
public class TutorialControllerPagination {

    @Autowired
    TutorialRepository tutorialRepository;

    @GetMapping("/tutorials")
    public ResponseEntity<Map<String,Object>> getPageOfTutorials(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "2") int size){

        Pageable paging = PageRequest.of(page, size);
        Page<Tutorial> pageTuts = tutorialRepository.findAll(paging);
        List<Tutorial> tutorials = pageTuts.getContent();

        if(tutorials.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        Map<String, Object> response = new HashMap<>();
        response.put("tutorials", tutorials);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalItems", pageTuts.getTotalElements());
        response.put("totalPage", pageTuts.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
