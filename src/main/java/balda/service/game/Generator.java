package balda.service.game;

import balda.game.Game;
import balda.game.Vocabulary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Brought by anatolyd on 08.04.2017.
 */
@Service
@RestController
public class Generator {

    private final Logger log = LoggerFactory.getLogger(Generator.class);

    private final Vocabulary vocabulary;

    @Autowired
    public Generator(Vocabulary vocabulary) {
        this.vocabulary = vocabulary;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/new-game")
    @ResponseBody
    public Game generateNewGame() {
        return new Game(vocabulary);
    }
}
