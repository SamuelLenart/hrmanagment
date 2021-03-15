package sk.kosickaakademia.rodnecislo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {
    private Bank bank;
    @BeforeEach
    void setUp(){
        System.out.println("Test started");
        bank = new Bank();
    }

    @AfterEach
    void tearDown(){
        bank = null;
        System.out.println("test finished");
    }

    @Test
    void termnovanyVklad() {
        assertEquals(1100, bank.termnovanyVklad(1000,1,10,false));
        assertEquals(1080, bank.termnovanyVklad(1000,1,10,true));

        assertEquals(1259.71, bank.termnovanyVklad(1000,3,10,true));
        assertEquals(1331, bank.termnovanyVklad(1000,3,10,false));

        assertEquals(0, bank.termnovanyVklad(1000,30,80,false));
        assertEquals(0, bank.termnovanyVklad(1000,30,80,true));

        assertEquals(10000, bank.termnovanyVklad(10000,30,0,false));
        assertEquals(100, bank.termnovanyVklad(100,1,1.50,true));

        assertEquals(0, bank.termnovanyVklad(-15478,30,14,false));
        assertEquals(0, bank.termnovanyVklad(1000,-2,1.50,true));

        assertEquals(1100, bank.termnovanyVklad(500,1,250,true));
    }
}