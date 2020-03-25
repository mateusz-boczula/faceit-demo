package pl.boczula.mateusz.faceitdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.boczula.mateusz.faceitdemo.dto.RestActionDto;

@RestController
@RequestMapping("/search")
public class SearchController {

    private static Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    @PostMapping("/user/created")
    public void userCreated(@RequestBody RestActionDto userDto) {
        LOGGER.info("User update received: {}", userDto);
    }

}
