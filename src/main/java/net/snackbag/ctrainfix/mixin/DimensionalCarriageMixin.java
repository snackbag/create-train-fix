package net.snackbag.ctrainfix.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.trains.entity.Carriage;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.snackbag.ctrainfix.CreateTrainFix;
import net.snackbag.ctrainfix.RefUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Carriage.DimensionalCarriageEntity.class)
public abstract class DimensionalCarriageMixin {
	@Shadow public Vec3d positionAnchor;

	@Shadow(remap = false) private Carriage this$0;

	@Inject(at = @At("HEAD"), method = "createEntity")
	private void createTrainFix$fixEntity(World level, boolean loadPassengers, CallbackInfo ci) {
		try {
			NbtCompound serialisedEntity = (NbtCompound) RefUtil.getPrivateFieldValue(this$0, "serialisedEntity");
			serialisedEntity.remove("Pos");
			serialisedEntity.put("Pos", newDoubleList(positionAnchor.x, positionAnchor.y, positionAnchor.z));
			RefUtil.setFieldValue(this$0, "serialisedEntity", serialisedEntity);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			CreateTrainFix.LOGGER.error("(CreateTrainFix) Failed to fix train position");
			throw new RuntimeException(e);
		}

		if (!Double.isFinite(positionAnchor.getX()) || !Double.isFinite(positionAnchor.getY()) || !Double.isFinite(positionAnchor.getZ())) {
			CreateTrainFix.LOGGER.info("Train failed to be created, because of infinity checks.");
		}
	}

	@Unique
	private static NbtList newDoubleList(double... pValues) {
		NbtList listTag = new NbtList();
		for (double d0 : pValues)
			listTag.add(NbtDouble.of(d0));
		return listTag;
	}
}