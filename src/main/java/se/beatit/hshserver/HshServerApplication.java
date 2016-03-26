package se.beatit.hshserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by stefan on 3/13/16.
 */

@SpringBootApplication
@ComponentScan()
public class HshServerApplication {
        public static void main(String args[]) {
            ApplicationContext ctx = SpringApplication.run(HshServerApplication.class, args);
        }

}
