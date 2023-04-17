package pers.doremyswee.drmpractice;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityBase extends TileEntity {
    public TileEntityBase(){super();}
    @Override
    public SPacketUpdateTileEntity getUpdatePacket(){return new SPacketUpdateTileEntity(getPos(), 1, writeToNBT(new NBTTagCompound()));}
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt){
        readFromNBT(pkt.getNbtCompound());
    }
    @Override
    public NBTTagCompound getUpdateTag(){return writeToNBT(new NBTTagCompound());}
    @Override
    public void handleUpdateTag(NBTTagCompound in){readFromNBT(in);}
}
