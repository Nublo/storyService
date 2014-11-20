package by.anatoldeveloper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import by.anatoldeveloper.repository.Story;
import by.anatoldeveloper.repository.StoryDimension;
import by.anatoldeveloper.repository.StoryRepository;

@RestController
public class StoryController {
	
	@Autowired
	StoryRepository storyRepository;
	
	@RequestMapping("/story/{id}") 
    public @ResponseBody Story storyById(@PathVariable("id") long id) {
    	return storyRepository.findById(id);
    }
	
	@RequestMapping("/story")
	public @ResponseBody List<Story> storiesRange(@RequestParam("start") long start, @RequestParam("end") long end) {
		return storyRepository.findByIdBetweenOrderByIdAsc(start, end);
	}
	
	@RequestMapping("/story/sex")
	public @ResponseBody List<Story> storiesRangeWithSex(@RequestParam("page") int page, @RequestParam("limit") int limit) {
		return storyRepository.findBySexOrderByIdAsc(true, new PageRequest(page, limit));
	}
	
	@RequestMapping("/dimension")
	public @ResponseBody StoryDimension dimension() {
		long stories = storyRepository.count();
		long sexStories = storyRepository.findSexStoriesCount();
		return new StoryDimension(stories, sexStories);
	}

}