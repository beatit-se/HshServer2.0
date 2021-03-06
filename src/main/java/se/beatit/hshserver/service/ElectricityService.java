package se.beatit.hshserver.service;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.beatit.hshserver.entities.ElectricityConsumption;
import se.beatit.hshserver.entities.Home;
import se.beatit.hshserver.repositories.ElectricityRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by stefan on 3/13/16.
 */
@Component("electricityConsumptionService")
@Transactional
public class ElectricityService {

    private final ElectricityRepository repo;

    @Autowired
    public ElectricityService(ElectricityRepository repo) {
        this.repo = repo;
    }

    public ElectricityConsumption save(ElectricityConsumption electricityConsumption) {
        return repo.save(electricityConsumption);
    }

    public ElectricityConsumption addElectricityConsumption(Home home, Long wh, Date from, Date to) {
        ElectricityConsumption ec = new ElectricityConsumption();
        ec.setHome(home);
        ec.setFromDate(from);
        ec.setToDate(to);
        ec.setWhUsed(wh);
        return save(ec);
    }


    public long getCurrentUsage(Home home) {
        // Om du låter en 60 W glödlampa lysa i en timme kommer den att konsumera 0,06 kWh,
        // vilket räknas ut så här:	(60 * 1)/1000 = 0,06
        // Så (watt * tiditimmar) / 1000 = kwh
        // och (watt * tiditimmar) / 1 = wh
        // ger watt = wh / tiditimmar
        // t.ex. 36wh på en minut ger 36 / 0,016667 = 2160w
        // t.ex 8wh på en minut ger 8 /0,016667 = 480w

        ElectricityConsumption ec = repo.findLatestForHome(home);
        long deltaMs = ec.getToDate().getTime() - ec.getFromDate().getTime();

        long calcHelper = 1000L;
        long watts = (ec.getWhUsed() * calcHelper) / ((deltaMs * calcHelper)/ DateUtils.MILLIS_PER_HOUR);

        return watts;
    }

    public Map<Date, Long> getHistoricUsage(Home home, Date from, Date to) {

        List<ElectricityConsumption> electricityConsumption = repo.find(home, from,to);
        Map<Date, Long> historyResult = new HashMap<Date, Long>();

        electricityConsumption.forEach(ec -> addToHistoryGrouped(ec, historyResult, Calendar.MINUTE));
        //electricityConsumption.forEach(ec -> addToHistoryGrouped(ec, historyResult, Calendar.HOUR));
        //electricityConsumption.forEach(ec -> addToHistoryGrouped(ec, historyResult, Calendar.DATE));
        //electricityConsumption.forEach(ec -> addToHistoryGrouped(ec, historyResult, Calendar.MONTH));
        //electricityConsumption.forEach(ec -> addToHistoryGrouped(ec, historyResult, Calendar.YEAR));

        return historyResult;
    }

    private void addToHistoryGrouped(ElectricityConsumption ec, Map<Date, Long> historyResult, int goupByCalendarConstant) {
        Date keyDate = DateUtils.truncate(ec.getFromDate(), goupByCalendarConstant);
        Long sum = ec.getWhUsed();

        if (historyResult.containsKey(keyDate)) {
            sum += historyResult.get(keyDate);
        }

        historyResult.put(keyDate, sum);
    }
}
