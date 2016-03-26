package se.beatit.hshserver.repositories;

import org.springframework.data.repository.CrudRepository;
import se.beatit.hshserver.entities.Sensor;

/**
 * Created by stefan on 3/25/16.
 */
public interface SensorRepository extends CrudRepository<Sensor, Long> {
}
