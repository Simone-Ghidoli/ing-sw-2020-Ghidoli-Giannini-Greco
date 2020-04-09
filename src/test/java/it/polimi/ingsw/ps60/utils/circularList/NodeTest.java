package it.polimi.ingsw.ps60.utils.circularList;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NodeTest {

    private Node<int[]> node;
    private Node<Object> genericNode;

    private Object genericValue;
    private int[] ints;

    @Before
    public void setup(){
        ints = new int[]{1, 2, 3};
        node = new Node<>(ints);

        genericValue = new Object();
        genericNode = new Node<>(genericValue);
    }

    @After
    public void tearDown(){
        node = null;
        genericNode= null;

        genericValue = null;
    }


    @Test
    public void getValue() {
        assertSame(node.getValue(), ints);
        assertNotSame(node.getValue(), new int[]{1, 2, 3});

        assertSame(genericNode.getValue(), genericValue);
        assertNotSame(genericNode, new Object());
    }
}
