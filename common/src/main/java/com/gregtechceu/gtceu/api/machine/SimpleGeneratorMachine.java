package com.gregtechceu.gtceu.api.machine;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.machine.feature.IUIMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.UITemplate;
import com.gregtechceu.gtceu.api.gui.widget.PredicatedImageWidget;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableEnergyContainer;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.texture.GuiTextureGroup;
import com.lowdragmc.lowdraglib.gui.texture.ResourceBorderTexture;
import com.lowdragmc.lowdraglib.gui.texture.ResourceTexture;
import com.lowdragmc.lowdraglib.gui.texture.TextTexture;
import com.lowdragmc.lowdraglib.gui.widget.CycleButtonWidget;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.mojang.blaze3d.MethodsReturnNonnullByDefault;
import it.unimi.dsi.fastutil.ints.Int2LongFunction;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * @author KilaBash
 * @date 2023/3/17
 * @implNote SimpleGeneratorMachine
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SimpleGeneratorMachine extends WorkableTieredMachine implements IUIMachine {

    public SimpleGeneratorMachine(IMetaMachineBlockEntity holder, int tier, Int2LongFunction tankScalingFunction, Object... args) {
        super(holder, tier, tankScalingFunction, args);
    }

    //////////////////////////////////////
    //*****     Initialization    ******//
    //////////////////////////////////////

    @Override
    protected NotifiableEnergyContainer createEnergyContainer(Object... args) {
        var energyContainer = super.createEnergyContainer(args);
        energyContainer.setSideOutputCondition(side -> !hasFrontFacing() || side == getFrontFacing());
        return energyContainer;
    }

    @Override
    protected boolean isEnergyEmitter() {
        return true;
    }

    @Override
    protected long getMaxInputOutputAmperage() {
        return 1L;
    }

    @Override
    public int tintColor(int index) {
        if (index == 2) {
            return GTValues.VC[getTier()];
        }
        return super.tintColor(index);
    }

    //////////////////////////////////////
    //******     RECIPE LOGIC    *******//
    //////////////////////////////////////

    @Override
    public @Nullable GTRecipe modifyRecipe(GTRecipe recipe) {
        // we never use overclock but parallel logic
        var EUt = RecipeHelper.getOutputEUt(recipe);
        if (EUt > 0) {
            var maxParallel = (int)(Math.min(energyContainer.getOutputVoltage(), GTValues.V[overclockTier]) / EUt);
            while (maxParallel > 0) {
                var copied = recipe.copy(ContentModifier.multiplier(maxParallel));
                if (copied.matchRecipe(this)) {
                    copied.duration = copied.duration / maxParallel;
                    return copied;
                }
                maxParallel /= 2;
            }
        }
        return null;
    }

    @Override
    public boolean dampingWhenWaiting() {
        return false;
    }

    //////////////////////////////////////
    //***********     GUI    ***********//
    //////////////////////////////////////
    @Override
    public ModularUI createUI(Player entityPlayer) {
        int yOffset = 0;
        if (recipeType.getMaxInputs(ItemRecipeCapability.CAP) >= 6 || recipeType.getMaxInputs(FluidRecipeCapability.CAP) >= 6 || recipeType.getMaxOutputs(ItemRecipeCapability.CAP) >= 6 || recipeType.getMaxOutputs(FluidRecipeCapability.CAP) >= 6) {
            yOffset = 9;
        }
        var modularUI = new ModularUI(176, 166 + yOffset, this, entityPlayer)
                .background(GuiTextures.BACKGROUND)
                .widget(recipeType.createUITemplate(recipeLogic::getProgressPercent, importItems.storage, exportItems.storage, importFluids.storages, exportFluids.storages))
                .widget(new LabelWidget(6, 6, getBlockState().getBlock().getDescriptionId()))
                .widget(new PredicatedImageWidget(79, 42 + yOffset, 18, 18, new ResourceTexture("gtceu:textures/gui/base/indicator_no_energy.png"))
                        .setPredicate(recipeLogic::isHasNotEnoughEnergy))
                .widget(UITemplate.bindPlayerInventory(entityPlayer.getInventory(), GuiTextures.SLOT, 7, 84 + yOffset, true));

        int leftButtonStartX = 7;

        modularUI.widget(new CycleButtonWidget(leftButtonStartX, 62 + yOffset, 18, 18, getMaxOverclockTier() + 1,
                index -> new GuiTextureGroup(ResourceBorderTexture.BUTTON_COMMON, new TextTexture(GTValues.VNF[index])), index -> {
            overclockTier = index;
            if (!isRemote()) {
                recipeLogic.markLastRecipeDirty();
            }
        }).setIndexSupplier(() -> overclockTier).setHoverTooltips("gtceu.gui.overclock.description"));

        return modularUI;
    }


}
