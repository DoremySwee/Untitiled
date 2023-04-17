package pers.doremyswee.drmpractice.fussion;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import pers.doremyswee.drmpractice.recipes.IAltarRecipe;
import pers.doremyswee.drmpractice.recipes.Ingredient;
import pers.doremyswee.drmpractice.recipes.ItemIngredient;
import pers.doremyswee.drmpractice.recipes.UnorderedRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IFussionRecipe extends IAltarRecipe<FussionAltarTE> {
    ItemStack exampleResult;
    UnorderedRecipe<ItemStack>items;
    List<FluidStack>fluids=new ArrayList<FluidStack>();
    IFussionRecipe(List<ItemIngredient>itemIngredients,List<FluidStack> fluidStacks){
        items=new UnorderedRecipe<ItemStack>(itemIngredients);
        for(FluidStack f:fluids)fluids.add(f);
    }
    @Override
    public boolean matchRecipe(FussionAltarTE te) {
        if(!items.apply(te.contents))return false;
        return true;
    }

    @Override
    public List<List<ItemStack>> getJEIItems() {
        List<ItemStack>list=new ArrayList<ItemStack>();
        for(Ingredient<ItemStack> i:items.requirements){
            list.add(i.getInstance());
        }
        return Arrays.asList(new List[]{list, Arrays.asList(new ItemStack[]{exampleResult})});
    }

    @Override
    public List<List<FluidStack>> getJEIFluids() {
        return Arrays.asList(new List[]{fluids});
    }
}
