package me.maxouxax.stockbot.commands.register.discord;

import me.maxouxax.stockbot.BOT;
import me.maxouxax.stockbot.commands.Command;
import me.maxouxax.stockbot.commands.CommandMap;
import me.maxouxax.stockbot.utils.EmbedCrafter;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class CommandEmbed {

    private final BOT bot;
    private final CommandMap commandMap;

    public CommandEmbed(CommandMap commandMap){
        this.commandMap = commandMap;
        this.bot = BOT.getInstance();
    }

    @Command(name="embed",type = Command.ExecutorType.ALL,power = 100,help = ".embed <titre>-²<description>-²<image (url)>",example = ".embed Ceci est une annonce-²Juste pour vous dire que les bananes c'est assez bon mais que la raclette reste au dessus.-²https://lien-de-l-image.fr/image32.png")
    public void embed(User user, TextChannel textChannel, String[] args) {
        try {
            MessageEmbed em = commandMap.getHelpEmbed("embed");
            if (args.length == 0) {
                textChannel.sendMessage(em).queue();
            } else {
                StringBuilder str = new StringBuilder();

                for (String arg : args) {
                    str.append(arg).append(" ");
                }

                if (!str.toString().contains("-²")) {
                    textChannel.sendMessage(em).queue();
                    return;
                }

                String[] argsReal = str.toString().split("-²");

                String title = argsReal[0];

                String description = "Aucune description n'a été fournie !";
                if (argsReal.length >= 2) {
                    description = argsReal[1];
                }

                String imageURL = null;
                if (argsReal.length == 3) {
                    imageURL = argsReal[2];
                }

                EmbedCrafter embedCrafter = new EmbedCrafter()
                        .setTitle(title, "https://sti2d.best/")
                        .setColor(15844367)
                        .setDescription(description);
                if (imageURL != null) {
                    embedCrafter.setImageUrl(imageURL);
                }
                textChannel.sendMessage(embedCrafter.build()).queue();
            }
        }catch (Exception e){
            bot.getErrorHandler().handleException(e);
        }
    }

}
