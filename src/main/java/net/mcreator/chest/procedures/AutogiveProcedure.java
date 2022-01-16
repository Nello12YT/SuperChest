package net.mcreator.chest.procedures;

import net.minecraftforge.eventbus.api.Event;

public class AutogiveProcedure {

	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
			Entity entity = event.getPlayer();
			Map<String, Object> dependencies = new HashMap<>();
			dependencies.put("x", entity.getPosX());
			dependencies.put("y", entity.getPosY());
			dependencies.put("z", entity.getPosZ());
			dependencies.put("world", entity.world);
			dependencies.put("entity", entity);
			dependencies.put("event", event);
			executeProcedure(dependencies);
		}
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				ChestMod.LOGGER.warn("Failed to load dependency entity for procedure Autogive!");
			return;
		}

		Entity entity = (Entity) dependencies.get("entity");

		if ((entity.getCapability(ChestModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new ChestModVariables.PlayerVariables())).primavoltaentrato == true) {
			{
				boolean _setval = (true);
				entity.getCapability(ChestModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.primavoltaentrato = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		} else {
			if (entity instanceof PlayerEntity) {
				ItemStack _setstack = new ItemStack(Chest5Block.block);
				_setstack.setCount((int) 1);
				ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) entity), _setstack);
			}
			{
				boolean _setval = (true);
				entity.getCapability(ChestModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.primavoltaentrato = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
	}

}
