package nepbot.commands;

import org.jetbrains.annotations.NotNull;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class CommandEvent extends ListenerAdapter{
    public static String[] splitMessage;
    static Boolean dadBotMode = false;
    
    @SuppressWarnings("null")
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event){
        splitMessage = event.getMessage().getContentRaw().toLowerCase().split(" ");

        if (splitMessage[0].contains("!nep") && splitMessage[1].equalsIgnoreCase("dadMode")){
            onDadCommandReceived(event);
        }
    }

    public void onDadCommandReceived(@NotNull MessageReceivedEvent event){
        if (splitMessage.length == 2){
            if (dadBotMode){
                event.getChannel().sendMessage("`Dad Bot Mode is enabled`").queue();
            }
            else{
                event.getChannel().sendMessage("`Dad Bot Mode is disabled`").queue();
            }
        }
        else if (splitMessage[2].equalsIgnoreCase("true" )){
            dadBotMode = true;
            event.getChannel().sendMessage("`Dad Bot Mode enabled.`").queue();
        }
        else if (splitMessage[2].equalsIgnoreCase("false" )){
            dadBotMode = false;
            event.getChannel().sendMessage("`Dad Bot Mode disabled.`").queue();
        }
        else{
            event.getChannel().sendMessage("`Sorry, I don't recognize that Command.`").queue();
        }
    }
    
    public static Boolean getDadBotMode(){
        return dadBotMode;
    }
}
