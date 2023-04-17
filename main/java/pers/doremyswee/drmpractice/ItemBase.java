package pers.doremyswee.drmpractice;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ItemBase extends Item {
    public int metadata=0;
    public String name;
    public String id;
    ItemBase(String nameIn,int metadataIn,String idIn){
        super();
        id=idIn;
        metadata=metadataIn;
        name=nameIn;
        setRegistryName(RandomAltars.ID,name);
    }
    ItemBase(String nameIn){
        this(nameIn,0,nameIn);
    }
    public ItemBase reg(){
        Registry.register(this);
        return this;
    }
    public void regModel(){
        ModelLoader.setCustomModelResourceLocation(this,metadata,new ModelResourceLocation(getRegistryName(), "inventory"));
    }

}
