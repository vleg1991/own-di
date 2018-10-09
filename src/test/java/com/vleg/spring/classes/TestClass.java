package com.vleg.spring.classes;

import java.util.Objects;


public class TestClass {

    private String value;

    public TestClass() {

    }

    public TestClass(String arg) {
        this.value = arg;
    }

    public TestClass testMethod(String arg) {
        return new TestClass(arg);
    }

    public void testMethod() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestClass)) return false;
        TestClass testClass = (TestClass) o;
        return Objects.equals(value, testClass.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
