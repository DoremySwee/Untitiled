package pers.doremyswee.drmpractice.recipes;

import com.google.common.base.Predicate;

public abstract class Ingredient<T> implements Predicate<T> {
    public abstract T getInstance();
}
