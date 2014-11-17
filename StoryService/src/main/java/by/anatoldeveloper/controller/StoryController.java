package by.anatoldeveloper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import by.anatoldeveloper.database.QueryLoader;
import by.anatoldeveloper.entity.Story;

@RestController
public class StoryController {
	
	@Autowired
	QueryLoader loader;
	
	@RequestMapping("/story/{id}") 
    public @ResponseBody Story shops(@PathVariable("id") long id) throws Exception {
    	return loader.getStory(id);
    }

}
