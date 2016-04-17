package se.beatit.hshserver.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import se.beatit.hshserver.entities.ElectricityConsumption;
import se.beatit.hshserver.entities.Home;

import java.util.Date;
import java.util.List;

/**
 * Created by stefan on 3/13/16.
 */
public interface ElectricityRepository extends CrudRepository<ElectricityConsumption, Long> {

    @Query(value = "SELECT e FROM ElectricityConsumption e WHERE e.toDate in (SELECT max(e2.toDate) FROM ElectricityConsumption e2 WHERE e2.home = :home)")
    ElectricityConsumption findLatestForHome(@Param("home") Home home);

    @Query(value = "SELECT e FROM ElectricityConsumption e WHERE e.home = :home AND e.fromDate > :fromdate AND e.toDate < :todate")
    List<ElectricityConsumption> find(@Param("home") Home home, @Param("fromdate") Date fromdate, @Param("todate") Date todate);
}
