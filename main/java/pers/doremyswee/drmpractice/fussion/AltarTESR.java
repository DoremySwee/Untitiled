package pers.doremyswee.drmpractice.fussion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

import javax.vecmath.Quat4d;

public class AltarTESR extends TileEntitySpecialRenderer<FussionAltarTE>{
    public void renderItem(ItemStack stack) {
        //from brandon's core
        if (!stack.isEmpty()) {
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
        }
    }
    @Override
    public void render(FussionAltarTE tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if(tile.progress<0){
            int PERIOD=477;
            double R1=1; double R2=0.3; double y0=1.3;
            int time=(int)(tile.getWorld().getTotalWorldTime())%PERIOD;
            double t0=((double)(time+partialTicks))/PERIOD*1080;
            GlStateManager.pushMatrix();
            GlStateManager.translate(x+0.5,y+y0,z+0.5);
            for(int i=0;i<tile.contents.size();i++){
                double t1=t0+210*i;
                GlStateManager.pushMatrix();
                GlStateManager.rotate(((float)((tile.getWorld().getTotalWorldTime())%1700))/1700F*1080F,0,1,0);
                GlStateManager.rotate((float)(t1),0,1,0);
                GlStateManager.translate(R1,0,0);
                GlStateManager.rotate((float)t1/3F,0,0,1);
                GlStateManager.translate(0,R2,0);
                GlStateManager.scale(0.5,0.5,0.5);
                GlStateManager.color(1F, 1F, 1F, 1F);
                GlStateManager.enableLighting();
                renderItem(tile.contents.get(i));
                GlStateManager.popMatrix();
            }
            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean isGlobalRenderer(FussionAltarTE in) {
        return in.progress>=0;
    }
}
