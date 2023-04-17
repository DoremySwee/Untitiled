package pers.doremyswee.drmpractice.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class RenderUtils {
    public static void renderTextureAtlasSprite(TextureAtlasSprite sprite, long[]ARGB, int brightness, Vector3d[]vertexes, double[]percentU,double[]percentV){
        if(vertexes.length<4)return;
        if(percentU.length<4)return;
        if(percentV.length<4)return;
        if(ARGB.length<4)return;
        Tessellator tessellator=Tessellator.getInstance();
        BufferBuilder renderer=tessellator.getBuffer();
        renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        for(int i=0;i<4;i++){
            renderer.pos(vertexes[i].x,vertexes[i].y,vertexes[i].z)
                    .color(1f*(ARGB[i]>>16&0xFFl)/255f,1f*(ARGB[i]>>8&0xFFl)/255f,1f*(ARGB[i]&0xFFl)/255f,ARGB[i]<0x1000000?1f:1f*(ARGB[i]>>24&0xFFl)/255f)
                    .tex(sprite.getMinU()+percentU[i]*(sprite.getMaxU()-sprite.getMinU()),sprite.getMinV()+percentV[i]*(sprite.getMaxV()-sprite.getMinV()))
                    .lightmap(brightness >> 0x10 & 0xFFFF,brightness & 0xFFFF)
                    .endVertex();
        }
        tessellator.draw();
    }
    public static void renderTextureAtlasSprite(TextureAtlasSprite sprite, long ARGB, int brightness, Vector3d[]vertexes, double[]percentU,double[]percentV){
        renderTextureAtlasSprite(sprite, new long[]{ARGB, ARGB, ARGB, ARGB},brightness,vertexes,percentU,percentV);
    }
    static Vector3d formVector3d(double x,double y,double z){Vector3d temp=new Vector3d();temp.x=x;temp.y=y;temp.z=z;return temp;}
    public static void renderTextureAtlasSprite(TextureAtlasSprite sprite, long ARGB, int brightness, boolean flip){
        if(flip)    renderTextureAtlasSprite(sprite, new long[]{ARGB, ARGB, ARGB, ARGB},brightness,
                new Vector3d[]{formVector3d(0,0,0),formVector3d(1,0,0),formVector3d(1,0,1),formVector3d(0,0,1)},
                new double[]{0,1,1,0},new double[]{0,0,1,1});
            else    renderTextureAtlasSprite(sprite, new long[]{ARGB, ARGB, ARGB, ARGB},brightness,
                new Vector3d[]{formVector3d(0,0,0),formVector3d(0,0,1),formVector3d(1,0,1),formVector3d(1,0,0)},
                new double[]{0,0,1,1},new double[]{0,1,1,0});
    }
    public static void renderTextureAtlasSprite(TextureAtlasSprite sprite, int brightness, Vector3d[]vertexes, double[]percentU,double[]percentV){
        renderTextureAtlasSprite(sprite,0xFFFFFFl,brightness,vertexes,percentU,percentV);
    }
    public static void renderTextureAtlasSprite(TextureAtlasSprite sprite, Vector3d[]vertexes, double[]percentU,double[]percentV){
        renderTextureAtlasSprite(sprite,0xF000F0,vertexes,percentU,percentV);
    }
    public static void renderTextureAtlasSprite(TextureAtlasSprite sprite, int brightness, boolean flip){
        renderTextureAtlasSprite(sprite,0xFFFFFFl,brightness,flip);
    }
    public static void renderTextureAtlasSprite(TextureAtlasSprite sprite, boolean flip){
        renderTextureAtlasSprite(sprite,0xF000F0,flip);
    }
    public static void renderFluidPiece(Fluid fluid){
        TextureAtlasSprite still = Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(fluid.getStill(new FluidStack(fluid,1)).toString());
        if(still == null) {
            still = Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();
        }
        if(true){
            /*GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.disableCull();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.color(1F, 1F, 1F, 1F);*/
            GlStateManager.pushMatrix();
            renderTextureAtlasSprite(still,true);
            renderTextureAtlasSprite(still,false);
            GlStateManager.popMatrix();
            /*GlStateManager.enableCull();
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();*/
        }
        else{
            renderTextureAtlasSprite(still,
                    new Vector3d[]{formVector3d(0,0,0),formVector3d(1,0,0),formVector3d(0,0,0),formVector3d(0,0,1)},
                    new double[]{0,1,1,0},new double[]{0,0,1,1});
        }
    }
}/*
public class RenderUtils {
    public static void renderFluidPiece(Fluid fluid){
        TextureAtlasSprite still = Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(fluid.getStill(new FluidStack(fluid,1)).toString());
        if(still == null) {
            still = Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();
        }
        renderBothFaces(still,0xFFFFFFFFL);
    }
    public static void renderFlowingFluidPiece(Fluid fluid){
        TextureAtlasSprite flow = Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(fluid.getFlowing(new FluidStack(fluid,1)).toString());
        if(flow == null) {
            flow = Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();
        }
        renderBothFaces(flow,0xFFFFFFFFL);
    }
    public static void renderTextureAtlasSprite(TextureAtlasSprite sprite, long colorIn, boolean flip){
        Tessellator tessellator=Tessellator.getInstance();
        BufferBuilder renderer=tessellator.getBuffer();
        int brightness = Minecraft.getMinecraft().world.getCombinedLight(new BlockPos(0,64,0), FluidRegistry.WATER.getLuminosity());
        int l1=1;//brightness >> 0x10 & 0xFFFF;
        int l2=1;//brightness & 0xFFFF;
        renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        // Textures of different blocks are integrated in a png file
        // UV are the coordinates that the wanted texture locates in
        // We may vary the wanted UV so that only part of the texture presents
        double minU=sprite.getMinU();
        double maxU=sprite.getMaxU();
        double minV=sprite.getMinV();
        double maxV=sprite.getMaxV();
        float r=((float)((colorIn>>24)%256))/255F;
        float g=((float)((colorIn>>16)%256))/255F;
        float b=((float)((colorIn>>8)%256))/255F;
        float a=((float)((colorIn)%256))/255F;
        if(r==0)r=1F;
        //The order of ".pos.tex.color" matters, corresponding to the "VertexFormats".
        /*
        if(flip){
            renderer.pos(0, 0, 0).color(r, g, b, a).tex(minU, minV).lightmap(l1,l2).endVertex();
            renderer.pos(1, 0, 0).color(r, g, b, a).tex(maxU, minV).lightmap(l1,l2).endVertex();
            renderer.pos(1, 0, 1).color(r, g, b, a).tex(maxU, maxV).lightmap(l1,l2).endVertex();
            renderer.pos(0, 0, 1).color(r, g, b, a).tex(minU, maxV).lightmap(l1,l2).endVertex();
        }
        else {
            renderer.pos(0, 0, 0).color(r, g, b, a).tex(minU, minV).lightmap(l1,l2).endVertex();
            renderer.pos(0, 0, 1).color(r, g, b, a).tex(minU, maxV).lightmap(l1,l2).endVertex();
            renderer.pos(1, 0, 1).color(r, g, b, a).tex(maxU, maxV).lightmap(l1,l2).endVertex();
            renderer.pos(1, 0, 0).color(r, g, b, a).tex(maxU, minV).lightmap(l1,l2).endVertex();
        }/*
        renderer.pos(0, 0, 0).color(r, g, b, a).tex(minU, minV).lightmap(l1,l2).endVertex();
        renderer.pos(0, 0, 1).color(r, g, b, a).tex(minU, maxV).lightmap(l1,l2).endVertex();
        renderer.pos(0, 1, 0).color(r, g, b, a).tex(maxU, maxV).lightmap(l1,l2).endVertex();
        renderer.pos(1, 0, 0).color(r, g, b, a).tex(maxU, minV).lightmap(l1,l2).endVertex();/**
        tessellator.draw();
    }
    public static void renderTextureAtlasSprite(TextureAtlasSprite sprite, long colorIn){
        renderTextureAtlasSprite(sprite,colorIn,false);
    }
    public static void renderBothFaces(TextureAtlasSprite sprite, long colorIn){
        renderTextureAtlasSprite(sprite,colorIn);
        renderTextureAtlasSprite(sprite,colorIn,true);
    }
}
*/