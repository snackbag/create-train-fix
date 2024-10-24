package net.snackbag.ctrainfix;

import net.fabricmc.api.ModInitializer;

import net.minecraft.nbt.NbtCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateTrainFix implements ModInitializer {
	public static NbtCompound capturedSerialisedEntity = null;
    public static final Logger LOGGER = LoggerFactory.getLogger("create-trainfix");

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
	}
}