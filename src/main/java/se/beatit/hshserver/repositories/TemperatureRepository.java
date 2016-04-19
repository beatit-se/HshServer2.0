package se.beatit.hshserver.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import se.beatit.hshserver.entities.Home;
import se.beatit.hshserver.entities.Sensor;
import se.beatit.hshserver.entities.Temperature;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by stefan on 3/13/16.
 */
public interface TemperatureRepository extends CrudRepository<Temperature, Long> {

    @Query(value = "SELECT t FROM Temperature t WHERE t.sensor = :sensor AND t.timestamp in (SELECT max(t2.timestamp) FROM Temperature t2 WHERE t2.sensor = :sensor)")
    Temperature findLatestForSensor(@Param("sensor") Sensor sensor);

    @Query(value = "SELECT t FROM Temperature t WHERE t.sensor = :sensor and t.timestamp > :fromdate and t.timestamp < :todate")
    List<Temperature> findForSensor(@Param("sensor") Sensor sensor, @Param("fromdate") Date fromdate, @Param("todate") Date todate);
}
