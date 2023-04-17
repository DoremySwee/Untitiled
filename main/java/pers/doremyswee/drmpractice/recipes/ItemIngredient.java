package pers.doremyswee.drmpractice.recipes;

import net.minecraft.item.ItemStack;

import com.google.common.base.Predicate;
import net.minecraft.nbt.NBTTagCompound;
import pers.doremyswee.drmpractice.RandomAltars;

import javax.annotation.Nullable;

public class ItemIngredient extends Ingredient<ItemStack> {
    ItemMatcher m=ItemMatcher.EMPTY;
    public int count=0;
    boolean exactCount=false;
    ItemIngredient(ItemMatcher m,int count){
        this.m=m;this.count=count;
    }
    public void setExactCount(boolean exactCount) {
        this.exactCount = exactCount;
    }
    public ItemIngredient(ItemStack a, boolean exactNBT){
        if(a.isEmpty()||a.getCount()==0){
            count=0;m=ItemMatcher.EMPTY;
        }
        else if(exactNBT){
            count=a.getCount();
            m=new ItemMatcher() {
                ItemStack stack=a.copy();
                @Override
                public boolean apply(ItemStack input) {
                    if(input.isEmpty())return false;
                    return input.isItemEqual(stack);
                }
                @Override
                public ItemStack getInstance(){return stack;}
            };
        }
        else{
            count=a.getCount();
            m=new ItemMatcher() {
                ItemStack stack=a.copy();
                @Override
                public boolean apply(ItemStack input) {
                    if(input.isEmpty())return false;
                    NBTTagCompound nbt=input.getTagCompound().copy();
                    nbt.merge(input.getTagCompound());
                    return input.getMetadata()==stack.getMetadata()&&
                            input.getItem().getRegistryName()==stack.getItem().getRegistryName()&&
                            nbt.equals(input.getTagCompound());
                }
                @Override
                public ItemStack getInstance(){return stack;}
            };
        }
    }
    public ItemIngredient(ItemStack stack){this(stack,false);}
    public boolean isEmpty(){
        if(count==0||m==ItemMatcher.EMPTY){
            count=0;
            m=ItemMatcher.EMPTY;
            return true;
        }
        else{
            return false;
        }
    }
    public ItemIngredient copy(){
        return new ItemIngredient(m,count);
    }
    @Override
    public boolean apply(@Nullable ItemStack input) {
        if(isEmpty())return m.apply(input);
        if(exactCount)return m.apply(input)&&input.getCount()==count;
        else return m.apply(input)&&input.getCount()>=count;
    }
    public ItemIngredient or(ItemIngredient b){
        return new ItemIngredient(new ItemMatcher() {
            ItemMatcher x=m;
            ItemMatcher y=b.m;
            @Override
            public boolean apply(ItemStack input) {
                return x.apply(input)||y.apply(input);
            }

            @Override
            public ItemStack getInstance() {
                return x.getInstance();
            }
        }, count);
    }
    public ItemIngredient and(ItemIngredient b, ItemStack instance){
        return new ItemIngredient(new ItemMatcher() {
            ItemMatcher x=m;
            ItemMatcher y=b.m;
            ItemStack instance_=instance;
            @Override
            public boolean apply(ItemStack input) {
                return x.apply(input)&&y.apply(input);
            }

            @Override
            public ItemStack getInstance() {
                if(!instance_.isEmpty())if(x.apply(instance_)&&y.apply(instance_))return instance_;
                if(x.apply(y.getInstance()))return y.getInstance();
                if(y.apply(x.getInstance()))return x.getInstance();
                RandomAltars.logger.error("Failed to find a suitable Instance Item for JEI display");
                return ItemStack.EMPTY;
            }
        }, count);
    }
    public ItemIngredient and(ItemIngredient b){return and(b,ItemStack.EMPTY);}
    public ItemIngredient xor(ItemIngredient b, ItemStack instance){
        return new ItemIngredient(new ItemMatcher() {
            ItemMatcher x=m;
            ItemMatcher y=b.m;
            ItemStack instance_=instance;
            @Override
            public boolean apply(ItemStack input) {
                return x.apply(input)^y.apply(input);
            }

            @Override
            public ItemStack getInstance() {
                if(!instance_.isEmpty())if(x.apply(instance_)^y.apply(instance_))return instance_;
                if(!x.apply(y.getInstance()))return y.getInstance();
                if(!y.apply(x.getInstance()))return x.getInstance();
                RandomAltars.logger.error("Failed to find a suitable Instance Item for JEI display");
                return ItemStack.EMPTY;
            }
        }, count);
    }
    public ItemIngredient xor(ItemIngredient b){return xor(b,ItemStack.EMPTY);}

    @Override
    public ItemStack getInstance() {
        if(isEmpty())return ItemStack.EMPTY;
        ItemStack t=m.getInstance();
        t.setCount(count);
        return t;
    }
}
interface ItemMatcher{
    public boolean apply(ItemStack input);
    public ItemStack getInstance();
    public static ItemMatcher EMPTY=new ItemMatcher() {
        @Override
        public boolean apply(ItemStack input) {
            return input.isEmpty()||input.getCount()==0;
        }
        @Override
        public ItemStack getInstance(){
            return ItemStack.EMPTY;
        }
    };
}