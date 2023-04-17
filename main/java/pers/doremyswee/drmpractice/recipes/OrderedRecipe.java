package pers.doremyswee.drmpractice.recipes;

import com.google.common.base.Predicate;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class OrderedRecipe<T> implements Predicate<List<T>> {
    public List<Ingredient<T>>requirements=new ArrayList<Ingredient<T>>();
    public OrderedRecipe(Collection<Ingredient<T>>p){
        for(Ingredient<T> i:p){
            requirements.add(i);
        }
    }
    public OrderedRecipe<T> add(Ingredient<T> t){requirements.add(t);return this;}
    @Override
    public boolean apply(@Nullable List<T> input) {
        if(input.size()!=requirements.size())return false;
        for(int i=0;i<input.size();i++){
            if(!requirements.get(i).apply(input.get(i)))return false;
        }
        return true;
    }
}
