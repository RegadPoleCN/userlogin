package com.elchologamer.userlogin.commands;

import com.elchologamer.userlogin.util.PasswordEncryptor;
import com.elchologamer.userlogin.util.Path;
import com.elchologamer.userlogin.util.ULPlayer;
import com.elchologamer.userlogin.util.database.Database;
import org.bukkit.entity.Player;

public class LoginCommand extends AuthCommand {

    @Override
    protected boolean authenticate(Player p, String[] args) {
        Database db = getPlugin().getDB();
        String password = db.getPassword(p.getUniqueId());

        ULPlayer ulPlayer = getPlugin().getPlayer(p);

        // Check if player is registered
        if (password == null) {
            ulPlayer.sendPathMessage(Path.NOT_REGISTERED);
            return false;
        }

        // Check usage
        if (args.length != 1) return false;

        // Decrypt stored password if needed
        password = PasswordEncryptor.decodeBase64(password);

        if (!args[0].equals(password)) {
            ulPlayer.sendPathMessage(Path.INCORRECT_PASSWORD);
            return false;
        }

        return true;
    }
}