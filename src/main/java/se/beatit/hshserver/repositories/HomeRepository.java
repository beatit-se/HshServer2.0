package se.beatit.hshserver.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import se.beatit.hshserver.entities.Home;

import java.util.List;

/**
 * Created by stefan on 3/13/16.
 */
public interface HomeRepository extends CrudRepository<Home, Long> {

    @Query(value = "select h from Home h where h.name = :name")
    Home findByName(@Param("name") String name);
}
