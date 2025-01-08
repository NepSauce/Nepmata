package nepbot;

import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import nepbot.commands.CommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Event extends ListenerAdapter{
    public static String[] splitMessage;
    
    @SuppressWarnings("null")
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event){
        if (event.getAuthor().isBot()){
            return;
        }
    
        String message = event.getMessage().getContentRaw();
        String messageLower = message.toLowerCase();

        List<String> negativeWords = Arrays.asList("suck", "stupid", "dumb", "awful","trash", 
        "garbage", "useless", "weak", "lame", "cringe", "pathetic", "toxic", "shit", "sucks", "gay", 
        "ugly", "horrible", "disgusting", "awful", "ridiculous", "stupid", "annoying", "worthless", 
        "fail", "dead", "screwed", "unreal", "unwanted", "unhelpful", "unvalued", "unnecessary", "bad", 
        "ass");
        List<String> positiveWords = Arrays.asList("great", "good", "awesome", "amazing", "fantastic", 
        "nice", "wonderful", "excellent", "cool", "happy", "lovely", "positive", "friend", "supportive", 
        "kind", "help", "smart", "strong", "healthy", "beautiful", "chill", "outstanding", "remarkable", 
        "impressive", "motivated", "thankful", "love", "fun", "peaceful", "grateful", "honest", "reliable", 
        "bright", "productive", "perfect");

    
        splitMessage = event.getMessage().getContentRaw().toLowerCase().split(" ");

        boolean dadCheck = ((splitMessage[0].contains("i") && splitMessage[1].contains("am")) || (splitMessage[0].contains("i'm")) || splitMessage[0].contains("im"));
    
        if (message.toLowerCase().contains("rot")){
            BotStatement.onRotReceived(event);
        }
    
        else if (messageLower.contains("nep")){
            boolean containsBadWord = false;
            String negativeWord = null;
            boolean containsGoodWord = false;
            String positiveWord = null;

            for (String badWord : negativeWords){
                if (messageLower.contains(badWord)){
                containsBadWord = true;
                negativeWord = badWord;
                break;
                }
            }

            if (containsBadWord) {
                if (messageLower.contains("not")){
                    if (messageLower.indexOf("nep") < messageLower.indexOf("not") 
                    && messageLower.indexOf("not") < messageLower.indexOf(negativeWord)){
                        return;
                    }
                }
                BotStatement.onNegativeReceived(event);
            }

            for (String goodWord : positiveWords) {
                if (messageLower.contains(goodWord)) {
                    containsGoodWord = true;
                    positiveWord = goodWord;
                    break;
                }
            }

            if (containsGoodWord) {
                if (messageLower.contains("not")){
                    if (messageLower.indexOf("nep") < messageLower.indexOf("not") 
                    && messageLower.indexOf("not") < messageLower.indexOf(positiveWord)){
                        BotStatement.onNegativeReceived(event);
                        return;
                    }
                }
                BotStatement.onPositiveReceived(event);
            }
        }
        
        else if (splitMessage[0].contains("nep") && splitMessage.length == 1){
            BotStatement.onNepReceived(event);
        }

        else if (dadCheck){
            StringBuilder dadResponse = new StringBuilder("Hi ");
            int startIndex = splitMessage[1].contains("am") ? 2 : 1;
        
            for (int i = startIndex; i < splitMessage.length; i++){
                dadResponse.append(splitMessage[i]);
                if (i < splitMessage.length - 1){
                    dadResponse.append(" ");
                } 
                else{
                    dadResponse.append(", ");
                }
            }
        
            dadResponse.append("I'm dad.");
            if (CommandEvent.getDadBotMode()){
                BotStatement.onDadReceived(event, dadResponse.toString());
            }
        }
    }
}
