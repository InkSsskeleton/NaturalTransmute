package com.zg.natural_transmute.client.gui;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.client.inventory.GatheringPlatformMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GatheringPlatformScreen extends AbstractContainerScreen<GatheringPlatformMenu> {

    private static final ResourceLocation PROGRESS_BAR = NaturalTransmute.prefix("gathering_bar");

    public GatheringPlatformScreen(GatheringPlatformMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBg(guiGraphics, partialTick, mouseX, mouseY);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        int time = Mth.ceil(this.menu.getGatheringTime() * 27.0F);
        ResourceLocation texture = NaturalTransmute.prefix("textures/gui/gathering_platform.png");
        guiGraphics.blit(texture, i, j, 0, 0, this.imageWidth, this.imageHeight);
        guiGraphics.blitSprite(PROGRESS_BAR, (27), (3), (0), (0), i + 76, j + 27, time, (3));
    }

}