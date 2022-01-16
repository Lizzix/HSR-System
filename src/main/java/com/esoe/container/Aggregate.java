package com.esoe.container;

import java.util.Iterator;

public interface Aggregate {
    public void add(Object obj1, Object Obj2);
    public void remove(Object obj);
    public Iterator getIterator();
}
