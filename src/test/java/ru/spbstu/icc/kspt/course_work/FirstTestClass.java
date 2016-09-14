package ru.spbstu.icc.kspt.course_work;
import static org.junit.Assert.*;

import org.junit.Test;

public class FirstTestClass {

    @Test
    public void sumTest() {
        FirstClass firstClass = new FirstClass();
        assertTrue(2 + 5 == firstClass.sum(2, 5));
    }
}
