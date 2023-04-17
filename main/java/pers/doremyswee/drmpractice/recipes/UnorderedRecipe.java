package pers.doremyswee.drmpractice.recipes;

import com.google.common.base.Predicate;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.RecipeMatcher;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UnorderedRecipe<T> implements Predicate<List<T>> {
    public List<Ingredient<T>>requirements=new ArrayList<Ingredient<T>>();
    public UnorderedRecipe(Collection<? extends Ingredient<T>> p){
        for(Ingredient<T> i:p){
            requirements.add(i);
        }
    }
    public UnorderedRecipe<T> add(Ingredient<T> t){requirements.add(t);return this;}
    @Override
    public boolean apply(@Nullable List<T> input) {
        //Algorithm provided by forge
        return RecipeMatcher.findMatches(input,requirements)!=null;
    }
}
