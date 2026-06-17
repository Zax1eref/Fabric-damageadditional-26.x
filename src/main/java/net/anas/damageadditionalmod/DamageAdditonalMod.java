package net.anas.damageadditionalmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class DamageAdditonalMod implements ModInitializer {
	@Override
	public void onInitialize() {
		DamageAdditionalConfig.load();
		DamageAdditionalCommands.register();

		ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
			if (entity instanceof Mob && damageSource.getEntity() instanceof Player player) {
				AttributeInstance attackDamage = player.getAttribute(Attributes.ATTACK_DAMAGE);
				if (attackDamage != null) {
					attackDamage.setBaseValue(attackDamage.getBaseValue() + DamageAdditionalConfig.INSTANCE.killDamageBonus);
				}
			}
		});
	}
}

// DamageAdditonalMod