package pers.doremyswee.drmpractice.fussion;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import pers.doremyswee.drmpractice.TileEntityBase;
import pers.doremyswee.drmpractice.utils.NBTHelper;

public class FluidPedestalTE extends TileEntityBase {
    public FluidStack content=null;
    BlockPos altarPos=null;
    Boolean onUse=false;
    public FluidPedestalTE(){
        super();content=null;altarPos=null;onUse=false;
    }
    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        if(tag.hasKey("content"))content=FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("content"));
        else content=null;
        if(tag.hasKey("master"))altarPos=NBTHelper.formBlockPos(tag.getCompoundTag("master"));
        else altarPos=null;
        onUse=tag.getBoolean("onUse");
    }
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setBoolean("onUse", onUse);
        if (content != null) if (content.amount > 0) tag.setTag("content", content.writeToNBT(new NBTTagCompound()));
        if (altarPos != null) tag.setTag("master", NBTHelper.serializeBlockPos(altarPos));
        return super.writeToNBT(tag);
    }
}
