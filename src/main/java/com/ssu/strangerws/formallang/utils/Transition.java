package com.ssu.strangerws.formallang.utils;

public class Transition<T, C, N> {
    private T transition;
    private C current;
    private N next;

    public Transition(T transition, C current, N next) {
        this.transition = transition;
        this.current = current;
        this.next = next;
    }

    public T getTransition() {
        return transition;
    }

    public void setTransition(T transition) {
        this.transition = transition;
    }

    public C getCurrent() {
        return current;
    }

    public void setCurrent(C current) {
        this.current = current;
    }

    public N getNext() {
        return next;
    }

    public void setNext(N next) {
        this.next = next;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transition<?, ?, ?> that = (Transition<?, ?, ?>) o;

        if (transition != null ? !transition.equals(that.transition) : that.transition != null) return false;
        if (current != null ? !current.equals(that.current) : that.current != null) return false;
        return next != null ? next.equals(that.next) : that.next == null;
    }

    @Override
    public int hashCode() {
        int result = transition != null ? transition.hashCode() : 0;
        result = 31 * result + (current != null ? current.hashCode() : 0);
        result = 31 * result + (next != null ? next.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Transition{" +
                "transition=" + transition +
                ", current=" + current +
                ", next=" + next +
                '}';
    }
}
