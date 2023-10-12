package src.auto_move;

import ch.aplu.jgamegrid.*;
import src.Game;
import src.PacActor;

public interface NextMove {
    Location getNext(PacActor pacman, Game game);
}
