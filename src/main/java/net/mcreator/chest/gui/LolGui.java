
package net.mcreator.chest.gui;

import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.container.Slot;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Container;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.gui.ScreenManager;

import net.mcreator.chest.ChestModElements;
import net.mcreator.chest.ChestMod;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;

@ChestModElements.ModElement.Tag
public class LolGui extends ChestModElements.ModElement {
	public static HashMap guistate = new HashMap();
	private static ContainerType<GuiContainerMod> containerType = null;

	public LolGui(ChestModElements instance) {
		super(instance, 1);
		elements.addNetworkMessage(ButtonPressedMessage.class, ButtonPressedMessage::buffer, ButtonPressedMessage::new,
				ButtonPressedMessage::handler);
		elements.addNetworkMessage(GUISlotChangedMessage.class, GUISlotChangedMessage::buffer, GUISlotChangedMessage::new,
				GUISlotChangedMessage::handler);
		containerType = new ContainerType<>(new GuiContainerModFactory());
		FMLJavaModLoadingContext.get().getModEventBus().register(new ContainerRegisterHandler());
	}

	private static class ContainerRegisterHandler {
		@SubscribeEvent
		public void registerContainer(RegistryEvent.Register<ContainerType<?>> event) {
			event.getRegistry().register(containerType.setRegistryName("lol"));
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void initElements() {
		DeferredWorkQueue.runLater(() -> ScreenManager.registerFactory(containerType, LolGuiWindow::new));
	}

	public static class GuiContainerModFactory implements IContainerFactory {
		public GuiContainerMod create(int id, PlayerInventory inv, PacketBuffer extraData) {
			return new GuiContainerMod(id, inv, extraData);
		}
	}

	public static class GuiContainerMod extends Container implements Supplier<Map<Integer, Slot>> {
		World world;
		PlayerEntity entity;
		int x, y, z;
		private IItemHandler internal;
		private Map<Integer, Slot> customSlots = new HashMap<>();
		private boolean bound = false;

		public GuiContainerMod(int id, PlayerInventory inv, PacketBuffer extraData) {
			super(containerType, id);
			this.entity = inv.player;
			this.world = inv.player.world;
			this.internal = new ItemStackHandler(184);
			BlockPos pos = null;
			if (extraData != null) {
				pos = extraData.readBlockPos();
				this.x = pos.getX();
				this.y = pos.getY();
				this.z = pos.getZ();
			}
			if (pos != null) {
				if (extraData.readableBytes() == 1) { // bound to item
					byte hand = extraData.readByte();
					ItemStack itemstack;
					if (hand == 0)
						itemstack = this.entity.getHeldItemMainhand();
					else
						itemstack = this.entity.getHeldItemOffhand();
					itemstack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						this.internal = capability;
						this.bound = true;
					});
				} else if (extraData.readableBytes() > 1) {
					extraData.readByte(); // drop padding
					Entity entity = world.getEntityByID(extraData.readVarInt());
					if (entity != null)
						entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
							this.internal = capability;
							this.bound = true;
						});
				} else { // might be bound to block
					TileEntity ent = inv.player != null ? inv.player.world.getTileEntity(pos) : null;
					if (ent != null) {
						ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
							this.internal = capability;
							this.bound = true;
						});
					}
				}
			}
			this.customSlots.put(0, this.addSlot(new SlotItemHandler(internal, 0, 10, 11) {
			}));
			this.customSlots.put(1, this.addSlot(new SlotItemHandler(internal, 1, 28, 11) {
			}));
			this.customSlots.put(2, this.addSlot(new SlotItemHandler(internal, 2, 46, 11) {
			}));
			this.customSlots.put(3, this.addSlot(new SlotItemHandler(internal, 3, 64, 11) {
			}));
			this.customSlots.put(4, this.addSlot(new SlotItemHandler(internal, 4, 82, 11) {
			}));
			this.customSlots.put(5, this.addSlot(new SlotItemHandler(internal, 5, 100, 11) {
			}));
			this.customSlots.put(6, this.addSlot(new SlotItemHandler(internal, 6, 118, 11) {
			}));
			this.customSlots.put(7, this.addSlot(new SlotItemHandler(internal, 7, 136, 11) {
			}));
			this.customSlots.put(8, this.addSlot(new SlotItemHandler(internal, 8, 154, 11) {
			}));
			this.customSlots.put(9, this.addSlot(new SlotItemHandler(internal, 9, 172, 11) {
			}));
			this.customSlots.put(10, this.addSlot(new SlotItemHandler(internal, 10, 190, 11) {
			}));
			this.customSlots.put(11, this.addSlot(new SlotItemHandler(internal, 11, 208, 11) {
			}));
			this.customSlots.put(12, this.addSlot(new SlotItemHandler(internal, 12, 226, 11) {
			}));
			this.customSlots.put(13, this.addSlot(new SlotItemHandler(internal, 13, 244, 11) {
			}));
			this.customSlots.put(14, this.addSlot(new SlotItemHandler(internal, 14, 262, 11) {
			}));
			this.customSlots.put(15, this.addSlot(new SlotItemHandler(internal, 15, 280, 11) {
			}));
			this.customSlots.put(16, this.addSlot(new SlotItemHandler(internal, 16, 298, 11) {
			}));
			this.customSlots.put(17, this.addSlot(new SlotItemHandler(internal, 17, 316, 11) {
			}));
			this.customSlots.put(18, this.addSlot(new SlotItemHandler(internal, 18, 334, 11) {
			}));
			this.customSlots.put(19, this.addSlot(new SlotItemHandler(internal, 19, 352, 11) {
			}));
			this.customSlots.put(20, this.addSlot(new SlotItemHandler(internal, 20, 370, 11) {
			}));
			this.customSlots.put(21, this.addSlot(new SlotItemHandler(internal, 21, 388, 11) {
			}));
			this.customSlots.put(22, this.addSlot(new SlotItemHandler(internal, 22, 406, 11) {
			}));
			this.customSlots.put(23, this.addSlot(new SlotItemHandler(internal, 23, 10, 29) {
			}));
			this.customSlots.put(24, this.addSlot(new SlotItemHandler(internal, 24, 28, 29) {
			}));
			this.customSlots.put(25, this.addSlot(new SlotItemHandler(internal, 25, 46, 29) {
			}));
			this.customSlots.put(26, this.addSlot(new SlotItemHandler(internal, 26, 64, 29) {
			}));
			this.customSlots.put(27, this.addSlot(new SlotItemHandler(internal, 27, 82, 29) {
			}));
			this.customSlots.put(28, this.addSlot(new SlotItemHandler(internal, 28, 100, 29) {
			}));
			this.customSlots.put(29, this.addSlot(new SlotItemHandler(internal, 29, 118, 29) {
			}));
			this.customSlots.put(30, this.addSlot(new SlotItemHandler(internal, 30, 136, 29) {
			}));
			this.customSlots.put(31, this.addSlot(new SlotItemHandler(internal, 31, 154, 29) {
			}));
			this.customSlots.put(32, this.addSlot(new SlotItemHandler(internal, 32, 172, 29) {
			}));
			this.customSlots.put(33, this.addSlot(new SlotItemHandler(internal, 33, 190, 29) {
			}));
			this.customSlots.put(34, this.addSlot(new SlotItemHandler(internal, 34, 208, 29) {
			}));
			this.customSlots.put(35, this.addSlot(new SlotItemHandler(internal, 35, 226, 29) {
			}));
			this.customSlots.put(36, this.addSlot(new SlotItemHandler(internal, 36, 244, 29) {
			}));
			this.customSlots.put(37, this.addSlot(new SlotItemHandler(internal, 37, 262, 29) {
			}));
			this.customSlots.put(38, this.addSlot(new SlotItemHandler(internal, 38, 280, 29) {
			}));
			this.customSlots.put(39, this.addSlot(new SlotItemHandler(internal, 39, 298, 29) {
			}));
			this.customSlots.put(40, this.addSlot(new SlotItemHandler(internal, 40, 316, 29) {
			}));
			this.customSlots.put(41, this.addSlot(new SlotItemHandler(internal, 41, 334, 29) {
			}));
			this.customSlots.put(42, this.addSlot(new SlotItemHandler(internal, 42, 352, 29) {
			}));
			this.customSlots.put(43, this.addSlot(new SlotItemHandler(internal, 43, 370, 29) {
			}));
			this.customSlots.put(44, this.addSlot(new SlotItemHandler(internal, 44, 388, 29) {
			}));
			this.customSlots.put(45, this.addSlot(new SlotItemHandler(internal, 45, 406, 29) {
			}));
			this.customSlots.put(46, this.addSlot(new SlotItemHandler(internal, 46, 10, 47) {
			}));
			this.customSlots.put(47, this.addSlot(new SlotItemHandler(internal, 47, 28, 47) {
			}));
			this.customSlots.put(48, this.addSlot(new SlotItemHandler(internal, 48, 46, 47) {
			}));
			this.customSlots.put(49, this.addSlot(new SlotItemHandler(internal, 49, 64, 47) {
			}));
			this.customSlots.put(50, this.addSlot(new SlotItemHandler(internal, 50, 82, 47) {
			}));
			this.customSlots.put(51, this.addSlot(new SlotItemHandler(internal, 51, 100, 47) {
			}));
			this.customSlots.put(52, this.addSlot(new SlotItemHandler(internal, 52, 118, 47) {
			}));
			this.customSlots.put(53, this.addSlot(new SlotItemHandler(internal, 53, 136, 47) {
			}));
			this.customSlots.put(54, this.addSlot(new SlotItemHandler(internal, 54, 154, 47) {
			}));
			this.customSlots.put(55, this.addSlot(new SlotItemHandler(internal, 55, 172, 47) {
			}));
			this.customSlots.put(56, this.addSlot(new SlotItemHandler(internal, 56, 190, 47) {
			}));
			this.customSlots.put(57, this.addSlot(new SlotItemHandler(internal, 57, 208, 47) {
			}));
			this.customSlots.put(58, this.addSlot(new SlotItemHandler(internal, 58, 226, 47) {
			}));
			this.customSlots.put(59, this.addSlot(new SlotItemHandler(internal, 59, 244, 47) {
			}));
			this.customSlots.put(60, this.addSlot(new SlotItemHandler(internal, 60, 262, 47) {
			}));
			this.customSlots.put(61, this.addSlot(new SlotItemHandler(internal, 61, 280, 47) {
			}));
			this.customSlots.put(62, this.addSlot(new SlotItemHandler(internal, 62, 298, 47) {
			}));
			this.customSlots.put(71, this.addSlot(new SlotItemHandler(internal, 71, 46, 65) {
			}));
			this.customSlots.put(72, this.addSlot(new SlotItemHandler(internal, 72, 64, 65) {
			}));
			this.customSlots.put(73, this.addSlot(new SlotItemHandler(internal, 73, 82, 65) {
			}));
			this.customSlots.put(74, this.addSlot(new SlotItemHandler(internal, 74, 100, 65) {
			}));
			this.customSlots.put(75, this.addSlot(new SlotItemHandler(internal, 75, 118, 65) {
			}));
			this.customSlots.put(76, this.addSlot(new SlotItemHandler(internal, 76, 136, 65) {
			}));
			this.customSlots.put(77, this.addSlot(new SlotItemHandler(internal, 77, 154, 65) {
			}));
			this.customSlots.put(78, this.addSlot(new SlotItemHandler(internal, 78, 172, 65) {
			}));
			this.customSlots.put(79, this.addSlot(new SlotItemHandler(internal, 79, 190, 65) {
			}));
			this.customSlots.put(80, this.addSlot(new SlotItemHandler(internal, 80, 208, 65) {
			}));
			this.customSlots.put(81, this.addSlot(new SlotItemHandler(internal, 81, 226, 65) {
			}));
			this.customSlots.put(82, this.addSlot(new SlotItemHandler(internal, 82, 244, 65) {
			}));
			this.customSlots.put(83, this.addSlot(new SlotItemHandler(internal, 83, 262, 65) {
			}));
			this.customSlots.put(84, this.addSlot(new SlotItemHandler(internal, 84, 280, 65) {
			}));
			this.customSlots.put(85, this.addSlot(new SlotItemHandler(internal, 85, 298, 65) {
			}));
			this.customSlots.put(86, this.addSlot(new SlotItemHandler(internal, 86, 316, 65) {
			}));
			this.customSlots.put(87, this.addSlot(new SlotItemHandler(internal, 87, 334, 65) {
			}));
			this.customSlots.put(88, this.addSlot(new SlotItemHandler(internal, 88, 352, 65) {
			}));
			this.customSlots.put(89, this.addSlot(new SlotItemHandler(internal, 89, 370, 65) {
			}));
			this.customSlots.put(90, this.addSlot(new SlotItemHandler(internal, 90, 388, 65) {
			}));
			this.customSlots.put(91, this.addSlot(new SlotItemHandler(internal, 91, 406, 65) {
			}));
			this.customSlots.put(92, this.addSlot(new SlotItemHandler(internal, 92, 10, 83) {
			}));
			this.customSlots.put(93, this.addSlot(new SlotItemHandler(internal, 93, 28, 83) {
			}));
			this.customSlots.put(94, this.addSlot(new SlotItemHandler(internal, 94, 46, 83) {
			}));
			this.customSlots.put(95, this.addSlot(new SlotItemHandler(internal, 95, 64, 83) {
			}));
			this.customSlots.put(96, this.addSlot(new SlotItemHandler(internal, 96, 82, 83) {
			}));
			this.customSlots.put(97, this.addSlot(new SlotItemHandler(internal, 97, 100, 83) {
			}));
			this.customSlots.put(98, this.addSlot(new SlotItemHandler(internal, 98, 118, 83) {
			}));
			this.customSlots.put(99, this.addSlot(new SlotItemHandler(internal, 99, 136, 83) {
			}));
			this.customSlots.put(100, this.addSlot(new SlotItemHandler(internal, 100, 154, 83) {
			}));
			this.customSlots.put(101, this.addSlot(new SlotItemHandler(internal, 101, 172, 83) {
			}));
			this.customSlots.put(102, this.addSlot(new SlotItemHandler(internal, 102, 190, 83) {
			}));
			this.customSlots.put(103, this.addSlot(new SlotItemHandler(internal, 103, 208, 83) {
			}));
			this.customSlots.put(104, this.addSlot(new SlotItemHandler(internal, 104, 226, 83) {
			}));
			this.customSlots.put(105, this.addSlot(new SlotItemHandler(internal, 105, 244, 83) {
			}));
			this.customSlots.put(106, this.addSlot(new SlotItemHandler(internal, 106, 262, 83) {
			}));
			this.customSlots.put(107, this.addSlot(new SlotItemHandler(internal, 107, 280, 83) {
			}));
			this.customSlots.put(108, this.addSlot(new SlotItemHandler(internal, 108, 298, 83) {
			}));
			this.customSlots.put(109, this.addSlot(new SlotItemHandler(internal, 109, 316, 83) {
			}));
			this.customSlots.put(110, this.addSlot(new SlotItemHandler(internal, 110, 334, 83) {
			}));
			this.customSlots.put(111, this.addSlot(new SlotItemHandler(internal, 111, 352, 83) {
			}));
			this.customSlots.put(112, this.addSlot(new SlotItemHandler(internal, 112, 370, 83) {
			}));
			this.customSlots.put(113, this.addSlot(new SlotItemHandler(internal, 113, 388, 83) {
			}));
			this.customSlots.put(114, this.addSlot(new SlotItemHandler(internal, 114, 406, 83) {
			}));
			this.customSlots.put(115, this.addSlot(new SlotItemHandler(internal, 115, 10, 101) {
			}));
			this.customSlots.put(116, this.addSlot(new SlotItemHandler(internal, 116, 28, 101) {
			}));
			this.customSlots.put(117, this.addSlot(new SlotItemHandler(internal, 117, 46, 101) {
			}));
			this.customSlots.put(118, this.addSlot(new SlotItemHandler(internal, 118, 64, 101) {
			}));
			this.customSlots.put(119, this.addSlot(new SlotItemHandler(internal, 119, 82, 101) {
			}));
			this.customSlots.put(120, this.addSlot(new SlotItemHandler(internal, 120, 100, 101) {
			}));
			this.customSlots.put(121, this.addSlot(new SlotItemHandler(internal, 121, 118, 101) {
			}));
			this.customSlots.put(122, this.addSlot(new SlotItemHandler(internal, 122, 136, 101) {
			}));
			this.customSlots.put(123, this.addSlot(new SlotItemHandler(internal, 123, 154, 101) {
			}));
			this.customSlots.put(124, this.addSlot(new SlotItemHandler(internal, 124, 172, 101) {
			}));
			this.customSlots.put(125, this.addSlot(new SlotItemHandler(internal, 125, 190, 101) {
			}));
			this.customSlots.put(126, this.addSlot(new SlotItemHandler(internal, 126, 208, 101) {
			}));
			this.customSlots.put(127, this.addSlot(new SlotItemHandler(internal, 127, 226, 101) {
			}));
			this.customSlots.put(128, this.addSlot(new SlotItemHandler(internal, 128, 244, 101) {
			}));
			this.customSlots.put(129, this.addSlot(new SlotItemHandler(internal, 129, 262, 101) {
			}));
			this.customSlots.put(130, this.addSlot(new SlotItemHandler(internal, 130, 280, 101) {
			}));
			this.customSlots.put(131, this.addSlot(new SlotItemHandler(internal, 131, 298, 101) {
			}));
			this.customSlots.put(132, this.addSlot(new SlotItemHandler(internal, 132, 316, 101) {
			}));
			this.customSlots.put(133, this.addSlot(new SlotItemHandler(internal, 133, 334, 101) {
			}));
			this.customSlots.put(134, this.addSlot(new SlotItemHandler(internal, 134, 352, 101) {
			}));
			this.customSlots.put(135, this.addSlot(new SlotItemHandler(internal, 135, 370, 101) {
			}));
			this.customSlots.put(136, this.addSlot(new SlotItemHandler(internal, 136, 388, 101) {
			}));
			this.customSlots.put(137, this.addSlot(new SlotItemHandler(internal, 137, 406, 101) {
			}));
			this.customSlots.put(138, this.addSlot(new SlotItemHandler(internal, 138, 10, 119) {
			}));
			this.customSlots.put(139, this.addSlot(new SlotItemHandler(internal, 139, 28, 119) {
			}));
			this.customSlots.put(140, this.addSlot(new SlotItemHandler(internal, 140, 46, 119) {
			}));
			this.customSlots.put(141, this.addSlot(new SlotItemHandler(internal, 141, 64, 119) {
			}));
			this.customSlots.put(142, this.addSlot(new SlotItemHandler(internal, 142, 82, 119) {
			}));
			this.customSlots.put(143, this.addSlot(new SlotItemHandler(internal, 143, 406, 119) {
			}));
			this.customSlots.put(144, this.addSlot(new SlotItemHandler(internal, 144, 100, 119) {
			}));
			this.customSlots.put(145, this.addSlot(new SlotItemHandler(internal, 145, 118, 119) {
			}));
			this.customSlots.put(146, this.addSlot(new SlotItemHandler(internal, 146, 136, 119) {
			}));
			this.customSlots.put(147, this.addSlot(new SlotItemHandler(internal, 147, 154, 119) {
			}));
			this.customSlots.put(148, this.addSlot(new SlotItemHandler(internal, 148, 172, 119) {
			}));
			this.customSlots.put(149, this.addSlot(new SlotItemHandler(internal, 149, 190, 119) {
			}));
			this.customSlots.put(150, this.addSlot(new SlotItemHandler(internal, 150, 208, 119) {
			}));
			this.customSlots.put(151, this.addSlot(new SlotItemHandler(internal, 151, 226, 119) {
			}));
			this.customSlots.put(152, this.addSlot(new SlotItemHandler(internal, 152, 244, 119) {
			}));
			this.customSlots.put(153, this.addSlot(new SlotItemHandler(internal, 153, 262, 119) {
			}));
			this.customSlots.put(154, this.addSlot(new SlotItemHandler(internal, 154, 280, 119) {
			}));
			this.customSlots.put(155, this.addSlot(new SlotItemHandler(internal, 155, 298, 119) {
			}));
			this.customSlots.put(156, this.addSlot(new SlotItemHandler(internal, 156, 316, 119) {
			}));
			this.customSlots.put(157, this.addSlot(new SlotItemHandler(internal, 157, 334, 119) {
			}));
			this.customSlots.put(158, this.addSlot(new SlotItemHandler(internal, 158, 352, 119) {
			}));
			this.customSlots.put(159, this.addSlot(new SlotItemHandler(internal, 159, 370, 119) {
			}));
			this.customSlots.put(160, this.addSlot(new SlotItemHandler(internal, 160, 388, 119) {
			}));
			this.customSlots.put(161, this.addSlot(new SlotItemHandler(internal, 161, 10, 137) {
			}));
			this.customSlots.put(162, this.addSlot(new SlotItemHandler(internal, 162, 28, 137) {
			}));
			this.customSlots.put(163, this.addSlot(new SlotItemHandler(internal, 163, 46, 137) {
			}));
			this.customSlots.put(164, this.addSlot(new SlotItemHandler(internal, 164, 64, 137) {
			}));
			this.customSlots.put(165, this.addSlot(new SlotItemHandler(internal, 165, 82, 137) {
			}));
			this.customSlots.put(166, this.addSlot(new SlotItemHandler(internal, 166, 100, 137) {
			}));
			this.customSlots.put(167, this.addSlot(new SlotItemHandler(internal, 167, 118, 137) {
			}));
			this.customSlots.put(63, this.addSlot(new SlotItemHandler(internal, 63, 316, 47) {
			}));
			this.customSlots.put(64, this.addSlot(new SlotItemHandler(internal, 64, 334, 47) {
			}));
			this.customSlots.put(65, this.addSlot(new SlotItemHandler(internal, 65, 352, 47) {
			}));
			this.customSlots.put(66, this.addSlot(new SlotItemHandler(internal, 66, 370, 47) {
			}));
			this.customSlots.put(67, this.addSlot(new SlotItemHandler(internal, 67, 388, 47) {
			}));
			this.customSlots.put(68, this.addSlot(new SlotItemHandler(internal, 68, 406, 47) {
			}));
			this.customSlots.put(69, this.addSlot(new SlotItemHandler(internal, 69, 10, 65) {
			}));
			this.customSlots.put(70, this.addSlot(new SlotItemHandler(internal, 70, 28, 65) {
			}));
			this.customSlots.put(168, this.addSlot(new SlotItemHandler(internal, 168, 136, 137) {
			}));
			this.customSlots.put(169, this.addSlot(new SlotItemHandler(internal, 169, 154, 137) {
			}));
			this.customSlots.put(170, this.addSlot(new SlotItemHandler(internal, 170, 172, 137) {
			}));
			this.customSlots.put(171, this.addSlot(new SlotItemHandler(internal, 171, 190, 137) {
			}));
			this.customSlots.put(172, this.addSlot(new SlotItemHandler(internal, 172, 208, 137) {
			}));
			this.customSlots.put(173, this.addSlot(new SlotItemHandler(internal, 173, 226, 137) {
			}));
			this.customSlots.put(174, this.addSlot(new SlotItemHandler(internal, 174, 244, 137) {
			}));
			this.customSlots.put(175, this.addSlot(new SlotItemHandler(internal, 175, 262, 137) {
			}));
			this.customSlots.put(176, this.addSlot(new SlotItemHandler(internal, 176, 280, 137) {
			}));
			this.customSlots.put(177, this.addSlot(new SlotItemHandler(internal, 177, 298, 137) {
			}));
			this.customSlots.put(178, this.addSlot(new SlotItemHandler(internal, 178, 316, 137) {
			}));
			this.customSlots.put(179, this.addSlot(new SlotItemHandler(internal, 179, 334, 137) {
			}));
			this.customSlots.put(180, this.addSlot(new SlotItemHandler(internal, 180, 352, 137) {
			}));
			this.customSlots.put(181, this.addSlot(new SlotItemHandler(internal, 181, 370, 137) {
			}));
			this.customSlots.put(182, this.addSlot(new SlotItemHandler(internal, 182, 388, 137) {
			}));
			this.customSlots.put(183, this.addSlot(new SlotItemHandler(internal, 183, 406, 137) {
			}));
			int si;
			int sj;
			for (si = 0; si < 3; ++si)
				for (sj = 0; sj < 9; ++sj)
					this.addSlot(new Slot(inv, sj + (si + 1) * 9, 128 + 8 + sj * 18, 81 + 84 + si * 18));
			for (si = 0; si < 9; ++si)
				this.addSlot(new Slot(inv, si, 128 + 8 + si * 18, 81 + 142));
		}

		public Map<Integer, Slot> get() {
			return customSlots;
		}

		@Override
		public boolean canInteractWith(PlayerEntity player) {
			return true;
		}

		@Override
		public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
			ItemStack itemstack = ItemStack.EMPTY;
			Slot slot = (Slot) this.inventorySlots.get(index);
			if (slot != null && slot.getHasStack()) {
				ItemStack itemstack1 = slot.getStack();
				itemstack = itemstack1.copy();
				if (index < 184) {
					if (!this.mergeItemStack(itemstack1, 184, this.inventorySlots.size(), true)) {
						return ItemStack.EMPTY;
					}
					slot.onSlotChange(itemstack1, itemstack);
				} else if (!this.mergeItemStack(itemstack1, 0, 184, false)) {
					if (index < 184 + 27) {
						if (!this.mergeItemStack(itemstack1, 184 + 27, this.inventorySlots.size(), true)) {
							return ItemStack.EMPTY;
						}
					} else {
						if (!this.mergeItemStack(itemstack1, 184, 184 + 27, false)) {
							return ItemStack.EMPTY;
						}
					}
					return ItemStack.EMPTY;
				}
				if (itemstack1.getCount() == 0) {
					slot.putStack(ItemStack.EMPTY);
				} else {
					slot.onSlotChanged();
				}
				if (itemstack1.getCount() == itemstack.getCount()) {
					return ItemStack.EMPTY;
				}
				slot.onTake(playerIn, itemstack1);
			}
			return itemstack;
		}

		@Override /** 
					* Merges provided ItemStack with the first avaliable one in the container/player inventor between minIndex (included) and maxIndex (excluded). Args : stack, minIndex, maxIndex, negativDirection. /!\ the Container implementation do not check if the item is valid for the slot
					*/
		protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
			boolean flag = false;
			int i = startIndex;
			if (reverseDirection) {
				i = endIndex - 1;
			}
			if (stack.isStackable()) {
				while (!stack.isEmpty()) {
					if (reverseDirection) {
						if (i < startIndex) {
							break;
						}
					} else if (i >= endIndex) {
						break;
					}
					Slot slot = this.inventorySlots.get(i);
					ItemStack itemstack = slot.getStack();
					if (slot.isItemValid(itemstack) && !itemstack.isEmpty() && areItemsAndTagsEqual(stack, itemstack)) {
						int j = itemstack.getCount() + stack.getCount();
						int maxSize = Math.min(slot.getSlotStackLimit(), stack.getMaxStackSize());
						if (j <= maxSize) {
							stack.setCount(0);
							itemstack.setCount(j);
							slot.putStack(itemstack);
							flag = true;
						} else if (itemstack.getCount() < maxSize) {
							stack.shrink(maxSize - itemstack.getCount());
							itemstack.setCount(maxSize);
							slot.putStack(itemstack);
							flag = true;
						}
					}
					if (reverseDirection) {
						--i;
					} else {
						++i;
					}
				}
			}
			if (!stack.isEmpty()) {
				if (reverseDirection) {
					i = endIndex - 1;
				} else {
					i = startIndex;
				}
				while (true) {
					if (reverseDirection) {
						if (i < startIndex) {
							break;
						}
					} else if (i >= endIndex) {
						break;
					}
					Slot slot1 = this.inventorySlots.get(i);
					ItemStack itemstack1 = slot1.getStack();
					if (itemstack1.isEmpty() && slot1.isItemValid(stack)) {
						if (stack.getCount() > slot1.getSlotStackLimit()) {
							slot1.putStack(stack.split(slot1.getSlotStackLimit()));
						} else {
							slot1.putStack(stack.split(stack.getCount()));
						}
						slot1.onSlotChanged();
						flag = true;
						break;
					}
					if (reverseDirection) {
						--i;
					} else {
						++i;
					}
				}
			}
			return flag;
		}

		@Override
		public void onContainerClosed(PlayerEntity playerIn) {
			super.onContainerClosed(playerIn);
			if (!bound && (playerIn instanceof ServerPlayerEntity)) {
				if (!playerIn.isAlive() || playerIn instanceof ServerPlayerEntity && ((ServerPlayerEntity) playerIn).hasDisconnected()) {
					for (int j = 0; j < internal.getSlots(); ++j) {
						playerIn.dropItem(internal.extractItem(j, internal.getStackInSlot(j).getCount(), false), false);
					}
				} else {
					for (int i = 0; i < internal.getSlots(); ++i) {
						playerIn.inventory.placeItemBackInInventory(playerIn.world,
								internal.extractItem(i, internal.getStackInSlot(i).getCount(), false));
					}
				}
			}
		}

		private void slotChanged(int slotid, int ctype, int meta) {
			if (this.world != null && this.world.isRemote()) {
				ChestMod.PACKET_HANDLER.sendToServer(new GUISlotChangedMessage(slotid, x, y, z, ctype, meta));
				handleSlotAction(entity, slotid, ctype, meta, x, y, z);
			}
		}
	}

	public static class ButtonPressedMessage {
		int buttonID, x, y, z;

		public ButtonPressedMessage(PacketBuffer buffer) {
			this.buttonID = buffer.readInt();
			this.x = buffer.readInt();
			this.y = buffer.readInt();
			this.z = buffer.readInt();
		}

		public ButtonPressedMessage(int buttonID, int x, int y, int z) {
			this.buttonID = buttonID;
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public static void buffer(ButtonPressedMessage message, PacketBuffer buffer) {
			buffer.writeInt(message.buttonID);
			buffer.writeInt(message.x);
			buffer.writeInt(message.y);
			buffer.writeInt(message.z);
		}

		public static void handler(ButtonPressedMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				PlayerEntity entity = context.getSender();
				int buttonID = message.buttonID;
				int x = message.x;
				int y = message.y;
				int z = message.z;
				handleButtonAction(entity, buttonID, x, y, z);
			});
			context.setPacketHandled(true);
		}
	}

	public static class GUISlotChangedMessage {
		int slotID, x, y, z, changeType, meta;

		public GUISlotChangedMessage(int slotID, int x, int y, int z, int changeType, int meta) {
			this.slotID = slotID;
			this.x = x;
			this.y = y;
			this.z = z;
			this.changeType = changeType;
			this.meta = meta;
		}

		public GUISlotChangedMessage(PacketBuffer buffer) {
			this.slotID = buffer.readInt();
			this.x = buffer.readInt();
			this.y = buffer.readInt();
			this.z = buffer.readInt();
			this.changeType = buffer.readInt();
			this.meta = buffer.readInt();
		}

		public static void buffer(GUISlotChangedMessage message, PacketBuffer buffer) {
			buffer.writeInt(message.slotID);
			buffer.writeInt(message.x);
			buffer.writeInt(message.y);
			buffer.writeInt(message.z);
			buffer.writeInt(message.changeType);
			buffer.writeInt(message.meta);
		}

		public static void handler(GUISlotChangedMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				PlayerEntity entity = context.getSender();
				int slotID = message.slotID;
				int changeType = message.changeType;
				int meta = message.meta;
				int x = message.x;
				int y = message.y;
				int z = message.z;
				handleSlotAction(entity, slotID, changeType, meta, x, y, z);
			});
			context.setPacketHandled(true);
		}
	}

	static void handleButtonAction(PlayerEntity entity, int buttonID, int x, int y, int z) {
		World world = entity.world;
		// security measure to prevent arbitrary chunk generation
		if (!world.isBlockLoaded(new BlockPos(x, y, z)))
			return;
	}

	private static void handleSlotAction(PlayerEntity entity, int slotID, int changeType, int meta, int x, int y, int z) {
		World world = entity.world;
		// security measure to prevent arbitrary chunk generation
		if (!world.isBlockLoaded(new BlockPos(x, y, z)))
			return;
	}
}
