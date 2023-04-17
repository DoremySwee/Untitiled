package pers.doremyswee.drmpractice.fussion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import org.lwjgl.opengl.GL11;
import pers.doremyswee.drmpractice.utils.RenderUtils;

public class FluidPedestalTESR extends TileEntitySpecialRenderer<FluidPedestalTE> {
    @Override
    public void render(FluidPedestalTE tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x,y+1f/4096f,z);
        render(tile.content);
        //RenderUtils.renderFluidPiece(FluidRegistry.WATER);
        GlStateManager.popMatrix();
    }
    public static void render(FluidStack content){
        RenderUtils.renderFluidPiece(FluidRegistry.WATER);
    }
    @Override
    public boolean isGlobalRenderer(FluidPedestalTE in) {
        return true;
    }
}
