package com.example.tommy.cg24.Models;


public class Sort<A,B> {

    private A _a;
    private B _b;

    public Sort(A a, B b) {
        _a = a;
        _b = b;
    }

    public A getA() {
        return _a;
    }

    public B getB() {
        return _b;
    }
}
