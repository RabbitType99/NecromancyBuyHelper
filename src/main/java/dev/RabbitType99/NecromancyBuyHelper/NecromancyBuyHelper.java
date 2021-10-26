package dev.RabbitType99.NecromancyBuyHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;


import java.util.Arrays;

public class NecromancyBuyHelper {
    private static int totalSouls=0;

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        if (event.toolTip == null) return;
        ItemStack item = event.itemStack;

        if (Arrays.stream(checkForSouls(item,true)).anyMatch(val -> val)) {




            for (int i = 0; i < event.toolTip.size(); i++){
               if (event.toolTip.get(i).contains("Absorbed Souls:")){
                   for (int j = 0; j < totalSouls; j++){
                       String lineToReplace = event.toolTip.get(i+1+j);
                       event.toolTip.remove(i+1+j);
                       event.toolTip.add(i+1+j,lineToReplace + " - §r§5§3MASTERMODE SOUL");
                   }
                   break;
               }
            }


        }



    }

    @SubscribeEvent
    public void onGuiRender(GuiScreenEvent.BackgroundDrawnEvent event) {
        if (event.gui instanceof GuiChest) {
            if (Minecraft.getMinecraft().currentScreen instanceof GuiChest) {
                ContainerChest chest = ((ContainerChest) Minecraft.getMinecraft().thePlayer.openContainer);
                String chestName = chest.getLowerChestInventory().getDisplayName().getUnformattedText().trim();
                if (chestName.startsWith("Auctions:")) {
                    for (Slot slot: ((GuiChest) event.gui).inventorySlots.inventorySlots ) {
                        if (slot.getStack() != null) {

                            if (Arrays.stream(checkForSouls(slot.getStack(),false)).anyMatch(val -> val)) {
                                if (Arrays.stream(checkForSouls(slot.getStack(),false)).allMatch(val -> val)) {
                                    drawOnSlot(slot.xDisplayPosition, slot.yDisplayPosition, 0xE500b300);

                                }
                                else {
                                    drawOnSlot(slot.xDisplayPosition, slot.yDisplayPosition,  0xE5e05a00);
                                }

                            }
                        }
                    }


                }
                if (chestName.startsWith("You")){
                    for (Slot slot: ((GuiChest) event.gui).inventorySlots.inventorySlots ) {
                        if (slot.getStack() != null) {

                            if (Arrays.stream(checkForSouls(slot.getStack(),false)).anyMatch(val -> val)) {
                                if (Arrays.stream(checkForSouls(slot.getStack(),false)).allMatch(val -> val)) {
                                    drawOnSlot(slot.xDisplayPosition, slot.yDisplayPosition+9, 0xE500b300);

                                }
                                else {
                                    drawOnSlot(slot.xDisplayPosition, slot.yDisplayPosition+9,  0xE5e05a00);
                                }

                            }
                        }
                    }

                }
            }
        }
    }

    public static void drawOnSlot(int x, int y, int color) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int xInGui =(sr.getScaledWidth() - 176) / 2 +x;
        int yInGui =(sr.getScaledHeight() - 222) / 2 +y;
        GL11.glTranslated(0, 0, 1);
        Gui.drawRect(xInGui, yInGui, xInGui + 16, yInGui + 16, color);
        GL11.glTranslated(0, 0, -1);
    }

    public static Boolean[] checkForSouls(ItemStack item,boolean refresh) {
        Boolean[] all_mastersouls = new Boolean[1];
        all_mastersouls[0] = false;
        if (item.hasTagCompound()) {
            NBTTagCompound tags = item.getSubCompound("ExtraAttributes", false);
            if (tags != null) {
                if (tags.hasKey("necromancer_souls")) {
                    NBTTagList necromancer_souls = (NBTTagList) tags.getTag("necromancer_souls");
                    if (necromancer_souls.tagCount() != 0) {
                        all_mastersouls = new Boolean[necromancer_souls.tagCount()];
                        if (refresh) totalSouls = necromancer_souls.tagCount();
                        for (int i = 0; i < necromancer_souls.tagCount(); i++) {
                            NBTBase base = necromancer_souls.get(i);
                            all_mastersouls[i] = base.toString().contains("MASTER_");
                        }
                    }
                }
            }
        }


        return all_mastersouls;
    }
}