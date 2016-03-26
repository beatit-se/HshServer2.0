package se.beatit.hshserver.service;

import org.apache.tomcat.util.digester.ArrayStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.beatit.hshserver.entities.Home;
import se.beatit.hshserver.repositories.HomeRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stefan on 3/13/16.
 */
@Component("homeService")
@Transactional
public class HomeService {
    private final HomeRepository repo;

    @Autowired
    public HomeService(HomeRepository repo) {
        this.repo = repo;
    }

    public Home save(Home home) {
        return repo.save(home);
    }

    public Iterable<Home> findAll() {
        return repo.findAll();
    }

    public Home findByName(String name) {
        return repo.findByName(name);
    }
}
