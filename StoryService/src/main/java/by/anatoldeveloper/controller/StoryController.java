package by.anatoldeveloper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import by.anatoldeveloper.repository.Story;
import by.anatoldeveloper.repository.StoryRepository;

@RestController
public class StoryController {
	
	@Autowired
	StoryRepository storyRepository;
	
	@RequestMapping("/story/{id}") 
    public @ResponseBody Story shops(@PathVariable("id") long id) {
    	return storyRepository.findById(id);
    }

}
