package ru.netology.manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Player;
import ru.netology.exceptions.NotRegisteredException;

import java.util.HashMap;

public class GameTest {
    Game service = new Game();

    Player player1 = new Player(1, "Владимир", 80);
    Player player2 = new Player(2, "Дмитрий", 80);
    Player player3 = new Player(3, "Роман", 100);
    Player player4 = new Player(4, "Ирина", 90);

    @Test
    public void shouldRegisterOnePlayer() {
        service.register(player3);

        boolean expected = true;
        boolean actual = service.getPlayers().containsKey("Роман");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldRegisterSeveralPlayers() {

        service.register(player1);
        service.register(player2);
        service.register(player3);
        service.register(player4);

        HashMap<String, Player> expected = new HashMap<>();
        expected.put("Владимир", player1);
        expected.put("Дмитрий", player2);
        expected.put("Роман", player3);
        expected.put("Ирина", player4);
        HashMap<String, Player> actual = service.getPlayers();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldWinFirstPlayer() {

        service.register(player1);
        service.register(player3);

        int expected = 1;
        int actual = service.round("Роман", "Владимир");

        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void shouldWinSecondPlayer() {

        service.register(player1);
        service.register(player4);

        int expected = 2;
        int actual = service.round("Владимир", "Ирина");

        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void shouldNobodyWin() {

        service.register(player1);
        service.register(player2);

        int expected = 0;
        int actual = service.round("Владимир", "Дмитрий");

        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void shouldThrowNotRegisteredExceptionForTheFirstPlayer() {
        service.register(player1);

        Assertions.assertThrows(NotRegisteredException.class, () -> {
            service.round("Наташа", "Владимир");
        });
    }

    @Test
    public void shouldThrowNotRegisteredExceptionForTheSecondPlayer() {
        service.register(player1);

        Assertions.assertThrows(NotRegisteredException.class, () -> {
            service.round("Владимир", "Наташа");
        });
    }

    @Test
    public void shouldThrowNotRegisteredExceptionForBothPlayers() {

        Assertions.assertThrows(NotRegisteredException.class, () -> {
            service.round("Ирина", "Роман");
        });
    }
}
