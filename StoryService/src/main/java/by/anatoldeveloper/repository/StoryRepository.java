package by.anatoldeveloper.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface StoryRepository extends CrudRepository<Story, Long> {

	public Story findById(long id);
	public List<Story> findByIdBetweenOrderByIdAsc(long start, long end);
	public List<Story> findBySexOrderByIdAsc(boolean sex, Pageable pageable);
	
	@Query("select count(s) from Story s where sex = true")
	public Long findSexStoriesCount();
	
}
