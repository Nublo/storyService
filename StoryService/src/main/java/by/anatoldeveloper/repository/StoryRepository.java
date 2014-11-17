package by.anatoldeveloper.repository;

import org.springframework.data.repository.CrudRepository;

public interface StoryRepository extends CrudRepository<Story, Long> {

	public Story findById(long id);
	
}
