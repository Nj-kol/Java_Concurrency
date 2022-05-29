package com.njkol.atomic.doublecas.markablereference;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class Node {
    int value;
    AtomicMarkableReference<Node> next;

    public Node(int value, Node next) {
        this.value = value;
        // initially marked the Node as false
        this.next = new AtomicMarkableReference<Node>(next, false);
    }
}