package com.github.iunius118.tolaserblade.client.color.item;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColor;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeVisual;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;

public class LaserBladeItemColor {
    public final boolean isBroken;
    public final int rawGripColor;
    public final int gripColor;
    public final int rawInnerColor;
    public final int innerColor;
    public final boolean isInnerSubColor;
    public final int simpleInnerColor;
    public final int rawOuterColor;
    public final int outerColor;
    public final boolean isOuterSubColor;
    public final int simpleOuterColor;

    public LaserBladeItemColor(ItemStack itemStack) {
        if (itemStack == null || itemStack.isEmpty()) {
            isBroken = false;

            gripColor = -1;
            rawGripColor = -1;
            rawInnerColor = -1;

            innerColor = -1;
            isInnerSubColor = false;
            simpleInnerColor = -1;

            rawOuterColor = -1;
            outerColor = -1;
            isOuterSubColor = false;
            simpleOuterColor = -1;

            return;
        }

        // var item = itemStack.getItem();
        // isBroken = (item instanceof LBBrokenItem|| item instanceof LBBrandNewItem);
        isBroken = false;

        LaserBladeVisual visual = LaserBlade.visualOf(itemStack);

        LaserBladeVisual.PartColor gripPartColor = visual.getGripColor();
        rawGripColor = gripPartColor.color;
        gripColor = checkGamingColor(rawGripColor);

        LaserBladeVisual.PartColor innerPartColor = visual.getInnerColor();
        rawInnerColor = innerPartColor.color;
        innerColor = checkGamingColor(rawInnerColor);
        isInnerSubColor = innerPartColor.isSubtractColor;
        simpleInnerColor = (isInnerSubColor ? ~innerColor : innerColor) | 0xFF000000;

        LaserBladeVisual.PartColor outerPartColor = visual.getOuterColor();
        rawOuterColor = outerPartColor.color;
        outerColor = checkGamingColor(rawOuterColor);
        isOuterSubColor = outerPartColor.isSubtractColor;
        simpleOuterColor = (isOuterSubColor ? ~outerColor : outerColor) | 0xFF000000;
    }

    public static LaserBladeItemColor of(ItemStack stack) {
        return new LaserBladeItemColor(stack);
    }

    public int checkGamingColor(int color) {
        if (color == LaserBladeColor.SPECIAL_GAMING.getBladeColor()) {
            return getGamingColor();
        }

        return color;
    }

    private int getGamingColor() {
        var minecraft = Minecraft.getInstance();
        var player = minecraft.player;

        if (player != null) {
            float partialTick = minecraft.getFrameTime();
            var level = player.level;
            int tick1 = (int)(level.getGameTime() % 30);
            int tick2 = tick1 % 10;
            int colorElement;

            if (tick2 % 10 < 5) {
                colorElement = (int)(((float)tick2 + partialTick) * (float)0x33) & 0xFF;

                return switch (tick1 / 10) {
                    case 0 -> 0xFFFF0000 | (colorElement << 8);
                    case 1 -> 0xFF00FF00 | colorElement;
                    default -> 0xFF0000FF | (colorElement << 16);
                };
            } else {
                colorElement = (int)(((float)(10 - tick2) - partialTick) * (float)0x33) & 0xFF;

                return switch (tick1 / 10) {
                    case 0 -> 0xFF00FF00 | (colorElement << 16);
                    case 1 -> 0xFF0000FF | (colorElement << 8);
                    default -> 0xFFFF0000 | colorElement;
                };
            }


        }

        return 0xFF010101;
    }
}
