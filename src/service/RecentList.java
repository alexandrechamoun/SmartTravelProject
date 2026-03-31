// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Generic wrapper around LinkedList<T> that maintains a fixed-size
// history of recently viewed/accessed items (max 10).
// New items are added to the front; when over capacity the oldest
// item (at the back) is automatically removed.
//
// Why LinkedList instead of ArrayList?
// addFirst() and removeLast() are O(1) on a LinkedList because it
// maintains direct head/tail node pointers — no shifting required.
// An ArrayList would need to shift every element on every addFirst(),
// making it O(n). Since RecentList only ever inserts at the front
// and removes from the back, LinkedList is the natural fit.

package service;

import java.util.LinkedList;

public class RecentList<T> {

    private LinkedList<T> list = new LinkedList<>();
    private final int MAX_SIZE = 10;

    /**
     * Adds item to the front of the recent list.
     * If the list exceeds MAX_SIZE, the oldest item (back) is removed.
     *
     * @param item the item to record as most recently viewed
     */
    public void addRecent(T item) {
        list.addFirst(item);
        if (list.size() > MAX_SIZE) {
            list.removeLast();
        }
    }

    /**
     * Prints up to maxToShow items, most recent first.
     *
     * @param maxToShow maximum number of items to display
     */
    public void printRecent(int maxToShow) {
        if (list.isEmpty()) {
            System.out.println("  (no recent items)");
            return;
        }
        int limit = Math.min(maxToShow, list.size());
        int count = 0;
        for (T item : list) {
            if (count >= limit) break;
            System.out.println("  [" + (count + 1) + "] " + item);
            System.out.println();
            count++;
        }
    }

    /**
     * Returns the number of items currently in the recent list.
     */
    public int size() {
        return list.size();
    }

    /**
     * Returns true if the recent list is empty.
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }
}