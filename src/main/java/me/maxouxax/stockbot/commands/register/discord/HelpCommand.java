package me.maxouxax.stockbot.commands.register.discord;

import me.maxouxax.stockbot.commands.Command;
import me.maxouxax.stockbot.commands.CommandMap;
import me.maxouxax.stockbot.commands.SimpleCommand;
import me.maxouxax.stockbot.utils.EmbedCrafter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.internal.entities.UserImpl;

public class HelpCommand {

    private final CommandMap commandMap;

    public HelpCommand(CommandMap commandMap) {
        this.commandMap = commandMap;
    }

    @Command(name="help",type= Command.ExecutorType.USER,description="Affiche l'entièreté des commandes disponibles", help = "help", example = "help")
    private void help(User user, MessageChannel channel, Guild guild){
        EmbedCrafter builder = new EmbedCrafter();
        builder.setTitle("Aide » Liste des commandes");
        builder.setColor(3447003);

        for(SimpleCommand command : commandMap.getDiscordCommands()){
            if(command.getExecutorType() == Command.ExecutorType.CONSOLE) continue;

            if(guild != null && command.getPower() > commandMap.getPowerUser(guild, user)) continue;

            builder.addField(new MessageEmbed.Field(command.getName(), command.getDescription(), true));
        }

        if(!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl)user).getPrivateChannel().sendMessage(builder.build()).queue();

        channel.sendMessage(user.getAsMention()+", veuillez regarder vos message privés.").queue();

    }

}
