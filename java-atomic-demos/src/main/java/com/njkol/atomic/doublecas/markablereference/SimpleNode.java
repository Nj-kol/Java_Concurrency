package com.njkol.atomic.doublecas.markablereference;

import java.util.concurrent.atomic.AtomicReference;

public class SimpleNode {
	
    int value;
    AtomicReference<SimpleNode> next;

    public SimpleNode(int value, SimpleNode next) {
        this.value = value;
        this.next = new AtomicReference<>(next);
    }
}
