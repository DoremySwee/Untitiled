package pers.doremyswee.drmpractice.fussion;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import pers.doremyswee.drmpractice.TileEntityBase;

import java.util.ArrayList;
import java.util.List;

public class FussionAltarTE extends TileEntityBase implements ITickable {
    float progress=-1;
    boolean powered=false;
    public static final int MAX_ITEMS=36;
    List<ItemStack> contents=new ArrayList<ItemStack>();
    List<BlockPos> pedestals=new ArrayList<BlockPos>();
    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.progress = tag.getFloat("Progress");
        this.powered=tag.getBoolean("Powered");
        this.contents = new ArrayList<ItemStack>();
        NBTTagList contentsNBT=tag.getTagList("contents",10);
        for(int i=0;i<contentsNBT.tagCount();i++){
            contents.add(new ItemStack(contentsNBT.getCompoundTagAt(i)));
        }
    }
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setFloat("Progress", this.progress);
        tag.setBoolean("Powered",powered);
        NBTTagList contentsNBT=new NBTTagList();
        for(int i=0;i<contents.size();i++){
            contentsNBT.appendTag(contents.get(i).serializeNBT());
        }
        tag.setTag("contents",contentsNBT);
        return super.writeToNBT(tag);
    }
    public boolean addItem(ItemStack in){
        if(in.isEmpty())return false;
        if(in.getCount()<1)return false;
        if(contents.size()>=MAX_ITEMS)return false;
        ItemStack temp=in.copy();
        temp.setCount(1);
        contents.add(temp);
        /*if(in.getCount()<2)in=null;
        else in.setCount(in.getCount()-1);*/
        return true;
    }
    @Override
    public void update() {
        if (world != null && !world.isRemote) {
            if(this.progress<0){
                BlockPos p=this.getPos();
                boolean f=false;
                List<EntityItem>entities=
                    world.<EntityItem>getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(
                        p.getX(),p.getY()+1,p.getZ(),p.getX()+1,p.getY()+1.25,p.getZ()+1
                    ));
                for(EntityItem i:entities){
                    if(contents.size()>=MAX_ITEMS)break;
                    f=true;
                    ItemStack t=i.getItem();
                    while(addItem(t)){
                        if(t.getCount()>1){
                            t.setCount(t.getCount()-1);
                            i.setItem(t);
                        }
                        else{
                            i.setDead();
                            break;
                        }
                    }
                }
                if(f)world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
            }
            else{
                //TODO
                this.progress=-1;
            }
        }
    }
}
