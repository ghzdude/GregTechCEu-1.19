package com.gregtechceu.gtceu.common.data;

import com.gregtechceu.gtceu.data.recipe.chemistry.*;
import com.gregtechceu.gtceu.data.recipe.handler.*;
import com.gregtechceu.gtceu.data.recipe.misc.*;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.tterrag.registrate.providers.ProviderType;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

/**
 * @author KilaBash
 * @date 2023/2/21
 * @implNote GTRecipes
 */
public class GTRecipes {
    public static void init() {
        // chemistry
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, DistillationRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, AcidRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, AssemblerRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, BrewingRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, ChemicalBathRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, ChemistryRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, GemSlurryRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, GrowthMediumRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, LCRCombinedRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, MixerRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, NaquadahRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, NuclearRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, PetrochemRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, PlatGroupMetalsRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, PolymerRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, ReactorRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, SeparationRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, FurnaceRecipes::init);
        // misc
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, BatteryRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, CircuitRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, ComponentRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, MiscRecipeRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, FuelRecipes::init);
    }

    public static void autoGenerated(Consumer<FinishedRecipe> consumer) {
        // handler
        MaterialRecipeHandler.init(consumer);
        WireRecipeHandler.init(consumer);
        PipeRecipeHandler.init(consumer);
        WireCombiningHandler.init(consumer);
        ToolRecipeHandler.init(consumer);
        DecompositionRecipeHandler.init(consumer);
        PartsRecipeHandler.init(consumer);
        PolarizingRecipeHandler.init(consumer);
        RecyclingRecipeHandler.init(consumer);
//        ToolRecipeHandler.init(consumer);
        // fuel
        FuelRecipes.initFuel(consumer);
    }

}
