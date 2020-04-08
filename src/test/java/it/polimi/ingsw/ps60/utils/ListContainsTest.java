package it.polimi.ingsw.ps60.utils;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ListContainsTest {

    int[] a, b, c;
    ListContains listContains;

    @Before
    public void setUp() throws Exception {
        List<int[]> list = new ArrayList<>();

        a = new int[]{0, 1};
        b = new int[]{0, 1};
        c = new int[]{1, 1};

        listContains = new ListContains(list);
        list.add(a);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void isContained() {
        assertTrue(listContains.isContained(b));
        assertFalse(listContains.isContained(c));
    }
}
