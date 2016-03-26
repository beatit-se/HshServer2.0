package se.beatit.hshserver.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import se.beatit.hshserver.entities.Home;
import se.beatit.hshserver.entities.Sensor;
import se.beatit.hshserver.entities.Temperature;
import org.springframework.data.repository.query.Param;

/**
 * Created by stefan on 3/13/16.
 */
public interface TemperatureRepository extends CrudRepository<Temperature, Long> {

    @Query(value = "SELECT t FROM Temperature t WHERE t.timestamp in (SELECT max(t2.timestamp) FROM Temperature t2 WHERE t2.sensor = :sensor)")
    Temperature findLatestForSensor(@Param("sensor") Sensor sensor);
}
