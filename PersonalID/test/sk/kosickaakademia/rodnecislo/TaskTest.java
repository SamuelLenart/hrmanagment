package sk.kosickaakademia.rodnecislo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void suc() {
        Task task = new Task();
        assertEquals(0, task.suc(0,0));
        assertEquals(0, task.suc(5,-5));
        assertEquals(0, task.suc(-999999998,+999999998));
        assertEquals(47, task.suc(12,35));
        assertEquals(499100, task.suc(-900,500000));

    }

    @Test
    void isPrimeNumber() {
        Task task = new Task();
        assertTrue(task.isPrimeNumber(7));
        assertTrue(task.isPrimeNumber(97));
        assertTrue(task.isPrimeNumber(997));
        assertTrue(task.isPrimeNumber(859844473));
    }

    @Test
    void isRectangularTriangle() {
        Task task = new Task();
        assertTrue(task.isRectangularTriangle(3,4,5));
        assertTrue(task.isRectangularTriangle(4,5,3));
        assertTrue(task.isRectangularTriangle(5,4,3));
        assertTrue(task.isRectangularTriangle(5,12,13));
        assertTrue(task.isRectangularTriangle(20,48,52));
        assertTrue(task.isRectangularTriangle(12,20,16));

        assertFalse(task.isRectangularTriangle(0,0,0));
        assertFalse(task.isRectangularTriangle(4,5,6));
        assertFalse(task.isRectangularTriangle(-3,-4,-5));
    }

    @Test
    void revers() {
        Task task = new Task();
        assertEquals("AHOJ", task.revers("JOHA"));
        assertEquals(" ", task.revers(" "));
        assertEquals("", task.revers(""));
        assertEquals("VOLE", task.revers("ELOV"));
        assertEquals("123456789", task.revers("987654321"));
        assertEquals("KEKW", task.revers("WKEK"));
    }

    @Test
    void calculateRectangle() {
        Task task = new Task();
        assertEquals(6, task.calculateRectangle(2,3));
        assertEquals(4, task.calculateRectangle(2,2));
        assertEquals(56, task.calculateRectangle(8,7));
        assertEquals(80, task.calculateRectangle(8,10));
    }


    @Test
    void divide() {
        Task task = new Task();
        assertEquals(1, task.divide(2,2));
        assertEquals(0, task.divide(2,0));
        assertEquals(2, task.divide(8,4));
        assertEquals(8, task.divide(80,10));
    }


    @Test
    void isThisTrue() {
        Task task = new Task();
        assertTrue(task.isThisTrue());
    }

    @Test
    void howLongisRectangle() {
        Task task = new Task();
        assertEquals(4, 8, task.howLongisRectangle(2,4));
        assertEquals(16, 12, task.howLongisRectangle(8,6));
        assertEquals(36, 12, task.howLongisRectangle(18,6));
        assertEquals(8, 24, task.howLongisRectangle(4,12));
    }

    @Test
    void max() {
        Task task = new Task();
        assertEquals(5,task.max(pole));
    }

    @Test
    void VolumeofCone() {
        Task task = new Task();
        assertEquals(0, task.VolumeofCone(0,5));
        assertEquals(0, task.VolumeofCone(2,0));
        assertEquals(20.94, task.VolumeofCone(2,5));
    }
}