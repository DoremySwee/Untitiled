package pers.doremyswee.drmpractice;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = RandomAltars.ID)
public class BlockBase extends Block {
    public Item itemBlock=null;
    public int meta;
    public String name;
    public String id;
    public BlockBase(Material materialIn,String nameIn,int metaIn, String idIn) {
        super(materialIn);
        name=nameIn;
        meta=metaIn;
        id=idIn;
        setRegistryName(RandomAltars.ID,id);
    }
    public BlockBase(Material materialIn,String nameIn){
        this(materialIn,nameIn,0,nameIn);
    }
    public Item getItemBlock(){
        if(itemBlock==null)
            itemBlock=new ItemBlock(this).setRegistryName(RandomAltars.ID,id);
        return itemBlock;
    }
    Class<?extends TileEntity> teClass=null;
    public BlockBase setTE(Class<?extends TileEntity>in){
        teClass=in;
        return this;
    }
    public BlockBase reg(){
        Registry.register(this);
        return this;
    }
    public void regModel(){
        ModelLoader.setCustomModelResourceLocation(getItemBlock(),meta,new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
