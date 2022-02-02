package com.njkol.patterns.guardedsuspension;

import java.util.LinkedList;


public class SimpleBlockingBoundedQueue<E> implements BoundedQueue<E> {

    /**
     * The queue consists of a LinkedList of E's.
     */
    final private LinkedList<E> mList;

    /**
     * The maximum capacity of the queue or Integer.MAX_VALUE if none.
     */
    private final int mCapacity;

    /**
     * Create a SimpleBlocking queue with a capacity of
     * Integer.MAX_VALUE.
     */
    public SimpleBlockingBoundedQueue() {
        this(Integer.MAX_VALUE);
    }

    /**
     * Create a SimpleBlocking queue with the given capacity.
     */
    public SimpleBlockingBoundedQueue(int capacity) {
        if (capacity <= 0) 
            throw new IllegalArgumentException();
        mCapacity = capacity;
        mList = new LinkedList<>();
    }

    /**
     * Inserts the specified element into this queue, waiting if
     * necessary for space to become available.
     *
     * @param e the element to add
     * @throws InterruptedException if interrupted while waiting
     */
    @Override
    public void put(E e)
        throws InterruptedException {
        synchronized(this) {
            if (e == null)
                throw new NullPointerException();

            /**
             * Guarded suspension pattern 
             */
            // Wait until the queue is not full.
            while (isFull()) {
            	wait();	
            }
                
            // Add e to the LinkedList.
            mList.add(e);
            
            // Notify that the queue may have changed state, e.g., "no
            // longer empty".
            notifyAll();
        }
    } 

    /**
     * Retrieves and removes the head of this queue, waiting if necessary
     * until an element becomes available.
     *
     * @return the head of this queue
     * @throws InterruptedException if interrupted while waiting
     */
    @Override
    public E take() throws InterruptedException {
        synchronized(this) {
            // Wait until the queue is not empty.
            while (mList.isEmpty())  {
            	System.out.println("Nothing to take!");
            	wait();
            	System.out.println("Got element!");
            }
                
            // Notify that the queue may have changed state, e.g., "no longer full".
            notifyAll();

            // Remove/return first item on the queue without blocking.
            return mList.poll();
        }
    } 

    /**
     * Returns true if the queue is empty, else false.
     */
    @Override
    public boolean isEmpty() {
        synchronized(this) {
            return mList.size() == 0;
        }
    }

    /**
     * Returns true if the queue is full, else false.  Since this
     * isn't a public method it assumes the monitor lock is held.
     */
    public boolean isFull() {
        return mList.size() == mCapacity;
    }

    /**
     * Returns the number of elements in this queue.
     */
    @Override
    public int size() {
        synchronized(this) {
            return mList.size();
        }
    }
}
