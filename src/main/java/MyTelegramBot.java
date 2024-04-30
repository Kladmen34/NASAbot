import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class MyTelegramBot extends TelegramLongPollingBot {

    private final String BOT_NAME;
    private final String BOT_TOKEN;

    private final String NASA_URL = "";

    public MyTelegramBot(String botName, String botToken) throws TelegramApiException {
        BOT_NAME = botName;
        BOT_TOKEN = botToken;
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();
            String[] separatedText = text.split(" ");

            switch (separatedText[0]) {
                case "/help":
                case "/помощь":
                    sendMessage("Я бот NASA, введи /image или /картинка", chatId);
                    break;
                case "/image":
                case "/картинка":
                    String image = Utils.getURL(NASA_URL);
                    sendMessage(image, chatId);
                    break;
                case "/date":
                case "/дата":
                    image = Utils.getURL(NASA_URL + "&date=" + separatedText[1]);
                    sendMessage(image, chatId);
                    break;
                default:
                    sendMessage("Я тебя не понимаю", chatId);
            }

        }
    }

    void sendMessage(String msgText, long chatId){
        SendMessage msg = new SendMessage();
        msg.setChatId(chatId);
        msg.setText(msgText);
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            System.out.println("Какая-то ошибка");
        }
    }


    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
