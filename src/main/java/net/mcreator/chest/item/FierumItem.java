
package net.mcreator.chest.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.block.BlockState;

import net.mcreator.chest.itemgroup.SuperChestItemGroup;
import net.mcreator.chest.ChestModElements;

@ChestModElements.ModElement.Tag
public class FierumItem extends ChestModElements.ModElement {
	@ObjectHolder("chest:fierum")
	public static final Item block = null;

	public FierumItem(ChestModElements instance) {
		super(instance, 11);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}

	public static class ItemCustom extends Item {
		public ItemCustom() {
			super(new Item.Properties().group(SuperChestItemGroup.tab).maxStackSize(64).rarity(Rarity.COMMON));
			setRegistryName("fierum");
		}

		@Override
		public int getItemEnchantability() {
			return 0;
		}

		@Override
		public int getUseDuration(ItemStack itemstack) {
			return 0;
		}

		@Override
		public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
			return 1F;
		}
	}
}
