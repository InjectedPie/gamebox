
package me.nikl.gamebox;

import java.util.Collection;
import java.util.Iterator;

import me.nikl.nmsutilities.NmsUtility;
import me.nikl.nmsutilities.WindowType_1_20_R1;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.chat.IChatBaseComponent.ChatSerializer;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindow;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.network.protocol.game.PacketPlayOutWindowItems;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NmsUtility_1_20_R2 implements NmsUtility {
    public NmsUtility_1_20_R2() {
    }

    public void updateInventoryTitle(Player player, String newTitle) {
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
        PacketPlayOutOpenWindow packet = new PacketPlayOutOpenWindow(entityPlayer.bR.j, WindowType_1_20_R1.guessBySlots(entityPlayer.bR.getBukkitView().getTopInventory().getSize()).getType(), ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', newTitle + "\"}")));
        PacketPlayOutWindowItems contentPacket = new PacketPlayOutWindowItems(entityPlayer.bR.j, 0, entityPlayer.bR.c(), entityPlayer.eS());
        entityPlayer.c.a(packet);
        entityPlayer.c.a(contentPacket);
    }

    public void sendJSON(Player player, String json) {
        ChatMessageType.a chatType = ChatMessageType.a(ChatMessageType.c, ((CraftPlayer)player).getHandle().cJ().G_(), ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', player.getDisplayName() + "\"}")));
        ((CraftPlayer)player).getHandle().a(OutgoingChatMessage.a(PlayerChatMessage.a(ChatColor.translateAlternateColorCodes('&', json))), true, chatType);
    }

    public void sendJSON(Player player, Collection<String> json) {
        Iterator var3 = json.iterator();

        while(var3.hasNext()) {
            String message = (String)var3.next();
            this.sendJSON(player, message);
        }

    }

    public void sendJSON(Collection<Player> players, String json) {
        Iterator var3 = players.iterator();

        while(var3.hasNext()) {
            Player player = (Player)var3.next();
            this.sendJSON(player, json);
        }

    }

    public void sendJSON(Collection<Player> players, Collection<String> json) {
        Iterator var3 = json.iterator();

        while(var3.hasNext()) {
            String message = (String)var3.next();
            this.sendJSON(players, message);
        }

    }

    public void sendTitle(Player player, String title, String subTitle, int durationTicks) {
        IChatMutableComponent comp;
        if (title != null) {
            comp = ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', title + "\"}"));
            ClientboundSetTitleTextPacket packet = new ClientboundSetTitleTextPacket(comp);
            ((CraftPlayer)player).getHandle().c.a(packet);
        }

        if (subTitle != null) {
            comp = ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', subTitle + "\"}"));
            ClientboundSetSubtitleTextPacket packet = new ClientboundSetSubtitleTextPacket(comp);
            ((CraftPlayer)player).getHandle().c.a(packet);
        }

        ClientboundSetTitlesAnimationPacket length = new ClientboundSetTitlesAnimationPacket(10, durationTicks, 10);
        ((CraftPlayer)player).getHandle().c.a(length);
    }

    public void sendActionbar(Player player, String message) {
        IChatBaseComponent comp = ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', message + "\"}"));
        ClientboundSetActionBarTextPacket packet = new ClientboundSetActionBarTextPacket(comp);
        ((CraftPlayer)player).getHandle().c.a(packet);
    }

    public void sendList(Player player, String header, String footer) {
        IChatBaseComponent headerComponent = header == null ? null : ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', header + "\"}"));
        IChatBaseComponent footerComponent = footer == null ? null : ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', footer + "\"}"));
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(headerComponent, footerComponent);
        ((CraftPlayer)player).getHandle().c.a(packet);
    }

    public void sendListFooter(Player player, String footer) {
        Bukkit.getConsoleSender().sendMessage("From MC 1.17 on, please use the method 'sendList' instead of 'sendListFooter'/'sendListHeader'");
    }

    public void sendListHeader(Player player, String header) {
        Bukkit.getConsoleSender().sendMessage("From MC 1.17 on, please use the method 'sendList' instead of 'sendListFooter'/'sendListHeader'");
    }

    public ItemStack removeGlow(ItemStack item) {
        if (item == null) {
            return null;
        } else {
            ItemMeta meta = item.getItemMeta();
            Enchantment[] var3 = Enchantment.values();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Enchantment enchantment = var3[var5];
                meta.removeEnchant(enchantment);
            }

            item.setItemMeta(meta);
            return item;
        }
    }

    public ItemStack addGlow(ItemStack item) {
        if (item == null) {
            return null;
        } else {
            item.addUnsafeEnchantment(Enchantment.LUCK, 1);
            ItemMeta meta = item.getItemMeta();
            meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
            item.setItemMeta(meta);
            return item;
        }
    }
}
