package swc3.server2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import swc3.server2.model.Tutorial;
import swc3.server2.repository.TutorialRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TutorialService {

    @Autowired
    TutorialRepository tutorialRepository;

    public ResponseEntity<Tutorial> getById(long id) {
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
        if (tutorialData.isPresent())
            return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<Tutorial>> getAll(String title) {
        try {
            List<Tutorial> tutorials = new ArrayList<>();

            if(title == null)
                tutorials = tutorialRepository.findAll();
            else
                tutorials = tutorialRepository.findByTitleContaining(title);

            if(tutorials.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(tutorials, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
