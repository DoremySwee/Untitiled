package pers.doremyswee.drmpractice;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = RandomAltars.ID)
public class ClientRegistry{
    @SubscribeEvent
    public static void onModelReg(ModelRegistryEvent event) {
        for(ItemBase i:Registry.items)i.regModel();
        for(BlockBase i:Registry.blocks)i.regModel();
    }
    @SubscribeEvent
    public static void bakeModel(ModelBakeEvent e) {
    }
}
