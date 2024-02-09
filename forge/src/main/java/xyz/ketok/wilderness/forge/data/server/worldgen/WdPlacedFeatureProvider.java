package xyz.ketok.wilderness.forge.data.server.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import xyz.ketok.wilderness.registry.dynamic.WdConfiguredFeatures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static xyz.ketok.wilderness.registry.dynamic.WdPlacedFeatures.*;

public class WdPlacedFeatureProvider {
    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        var configured = context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(FALLEN_OAK, surfaceFeature(configured.getOrThrow(WdConfiguredFeatures.FALLEN_OAK), RarityFilter.onAverageOnceEvery(8), PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING))); //TODO: Tree threshold?
        context.register(FALLEN_BIRCH, surfaceFeature(configured.getOrThrow(WdConfiguredFeatures.FALLEN_BIRCH), RarityFilter.onAverageOnceEvery(8), PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)));
        context.register(FALLEN_SPRUCE, surfaceFeature(configured.getOrThrow(WdConfiguredFeatures.FALLEN_SPRUCE), RarityFilter.onAverageOnceEvery(8), PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)));
        context.register(FALLEN_JUNGLE_TREE, surfaceFeature(configured.getOrThrow(WdConfiguredFeatures.FALLEN_JUNGLE_TREE), RarityFilter.onAverageOnceEvery(8), PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)));

        context.register(MEDIUM_OAK, treeFeature(configured.getOrThrow(WdConfiguredFeatures.MEDIUM_OAK)));
        context.register(MOSSY_FANCY_OAK, treeFeature(configured.getOrThrow(WdConfiguredFeatures.MOSSY_FANCY_OAK)));

        context.register(TREES_OLD_GROWTH_FOREST, surfaceFeature(configured.getOrThrow(WdConfiguredFeatures.TREES_OLD_GROWTH_FOREST), CountPlacement.of(10)));
        context.register(TREES_MIXED_FOREST, surfaceFeature(configured.getOrThrow(WdConfiguredFeatures.TREES_MIXED_FOREST), CountPlacement.of(8)));

        context.register(BROWN_RED_MUSHROOM_PATCH, surfaceFeature(configured.getOrThrow(WdConfiguredFeatures.BROWN_RED_MUSHROOM_PATCH), null));
        context.register(FOREST_ROCK_RARE, surfaceFeature(configured.getOrThrow(MiscOverworldFeatures.FOREST_ROCK), RarityFilter.onAverageOnceEvery(4)));

        context.register(PATCH_COARSE_DIRT, surfaceFeature(configured.getOrThrow(WdConfiguredFeatures.PATCH_COARSE_DIRT), CountPlacement.of(2)));
        context.register(PATCH_PODZOL, surfaceFeature(configured.getOrThrow(WdConfiguredFeatures.PATCH_PODZOL), CountPlacement.of(1)));
        context.register(PATCH_MOSS, surfaceFeature(configured.getOrThrow(WdConfiguredFeatures.PATCH_MOSS), CountPlacement.of(2)));
    }

    private static PlacedFeature surfaceFeature(Holder<ConfiguredFeature<?, ?>> feature, PlacementModifier countOrRarity, PlacementModifier... placementModifiers) {
        ArrayList<PlacementModifier> modifiers = new ArrayList<>();
        if (countOrRarity != null) modifiers.add(countOrRarity);
        modifiers.addAll(List.of(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR));
        Collections.addAll(modifiers, placementModifiers);
        modifiers.add(BiomeFilter.biome());
        return new PlacedFeature(feature, modifiers);
    }

    private static PlacedFeature treeFeature(Holder<ConfiguredFeature<?, ?>> feature) {
        return new PlacedFeature(feature, List.of(PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING), SurfaceWaterDepthFilter.forMaxDepth(0)));
    }
}