package pers.doremyswee.drmpractice;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import pers.doremyswee.drmpractice.fussion.FluidPedestal;
import pers.doremyswee.drmpractice.fussion.FussionAltar;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = RandomAltars.ID)
public class Registry {
    static List<BlockBase> blocks=new ArrayList<BlockBase>();
    static List<ItemBase> items=new ArrayList<ItemBase>();
    public static void register(BlockBase in){
        blocks.add(in);
    }
    public static void register(ItemBase in){
        items.add(in);
    }
    public static void init(){
        new FluidPedestal();
        new FussionAltar();
    }
    @SubscribeEvent
    public static void registerBlock(RegistryEvent.Register<Block> event) {
        for(BlockBase i:blocks){
            event.getRegistry().register(i);
            if(i.teClass!=null){
                GameRegistry.registerTileEntity(i.teClass,new ResourceLocation(RandomAltars.ID, i.id));
            }
        }
    }
    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        for(Item i:items)event.getRegistry().register(i);
        for(BlockBase i:blocks)event.getRegistry().register(i.getItemBlock());
    }
}
