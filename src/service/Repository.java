// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Part 3: Generic Repository
// Works with any type T that has an ID (Identifiable) and a natural
// sort order (Comparable). Mirrors the main service list and adds
// findById, filter, and getSorted on top of it.

package service;

import exceptions.EntityNotFoundException;
import interfaces.Identifiable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class Repository<T extends Identifiable & Comparable<? super T>> {

    // internal list that mirrors whatever is in the service
    private final List<T> items = new ArrayList<>();


    // ================= ADD =================

    // add an item to the repo — called every time we add to the service list
    public void add(T item) {
        items.add(item);
    }


    // ================= FIND BY ID =================

    // look up any entity by its ID string, works for Client, Trip, etc.
    // throws EntityNotFoundException if nothing matches
    public T findById(String id) throws EntityNotFoundException {
        for (T item : items) {
            if (item.getId().equalsIgnoreCase(id))
                return item;
        }
        throw new EntityNotFoundException(
                "No entity found with ID '" + id + "'.");
    }


    // ================= FILTER =================

    // returns all items that pass the given predicate (true/false lambda)
    // e.g. tripRepo.filter(t -> t.getTotalCost() > 2000)
    public List<T> filter(Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : items) {
            if (predicate.test(item))
                result.add(item);
        }
        return result;
    }


    // ================= GET SORTED =================

    // returns a sorted copy of the list using each class's compareTo()
    // Client -> descending by amountSpent, Trip -> descending by total cost, etc.
    // returns a new list so the original is not changed
    public List<T> getSorted() {
        List<T> sorted = new ArrayList<>(items);
        Collections.sort(sorted);
        return sorted;
    }


    // ================= UTILITY =================

    // clears the repo when data is reloaded from CSV
    public void clear() {
        items.clear();
    }

    // returns how many items are in the repo
    public int size() {
        return items.size();
    }
}
