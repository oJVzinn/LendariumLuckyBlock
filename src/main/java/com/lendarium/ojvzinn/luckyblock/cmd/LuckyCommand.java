package com.lendarium.ojvzinn.luckyblock.cmd;

import com.lendarium.ojvzinn.luckyblock.Main;
import com.lendarium.ojvzinn.luckyblock.lucky.enuns.LuckyEnuns;
import com.lendarium.ojvzinn.luckyblock.menu.MenuEditLuckyBlock;
import com.lendarium.ojvzinn.luckyblock.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LuckyCommand extends Commands {

    protected LuckyCommand(String name, String... aliases) {
        super(name, aliases);
    }

    @Override
    void performace(CommandSender sender, String s, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("\n§eAJUDA - 1/1\n \n§6/lb give §7[PLAYER] [TIPO] [QUANTIA]\n§6/lb editor §7[TIPO]\n \n§7LuckyBlock version §8- §7" + Main.getInstance().getDescription().getVersion() + "\n \n");
            return;
        }

        String action = args[0];

        switch (action) {

            case "give": {
                if (args.length < 3) {
                    sender.sendMessage("§cUso incorreto! Tente /lb give [PLAYER] [TIPO] [QUANTIA]");
                    return;
                }

                Player target = null;
                LuckyEnuns type = LuckyEnuns.findByName(args[2]);
                if (type == null) {
                    sender.sendMessage("§cEste tipo de lucky block não existe!");
                    return;
                }

                try {
                    target = Bukkit.getPlayer(args[1]);
                } catch (Exception e) {
                    sender.sendMessage("§cEste jogador não existe ou não se encontra online no momento!");
                }
                if (target == null) {
                    return;
                }

                if (args.length > 3) {
                    try {
                        Integer.parseInt(args[3]);
                    } catch (Exception e) {
                        sender.sendMessage("§cDigite a quantia em um formato válido!");
                        return;
                    }

                    for (int i = 0; i < Integer.parseInt(args[3]); i++) {
                        ItemStack item = ItemUtils.getItemStackFromString("397:3 : 1 : nome>§b" + type.getName() + " : desc>&7Clique com o direito no chão;;&7para criar uma luckyblock! : skin>" + type.getSkin());
                        target.getInventory().addItem(item);
                    }
                } else {
                    ItemStack item = ItemUtils.getItemStackFromString("397:3 : 1 : nome>§b" + type.getName() + " : desc>&7Clique com o direito no chão;;&7para criar uma luckyblock! : skin>" + type.getSkin());
                    target.getInventory().addItem(item);
                }

                break;
            }

            case "editor": {
                if (args.length < 3) {
                    sender.sendMessage("§cUso incorreto! Tente /lb editor [TIPO]");
                    return;
                }

                Player player = (Player) sender;
                LuckyEnuns type = LuckyEnuns.findByName(args[2]);
                if (type == null) {
                    sender.sendMessage("§cEste tipo de lucky block não existe!");
                    return;
                }

                new MenuEditLuckyBlock(type, player);
                break;
            }
        }
    }
}
