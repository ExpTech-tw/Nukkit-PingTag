package Nukkit_PingTag_whes1015;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.LoginChainData;
import cn.nukkit.utils.TextFormat;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class whes1015 extends PluginBase implements Listener {

    String vername="3.0.6-stable";

    @Override
    public void onEnable() {
        String webPage = "https://api.github.com/repos/ExpTechTW/Nukkit-PingTag/releases";

        InputStream is = null;
        try {
            is = new URL(webPage).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
        JsonElement jsonElement = new JsonParser().parse(reader);
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        JsonObject jsonObject = (JsonObject) jsonArray.get(0);
        saveDefaultConfig();
        if (jsonObject.get("tag_name").toString() != vername) {
            if ((getConfig().getString("BetaVersion") == "true" && jsonObject.get("prerelease").getAsBoolean() == true) || (getConfig().getString("BetaVersion") == "false" && jsonObject.get("prerelease").getAsBoolean() == false)) {
                this.getLogger().warning(TextFormat.RED + "Please Update Your Plugin ! " + vername);
                this.getLogger().info(TextFormat.RED + "DownloadLink: https://github.com/ExpTechTW/Nukkit-PingTag/releases");
                this.getPluginLoader().disablePlugin(this);
            } else {
                this.getLogger().info(TextFormat.BLUE + "Nukkit-Engineer Update Checking Success! " + vername);
                this.getLogger().info(TextFormat.BLUE + "Nukkit-Engineer Loading! - Designed by ExpTech.tw (whes1015) " + vername);
                this.getServer().getPluginManager().registerEvents(this, this);
            }
        } else {
            this.getLogger().info(TextFormat.BLUE + "Nukkit-Engineer Update Checking Success! " + vername);
            this.getLogger().info(TextFormat.BLUE + "Nukkit-Engineer Loading! - Designed by ExpTech.tw (whes1015) " + vername);
            this.getServer().getPluginManager().registerEvents(this, this);
        }
    }

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent event) {
        String ping=event.getPlayer().getPing() + "(ms)";
        String OS;
        LoginChainData os=event.getPlayer().getLoginChainData();
        if(os.getDeviceOS() == 1){
            OS="Android";
        }else if(os.getDeviceOS() == 2){
            OS="iOS";
        }else{
            OS="Windows";
        }
        if (event.getPlayer().getPing()>300) {
            event.getPlayer().setNameTag(event.getPlayer().getName() + "\n" +TextFormat.BLUE +OS+" "+ TextFormat.RED +ping);
        }else if(event.getPlayer().getPing()>100) {
            event.getPlayer().setNameTag(event.getPlayer().getName() + "\n" +TextFormat.BLUE +OS+" "+ TextFormat.YELLOW +ping);
        }else{
            event.getPlayer().setNameTag(event.getPlayer().getName() + "\n" +TextFormat.BLUE +OS+" "+ TextFormat.GREEN +ping);
        }
    }
}
